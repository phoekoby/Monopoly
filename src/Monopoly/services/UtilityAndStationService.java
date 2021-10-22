package Monopoly.services;

import Monopoly.models.Game;
import Monopoly.models.Gamer;
import Monopoly.models.cells.BlockOfProperties;
import Monopoly.models.cells.Cell;

public class UtilityAndStationService {
    /* Перерасчет стоимости ренты в зависимости от количства имеющихся карт*/
    public void recalculationStationAndUtility(Game game, BlockOfProperties blockOfProperties, Gamer gamer) {
        for (Cell cell : game.getPlayersAndHisCards().get(gamer).get(blockOfProperties)) {
            game.getHowManyToPayRenta().remove(cell);
            game.getHowManyToPayRenta().put(cell,
                    (int) (cell.getPrice() * 0.025 * game.getPlayersAndHisCards().get(gamer).get(blockOfProperties).size()));
        }
    }
}
