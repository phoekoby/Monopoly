package Monopoly.services;

import Monopoly.models.Cells.BlockOfStreets;
import Monopoly.models.Cells.Cell;
import Monopoly.models.Cells.Property;
import Monopoly.models.Game;
import Monopoly.models.Gamer;

import java.util.*;

public class GamerService {

    /** Создаем список игроков и заполняем их начальными значениями**/
    public LinkedList<Gamer> createGamerList(int count, Game game) throws Exception{
        if(count<=1 || count > 6){
            throw new Exception("Неккоректно число игроков");
        }
        Map<Gamer, Map<BlockOfStreets,Set<Property>>> gamersCards = new HashMap<>();
        LinkedList<Gamer> gamers = new LinkedList<>();
        for(int i = 0 ;i<count;i++){
            Gamer gamer = new Gamer("Player " + (i+1) , 6000, game.getMap(),new HashMap<>(),new HashSet<>());
            gamers.add(gamer);
            gamersCards.put(gamer,new HashMap<>());
        }
        game.setPlayersAndHisCards(gamersCards);
        return gamers;
    }
    public Cell step(Gamer gamer,int howMany){
        if(gamer.isCanStep()){
            for(int i = 0; i < howMany;i++){
               gamer.setCurrent(gamer.getCurrent().getNextCell());
            }
        }else{
            gamer.setCanStep(true);
        }

        return gamer.getCurrent();
    }






}
