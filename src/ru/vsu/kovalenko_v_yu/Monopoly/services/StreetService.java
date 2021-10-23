package ru.vsu.kovalenko_v_yu.Monopoly.services;

import ru.vsu.kovalenko_v_yu.Monopoly.models.Game;
import ru.vsu.kovalenko_v_yu.Monopoly.models.Gamer;
import ru.vsu.kovalenko_v_yu.Monopoly.models.cells.BlockOfProperties;
import ru.vsu.kovalenko_v_yu.Monopoly.models.cells.Cell;
import ru.vsu.kovalenko_v_yu.Monopoly.models.cells.CellType;

public class StreetService {
    private final double COEFFICIENT_FOR_EACH_HOUSE = 2.2;
    private final int COEFFICIENT_FOR_FULL_STACK_OF_PROPERTY = 2;

    /* Проверка можно ли построить дом на этой улице? */
    public boolean canBuildHouse(Game game, Gamer gamer, Cell cell) {
        if (game.getPlayersAndHisCards().get(gamer).get(cell.getBlockOfProperties()).size() == cell.getBlockOfProperties().getI()
                && cell.getCellType() == CellType.STREET && game.getHouses().get(cell) < 4) {
            for (int i = 0; i < game.getAllCards().get(cell.getBlockOfProperties()).size(); ++i) {
                if (game.getHouses().get(game.getAllCards().get(cell.getBlockOfProperties()).get(i)) < game.getHouses().get(cell)) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void buildHouse(Game game, Cell cell) {
        game.getHouses().put(cell, game.getHouses().remove(cell) + 1);
        recalculatingRentaWithHouses(game, cell, true);
    }

    public void destroyHouse(Game game, Cell cell) {
        game.getHotels().put(cell, game.getHouses().remove(cell) - 1);
        recalculatingRentaWithHouses(game, cell, false);
    }

    /*
    Перерасчет ренты, если набран полный блок улиц
     */
    public void recalculationRentaIfGetFullStack(Game game, BlockOfProperties blockOfProperties) {
        for (int i = 0; i < game.getAllCards().get(blockOfProperties).size(); i++) {
            Cell cell = game.getAllCards().get(blockOfProperties).get(i);
            game.getHowManyToPayRenta().put(cell, game.getHowManyToPayRenta().remove(cell) * 2);
        }
    }

    /*
    Перерасчет стоимости ренты при покупке дома
     */
    private void recalculatingRentaWithHouses(Game game, Cell cell, boolean buildOrBreak) {
        if (buildOrBreak) {
            game.getHowManyToPayRenta().put(cell, (int) (game.getHowManyToPayRenta().remove(cell) * COEFFICIENT_FOR_EACH_HOUSE));
        } else {
            game.getHowManyToPayRenta().put(cell, (int) (game.getHowManyToPayRenta().remove(cell) / COEFFICIENT_FOR_EACH_HOUSE));
        }
    }
}
