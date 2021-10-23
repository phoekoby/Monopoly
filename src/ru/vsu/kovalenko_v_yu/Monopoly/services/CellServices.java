package ru.vsu.kovalenko_v_yu.Monopoly.services;

import ru.vsu.kovalenko_v_yu.Monopoly.models.cells.BlockOfProperties;
import ru.vsu.kovalenko_v_yu.Monopoly.models.cells.Cell;

import ru.vsu.kovalenko_v_yu.Monopoly.models.Game;
import ru.vsu.kovalenko_v_yu.Monopoly.models.Gamer;
import ru.vsu.kovalenko_v_yu.Monopoly.models.cells.CellType;

import java.util.*;

public class CellServices {
/* можно ли купить карточку?*/
    public boolean canBuy(Game game, Cell card) throws Exception {
        if(card.getCellType()!= CellType.STREET&&card.getCellType()!=CellType.STATION&&card.getCellType()!=CellType.UTILITY){
            throw new Exception("Эта карточка не является имуществом");
        }
       // System.out.println(game.getBank().getAllCardInBank().get(card.getBlockOfProperties()).toString());
        if (game.getBank().getAllCardInBank().get(card.getBlockOfProperties()).contains(card)) {
           // System.out.println("true");
            return true;
        } else {
            return false;
        }
    }
/* Добавление купленной карточки в словарь имющихся у игроков карт*/
    public void addToPlayersAndHisCards_ForGame(Gamer gamer, Game game, Cell cell) {
        Map<BlockOfProperties, Set<Cell>> gamerMap = game.getPlayersAndHisCards().get(gamer);
        if (gamerMap.containsKey(cell.getBlockOfProperties())) {
            gamerMap.get(cell.getBlockOfProperties()).add(cell);
        } else {
            gamerMap.put(cell.getBlockOfProperties(), new HashSet<>());
            gamerMap.get(cell.getBlockOfProperties()).add(cell);
        }
        game.getCardsAndOwners().put(cell, gamer);
        game.getCardsAndOwners().put(cell,gamer);
    }
}
