package ru.vsu.monopoly.services;

import ru.vsu.monopoly.models.cells.PropertiesType;
import ru.vsu.monopoly.models.cells.Cell;

import ru.vsu.monopoly.models.Game;
import ru.vsu.monopoly.models.Gamer;
import ru.vsu.monopoly.models.cells.CellType;

import java.util.*;

public class CellServices {
    /* можно ли купить карточку?*/
    public boolean canBuy(Game game, Cell card) throws Exception {
        if (card.getCellType() != CellType.STREET && card.getCellType() != CellType.STATION && card.getCellType() != CellType.UTILITY) {
            throw new Exception("Эта карточка не является имуществом");
        }
        Map<PropertiesType, Set<Cell>> allCardsMap = game.getBank().getAllCardInBank();
        Set<Cell> cells = allCardsMap.get(card.getBlockOfProperties());
        if (cells != null) {
            return cells.contains(card);
        }
        return false;
    }

    /* Добавление купленной карточки в словарь имющихся у игроков карт*/
    public void addToPlayersAndHisCardsForGame(Gamer gamer, Game game, Cell cell) {
        Map<PropertiesType, Set<Cell>> gamerMap = game.getPlayersAndHisCards().get(gamer);
        if (!gamerMap.containsKey(cell.getBlockOfProperties())) {
            gamerMap.put(cell.getBlockOfProperties(), new HashSet<>());
        }
        gamerMap.get(cell.getBlockOfProperties()).add(cell);
        game.getCardsAndOwners().put(cell, gamer);
    }
}
