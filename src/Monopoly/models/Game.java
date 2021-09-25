package Monopoly.models;

import Monopoly.models.Cells.BlockOfStreets;
import Monopoly.models.Cells.Cell;
import Monopoly.models.Cells.Chance;
import Monopoly.models.Cells.Property;

import java.util.*;

public class Game {
    private GameState gameState;
    private List<Gamer> gamers;
    private Cell map;
    private Map<Property, Gamer> cardsAndOwners;
    private Map<Gamer, Map<BlockOfStreets, Set<Property>>> playersAndHisCards;
    private Map<BlockOfStreets, Integer> howManyYouNeedToBuildHouse;

    private Bank bank;
    private Queue<Chance> chances;




    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setPlayersAndHisCards(Map<Gamer, Map<BlockOfStreets,Set< Property>>> playersAndHisCards) {
        this.playersAndHisCards = playersAndHisCards;
    }

    public void setHowManyYouNeedToBuildHouse(Map<BlockOfStreets, Integer> howManyYouNeedToBuildHouse) {
        this.howManyYouNeedToBuildHouse = howManyYouNeedToBuildHouse;
    }

    public GameState getGameState() {
        return gameState;
    }

    public Map<Gamer, Map<BlockOfStreets,Set<Property>> >getPlayersAndHisCards() {
        return playersAndHisCards;
    }

    public Map<BlockOfStreets, Integer> getHowManyYouNeedToBuildHouse() {
        return howManyYouNeedToBuildHouse;
    }

    public void setGamerState(GameState gameState) {
        this.gameState = gameState;
    }

    public void setGamers(LinkedList<Gamer> gamers) {
        this.gamers = gamers;
    }

    public void setMap(Cell map) {
        this.map = map;
    }

    public void setCardsAndOwners(Map<Property, Gamer> cardsAndOwners) {
        this.cardsAndOwners = cardsAndOwners;
    }



    public GameState getGamerState() {
        return gameState;
    }

    public List<Gamer> getGamers() {
        return gamers;
    }

    public Cell getMap() {
        return map;
    }

    public Map<Property, Gamer> getCardsAndOwners() {
        return cardsAndOwners;
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
