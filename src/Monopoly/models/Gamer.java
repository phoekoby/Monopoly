package Monopoly.models;



public class Gamer {
    private boolean canStep =true;
    private String name;
    private int money;



    public Gamer(String name, int money) {
        this.name = name;
        this.money = money;

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




    public void setName(String name) {
        this.name = name;
    }

    public void setMoney(int money) {
        this.money = money;
    }


}
