package ru.vsu.monopoly.services;

import ru.vsu.monopoly.models.Bank;
import ru.vsu.monopoly.models.Gamer;
import ru.vsu.monopoly.models.Game;
import ru.vsu.monopoly.models.cells.PropertiesType;
import ru.vsu.monopoly.models.cells.Cell;
import ru.vsu.monopoly.models.cells.CellType;


import java.util.*;

public class BankService {
    private final int MIN_BET = 10;

    public Bank createBank(Game game) {
        Cell cell = game.getStart().getNextCell();
        Map<PropertiesType, Set<Cell>> allcards = new HashMap<>();
        Map<PropertiesType, List<Cell>> allCardsToGAme = new HashMap<>();
        while (cell != game.getStart()) {
            if (cell.getCellType().equals(CellType.STREET) || cell.getCellType().equals(CellType.STATION)
                    || cell.getCellType().equals(CellType.UTILITY)) {
                if (!allcards.containsKey(cell.getPropertiesType())) {
                    allcards.put(cell.getPropertiesType(), new HashSet<>());
                    allCardsToGAme.put(cell.getPropertiesType(), new ArrayList<>());
                }
                allcards.get(cell.getPropertiesType()).add(cell);
                allCardsToGAme.get(cell.getPropertiesType()).add(cell);
            }
            cell = cell.getNextCell();
        }
        game.setAllCards(allCardsToGAme);
        return new Bank(allcards);
    }

    /*
    Аукцион
     */
    public void auction(Cell cell, Game game, GamerService gamerService) throws Exception {
        int price = MIN_BET;
        Queue<Gamer> gamers = new LinkedList<>();
        Gamer gamerK = game.getPlayerMoves().poll();
        game.getPlayerMoves().offer(gamerK);
        while (game.getPlayerMoves().peek() != gamerK) {
            gamers.offer(game.getPlayerMoves().peek());
            game.getPlayerMoves().offer(game.getPlayerMoves().poll());
        }
        //Каждый игрок, начиная с первого делают ставку по очереди, пока хотят
        Gamer gamer;
        while (gamers.size() > 1) {
            gamer = gamers.poll();
            int sum = gamerService.doBit(gamer, price, cell, game);
            if (sum != 0) {
                price += sum;
                gamers.offer(gamer);
            }
        }
        gamer = gamers.poll();
        if (price == MIN_BET) {
            assert gamer != null;
            price += gamerService.doBit(gamer, price, cell, game);
            if (price == MIN_BET) {
                return;
            }
        }
        assert gamer != null;
        System.out.println("Игрок " + gamer.getName() + " выигрывает " + cell.getName() + " на аукционе");
        gamerService.buy(gamer, game, cell, price);

    }
}
