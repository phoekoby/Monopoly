package ru.vsu.monopoly.models;

import ru.vsu.monopoly.models.cells.Cell;
import ru.vsu.monopoly.models.cells.ChanceCard;
import ru.vsu.monopoly.models.cells.PropertiesType;


import java.util.*;


public class Game {
    private Map<PropertiesType, List<Cell>> allCards;
    private Cell start;
    private Cell jail;
    private Map<Gamer, Cell> gamersLocation;
    private Map<Cell, Gamer> cardsAndOwners;
    private Map<Gamer, Map<PropertiesType, Set<Cell>>> playersAndHisCards;
    private Map<PropertiesType, Integer> howManyYouNeedToBuildHouse;
    private Queue<Gamer> playerMoves;
    private final Map<Gamer, Boolean> canGamerDoStep = new HashMap<>();
    private final Map<Cell, Integer> houses = new HashMap<>();
    private final Map<Cell, Integer> hotels = new HashMap<>();
    private final Map<Cell, Integer> howManyToPayRenta = new HashMap<>();
    private Bank bank;
    private Queue<ChanceCard> chances;

    public Map<PropertiesType, List<Cell>> getAllCards() {
        return allCards;
    }

    public void setAllCards(Map<PropertiesType, List<Cell>> allCards) {
        this.allCards = allCards;
    }

    public Cell getJail() {
        return jail;
    }

    public void setJail(Cell jail) {
        this.jail = jail;
    }

    public Queue<Gamer> getPlayerMoves() {
        return playerMoves;
    }

    public Map<Cell, Integer> getHouses() {
        return houses;
    }

    public Map<Cell, Integer> getHotels() {
        return hotels;
    }

    public Map<Cell, Integer> getHowManyToPayRenta() {
        return howManyToPayRenta;
    }

    public void setPlayerMoves(Queue<Gamer> playerMoves) {
        this.playerMoves = playerMoves;
    }

    public Map<Gamer, Boolean> getCanGamerDoStep() {
        return canGamerDoStep;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public Bank getBank() {
        return bank;
    }

    public void setPlayersAndHisCards(Map<Gamer, Map<PropertiesType, Set<Cell>>> playersAndHisCards) {
        this.playersAndHisCards = playersAndHisCards;
    }

    public void setHowManyYouNeedToBuildHouse(Map<PropertiesType, Integer> howManyYouNeedToBuildHouse) {
        this.howManyYouNeedToBuildHouse = howManyYouNeedToBuildHouse;
    }

    public Map<Gamer, Map<PropertiesType, Set<Cell>>> getPlayersAndHisCards() {
        return playersAndHisCards;
    }

    public Map<PropertiesType, Integer> getHowManyYouNeedToBuildHouse() {
        return howManyYouNeedToBuildHouse;
    }

    public void setCardsAndOwners(Map<Cell, Gamer> cardsAndOwners) {
        this.cardsAndOwners = cardsAndOwners;
    }

    public Map<Cell, Gamer> getCardsAndOwners() {
        return cardsAndOwners;
    }

    public void setStart(Cell start) {
        this.start = start;
    }

    public void setGamersLocation(Map<Gamer, Cell> gamersLocation) {
        this.gamersLocation = gamersLocation;
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

    public Queue<ChanceCard> getChances() {
        return chances;
    }

}
