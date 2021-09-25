package Monopoly.models.Cells;

public class Chance extends Cell {
    private TypeOfChance type;
    private String message;
    private Cell go_to;
    private int countOfMoney;

    public Chance(String name, Cell nextCell) {
        super(name, nextCell);
    }
}
