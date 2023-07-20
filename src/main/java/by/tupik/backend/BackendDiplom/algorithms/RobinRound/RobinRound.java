package by.tupik.backend.BackendDiplom.algorithms.RobinRound;

import by.tupik.backend.BackendDiplom.dto.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RobinRound {
    public static List<RoundMatching> createSchedule(MatchingRule roundMatchingRule, List<PlayerDTO> players, int allRounds, TournamentDTO tournament) {
        if(roundMatchingRule == MatchingRule.MATCH_ORDERED){
            return createScheduleOrderedMatch(players, allRounds, tournament);
        }

        if(roundMatchingRule == MatchingRule.MATCH_RANDOM){
            return createScheduleRandomMatch(players, allRounds, tournament);
        }
        throw new IllegalArgumentException("do not set rule for creating scheduler (robin round)");
    }

    private static List<RoundMatching> createScheduleOrderedMatch(List<PlayerDTO> players, int allRounds, TournamentDTO tournament){
        return robinRoundAlgorithm(players, allRounds, tournament);
    }

    private static List<RoundMatching> robinRoundAlgorithm(List<PlayerDTO> players, int allRounds, TournamentDTO tournament){
        if(players.size() % 2 == 1){
            players.add(new PlayerDTO("bye"));
        }

        List<RoundMatching> allMatches = new ArrayList<>();

        for(int i = 0; i < allRounds; i++){
            List<PlayerDTO> firstHalf = new ArrayList<>();
            List<PlayerDTO> secondHalf = new ArrayList<>();

            for(int j = 0, k = players.size() / 2; j < players.size() / 2 && k < players.size(); j++, k++){
                firstHalf.add(players.get(j));
                secondHalf.add(players.get(k));
            }

            Collections.reverse(secondHalf);

            RoundMatching match = new RoundMatching(i+1);
            for(int j = 0; j < firstHalf.size(); j++){


                if(i % 2 != 0 && j == 0){
                    match.addMatch(secondHalf.get(j), firstHalf.get(j), tournament);
                } else {
                    match.addMatch(firstHalf.get(j), secondHalf.get(j), tournament);
                }
            }

            allMatches.add(i, match);

            PlayerDTO switchPlayer = players.remove(players.size() - 1);
            players.add(1, switchPlayer);
        }

        return allMatches;
    }

    private static List<RoundMatching> createScheduleRandomMatch(List<PlayerDTO> players, int allRounds, TournamentDTO tournament){
        Collections.shuffle(players);
        return robinRoundAlgorithm(players, allRounds, tournament);
    }
}
