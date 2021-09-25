package Monopoly.services;

import Monopoly.models.cells.BlockOfProperties;
import Monopoly.models.cells.Cell;
import Monopoly.models.Game;
import Monopoly.models.Gamer;

import java.util.*;

public class GamerService {

    /** Создаем список игроков и заполняем их начальными значениями**/
    public LinkedList<Gamer> createGamerList(int count, Game game) throws Exception{
        if(count<=1 || count > 6){
            throw new Exception("Неккоректно число игроков");
        }
        Map<Gamer, Map<BlockOfProperties,Set<Cell>>> gamersCards = new HashMap<>();
        Map<Gamer, Cell> location = new HashMap<>();
        LinkedList<Gamer> gamers = new LinkedList<>();
        for(int i = 0 ;i<count;i++){
            Gamer gamer = new Gamer("Player " + (i+1) , 1500);
            location.put(gamer,game.getStart());
            gamers.add(gamer);
            gamersCards.put(gamer,new HashMap<>());
        }
        game.setPlayersAndHisCards(gamersCards);
        return gamers;
    }
//
//    public Cell step(Gamer gamer, int howMany){
//        if(gamer.isCanStep()){
//            for(int i = 0; i < howMany;i++){
//               gamer.setCurrent(gamer.getCurrent().getNextCell());
//            }
//        }else{
//            gamer.setCanStep(true);
//        }
//
//        return gamer.getCurrent();
//    }
//





}
