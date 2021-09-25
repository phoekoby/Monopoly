import Monopoly.models.Game;
import Monopoly.services.GameService;

import java.util.HashSet;
import java.util.Set;

public class Main {
    public static void main(String[] args) throws Exception{

        System.out.println(new Game().toString());
        Set<Integer> s = new HashSet<>();

        Game game = new Game();
        GameService gameService = new GameService();
        gameService.start(game,6);

    }



}
