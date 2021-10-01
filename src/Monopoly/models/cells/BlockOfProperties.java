package Monopoly.models.cells;

public enum BlockOfProperties {
    BROWN(2),
    LIGHT_BLUE(3),
    PINK(3),
    ORANGE(3),
    RED(3),
    YELLOW(3),
    GREEN(3),
    BLUE(2),
    UTILITY(2),
    STATION(4),
    NONE(0);
    private int i ;
    BlockOfProperties(int i) {
      this.i=i;
    }
    public int getI(){
        return this.i;
    }

}
