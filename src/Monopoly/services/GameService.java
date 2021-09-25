package Monopoly.services;

import Monopoly.models.Cells.*;
import Monopoly.models.Game;
import Monopoly.models.Gamer;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class GameService {
    private final GamerService gamerService = new GamerService();
    private final BankService bankService = new BankService();
    private final CellServices cellService = new CellServices();



    private int throwCubes(){
        int max_for_throw_cubes = 12;
        int min_for_throw_cubes = 2;
        max_for_throw_cubes-=min_for_throw_cubes;
        return (int)((Math.random()*++max_for_throw_cubes)+min_for_throw_cubes);
    }


    private Cell createMap(){
        Map<BlockOfStreets, Set<Property>> allCards = new HashMap<>();
        Map<Property, Gamer> gamersCells = new HashMap<>();

        Cell start = new Cell("Старт", new StreetCell("Житная ул.", BlockOfStreets.BROWN,60,10,null));
        allCards.put(BlockOfStreets.BROWN,new HashSet<>());

        Cell current = start.getNextCell();
        //gamersCells.put((StreetCell)current,null);
        allCards.get(BlockOfStreets.BROWN).add((Property) current);


        current.setNextCell(new Chance("Шанс", null));
        System.out.println(current.toString());
        current=current.getNextCell();
        System.out.println(current.toString());
        current.setNextCell(new StreetCell("Никитинская",BlockOfStreets.BROWN,60,10,null));


        current=current.getNextCell();
        System.out.println(current.toString());
        current.setNextCell(new TaxCell("Подоходный налог",null,200));
        current=current.getNextCell();
        System.out.println(current.toString());
        current.setNextCell(new StationCell("Рижская жд",200,100,BlockOfStreets.STATION,null));
        current=current.getNextCell();
        System.out.println(current.toString());
        current.setNextCell(new StreetCell("Варшавское шоссе",BlockOfStreets.LIGHT_BLUE,100,10,start));

        return start;
    }

    public void start(Game game, int countOfGamers) throws Exception{
        game.setMap(createMap());
        game.setGamers(gamerService.createGamerList(countOfGamers,game));
        game.setBank(bankService.createBank(game));


        cellService.canBuy(game,(Property) game.getMap().getNextCell());
        System.out.println(game.getBank().getAllCardInBank().get(BlockOfStreets.BROWN).toArray().length);

        gamerService.step(game.getGamers().get(1),throwCubes());

        System.out.println(game.getGamers().get(1).getCurrent().toString());
        System.out.println(game.getGamers().toString());
    }

}
