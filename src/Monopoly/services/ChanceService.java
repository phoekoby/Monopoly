package Monopoly.services;

import Monopoly.models.Game;
import Monopoly.models.cells.ChanceCard;
import Monopoly.models.cells.TypeOfChance;

import java.util.LinkedList;
import java.util.Queue;

public class ChanceService {
    public Queue<ChanceCard> createStackOfChances(Game game){
        Queue<ChanceCard> stack = new LinkedList<>();
        stack.offer(new ChanceCard(TypeOfChance.GIFT, "У вас день рождения, получите 200 долларов",200));
        stack.offer(new ChanceCard(TypeOfChance.GO_TO,"Отправляйтесь на старт", game.getStart()));
        return stack;
    }
}
