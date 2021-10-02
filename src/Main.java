import Monopoly.models.Game;
import Monopoly.services.GameService;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception{


        Game game = new Game();
        GameService gameService = new GameService(game);
        gameService.start(6);

    }



}
