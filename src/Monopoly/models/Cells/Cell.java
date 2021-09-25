package Monopoly.models.Cells;

public class Cell {
    private String name;
    private Cell nextCell;


    public Cell(String name,Cell nextCell) {
        this.name = name;
        this.nextCell=nextCell;
    }

    public String getName() {
        return name;
    }

    public void setNextCell(Cell nextCell) {
        this.nextCell = nextCell;
    }

    public Cell getNextCell() {
        return nextCell;
    }

    @Override
    public String toString() {
        return name;
    }
}
