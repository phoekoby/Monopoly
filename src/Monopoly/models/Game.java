package Monopoly.models;

import Monopoly.models.cells.Cell;
import Monopoly.models.cells.ChanceCard;
import Monopoly.models.cells.BlockOfProperties;


import java.util.*;


public class Game {
    private GameState gameState;
    private List<Gamer> gamers;
    private Cell start;
    private Cell jail;
    private Map<Gamer, Cell> gamersLocation;
    private Map<Cell, Gamer> cardsAndOwners;
    private Map<Gamer, Map<BlockOfProperties, Set<Cell>>> playersAndHisCards;
    private Map<BlockOfProperties, Integer> howManyYouNeedToBuildHouse;
    private Map<Gamer,Cell> location;


    private Bank bank;
    private Queue<ChanceCard> chances;

    public Cell getJail() {
        return jail;
    }

    public void setJail(Cell jail) {
        this.jail = jail;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setPlayersAndHisCards(Map<Gamer, Map<BlockOfProperties,Set<Cell>>> playersAndHisCards) {
        this.playersAndHisCards = playersAndHisCards;
    }

    public void setHowManyYouNeedToBuildHouse(Map<BlockOfProperties, Integer> howManyYouNeedToBuildHouse) {
        this.howManyYouNeedToBuildHouse = howManyYouNeedToBuildHouse;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Map<Gamer, Map<BlockOfProperties,Set<Cell>> >getPlayersAndHisCards() {
        return playersAndHisCards;
    }

    public Map<BlockOfProperties, Integer> getHowManyYouNeedToBuildHouse() {
        return howManyYouNeedToBuildHouse;
    }

    public void setGamerState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setGamers(LinkedList<Gamer> gamers) {
        this.gamers = gamers;
    }


    public void setCardsAndOwners(Map<Cell, Gamer> cardsAndOwners) {
        this.cardsAndOwners = cardsAndOwners;
    }



    public GameState getGamerState() {
        return gameState;
    }

    public List<Gamer> getGamers() {
        return gamers;
    }



    public Map<Cell, Gamer> getCardsAndOwners() {
        return cardsAndOwners;
    }

    public void setGamers(List<Gamer> gamers) {
        this.gamers = gamers;
    }

    public void setStart(Cell start) {
        this.start = start;
    }

    public void setGamersLocation(Map<Gamer, Cell> gamersLocation) {
        this.gamersLocation = gamersLocation;
    }

    public void setLocation(Map<Gamer, Cell> location) {
        this.location = location;
    }

    public void setChances(Queue<ChanceCard> chances) {
        this.chances = chances;
    }

    public Cell getStart() {
        return start;
    }

    public Map<Gamer, Cell> getGamersLocation() {
        return gamersLocation;
    }

    public Map<Gamer, Cell> getLocation() {
        return location;
    }

    public Queue<ChanceCard> getChances() {
        return chances;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        for(int k = 0; k < 10;k++) {
            if(k==0||k==9) {
                for (int j = 0; j < 6; j++) {
                    for (int i = 0; i < 10; i++) {
                        if (j == 0 || j == 5) {
                            str.append("***************");

                        } else {
                            str.append("*             *");
                        }
                    }
                    str.append("\n");
                }
            }else{
                for (int j = 0; j < 5; j++) {
                    for(int i = 0; i < 10; i++){
                        if(j==4 && (i==0||i==9) && k!=8){
                            str.append("***************");
                        }else{
                            if(i==0||i==9){
                                str.append("*             *");
                            }else{
                                str.append("               ");
                            }
                        }
                    }
                    str.append("\n");
                }
            }
        }
        return str.toString();
    }
}
