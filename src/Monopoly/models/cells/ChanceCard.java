package Monopoly.models.cells;

public class ChanceCard {
    private TypeOfChance type;
    private String message;
    private Cell go_to;
    private int countOfMoneyOfSteps;





    public ChanceCard(TypeOfChance type, String message, Cell go_to) {
        this.type = type;
        this.message = message;
        this.go_to = go_to;
    }

    public ChanceCard(TypeOfChance type, String message, int countOfMoney) {
        this.type = type;
        this.message=message;
        this.countOfMoneyOfSteps=countOfMoney;
    }

    public ChanceCard(TypeOfChance type, String message) {
        this.type = type;
        this.message = message;
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

    public int getCountOfMoneyOrSteps() {
        return countOfMoneyOfSteps;
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

}
