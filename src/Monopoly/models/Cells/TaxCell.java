package Monopoly.models.Cells;

public class TaxCell extends Cell{
    private  int cost;
    private Cell nextCell;
    public TaxCell(String name,Cell nextCell, int cost) {
        super(name,nextCell);
        this.cost = cost;
    }

    public int getCost() {
        return cost;
    }


}
