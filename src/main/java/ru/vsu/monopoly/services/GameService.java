package ru.vsu.monopoly.services;

import ru.vsu.monopoly.models.Game;
import ru.vsu.monopoly.models.Gamer;
import ru.vsu.monopoly.models.cells.PropertiesType;
import ru.vsu.monopoly.models.cells.Cell;
import ru.vsu.monopoly.models.cells.CellType;


import java.util.*;

public class GameService {
    private final BankService bankService = new BankService();
    private final ChanceService chanceService = new ChanceService();


    private Cell putCostForEachHouse(Cell cell, Cell card, Game game) {
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
        Map<PropertiesType, Integer> howMany = new HashMap<>();
        howMany.put(PropertiesType.BROWN, 50);
        howMany.put(PropertiesType.BLUE, 200);
        howMany.put(PropertiesType.PINK, 100);
        howMany.put(PropertiesType.RED, 150);
        howMany.put(PropertiesType.ORANGE, 100);
        howMany.put(PropertiesType.GREEN, 200);
        howMany.put(PropertiesType.YELLOW, 150);
        howMany.put(PropertiesType.LIGHT_BLUE, 50);
        game.setHowManyYouNeedToBuildHouse(howMany);
    }

    private void createMap(Game game) {
        Cell start = new Cell("Старт", null, CellType.START, PropertiesType.NONE);
        Cell cell = start;
        cell = putCostForEachHouse(cell, new Cell("Житная ул.", null, CellType.STREET, PropertiesType.BROWN, 60), game);
        cell = putCostForEachHouse(cell, new Cell("Шанс", null, CellType.CHANCE, PropertiesType.NONE), game);
        cell = putCostForEachHouse(cell, new Cell("Нагатинская ул.", null, CellType.STREET, PropertiesType.BROWN, 60), game);
        cell = putCostForEachHouse(cell, new Cell("Подоходный налог", null, CellType.TAX, PropertiesType.NONE, 200), game);
        cell = putCostForEachHouse(cell, new Cell("Рижская жд", null, CellType.STATION, PropertiesType.STATION, 200), game);
        cell = putCostForEachHouse(cell, new Cell("Варшавское шоссе", null, CellType.STREET, PropertiesType.LIGHT_BLUE, 100), game);
        cell = putCostForEachHouse(cell, new Cell("Шанс", null, CellType.CHANCE, PropertiesType.NONE), game);
        cell = putCostForEachHouse(cell, new Cell("Ул. Огарева", null, CellType.STREET, PropertiesType.LIGHT_BLUE, 100), game);
        cell = putCostForEachHouse(cell, new Cell("Первая Парковая ул.", null, CellType.STREET, PropertiesType.LIGHT_BLUE, 120), game);
        cell = putCostForEachHouse(cell, new Cell("В ТЮРЬМЕ", null, CellType.JAIL, PropertiesType.NONE), game);
        cell = putCostForEachHouse(cell, new Cell("Ул. Полянка", null, CellType.STREET, PropertiesType.PINK, 140), game);
        cell = putCostForEachHouse(cell, new Cell("Электростанция", null, CellType.UTILITY, PropertiesType.UTILITY, 150), game);
        cell = putCostForEachHouse(cell, new Cell("Ул. Сретенка", null, CellType.STREET, PropertiesType.PINK, 140), game);
        cell = putCostForEachHouse(cell, new Cell("Ростовская наб.", null, CellType.STREET, PropertiesType.PINK, 160), game);
        cell = putCostForEachHouse(cell, new Cell("Куркская жд", null, CellType.STATION, PropertiesType.STATION, 200), game);
        cell = putCostForEachHouse(cell, new Cell("Рязанский проспект", null, CellType.STREET, PropertiesType.ORANGE, 180), game);
        cell = putCostForEachHouse(cell, new Cell("Шанс", null, CellType.CHANCE, PropertiesType.NONE), game);
        cell = putCostForEachHouse(cell, new Cell("Ул. Вавилова", null, CellType.STREET, PropertiesType.ORANGE, 180), game);
        cell = putCostForEachHouse(cell, new Cell("Рублевское шоссе", null, CellType.STREET, PropertiesType.ORANGE, 200), game);
        cell = putCostForEachHouse(cell, new Cell("Бесплатная стоянка", null, CellType.PARKING, PropertiesType.NONE), game);
        cell = putCostForEachHouse(cell, new Cell("Ул. Тверская", null, CellType.STREET, PropertiesType.RED, 220), game);
        cell = putCostForEachHouse(cell, new Cell("Шанс", null, CellType.CHANCE, PropertiesType.NONE), game);
        cell = putCostForEachHouse(cell, new Cell("Пушкинская ул.", null, CellType.STREET, PropertiesType.RED, 220), game);
        cell = putCostForEachHouse(cell, new Cell("Площадь Маяковского", null, CellType.STREET, PropertiesType.RED, 240), game);
        cell = putCostForEachHouse(cell, new Cell("Казанская жд", null, CellType.STATION, PropertiesType.STATION, 200), game);
        cell = putCostForEachHouse(cell, new Cell("Ул. Грузинский вал", null, CellType.STREET, PropertiesType.YELLOW, 260), game);
        cell = putCostForEachHouse(cell, new Cell("Новинский бульвар", null, CellType.STREET, PropertiesType.YELLOW, 260), game);
        cell = putCostForEachHouse(cell, new Cell("Водопровод", null, CellType.UTILITY, PropertiesType.UTILITY, 150), game);
        cell = putCostForEachHouse(cell, new Cell("Смоленская площадь", null, CellType.STREET, PropertiesType.YELLOW, 280), game);
        Cell jail = cell = putCostForEachHouse(cell, new Cell("Отправляйтесь в тюрьму", null, CellType.GO_TO_JAIL, PropertiesType.NONE), game);
        cell = putCostForEachHouse(cell, new Cell("Ул. Щусева", null, CellType.STREET, PropertiesType.GREEN, 300), game);
        cell = putCostForEachHouse(cell, new Cell("Гоголевский бульвар", null, CellType.STREET, PropertiesType.GREEN, 300), game);
        cell = putCostForEachHouse(cell, new Cell("Шанс", null, CellType.CHANCE, PropertiesType.NONE), game);
        cell = putCostForEachHouse(cell, new Cell("Кутузовский проспект", null, CellType.STREET, PropertiesType.GREEN, 320), game);
        cell = putCostForEachHouse(cell, new Cell("Лениградская жд", null, CellType.STATION, PropertiesType.STATION, 200), game);
        cell = putCostForEachHouse(cell, new Cell("Шанс", null, CellType.CHANCE, PropertiesType.NONE), game);
        cell = putCostForEachHouse(cell, new Cell("Ул. Малая Бронная", null, CellType.STREET, PropertiesType.BLUE, 350), game);
        cell = putCostForEachHouse(cell, new Cell("Сверхналог", null, CellType.TAX, PropertiesType.NONE, 300), game);
        cell = putCostForEachHouse(cell, new Cell("ул. Арбат", null, CellType.STREET, PropertiesType.BLUE, 400), game);
        cell.setNextCell(start);
        game.setStart(start);
        game.setJail(jail);
    }

    public void creatingGame(int countOfGamers, Game game, GamerService gamerService) throws Exception {
        game.setCardsAndOwners(new HashMap<>());
        createMap(game);
        game.setChances(chanceService.generateStack(game));
        gamerService.createGamerList(countOfGamers, game);
        game.setBank(bankService.createBank(game));
        createHowManyForHouse(game);
    }

    public void start(int countOfGamers, Game game, GamerService gamerService) throws Exception {
        creatingGame(countOfGamers, game, gamerService);
        play(game, gamerService);
    }

    /* игра */
    public void play(Game game, GamerService gamerService) throws Exception {
        while (game.getPlayerMoves().size() > 1) {
            Gamer gamer = game.getPlayerMoves().poll();
            game.getPlayerMoves().offer(gamer);
            if (!game.getCanGamerDoStep().get(gamer)) {
                gamerService.makeNextSkipOrNotSkip(game, gamer);
            } else {
                assert gamer != null;
                gamerService.doSomething(gamer, game);
            }
            //Thread.sleep(2000);
        }
        System.out.println(Objects.requireNonNull(game.getPlayerMoves().poll()).getName() + " победил !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
    }

}
