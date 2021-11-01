import ru.vsu.monopoly.models.Game;
import ru.vsu.monopoly.services.*;

public class Main {
    public static void main(String[] args) throws Exception{
        Game game = new Game();
        GameService gameService = new GameService();
        GamerService gamerService = new GamerService();
        gameService.start(6,game,gamerService);

    }



}
