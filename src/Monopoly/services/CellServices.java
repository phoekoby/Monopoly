package Monopoly.services;

import Monopoly.models.Cells.Chance;
import Monopoly.models.Cells.Property;
import Monopoly.models.Game;
import Monopoly.models.Gamer;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class CellServices {

   public CellServices(){
       Queue<Chance> chances = new LinkedList<>();
   }
   public boolean canBuy(Game game, Property card){
       System.out.println(game.getBank().getAllCardInBank().get(card.getBlockOfStreets()).toString());
       if(game.getBank().getAllCardInBank().get(card.getBlockOfStreets()).contains(card)){
           System.out.println("true");
           return true;
       }else{
           return false;
       }
   }
   public void buy(Gamer gamer, Game game, Property property) throws Exception{
       if(!canBuy(game,property)){
           throw new Exception("Нельзя купить карту, заплатите ипотеку");
       }
       gamer.setMoney(gamer.getMoney()- property.getPrice());
       game.getBank().getAllCardInBank().get(property.getBlockOfStreets()).remove(property);
       gamer.getOwn().add(property);
       addToOwnGamersCard_ForGamer(gamer,property);
       addToPlayersAndHisCards_ForGame(gamer,game,property);
   }
/** Метод добавляет карточку в словарь карточек класса Gamer **/
    private void addToOwnGamersCard_ForGamer(Gamer gamer, Property property){
        if(gamer.getOwnCards().containsKey(property.getBlockOfStreets())){
            gamer.getOwnCards().get(property.getBlockOfStreets()).add(property);
        }else{
            gamer.getOwnCards().put(property.getBlockOfStreets(),new HashSet<>());
            gamer.getOwnCards().get(property.getBlockOfStreets()).add(property);
        }
    }

    /** Метод добавляет карточку в словарь карточек класса Game **/
    private void addToPlayersAndHisCards_ForGame(Gamer gamer,Game game, Property property){
        if(game.getPlayersAndHisCards().get(gamer).containsKey(property.getBlockOfStreets())){
            game.getPlayersAndHisCards().get(gamer).get(property.getBlockOfStreets()).add(property);
        }else{
            game.getPlayersAndHisCards().get(gamer).put(property.getBlockOfStreets(),new HashSet<>());
            game.getPlayersAndHisCards().get(gamer).get(property.getBlockOfStreets()).add(property);
        }
        game.getCardsAndOwners().put(property,gamer);
    }
}
