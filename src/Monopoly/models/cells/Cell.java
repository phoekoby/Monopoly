package Monopoly.models.cells;

public class Cell {
    private String name;
    private Cell nextCell;
    private CellType cellType;
    private BlockOfProperties blockOfProperties;
    private int price;

    public Cell(String name, Cell nextCell, CellType cellType, BlockOfProperties blockOfProperties) {
        this.name = name;
        this.nextCell = nextCell;
        this.cellType = cellType;
        this.blockOfProperties = blockOfProperties;
    }

    public Cell(String name, Cell nextCell, CellType cellType, BlockOfProperties blockOfProperties, int price) {
        this.name = name;
        this.nextCell = nextCell;
        this.cellType = cellType;
        this.blockOfProperties = blockOfProperties;
        this.price = price;
    }
    public CellType getCellType() {
        return cellType;
    }
    public BlockOfProperties getBlockOfProperties() {
        return blockOfProperties;
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
