package Monopoly.models;

import Monopoly.models.Cells.BlockOfStreets;
import Monopoly.models.Cells.Cell;
import Monopoly.models.Cells.Property;

import java.util.Map;
import java.util.Set;

public class Bank {
    private Map<BlockOfStreets, Set<Property>> allCardInBank;


    public Bank(Map<BlockOfStreets,Set<Property>> allCardInBank) {
        this.allCardInBank = allCardInBank;
    }


    public Map<BlockOfStreets, Set<Property>> getAllCardInBank() {
        return allCardInBank;
    }



}
