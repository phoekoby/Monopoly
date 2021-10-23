import ru.vsu.kovalenko_v_yu.Monopoly.models.Game;
import ru.vsu.kovalenko_v_yu.Monopoly.services.*;

public class Main {
    public static void main(String[] args) throws Exception{

        Game game = new Game();
        GameService gameService = new GameService();
        BankService bankService = new BankService();
        StreetService streetService = new StreetService();
        GamerService gamerService = new GamerService();
        ChanceService chanceService = new ChanceService();
        CellServices cellServices = new CellServices();
        UtilityAndStationService utilityAndStationService = new UtilityAndStationService();

        gameService.start(6,game,bankService,gamerService,chanceService,cellServices,streetService,utilityAndStationService);

    }



}
