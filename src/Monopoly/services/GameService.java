package Monopoly.services;

import Monopoly.models.cells.*;
import Monopoly.models.Game;
import Monopoly.models.Gamer;


import java.util.*;

public class GameService {
    private final GamerService gamerService = new GamerService(this);
    private final BankService bankService = new BankService();
    private final CellServices cellService = new CellServices();
    private final ChanceService chanceService = new ChanceService();
    private final Game game;

    public GamerService getGamerService() {
        return gamerService;
    }

    public BankService getBankService() {
        return bankService;
    }

    public CellServices getCellService() {
        return cellService;
    }

    public ChanceService getChanceService() {
        return chanceService;
    }

    public Game getGame() {
        return game;
    }

    public GameService(Game game) {
    this.game=game;
    game.setCardsAndOwners(new HashMap<>());
    }


    private Cell function(Cell cell, Cell card) {
        if(card.getCellType()==CellType.STATION || card.getCellType()==CellType.UTILITY||card.getCellType()==CellType.STREET) {
            game.getHowManyToPayRenta().put(card, card.getCellType()==CellType.STREET?(int) (card.getPrice() * 0.08):(int)(card.getPrice()*0.25));
            if(card.getCellType()==CellType.STREET){
                game.getHouses().put(card,0);
                game.getHotels().put(card,0);
            }
        }
        cell.setNextCell(card);
        return cell.getNextCell();
    }
    private void createHowManyForHouse(){
        Map<BlockOfProperties, Integer> howMany = new HashMap<>();
        howMany.put(BlockOfProperties.BROWN,50);
        howMany.put(BlockOfProperties.BLUE,200);
        howMany.put(BlockOfProperties.PINK,100);
        howMany.put(BlockOfProperties.RED,150);
        howMany.put(BlockOfProperties.ORANGE,100);
        howMany.put(BlockOfProperties.GREEN,200);
        howMany.put(BlockOfProperties.YELLOW,150);
        howMany.put(BlockOfProperties.LIGHT_BLUE,50);
        game.setHowManyYouNeedToBuildHouse(howMany);
    }

    private void createMap(Game game) {
        Cell start = new Cell("Старт", null, CellType.START, BlockOfProperties.NONE);
        Cell cell = start;
        cell = function(cell, new Cell("Житная ул.", null, CellType.STREET, BlockOfProperties.BROWN, 60));
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE));
        cell = function(cell, new Cell("Нагатинская ул.", null, CellType.STREET, BlockOfProperties.BROWN, 60));
        cell = function(cell, new Cell("Подоходный налог", null, CellType.TAX, BlockOfProperties.NONE, 200));
        cell = function(cell, new Cell("Рижская жд", null, CellType.STATION, BlockOfProperties.STATION, 200));
        cell = function(cell, new Cell("Варшавское шоссе", null, CellType.STREET, BlockOfProperties.LIGHT_BLUE, 100));
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE));
        cell = function(cell, new Cell("Ул. Огарева", null, CellType.STREET, BlockOfProperties.LIGHT_BLUE, 100));
        cell = function(cell, new Cell("Первая Парковая ул.", null, CellType.STREET, BlockOfProperties.LIGHT_BLUE, 120));
        cell = function(cell, new Cell("В ТЮРЬМЕ", null, CellType.JAIL, BlockOfProperties.NONE));
        cell = function(cell, new Cell("Ул. Полянка", null, CellType.STREET, BlockOfProperties.PINK, 140));
        cell = function(cell, new Cell("Электростанция", null, CellType.UTILITY, BlockOfProperties.UTILITY, 150));
        cell = function(cell, new Cell("Ул. Сретенка", null, CellType.STREET, BlockOfProperties.PINK, 140));
        cell = function(cell, new Cell("Ростовская наб.", null, CellType.STREET, BlockOfProperties.PINK, 160));
        cell = function(cell, new Cell("Куркская жд", null, CellType.STATION, BlockOfProperties.STATION, 200));
        cell = function(cell, new Cell("Рязанский проспект", null, CellType.STREET, BlockOfProperties.ORANGE, 180));
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE));
        cell = function(cell, new Cell("Ул. Вавилова", null, CellType.STREET, BlockOfProperties.ORANGE, 180));
        cell = function(cell, new Cell("Рублевское шоссе", null, CellType.STREET, BlockOfProperties.ORANGE, 200));
        cell = function(cell, new Cell("Бесплатная стоянка", null, CellType.PARKING, BlockOfProperties.NONE));
        cell = function(cell, new Cell("Ул. Тверская", null, CellType.STREET, BlockOfProperties.RED, 220));
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE));
        cell = function(cell, new Cell("Пушкинская ул.", null, CellType.STREET, BlockOfProperties.RED, 220));
        cell = function(cell, new Cell("Площадь Маяковского", null, CellType.STREET, BlockOfProperties.RED, 240));
        cell = function(cell, new Cell("Казанская жд", null, CellType.STATION, BlockOfProperties.STATION, 200));
        cell = function(cell, new Cell("Ул. Грузинский вал", null, CellType.STREET, BlockOfProperties.YELLOW, 260));
        cell = function(cell, new Cell("Новинский бульвар", null, CellType.STREET, BlockOfProperties.YELLOW, 260));
        cell = function(cell, new Cell("Водопровод", null, CellType.UTILITY, BlockOfProperties.UTILITY, 150));
        cell = function(cell, new Cell("Смоленская площадь", null, CellType.STREET, BlockOfProperties.YELLOW, 280));
        Cell jail = cell = function(cell, new Cell("Отправляйтесь в тюрьму", null, CellType.GO_TO_JAIL, BlockOfProperties.NONE));
        cell = function(cell, new Cell("Ул. Щусева", null, CellType.STREET, BlockOfProperties.GREEN, 300));
        cell = function(cell, new Cell("Гоголевский бульвар", null, CellType.STREET, BlockOfProperties.GREEN, 300));
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE));
        cell = function(cell, new Cell("Кутузовский проспект", null, CellType.STREET, BlockOfProperties.GREEN, 320));
        cell = function(cell, new Cell("Лениградская жд", null, CellType.STATION, BlockOfProperties.STATION, 200));
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE));
        cell = function(cell, new Cell("Ул. Малая Бронная", null, CellType.STREET, BlockOfProperties.BLUE, 350));
        cell = function(cell, new Cell("Сверхналог", null, CellType.TAX, BlockOfProperties.NONE,300));
        cell = function(cell, new Cell("Ул. Арбат", null, CellType.STREET, BlockOfProperties.BLUE, 400));
        cell.setNextCell(start);
        game.setStart(start);
        game.setJail(jail);
    }

    public void creatingGame(int countOfGamers) throws Exception {
        createMap(game);
        game.setChances(chanceService.generateStack(game));
      gamerService.createGamerList(countOfGamers, game);
        game.setBank(bankService.createBank(game));
        createHowManyForHouse();
    }
    public void start(int countOfGamers) throws Exception {
        creatingGame(countOfGamers);
        play();
        }
/* игра */
    public void play() throws Exception{
    while (game.getPlayerMoves().size()>1){
        Gamer gamer = game.getPlayerMoves().poll();
        game.getPlayerMoves().offer(gamer);
        if(!game.getCanGamerDoStep().get(gamer)){
            gamerService.makeNextSkipOrNotSkip(game,gamer);
        }else {
           gamerService.doSomething(gamer, game);
        }
      Thread.sleep(2000);
    }
        System.out.println(game.getPlayerMoves().poll().getName() + " выиграл!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
