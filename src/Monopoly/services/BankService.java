package Monopoly.services;

import Monopoly.models.Bank;
import Monopoly.models.cells.*;
import Monopoly.models.Game;


import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class BankService {

    public Bank createBank(Game game){


        Cell cell = game.getStart().getNextCell();
        Map<BlockOfProperties,Set<Cell>> allcards = new HashMap<>();
        while (cell!= game.getStart()){
            if(cell.getCellType().equals(CellType.STREET)||cell.getCellType().equals(CellType.STATION)||cell.getCellType().equals(CellType.UTILITY)){
                if(allcards.containsKey(cell.getBlockOfProperties())){
                    allcards.get(cell.getBlockOfProperties()).add(cell);
                }else{
                    allcards.put(cell.getBlockOfProperties(),new HashSet<>());
                    allcards.get(cell.getBlockOfProperties()).add(cell);
                }
            }
            cell=cell.getNextCell();
        }



        return new Bank(allcards);
    }
}
