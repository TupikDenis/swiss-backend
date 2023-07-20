package by.tupik.backend.BackendDiplom.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoundMatching {
    private int round;
    @JsonManagedReference
    private List<MatchDTO> matches;

    public RoundMatching(){
    }

    public RoundMatching(int round) {
        this.round = round;
        matches = new ArrayList<>();
    }

    public void setMatches(List<MatchDTO> matches) {
        this.matches = matches;
    }

    public List<MatchDTO> getMatches() {
        return Collections.unmodifiableList(matches);
    }


    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public MatchDTO addMatch(PlayerDTO player1, PlayerDTO player2, TournamentDTO tournament) {
        for (MatchDTO match : matches) {
            if (match.getWhitePlayer().equals(player1)) {
                throw new IllegalArgumentException("Could not add match " + player1 + " - " + player2 + " : player 1 already matches");
            }
            if (match.getBlackPlayer().equals(player2)) {
                throw new IllegalArgumentException("Could not add match " + player1 + " - " + player2 + " : player 2 already matches");
            }
        }
        MatchDTO match = new MatchDTO(round, tournament, player1, MatchResult.NO_RESULT, player2);
        matches.add(match);
        player1.setOpponents(player2);
        player2.setOpponents(player1);
        return match;
    }

    public boolean hasMatchForPlayer(PlayerDTO player) {
        for (MatchDTO match : matches) {
            if (match.hasPlayer(player)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeMatchWithPlayer(PlayerDTO player) {
        MatchDTO foundMatch = null;
        for (MatchDTO match : matches) {
            if (match.hasPlayer(player)) {
                foundMatch = match;
                break;
            }
        }
        if (foundMatch != null) {
            foundMatch.getWhitePlayer().cancelGame();
            foundMatch.getWhitePlayer().removeLastOpponent();
            foundMatch.getBlackPlayer().cancelGame();
            foundMatch.getBlackPlayer().removeLastOpponent();
            foundMatch.getWhitePlayer().removeLastColors();
            foundMatch.getBlackPlayer().removeLastColors();
            return matches.remove(foundMatch);
        }
        return false;
    }

    public void removeLastMatches(int count) {
        matches.get(matches.size() - 1).getWhitePlayer().cancelGame();
        matches.get(matches.size() - 1).getBlackPlayer().cancelGame();
        matches.get(matches.size() - 1).getWhitePlayer().removeLastOpponent();
        matches.get(matches.size() - 1).getBlackPlayer().removeLastOpponent();
        matches.get(matches.size() - 1).getWhitePlayer().removeLastColors();
        matches.get(matches.size() - 1).getBlackPlayer().removeLastColors();

        matches.remove(matches.size() - 1);

        /*for(int i = 0; i < count; i++){
            matches.get(matches.size() - 1).getWhitePlayer().cancelGame();
            matches.get(matches.size() - 1).getBlackPlayer().cancelGame();
            matches.get(matches.size() - 1).getWhitePlayer().removeLastOpponent();
            matches.get(matches.size() - 1).getBlackPlayer().removeLastOpponent();
            matches.get(matches.size() - 1).getWhitePlayer().removeLastColors();
            matches.get(matches.size() - 1).getBlackPlayer().removeLastColors();
            matches.remove(matches.size() - 1);
        }*/
    }

    public boolean hasAllResults() {
        for (MatchDTO match : matches) {
            if (!match.hasResult()) {
                return false;
            }
        }
        return true;
    }
}
