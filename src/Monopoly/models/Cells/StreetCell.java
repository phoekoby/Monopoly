package Monopoly.models.Cells;

public class StreetCell extends Property{

    private final int MAX_COUNT_OF_HOUSES = 4;
    private boolean hotel = false;
    private int cost;
    private int pay;
    private Cell nextCell;
    private int houses;

    public StreetCell(String name, BlockOfStreets block, int cost, int pay, Cell nextCell) {
        super(name,cost,pay,block,nextCell);

        this.cost=cost;

    }

    public void setHotel(boolean hotel) {
        this.hotel = hotel;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public void setPay(int pay) {
        this.pay = pay;
    }

    @Override
    public void setNextCell(Cell nextCell) {
        this.nextCell = nextCell;
    }

    public void setHouses(int houses) {
        this.houses = houses;
    }

    public int getMAX_COUNT_OF_HOUSES() {
        return MAX_COUNT_OF_HOUSES;
    }

    public boolean isHotel() {
        return hotel;
    }

    @Override
    public Cell getNextCell() {
        return nextCell;
    }

    public int getHouses() {
        return houses;
    }

    public int getCost() {
        return cost;
    }

    public int getPay() {
        return pay;
    }


}
