package me.ma1de.practice.queue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.scheduler.BukkitRunnable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.ma1de.practice.Practice;
import me.ma1de.practice.kit.Kit;
import me.ma1de.practice.match.Match;
import me.ma1de.practice.util.Pair;

@Getter @AllArgsConstructor
public class Queue {
    private String id;
    private Kit kit;
    private List<UUID> queueing;
    private boolean ranked, open;

    /**
     * Gets first and the last player in the queueing list.
     *
     * Example of this queueing algorithm:
     * [player1, player2, player3, player4, player5, player6] - list of players queueing
     * =>
     * [player1, player6] - pair
     * =>
     * Match([player1, player6])
     * =>
     * [player2, player3, player4, player5] - list of players queueing
     * =>
     * ... (loop)
     * =>
     * [] - empty queue list
     *
     * Issues of this algorithm:
     * - If the amount of players queueing is odd, there's always going to be
     *   a player left out.
     * - Possibly slow.
     * 
     * @return Pair<UUID, UUID>
     */
    public Pair<UUID, UUID> getPair() {
        if (queueing.size() < 2) {
            return null;
        }

        Pair<UUID, UUID> pair = new Pair<>();
        pair.setFirst(queueing.get(0));
        pair.setSecond(queueing.get(queueing.size() - 1));

        return pair;
    }

    public void start() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (getPair() == null) {
                    return;
                }

                Pair<UUID, UUID> pair = getPair();

                List<UUID> firstTeam = new ArrayList<>();
                List<UUID> secondTeam = new ArrayList<>();
                firstTeam.add(pair.getFirst());
                secondTeam.add(pair.getSecond());

                Match match = new Match(Practice.getInstance().getArenaHandler().getRandomArena(), kit, firstTeam, secondTeam, 0, 0, ranked, false);

                Practice.getInstance().getMatchHandler().addMatch(match);
                match.start();
            }
        };
    }
}
