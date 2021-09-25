package Monopoly.models;

import Monopoly.models.cells.BlockOfProperties;
import Monopoly.models.cells.Cell;


import java.util.Map;
import java.util.Set;

public class Bank {
    private Map<BlockOfProperties, Set<Cell>> allCardInBank;


    public Bank(Map<BlockOfProperties,Set<Cell>> allCardInBank) {
        this.allCardInBank = allCardInBank;
    }


    public Map<BlockOfProperties, Set<Cell>> getAllCardInBank() {
        return allCardInBank;
    }



}
