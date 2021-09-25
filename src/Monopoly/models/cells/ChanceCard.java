package Monopoly.models.cells;

public class ChanceCard {
    private TypeOfChance type;
    private String message;
    private Cell go_to;
    private int countOfMoney;


    public ChanceCard(TypeOfChance type, String message, Cell go_to) {
        this.type = type;
        this.message = message;
        this.go_to = go_to;
    }

    public ChanceCard(TypeOfChance type, String message, int countOfMoney) {
        this.type = type;
        this.message=message;
        this.countOfMoney=countOfMoney;
    }

    public TypeOfChance getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }

    public Cell getGo_to() {
        return go_to;
    }

    public int getCountOfMoney() {
        return countOfMoney;
    }

    public void setType(TypeOfChance type) {
        this.type = type;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setGo_to(Cell go_to) {
        this.go_to = go_to;
    }

    public void setCountOfMoney(int countOfMoney) {
        this.countOfMoney = countOfMoney;
    }
}
