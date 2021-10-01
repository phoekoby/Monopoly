package Monopoly.services;

import Monopoly.models.Game;
import Monopoly.models.Gamer;
import Monopoly.models.cells.ChanceCard;
import Monopoly.models.cells.TypeOfChance;

import java.util.LinkedList;
import java.util.Queue;

public class ChanceService {
    public Queue<ChanceCard> generateStack(Game game){
        Queue<ChanceCard> stack = new LinkedList<>();
        stack.offer(new ChanceCard(TypeOfChance.GIFT, "У вас день рождения, получите 200 долларов",200));
        stack.offer(new ChanceCard(TypeOfChance.GO_TO,"Отправляйтесь на старт", game.getStart()));
        stack.offer(new ChanceCard(TypeOfChance.GO_TO_JAIL,"Отправляйтесь в тюрьму", game.getJail()));
        stack.offer(new ChanceCard(TypeOfChance.TAX, "Подоходный налог 150 долларов", 150));
        stack.offer(new ChanceCard(TypeOfChance.GO_TO, "Посетите тюрьму", game.getJail()));
        stack.offer(new ChanceCard(TypeOfChance.SKIP, "Пропустите следующий ход"));
        stack.offer(new ChanceCard(TypeOfChance.DO_STEPS,"Пройдите на три клетки вперед", 3));
        stack.offer(new ChanceCard(TypeOfChance.TAX, "Оплатите страхову в размере 100 долларов",100));
        stack.offer(new ChanceCard(TypeOfChance.GIFT, "Получите 300 долларов от страховой компании",300));
        return stack;
    }
    public ChanceCard getCardFromStack(Queue<ChanceCard> chanceCards){
        ChanceCard card = chanceCards.poll();
        chanceCards.offer(card);
        return card;
    }
    public void gamerCameOnChance(Game game, Gamer gamer, GamerService gamerService) throws Exception{
        ChanceCard card = getCardFromStack(game.getChances());
        System.out.println("Игрок вытаскивает карточку: " + card.getMessage());
        switch (card.getType()){
            case TAX -> gamerService.recalculationMoney(game,gamer,-card.getCountOfMoneyOrSteps());
            case GIFT -> gamerService.recalculationMoney(game,gamer,card.getCountOfMoneyOrSteps());
            case DO_STEPS -> {gamerService.checkWhichCardAndWhatToDo(game,gamer,gamerService.step(game,gamer,card.getCountOfMoneyOrSteps()));}
            case GO_TO_JAIL -> gamerService.gamerGoTo(game,gamer,card.getGo_to());
            case SKIP -> gamerService.makeNextSkipOrNotSkip(game,gamer);
            case GO_TO -> gamerService.gamerGoTo(game,gamer, card.getGo_to());
            //case
        }

    }
}
