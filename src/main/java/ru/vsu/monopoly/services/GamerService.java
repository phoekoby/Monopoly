package ru.vsu.monopoly.services;

import ru.vsu.monopoly.models.cells.PropertiesType;
import ru.vsu.monopoly.models.cells.Cell;
import ru.vsu.monopoly.models.Game;
import ru.vsu.monopoly.models.Gamer;
import ru.vsu.monopoly.models.cells.CellType;

import java.util.*;

public class GamerService {
    private final BankService bankService = new BankService();
    private final ChanceService chanceService = new ChanceService();
    private final CellServices cellServices = new CellServices();
    private final StreetService streetService = new StreetService();
    private final UtilityAndStationService utilityAndStationService = new UtilityAndStationService();

    /*
     Создаем список игроков и заполняем их начальными значениями
    */
    public void createGamerList(int count, Game game) throws Exception {
        if (count <= 1 || count > 6) {
            throw new Exception("Неккоректно число игроков");
        }
        Map<Gamer, Map<PropertiesType, Set<Cell>>> gamersCards = new HashMap<>();
        Map<Gamer, Cell> location = new HashMap<>();
        //LinkedList<Gamer> gamers = new LinkedList<>();
        Queue<Gamer> queuegamers = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            Gamer gamer = new Gamer("Player " + (i + 1), 1500);
            game.getCanGamerDoStep().put(gamer, true);
            location.put(gamer, game.getStart());
            queuegamers.offer(gamer);
            gamersCards.put(gamer, new HashMap<>());
        }

        game.setPlayerMoves(queuegamers);
        game.setGamersLocation(location);
        game.setPlayersAndHisCards(gamersCards);
    }

    /*
  Начинается ход игрока gamer
   */
    public void doSomething(Gamer gamer, Game game) throws Exception {
        System.out.println("Ход игрока " + gamer.getName() + " Баланс:" + gamer.getMoney());
        buyHouses(game, gamer);
        int cubesResult = throwCubes();
        System.out.println("Игрок " + gamer.getName() + " бросает кости и получает число " + cubesResult);
        Cell current = step(game, gamer, cubesResult);
        checkWhichCardAndWhatToDo(game, gamer, current, this);
        System.out.println("Баланс " + gamer.getMoney());
        System.out.println("\n\n");
    }

    /* Бросание кубиков */
    private int throwCubes() {
        int maxForThrowCubes = 12;
        int minForThrowCubes = 2;
        maxForThrowCubes -= minForThrowCubes;
        return (int) ((Math.random() * ++maxForThrowCubes) + minForThrowCubes);
    }

    /*
Идем на какое-то поле goTo
 */
    public void gamerGoTo(Game game, Gamer gamer, Cell goTo) throws Exception {
        game.getGamersLocation().put(gamer, goTo);
        if (goTo == game.getJail()) {
            makeNextSkipOrNotSkip(game, gamer);
        } else {
            checkWhichCardAndWhatToDo(game, gamer, goTo, this);
        }
    }

    /*
    Делаем ход на howMany шагов вперед
     */
    public Cell step(Game game, Gamer gamer, int howMany) {
        Cell cell = game.getGamersLocation().remove(gamer);
        for (int i = 0; i < howMany; i++) {

            cell = cell.getNextCell();
            if (cell == game.getStart()) {
                recalculationMoney(game, gamer, 200);
                System.out.println("Игрок " + gamer.getName() + " Прошел поле\"Старт\" и получает 200 долларов");
            }
        }
        game.getGamersLocation().put(gamer, cell);
        return cell;
    }

    /*
    Проверяем на какую карту попали и что будем делать
     */
    public void checkWhichCardAndWhatToDo(Game game, Gamer gamer, Cell current, GamerService gamerService) throws Exception {
        switch (current.getCellType()) {

            case GO_TO_JAIL -> {
                System.out.println("Игрок " + gamer.getName() + " отправляется в тюрьму");
                gamerGoTo(game, gamer, game.getJail());

            }
            case TAX -> {
                System.out.println("Игрок " + gamer.getName() + " попал на " + current.getName() + " и заплатил " + current.getPrice());
                recalculationMoney(game, gamer, -current.getPrice());
                //makeNextSkipOrNotSkip(game,gamer);

            }
            case START -> {
                System.out.println("Игрок " + gamer.getName() + " попал на " + current.getName() + " и получил 200 долларов");
                int MONEY_FOR_START = 200;
                recalculationMoney(game, gamer, MONEY_FOR_START);

            }
            case CHANCE -> {
                System.out.println("Игрок " + gamer.getName() + " попадает на " + current.getName());
                chanceService.gamerCameOnChance(game, gamer, gamerService);
            }
            case STREET, STATION, UTILITY -> {
                System.out.println("Игрок " + gamer.getName() + " попал на " + current.getName());
                doingWhenOnProperty(gamer, current, game, gamerService);

            }
            default -> System.out.println(current.getName());
        }
    }

    /*
    Что делаем, если попали ка карточку имущества
     */
    public void doingWhenOnProperty(Gamer gamer, Cell cell, Game game, GamerService gamerService) throws Exception {
        if (cellServices.canBuy(game, cell)) {
            if (needIThisCard(cell, gamer, game)) {
                buy(gamer, game, cell, 0);
            } else {
                bankService.auction(cell, game, gamerService);
            }
        } else {
            if (gamer != game.getCardsAndOwners().get(cell)) {
                payToOtherGamer(game, gamer, game.getCardsAndOwners().get(cell), game.getHowManyToPayRenta().get(cell));
            }

        }
    }

    /* Перерасчет денег */
    public void recalculationMoney(Game game, Gamer gamer, int money) {
        if (gamer.getMoney() + money < 0 && doWhenNotEnoughMoney(game, gamer, gamer.getMoney() + money)) {
            System.out.println(gamer.getName() + " отдает последние " + gamer.getMoney() + " и Вылетает из игры !!!!!!!!!!!!!!!!!!!!");
            recalculationMoney(game, gamer, -gamer.getMoney());
            gameOver(game, gamer);
            return;
        }
        gamer.setMoney(gamer.getMoney() + money);
    }

    public boolean doWhenNotEnoughMoney(Game game, Gamer gamer, int howMuch) {
        Map<PropertiesType, Set<Cell>> mapOfProperties = game.getPlayersAndHisCards().get(gamer);
        while (howMuch > 0 && !mapOfProperties.isEmpty()) {
            Optional<PropertiesType> propertiesType = mapOfProperties.keySet().stream()
                    .findAny();
            Optional<Cell> cell = mapOfProperties.get(propertiesType.get()).stream()
                    .findAny();
            if (cell.isPresent()) {
                if (cell.get().getCellType() != CellType.STREET) {
                    recalculationMoney(game, gamer, cell.get().getPrice() / 2);
                    howMuch -= cell.get().getPrice() / 2;
                } else {
                    recalculationMoney(game, gamer, cell.get().getPrice() / 2 +
                            (game.getHowManyYouNeedToBuildHouse().get(propertiesType.get())) / 2 *
                                    game.getHouses().get(cell.get()));
                    howMuch -= cell.get().getPrice() / 2 + (game.getHowManyYouNeedToBuildHouse().get(propertiesType.get())) / 2 * game.getHouses().get(cell.get());
                }
                game.getHowManyToPayRenta().put(cell.get(), cell.get().getPrice());
                game.getHouses().put(cell.get(), 0);
                game.getCardsAndOwners().remove(cell.get());
                game.getBank().getAllCardInBank().get(propertiesType.get()).add(cell.get());
                game.getPlayersAndHisCards().get(gamer).get(propertiesType.get()).remove(cell.get());
                if (game.getPlayersAndHisCards().get(gamer).get(propertiesType.get()).isEmpty()) {
                    game.getPlayersAndHisCards().get(gamer).remove(propertiesType.get());
                }

                System.out.println(gamer.getName() + " продает " + cell.get().getName());
            }
        }
        return howMuch > 0;

    }

    /* Плата другому игроку */
    public void payToOtherGamer(Game game, Gamer from, Gamer to, int howMuch) {
        if (from.getMoney() - howMuch < 0 && doWhenNotEnoughMoney(game, from, howMuch - from.getMoney())) {
            recalculationMoney(game, to, from.getMoney());
            System.out.println(from.getName() + " отдает последние " + from.getMoney() + " игроку " + to.getName() + " и  Вылетает из игры !!!!!!!!!!!!!!!!!!!!");
            recalculationMoney(game, from, -from.getMoney());
            gameOver(game, from);
            return;
        }
        System.out.println(from.getName() + " платит " + to.getName() + " " + howMuch);
        recalculationMoney(game, from, -howMuch);
        recalculationMoney(game, to, howMuch);
    }

    /* Удаление игрока из очереди игры */
    private void gameOver(Game game, Gamer gamer) {
        Gamer gamer1 = game.getPlayerMoves().poll();
        while (gamer1 != gamer) {
            game.getPlayerMoves().offer(gamer1);
            gamer1 = game.getPlayerMoves().poll();
        }
        game.getGamersLocation().remove(gamer1);
        game.getPlayersAndHisCards().remove(gamer);
    }

    /*
    Есть ли полный набор карточек одного блока(для постройки домов)
     */
    public boolean haveFullStack(PropertiesType propertiesType, Gamer gamer, Game game) {
        return game.getPlayersAndHisCards().get(gamer).get(propertiesType).size() == propertiesType.getI();
    }

    /*
    Покупаем дома во время хода
     */
    public void buyHouses(Game game, Gamer gamer) {
        Set<PropertiesType> setOfProperties = game.getPlayersAndHisCards().get(gamer).keySet();
        for (PropertiesType b : setOfProperties) {
            Set<Cell> setOfCells = game.getPlayersAndHisCards().get(gamer).get(b);
            for (Cell cell : setOfCells) {
                if (streetService.canBuildHouse(game, gamer, cell) && (gamer.getMoney() - game.getHowManyYouNeedToBuildHouse().get(b) > 500)) {
                    System.out.println("                        " + gamer.getName() + "покупает дом " + " на " + cell.getName());
                    streetService.buildHouse(game, cell);
                }
            }
        }
    }

    /*
    Устанавливаем пропуск хода игроку
     */
    public void makeNextSkipOrNotSkip(Game game, Gamer gamer) {
        boolean bool = !game.getCanGamerDoStep().remove(gamer);
        game.getCanGamerDoStep().put(gamer, bool);
    }

    /*
    Проверяет на наличие карточек блока cell, если есть - не покупает
     */
    public boolean checkGamersCards(Gamer gamer, Cell cell, Game game) {
        int i = game.getAllCards().get(cell.getBlockOfProperties()).size();
        for (; i > 0; i--) {
            if (game.getCardsAndOwners().containsKey(game.getAllCards().get(cell.getBlockOfProperties()).get(i - 1)) &&
                    game.getCardsAndOwners().get(game.getAllCards().get(cell.getBlockOfProperties()).get(i - 1)) != gamer) {
                return false;
            }
        }
        return true;
    }

    /*
    Проверяет нужна ли эта карточка
     */
    public boolean needIThisCard(Cell cell, Gamer gamer, Game game) {
        return checkGamersCards(gamer, cell, game) && gamer.getMoney() - cell.getPrice() > 500;
    }

    /*
    Покупает карточку
     */
    public void buy(Gamer gamer, Game game, Cell cell, int price) throws Exception {
        if (!cellServices.canBuy(game, cell)) {
            throw new Exception("Нельзя купить карту, заплатите ипотеку");
        }
        if (price != 0) {
            gamer.setMoney(gamer.getMoney() - price);
        } else {
            gamer.setMoney(gamer.getMoney() - cell.getPrice());
        }
        game.getBank().getAllCardInBank().get(cell.getBlockOfProperties()).remove(cell);
        cellServices.addToPlayersAndHisCardsForGame(gamer, game, cell);
        if (haveFullStack(cell.getBlockOfProperties(), gamer, game) && cell.getCellType() == CellType.STREET) {
            streetService.recalculationRentaIfGetFullStack(game, cell.getBlockOfProperties());
        } else if (cell.getCellType() == CellType.STATION || cell.getCellType() == CellType.UTILITY) {
            utilityAndStationService.recalculationStationAndUtility(game, cell.getBlockOfProperties(), gamer);
        }
        System.out.println("Игрок " + gamer.getName() + " покупает " + cell.getName() + " за " + (price == 0 ? cell.getPrice() : price));
    }

    /*
    Делает ставку на аукционе
     */
    public int doBit(Gamer gamer, int aucion, Cell cell, Game game) {
        if (gamer.getMoney() - aucion < 500 || !needIThisCard(cell, gamer, game)) {
            return 0;
        }
        return aucion + (int) (aucion * 1.5);
    }

}
