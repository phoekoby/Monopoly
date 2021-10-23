package ru.vsu.kovalenko_v_yu.Monopoly.models;

import ru.vsu.kovalenko_v_yu.Monopoly.models.cells.BlockOfProperties;
import ru.vsu.kovalenko_v_yu.Monopoly.models.cells.Cell;


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
