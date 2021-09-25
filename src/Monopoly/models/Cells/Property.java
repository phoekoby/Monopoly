package Monopoly.models.Cells;

public class Property extends Cell{
    private int price;
    private int pay;
    private BlockOfStreets blockOfStreets;

    public Property(String name, int price, int pay, BlockOfStreets block, Cell nextCell) {
        super(name,nextCell);
        this.price = price;
        this.pay=pay;
        this.blockOfStreets=block;

    }

    public BlockOfStreets getBlockOfStreets() {
        return blockOfStreets;
    }


    public int getPrice() {
        return price;
    }

    public int getPay() {
        return pay;
    }


}
