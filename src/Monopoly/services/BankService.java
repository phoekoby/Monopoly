package Monopoly.services;

import Monopoly.models.Bank;
import Monopoly.models.Gamer;
import Monopoly.models.cells.*;
import Monopoly.models.Game;


import java.util.*;

public class BankService {
    private Bank bank ;

    public Bank getBank() {
        return bank;
    }

    public Bank createBank(Game game){
        Cell cell = game.getStart().getNextCell();
        Map<BlockOfProperties,Set<Cell>> allcards = new HashMap<>();
        Map<BlockOfProperties,List<Cell>> allCardsToGAme = new HashMap<>();
        while (cell!= game.getStart()){
            if(cell.getCellType().equals(CellType.STREET)||cell.getCellType().equals(CellType.STATION)||cell.getCellType().equals(CellType.UTILITY)){
                if(!allcards.containsKey(cell.getBlockOfProperties())){
                    allcards.put(cell.getBlockOfProperties(),new HashSet<>());
                    allCardsToGAme.put(cell.getBlockOfProperties(), new ArrayList<>());
                }
                allcards.get(cell.getBlockOfProperties()).add(cell);
                allCardsToGAme.get(cell.getBlockOfProperties()).add(cell);
            }
            cell=cell.getNextCell();
        }


        game.setAllCards(allCardsToGAme);
        bank = new Bank(allcards);
        return bank;
    }
    /*
    Аукцион
     */
    public void auction(Cell cell, Game game, GamerService gamerService) throws Exception {
        int price = 10;
        Queue<Gamer> gamers= new LinkedList<>();
        for(int i = 0; i < game.getGamers().size();i++){
            gamers.offer(game.getGamers().get(i));
        }
        Gamer gamer;
        while (gamers.size()>1){
           gamer= gamers.poll();
            int sum = gamerService.doBit(gamer,price, cell, game);
            if(sum==0){
                continue;
            }else {
                price+=sum;
                gamers.offer(gamer);
            }
        }
        gamer = gamers.poll();
        if(price==10) {
            price+=gamerService.doBit(gamer,price,cell,game);
            if(price==10){
                return;
            }
        }
        System.out.println("Игрок " + gamer.getName() + " выигрывает " + cell.getName() + " на аукционе");
        gamerService.buy(gamer,game,cell, price);

    }
}
