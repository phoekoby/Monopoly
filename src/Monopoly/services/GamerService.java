package Monopoly.services;

import Monopoly.models.cells.BlockOfProperties;
import Monopoly.models.cells.Cell;
import Monopoly.models.Game;
import Monopoly.models.Gamer;

import java.util.*;

public class GamerService {
    private final ChanceService chanceService = new ChanceService();

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

    public void recalculationMoney(Gamer gamer, int money) {
        gamer.setMoney(gamer.getMoney() + money);
    }

    public Cell step(Game game, Gamer gamer, int howMany) {
        Cell cell = game.getGamersLocation().remove(gamer);
        for (int i = 0; i < howMany; i++) {

            cell = cell.getNextCell();
            if(cell==game.getStart()){
                recalculationMoney(gamer, 200);
                System.out.println("Игрок " + gamer.getName()+ " Прошел поле\"Старт\" и получает 200 долларов");
            }
        }
        game.getGamersLocation().put(gamer, cell);
        return cell;
    }

    public void gamerGoTo(Game game, Gamer gamer, Cell goTo) {
        game.getGamersLocation().remove(gamer);
        game.getGamersLocation().put(gamer, goTo);
        if(goTo== game.getJail()){
            makeNextSkipOrNotSkip(game,gamer);
        }else{
            checkWhichCardAndWhatToDo(game,gamer,goTo);
        }
    }
    public void checkWhichCardAndWhatToDo(Game game,Gamer gamer, Cell current){
        switch (current.getCellType()) {

            case GO_TO_JAIL -> {
                System.out.println("Игрок " + gamer.getName()+" отправляется в тюрьму");
                gamerGoTo(game, gamer, game.getJail());

            }
            case TAX -> {
                System.out.println("Игрок " + gamer.getName() + " попал на " + current.getName() + " и заплатил " + current.getPrice());
                recalculationMoney(gamer, -current.getPrice());
                System.out.println("Денег: " + gamer.getMoney());
                makeNextSkipOrNotSkip(game,gamer);

            }
            case START -> {
                System.out.println("Игрок " + gamer.getName() + " попал на " + current.getName() + " и получил 200 долларов");
                recalculationMoney(gamer, current.getPrice());
                System.out.println("Денег: " + gamer.getMoney());

            }
            case CHANCE -> {
                System.out.println("Игрок " + gamer.getName() + " попадает на " + current.getName());
                chanceService.gamerCameOnChance(game, gamer, this);

            }
            case STREET -> {
                System.out.println("Игрок " + gamer.getName() + " попал на " + current.getName());

            }
            default -> {
                System.out.println(current.getName());
            }
        }
    }


    public void doSomething(Gamer gamer, Game game) {

        int cubesResult = throwCubes();
        System.out.println("Игрок " + gamer.getName() + " бросает кости и получает число " + cubesResult);
        Cell current = step(game, gamer, cubesResult);
        checkWhichCardAndWhatToDo(game,gamer,current);


    }

    public void makeNextSkipOrNotSkip(Game game, Gamer gamer){
        boolean bool = !game.getCanGamerDoStep().remove(gamer);
        game.getCanGamerDoStep().put(gamer,bool);
    }
}
