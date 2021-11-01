package ru.vsu.monopoly.models;

import ru.vsu.monopoly.models.cells.PropertiesType;
import ru.vsu.monopoly.models.cells.Cell;


import java.util.Map;
import java.util.Set;

public class Bank {
    private final Map<PropertiesType, Set<Cell>> allCardInBank;

    public Bank(Map<PropertiesType, Set<Cell>> allCardInBank) {
        this.allCardInBank = allCardInBank;
    }

    public Map<PropertiesType, Set<Cell>> getAllCardInBank() {
        return allCardInBank;
    }
}
