package Monopoly.services;

import Monopoly.models.Bank;
import Monopoly.models.Cells.*;
import Monopoly.models.Game;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BankService {

    public Bank createBank(Game game){

        Cell start = game.getMap();
        Cell card = game.getMap().getNextCell();
        Map<BlockOfStreets, Set<Property>> allcards = new HashMap<>();
        while(card!=start){
                if(card instanceof StreetCell){
                    if(allcards.containsKey(((StreetCell) card).getBlockOfStreets())){
                        allcards.get(((StreetCell) card).getBlockOfStreets()).add((Property) card);
                    }else{
                        allcards.put(((StreetCell) card).getBlockOfStreets(),new HashSet<>());
                        allcards.get(((StreetCell) card).getBlockOfStreets()).add((Property) card);
                    }
                }else if(card instanceof StationCell){
                    if(allcards.containsKey(BlockOfStreets.STATION)){
                        allcards.get(BlockOfStreets.STATION).add((Property) card);
                    }else{
                        allcards.put(BlockOfStreets.STATION,new HashSet<>());
                        allcards.get(BlockOfStreets.STATION).add((Property) card);
                    }
                }else if(card instanceof UtilityCell){
                    if(allcards.containsKey(BlockOfStreets.UTILITY)){
                        allcards.get(BlockOfStreets.UTILITY).add((Property) card);
                    }else{
                        allcards.put(BlockOfStreets.UTILITY,new HashSet<>());
                        allcards.get(BlockOfStreets.UTILITY).add((Property) card);
                    }
                }

            card=card.getNextCell();
        }

        return new Bank(allcards);
    }
}
