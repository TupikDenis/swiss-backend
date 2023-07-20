package by.tupik.backend.BackendDiplom.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

public class MatchDTO {
    private int id;
    @JsonIgnore
    private TournamentDTO tournament;
    private Integer round;
    @JsonBackReference
    private PlayerDTO whitePlayer;
    private MatchResult result;
    @JsonBackReference
    private PlayerDTO blackPlayer;

    public MatchDTO(){}

    public MatchDTO(Integer round, TournamentDTO tournament, PlayerDTO whitePlayer, MatchResult result, PlayerDTO blackPlayer) {
        this.round = round;
        this.tournament = tournament;
        this.whitePlayer = whitePlayer;
        this.result = result;
        this.blackPlayer = blackPlayer;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TournamentDTO getTournament() {
        return tournament;
    }

    public void setTournament(TournamentDTO tournament) {
        this.tournament = tournament;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public PlayerDTO getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(PlayerDTO whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public MatchResult getResult() {
        return result;
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

    public PlayerDTO getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(PlayerDTO blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public boolean hasResult() {
        return result != MatchResult.NO_RESULT;
    }

    public boolean hasPlayer(PlayerDTO player) {
        return (player.equals(whitePlayer) || (player.equals(blackPlayer)));
    }

    public boolean hasPlayers(PlayerDTO player1, PlayerDTO player2) {
        return ((player1.equals(whitePlayer)) && (player2.equals(blackPlayer))) ||
                ((player1.equals(blackPlayer)) && (player2.equals(whitePlayer)));
    }
}
