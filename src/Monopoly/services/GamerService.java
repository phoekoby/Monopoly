package Monopoly.services;

import Monopoly.models.Bank;
import Monopoly.models.cells.BlockOfProperties;
import Monopoly.models.cells.Cell;
import Monopoly.models.Game;
import Monopoly.models.Gamer;
import Monopoly.models.cells.CellType;

import java.util.*;

public class GamerService {
    private final ChanceService chanceService = new ChanceService();
    private final CellServices cellServices = new CellServices();
    private final BankService bankService = new BankService();
    private final StreetService streetService = new StreetService();
    private final UtilityAndStationService utilityAndStationService = new UtilityAndStationService();

private final GameService gameService ;

    public GamerService(GameService gameService) {
        this.gameService = gameService;
    }

    /*
              Создаем список игроков и заполняем их начальными значениями
             */
    public LinkedList<Gamer> createGamerList(int count, Game game) throws Exception {
        if (count <= 1 || count > 6) {
            throw new Exception("Неккоректно число игроков");
        }
        Map<Gamer, Map<BlockOfProperties, Set<Cell>>> gamersCards = new HashMap<>();
        Map<Gamer, Cell> location = new HashMap<>();
        LinkedList<Gamer> gamers = new LinkedList<>();
        Queue<Gamer> queuegamers = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            Gamer gamer = new Gamer("Player " + (i + 1), 1500);
            game.getCanGamerDoStep().put(gamer, true);
            location.put(gamer, game.getStart());
            queuegamers.offer(gamer);
            gamers.add(gamer);
            gamersCards.put(gamer, new HashMap<>());
        }

        game.setPlayerMoves(queuegamers);

        game.setGamersLocation(location);
        game.setPlayersAndHisCards(gamersCards);

        return gamers;
    }

    /* Бросание кубиков */
    private int throwCubes() {
        int max_for_throw_cubes = 12;
        int min_for_throw_cubes = 2;
        max_for_throw_cubes -= min_for_throw_cubes;
        return (int) ((Math.random() * ++max_for_throw_cubes) + min_for_throw_cubes);
    }

    /* Перерасчет денег */
    public void recalculationMoney(Game game, Gamer gamer, int money) {
        if (gamer.getMoney() + money < 0) {
            recalculationMoney(game,gamer,gamer.getMoney());
            System.out.println(gamer.getName() + " отдает последние " + gamer.getMoney() + " и Вылетает из игры !!!!!!!!!!!!!!!!!!!!");
            gameOver(game, gamer);
            return;
        }
        gamer.setMoney(gamer.getMoney() + money);
    }

    /* Плата другому игроку */
    public void payToOtherGamer(Game game, Gamer from, Gamer to, int howMuch) {
        if (from.getMoney() - howMuch < 0) {
            recalculationMoney(game,to,from.getMoney());


            System.out.println(from.getName() + " отдает последние " + from.getMoney() + " игроку " + to.getName() +  " и  Вылетает из игры !!!!!!!!!!!!!!!!!!!!");
            recalculationMoney(game,from, -from.getMoney());
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
        for(BlockOfProperties b: game.getPlayersAndHisCards().get(gamer).keySet()){
            for(Cell c : game.getPlayersAndHisCards().get(gamer).get(b)){
                game.getHouses().remove(c);
                game.getHouses().put(c,0);
                game.getHowManyToPayRenta().remove(c);
                game.getHowManyToPayRenta().put(c,c.getPrice());
                game.getCardsAndOwners().remove(c);
                gameService.getBankService().getBank().getAllCardInBank().get(b).add(c);
            }
           //gameService.getBankService().getBank().getAllCardInBank().put(b,game.getPlayersAndHisCards().get(gamer).get(b));


        }

        game.getPlayersAndHisCards().remove(gamer);

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
        checkWhichCardAndWhatToDo(game, gamer, current);
        System.out.println("Баланс " + gamer.getMoney());
        System.out.println("\n\n\n\n");
    }

    /*
    Идем на какое-то поле goTo
     */
    public void gamerGoTo(Game game, Gamer gamer, Cell goTo) throws Exception {
        game.getGamersLocation().remove(gamer);
        game.getGamersLocation().put(gamer, goTo);
        if (goTo == game.getJail()) {
            makeNextSkipOrNotSkip(game, gamer);
        } else {
            checkWhichCardAndWhatToDo(game, gamer, goTo);
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
    public void checkWhichCardAndWhatToDo(Game game, Gamer gamer, Cell current) throws Exception {
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
                recalculationMoney(game, gamer, current.getPrice());

            }
            case CHANCE -> {
                System.out.println("Игрок " + gamer.getName() + " попадает на " + current.getName());
                chanceService.gamerCameOnChance(game, gamer, this);

            }
            case STREET, STATION, UTILITY -> {
                System.out.println("Игрок " + gamer.getName() + " попал на " + current.getName());
                doingWhenOnProperty(gamer, current, game);

            }
            default -> {
                System.out.println(current.getName());
            }
        }
    }

    /*
    Что делаем, если попали ка карточку имущества
     */
    public void doingWhenOnProperty(Gamer gamer, Cell cell, Game game) throws Exception {
        if (gameService.getCellService().canBuy(game, cell)) {
            if (needIThisCard(cell, gamer, game)) {
                buy(gamer, game, cell, 0);
            } else {
                gameService.getBankService().auction(cell, game, this);
            }
        } else {
            if (gamer != game.getCardsAndOwners().get(cell)) {
                payToOtherGamer(game, gamer, game.getCardsAndOwners().get(cell), game.getHowManyToPayRenta().get(cell));
            }

        }
    }

    /*
    Есть ли полный набор карточек одного блока(для постройки домов)
     */
    public boolean haveFullStack(BlockOfProperties blockOfProperties, Gamer gamer, Game game) {
        if (game.getPlayersAndHisCards().get(gamer).get(blockOfProperties).size() == blockOfProperties.getI()) {
            return true;
        }
        return false;
    }

    /*
    Покупаем дома во время хода
     */
    public void buyHouses(Game game, Gamer gamer) {

        for (BlockOfProperties b : game.getPlayersAndHisCards().get(gamer).keySet()) {
            for (Cell cell : game.getPlayersAndHisCards().get(gamer).get(b)) {
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
        cellServices.addToPlayersAndHisCards_ForGame(gamer, game, cell);
        if (haveFullStack(cell.getBlockOfProperties(), gamer, game) && cell.getCellType() == CellType.STREET) {
            streetService.recalculationRentaIfGetFullStack(game, cell.getBlockOfProperties());
        } else if (cell.getCellType() == CellType.STATION || cell.getCellType() == CellType.UTILITY) {
            utilityAndStationService.recalculationStationAndUtility(game, cell.getBlockOfProperties(), gamer);
        }
        System.out.println("Игрок " + gamer.getName() + " покупает " + cell.getName() + " за " + (price==0?cell.getPrice():price));
    }

    /*
    Делает ставку на аукционе
     */
    public int doBit(Gamer gamer, int aucion, Cell cell, Game game) {
        if (gamer.getMoney() - aucion < 500 || !needIThisCard(cell, gamer, game)) {
            return 0;
        }
        int result = (int) ((gamer.getMoney() - aucion) * 0.1);
        return result;
    }

}
