package ru.vsu.monopoly.models.cells;

public class Cell {
    private final String name;
    private Cell nextCell;
    private final CellType cellType;
    private final PropertiesType propertiesType;
    private int price;

    public Cell(String name, Cell nextCell, CellType cellType, PropertiesType propertiesType) {
        this.name = name;
        this.nextCell = nextCell;
        this.cellType = cellType;
        this.propertiesType = propertiesType;
    }

    public Cell(String name, Cell nextCell, CellType cellType, PropertiesType propertiesType, int price) {
        this.name = name;
        this.nextCell = nextCell;
        this.cellType = cellType;
        this.propertiesType = propertiesType;
        this.price = price;
    }

    public CellType getCellType() {
        return cellType;
    }

    public PropertiesType getBlockOfProperties() {
        return propertiesType;
    }

    public int getPrice() {
        return price;
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
