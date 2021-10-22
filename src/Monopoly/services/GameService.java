package Monopoly.services;

import Monopoly.models.cells.*;
import Monopoly.models.Game;
import Monopoly.models.Gamer;


import java.util.*;

public class GameService {
    private Cell function(Cell cell, Cell card, Game game) {
        if (card.getCellType() == CellType.STATION || card.getCellType() == CellType.UTILITY || card.getCellType() == CellType.STREET) {
            game.getHowManyToPayRenta().put(card, card.getCellType() == CellType.STREET ? (int) (card.getPrice() * 0.08) : (int) (card.getPrice() * 0.25));
            if (card.getCellType() == CellType.STREET) {
                game.getHouses().put(card, 0);
                game.getHotels().put(card, 0);
            }
        }
        cell.setNextCell(card);
        return cell.getNextCell();
    }

    private void createHowManyForHouse(Game game) {
        Map<BlockOfProperties, Integer> howMany = new HashMap<>();
        howMany.put(BlockOfProperties.BROWN, 50);
        howMany.put(BlockOfProperties.BLUE, 200);
        howMany.put(BlockOfProperties.PINK, 100);
        howMany.put(BlockOfProperties.RED, 150);
        howMany.put(BlockOfProperties.ORANGE, 100);
        howMany.put(BlockOfProperties.GREEN, 200);
        howMany.put(BlockOfProperties.YELLOW, 150);
        howMany.put(BlockOfProperties.LIGHT_BLUE, 50);
        game.setHowManyYouNeedToBuildHouse(howMany);
    }

    private void createMap(Game game) {
        Cell start = new Cell("Старт", null, CellType.START, BlockOfProperties.NONE);
        Cell cell = start;
        cell = function(cell, new Cell("Житная ул.", null, CellType.STREET, BlockOfProperties.BROWN, 60), game);
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE), game);
        cell = function(cell, new Cell("Нагатинская ул.", null, CellType.STREET, BlockOfProperties.BROWN, 60), game);
        cell = function(cell, new Cell("Подоходный налог", null, CellType.TAX, BlockOfProperties.NONE, 200), game);
        cell = function(cell, new Cell("Рижская жд", null, CellType.STATION, BlockOfProperties.STATION, 200), game);
        cell = function(cell, new Cell("Варшавское шоссе", null, CellType.STREET, BlockOfProperties.LIGHT_BLUE, 100), game);
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE), game);
        cell = function(cell, new Cell("Ул. Огарева", null, CellType.STREET, BlockOfProperties.LIGHT_BLUE, 100), game);
        cell = function(cell, new Cell("Первая Парковая ул.", null, CellType.STREET, BlockOfProperties.LIGHT_BLUE, 120), game);
        cell = function(cell, new Cell("В ТЮРЬМЕ", null, CellType.JAIL, BlockOfProperties.NONE), game);
        cell = function(cell, new Cell("Ул. Полянка", null, CellType.STREET, BlockOfProperties.PINK, 140), game);
        cell = function(cell, new Cell("Электростанция", null, CellType.UTILITY, BlockOfProperties.UTILITY, 150), game);
        cell = function(cell, new Cell("Ул. Сретенка", null, CellType.STREET, BlockOfProperties.PINK, 140), game);
        cell = function(cell, new Cell("Ростовская наб.", null, CellType.STREET, BlockOfProperties.PINK, 160), game);
        cell = function(cell, new Cell("Куркская жд", null, CellType.STATION, BlockOfProperties.STATION, 200), game);
        cell = function(cell, new Cell("Рязанский проспект", null, CellType.STREET, BlockOfProperties.ORANGE, 180), game);
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE), game);
        cell = function(cell, new Cell("Ул. Вавилова", null, CellType.STREET, BlockOfProperties.ORANGE, 180), game);
        cell = function(cell, new Cell("Рублевское шоссе", null, CellType.STREET, BlockOfProperties.ORANGE, 200), game);
        cell = function(cell, new Cell("Бесплатная стоянка", null, CellType.PARKING, BlockOfProperties.NONE), game);
        cell = function(cell, new Cell("Ул. Тверская", null, CellType.STREET, BlockOfProperties.RED, 220), game);
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE), game);
        cell = function(cell, new Cell("Пушкинская ул.", null, CellType.STREET, BlockOfProperties.RED, 220), game);
        cell = function(cell, new Cell("Площадь Маяковского", null, CellType.STREET, BlockOfProperties.RED, 240), game);
        cell = function(cell, new Cell("Казанская жд", null, CellType.STATION, BlockOfProperties.STATION, 200), game);
        cell = function(cell, new Cell("Ул. Грузинский вал", null, CellType.STREET, BlockOfProperties.YELLOW, 260), game);
        cell = function(cell, new Cell("Новинский бульвар", null, CellType.STREET, BlockOfProperties.YELLOW, 260), game);
        cell = function(cell, new Cell("Водопровод", null, CellType.UTILITY, BlockOfProperties.UTILITY, 150), game);
        cell = function(cell, new Cell("Смоленская площадь", null, CellType.STREET, BlockOfProperties.YELLOW, 280), game);
        Cell jail = cell = function(cell, new Cell("Отправляйтесь в тюрьму", null, CellType.GO_TO_JAIL, BlockOfProperties.NONE), game);
        cell = function(cell, new Cell("Ул. Щусева", null, CellType.STREET, BlockOfProperties.GREEN, 300), game);
        cell = function(cell, new Cell("Гоголевский бульвар", null, CellType.STREET, BlockOfProperties.GREEN, 300), game);
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE), game);
        cell = function(cell, new Cell("Кутузовский проспект", null, CellType.STREET, BlockOfProperties.GREEN, 320), game);
        cell = function(cell, new Cell("Лениградская жд", null, CellType.STATION, BlockOfProperties.STATION, 200), game);
        cell = function(cell, new Cell("Шанс", null, CellType.CHANCE, BlockOfProperties.NONE), game);
        cell = function(cell, new Cell("Ул. Малая Бронная", null, CellType.STREET, BlockOfProperties.BLUE, 350), game);
        cell = function(cell, new Cell("Сверхналог", null, CellType.TAX, BlockOfProperties.NONE, 300), game);
        cell = function(cell, new Cell("ул. Арбат", null, CellType.STREET, BlockOfProperties.BLUE, 400), game);
        cell.setNextCell(start);
        game.setStart(start);
        game.setJail(jail);
    }

    public void creatingGame(int countOfGamers, Game game, BankService bankService, GamerService gamerService,
                             ChanceService chanceService) throws Exception {
        game.setCardsAndOwners(new HashMap<>());
        createMap(game);
        game.setChances(chanceService.generateStack(game));
        gamerService.createGamerList(countOfGamers, game);
        game.setBank(bankService.createBank(game));
        createHowManyForHouse(game);
    }

    public void start(int countOfGamers, Game game, BankService bankService, GamerService gamerService,
                      ChanceService chanceService, CellServices cellServices, StreetService streetService,
                      UtilityAndStationService utilityAndStationService) throws Exception {
        creatingGame(countOfGamers, game, bankService, gamerService, chanceService);
        play(game, gamerService, chanceService, bankService, cellServices, streetService, utilityAndStationService);
    }

    /* игра */
    public void play(Game game, GamerService gamerService, ChanceService chanceService,
                     BankService bankService, CellServices cellServices, StreetService streetService,
                     UtilityAndStationService utilityAndStationService) throws Exception {
        while (game.getPlayerMoves().size() > 1) {
            Gamer gamer = game.getPlayerMoves().poll();
            game.getPlayerMoves().offer(gamer);
            if (!game.getCanGamerDoStep().get(gamer)) {
                gamerService.makeNextSkipOrNotSkip(game, gamer);
            } else {
                gamerService.doSomething(gamer, game, chanceService, bankService, cellServices, streetService, utilityAndStationService);
            }
            Thread.sleep(2000);
        }
        System.out.println(game.getPlayerMoves().poll().getName() + " победил !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
