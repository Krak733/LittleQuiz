package se2;

import org.junit.Assert;
import org.junit.Test;
import se2.model.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class PlayerWinnerTest {

    @Test
    public void PlayerWinnerTest() {
        Map<Integer, Player> players = new HashMap<>();

        players.put(1, new Player(5, "Player1"));
        players.put(2, new Player(7, "Player2"));
        players.put(3, new Player(9, "Player3"));
        players.put(4, new Player(3, "Player4"));

        Map<Integer, Player> sortedPlayers = players.entrySet().parallelStream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().getScore(), e1.getValue().getScore())).
                        collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        ArrayList<Integer> correctWinnerOrder = new ArrayList<>();
        correctWinnerOrder.add(3);
        correctWinnerOrder.add(2);
        correctWinnerOrder.add(1);
        correctWinnerOrder.add(4);

        ArrayList<Integer> playerOrderFromSortedPlayers = new ArrayList<>();

        for (Map.Entry<Integer, Player> entry : sortedPlayers.entrySet()) {
            playerOrderFromSortedPlayers.add(entry.getKey());
        }

        Assert.assertEquals(correctWinnerOrder, playerOrderFromSortedPlayers);

    }
}
