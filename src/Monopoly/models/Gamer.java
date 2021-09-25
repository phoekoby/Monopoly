package Monopoly.models;

import Monopoly.models.Cells.BlockOfStreets;
import Monopoly.models.Cells.Cell;
import Monopoly.models.Cells.Property;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Gamer {
    private Cell current;
    private boolean canStep =true;
    private String name;
    private int money;
    private Set<Property> own = new HashSet<>();
    private Map<BlockOfStreets,Set<Property>> ownCards;


    public Gamer(String name, int money, Cell current, Map<BlockOfStreets,Set<Property>> ownCards, Set<Property> own ) {
        this.name = name;
        this.money = money;
        this.current=current;
        this.ownCards = ownCards;
        this.own =own;
    }



    public Map<BlockOfStreets, Set<Property>> getOwnCards() {
        return ownCards;
    }

    public void setOwnCards(Map<BlockOfStreets, Set<Property>> ownCards) {
        this.ownCards = ownCards;
    }

    public void setCanStep(boolean canStep) {
        this.canStep = canStep;
    }

    public boolean isCanStep() {
        return canStep;
    }

    public String getName() {
        return name;
    }

    public int getMoney() {
        return money;
    }

    public Set<Property> getOwn() {
        return own;
    }

    public Cell getCurrent() {
        return current;
    }

    public void setCurrent(Cell current) {
        this.current = current;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public void setOwn(Set<Property> own) {
        this.own = own;
    }

}
