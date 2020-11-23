package se2.thread;

import se2.db.InMemoryDB;
import se2.model.Player;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.stream.Collectors;

public class CountPlayerScore implements Callable<String> {

    public CountPlayerScore()
    {

    }

    
    @Override
    public String call() {
try
{
    synchronized (InMemoryDB.class) {
        Map<Integer, Player> players = InMemoryDB.getInstance().getMultiPlayers();

        LinkedHashMap<Integer, Player> sortedPlayers = players.entrySet().parallelStream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue().getScore(), e1.getValue().getScore())).
                        collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e2, LinkedHashMap::new));

        InMemoryDB.getInstance().setSortedPlayers(sortedPlayers);
    }
    return "success";
}
catch (Exception e)
{
return "Fail: "+e.getMessage();
}

    }
}
