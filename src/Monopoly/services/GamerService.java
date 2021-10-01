package Monopoly.services;

import Monopoly.models.cells.BlockOfProperties;
import Monopoly.models.cells.Cell;
import Monopoly.models.Game;
import Monopoly.models.Gamer;

import java.util.*;

public class GamerService {
    private final ChanceService chanceService = new ChanceService();
    private final CellServices cellServices = new CellServices();
    private final BankService bankService = new BankService();


    /**
     * Создаем список игроков и заполняем их начальными значениями
     **/
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
            game.getCanGamerDoStep().put(gamer,true);
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

    private int throwCubes() {
        int max_for_throw_cubes = 12;
        int min_for_throw_cubes = 2;
        max_for_throw_cubes -= min_for_throw_cubes;
        return (int) ((Math.random() * ++max_for_throw_cubes) + min_for_throw_cubes);
    }

    public void recalculationMoney(Game game, Gamer gamer, int money) {
        if(gamer.getMoney()+money<0){
            gameOver(game, gamer);
            System.out.println("Проиграл");
            return;
        }
        gamer.setMoney(gamer.getMoney() + money);
    }

    public Cell step(Game game, Gamer gamer, int howMany) {
        Cell cell = game.getGamersLocation().remove(gamer);
        for (int i = 0; i < howMany; i++) {

            cell = cell.getNextCell();
            if(cell==game.getStart()){
                recalculationMoney(game,gamer, 200);
                System.out.println("Игрок " + gamer.getName()+ " Прошел поле\"Старт\" и получает 200 долларов");
            }
        }
        game.getGamersLocation().put(gamer, cell);
        return cell;
    }
    private void gameOver(Game game, Gamer gamer){
        Gamer gamer1 = game.getPlayerMoves().poll();
        while (gamer1!=gamer){
            game.getPlayerMoves().offer(gamer1);
            gamer1 = game.getPlayerMoves().poll();
        }
    }
    public void gamerGoTo(Game game, Gamer gamer, Cell goTo) throws Exception {
        game.getGamersLocation().remove(gamer);
        game.getGamersLocation().put(gamer, goTo);
        if(goTo== game.getJail()){
            makeNextSkipOrNotSkip(game,gamer);
        }else{
            checkWhichCardAndWhatToDo(game,gamer,goTo);
        }
    }
    public void checkWhichCardAndWhatToDo(Game game,Gamer gamer, Cell current) throws Exception{
        switch (current.getCellType()) {

            case GO_TO_JAIL -> {
                System.out.println("Игрок " + gamer.getName()+" отправляется в тюрьму");
                gamerGoTo(game, gamer, game.getJail());

            }
            case TAX -> {
                System.out.println("Игрок " + gamer.getName() + " попал на " + current.getName() + " и заплатил " + current.getPrice());
                recalculationMoney(game,gamer, -current.getPrice());
                System.out.println("Денег: " + gamer.getMoney());
                //makeNextSkipOrNotSkip(game,gamer);

            }
            case START -> {
                System.out.println("Игрок " + gamer.getName() + " попал на " + current.getName() + " и получил 200 долларов");
                recalculationMoney(game,gamer, current.getPrice());
                System.out.println("Денег: " + gamer.getMoney());

            }
            case CHANCE -> {
                System.out.println("Игрок " + gamer.getName() + " попадает на " + current.getName());
                chanceService.gamerCameOnChance(game, gamer, this);

            }
            case STREET -> {
                System.out.println("Игрок " + gamer.getName() + " попал на " + current.getName());
                doingWhenOnStreet(gamer,current,game);

            }
            default -> {
                System.out.println(current.getName());
            }
        }
    }

    public void doingWhenOnStreet(Gamer gamer, Cell cell, Game game) throws Exception{
        if(cellServices.canBuy(game,cell)){
            if(needIThisCard(cell, gamer,game)){
                buy(gamer,game,cell);
            }else{
                bankService.auction(cell,game,this);
            }
        }else{
            payToOtherGamer(game,gamer,game.getCardsAndOwners().get(cell),150);
        }
    }
    public void payToOtherGamer(Game game,Gamer from, Gamer to, int howMuch){
        if(from.getMoney()-howMuch<0){
            gameOver(game,from);
            System.out.println("Проиграл");
            return;
        }
        System.out.println(from.getName()+" платит " + to.getName() + " " + howMuch);
        recalculationMoney(game,from,-howMuch);
        recalculationMoney(game,to,howMuch);
    }
    public void doSomething(Gamer gamer, Game game) throws Exception{
        int cubesResult = throwCubes();
        System.out.println("Игрок " + gamer.getName() + " бросает кости и получает число " + cubesResult);
        Cell current = step(game, gamer, cubesResult);
        checkWhichCardAndWhatToDo(game,gamer,current);
    }

    public void makeNextSkipOrNotSkip(Game game, Gamer gamer){
        boolean bool = !game.getCanGamerDoStep().remove(gamer);
        game.getCanGamerDoStep().put(gamer,bool);
    }
    public int doBit(Gamer gamer, int aucion, Cell cell, Game game){
        if(gamer.getMoney()-aucion > gamer.getMoney() * 0.2 || !needIThisCard(cell,gamer,game) ){
            return 0;
        }
       int result = (int)((gamer.getMoney()-aucion)*0.1);
        return result>=10?result:0;
    }
    public boolean checkGamersCards(Gamer gamer, Cell cell, Game game){
        int i = game.getAllCards().get(cell.getBlockOfProperties()).size();
        for(;i>0;i--){
            if(game.getCardsAndOwners().containsKey(game.getAllCards().get(cell.getBlockOfProperties()).get(i-1)) &&
                    game.getCardsAndOwners().get(game.getAllCards().get(cell.getBlockOfProperties()).get(i-1))!=gamer){
                return false;
            }
        }
        return true;
    }
    public boolean needIThisCard(Cell cell, Gamer gamer, Game game){
        return checkGamersCards(gamer, cell, game) && gamer.getMoney() - cell.getPrice() > 500
                && cell.getPrice() < 0.3 * gamer.getMoney();
    }
    public void buy(Gamer gamer, Game game, Cell cell) throws Exception {
        if (!cellServices.canBuy(game, cell)) {
            throw new Exception("Нельзя купить карту, заплатите ипотеку");
        }
        gamer.setMoney(gamer.getMoney() - cell.getPrice());
        game.getBank().getAllCardInBank().get(cell.getBlockOfProperties()).remove(cell);
        cellServices.addToPlayersAndHisCards_ForGame(gamer, game, cell);
        System.out.println("Игрок " + gamer.getName() + " покупает " + cell.getName() + " за " + cell.getPrice() + "    Баланас: " + gamer.getMoney());
    }
    }
