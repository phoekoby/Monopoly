package Monopoly.services;

import Monopoly.models.cells.BlockOfProperties;
import Monopoly.models.cells.Cell;

import Monopoly.models.Game;
import Monopoly.models.Gamer;
import Monopoly.models.cells.CellType;

import java.util.*;

public class CellServices {



    public boolean canBuy(Game game, Cell card) throws Exception {
        if(card.getCellType()!= CellType.STREET&&card.getCellType()!=CellType.STATION&&card.getCellType()!=CellType.UTILITY){
            throw new Exception("Эта карточка не является имуществом");
        }
        System.out.println(game.getBank().getAllCardInBank().get(card.getBlockOfProperties()).toString());
        if (game.getBank().getAllCardInBank().get(card.getBlockOfProperties()).contains(card)) {
            System.out.println("true");
            return true;
        } else {
            return false;
        }
    }


    public void buy(Gamer gamer, Game game, Cell cell) throws Exception {
        if (!canBuy(game, cell)) {
            throw new Exception("Нельзя купить карту, заплатите ипотеку");
        }
        gamer.setMoney(gamer.getMoney() - cell.getPrice());
        game.getBank().getAllCardInBank().get(cell.getBlockOfProperties()).remove(cell);

        addToPlayersAndHisCards_ForGame(gamer, game, cell);
    }
//
//    /**
//     * Метод добавляет карточку в словарь карточек класса Gamer
//     **/
//    private void addToOwnGamersCard_ForGamer(Gamer gamer,Game game, Cell cell) {
//        if (game.getPlayersAndHisCards().get(gamer).containsKey(cell.getBlockOfProperties())) {
//            game.getPlayersAndHisCards().get(gamer).get(cell.getBlockOfProperties()).add(cell);
//        } else {
//            game.getPlayersAndHisCards().get(gamer).put(cell.getBlockOfProperties(),new HashSet<>());
//            game.getPlayersAndHisCards().get(gamer).get(cell.getBlockOfProperties()).add(cell);
//        }
//    }

    /**
     * Метод добавляет карточку в словарь карточек класса Game
     **/
    private void addToPlayersAndHisCards_ForGame(Gamer gamer, Game game, Cell cell) {
        Map<BlockOfProperties, Set<Cell>> gamerMap = game.getPlayersAndHisCards().get(gamer);
        if (gamerMap.containsKey(cell.getBlockOfProperties())) {
            gamerMap.get(cell.getBlockOfProperties()).add(cell);
        } else {
            gamerMap.put(cell.getBlockOfProperties(), new HashSet<>());
            gamerMap.get(cell.getBlockOfProperties()).add(cell);
        }
        game.getCardsAndOwners().put(cell, gamer);
    }


}
