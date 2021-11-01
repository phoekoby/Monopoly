package ru.vsu.monopoly.services;

import ru.vsu.monopoly.models.Game;
import ru.vsu.monopoly.models.Gamer;
import ru.vsu.monopoly.models.cells.PropertiesType;
import ru.vsu.monopoly.models.cells.Cell;

import java.util.Set;

public class UtilityAndStationService {
    /* Перерасчет стоимости ренты в зависимости от количства имеющихся карт*/
    public void recalculationStationAndUtility(Game game, PropertiesType propertiesType, Gamer gamer) {
        Set<Cell> cardsInTypeOfProperties = game.getPlayersAndHisCards().get(gamer).get(propertiesType);
        for (Cell cell : cardsInTypeOfProperties) {
            game.getHowManyToPayRenta().put(cell,
                    (int) (cell.getPrice() * 0.025 * cardsInTypeOfProperties.size()));
        }
    }
}
