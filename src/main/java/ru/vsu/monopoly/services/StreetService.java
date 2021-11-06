package ru.vsu.monopoly.services;

import ru.vsu.monopoly.models.Game;
import ru.vsu.monopoly.models.Gamer;
import ru.vsu.monopoly.models.cells.PropertiesType;
import ru.vsu.monopoly.models.cells.Cell;
import ru.vsu.monopoly.models.cells.CellType;

import java.util.List;
import java.util.Map;

public class StreetService {

    /* Проверка можно ли построить дом на этой улице? */
    public boolean canBuildHouse(Game game, Gamer gamer, Cell cell) {
        int MAX_COUNT_OF_HOUSES = 4;
        if (game.getPlayersAndHisCards().get(gamer).get(cell.getPropertiesType()).size() == cell.getPropertiesType().getI()
                && cell.getCellType() == CellType.STREET && game.getHouses().get(cell) < MAX_COUNT_OF_HOUSES) {
            List<Cell> listOfCells = game.getAllCards().get(cell.getPropertiesType());
            Map<Cell, Integer> houses = game.getHouses();
            for (Cell listOfCell : listOfCells) {
                if (houses.get(listOfCell) < game.getHouses().get(cell)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void buildHouse(Game game, Cell cell) {
        game.getHouses().put(cell, game.getHouses().remove(cell) + 1);
        recalculatingRentaWithHouses(game, cell);
    }

    /*
    Перерасчет ренты, если набран полный блок улиц
     */
    public void recalculationRentaIfGetFullStack(Game game, PropertiesType propertiesType) {
        List<Cell> listOfCells = game.getAllCards().get(propertiesType);
        for (Cell cell : listOfCells) {
            game.getHowManyToPayRenta().put(cell, game.getHowManyToPayRenta().remove(cell) * 2);
        }
    }

    /*
    Перерасчет стоимости ренты при покупке дома
     */
    private void recalculatingRentaWithHouses(Game game, Cell cell) {
        Map<Cell, Integer> howManyToPlayRenta = game.getHowManyToPayRenta();
        double COEFFICIENT_FOR_EACH_HOUSE = 2.2;
        howManyToPlayRenta.put(cell, (int) (game.getHowManyToPayRenta().remove(cell) * COEFFICIENT_FOR_EACH_HOUSE));
    }
}
