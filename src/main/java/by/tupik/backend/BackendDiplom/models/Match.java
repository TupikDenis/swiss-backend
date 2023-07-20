package by.tupik.backend.BackendDiplom.models;

import by.tupik.backend.BackendDiplom.dto.MatchResult;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "matches")
public class Match implements Comparable<Match>{
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    @JsonBackReference
    private Tournament tournament;

    @Column(name = "round")
    private Integer round;

    @ManyToOne
    @JoinColumn(name = "white_player_id")
    @JsonBackReference
    private Player whitePlayer;

    @Column(name = "result")
    @Enumerated(EnumType.STRING)
    private MatchResult result;

    @ManyToOne
    @JoinColumn(name = "black_player_id")
    @JsonBackReference
    private Player blackPlayer;

    public Match(){}

    public Match(int id, Tournament tournament, Integer round, Player whitePlayer,
                 MatchResult result, Player blackPlayer) {
        this.id = id;
        this.tournament = tournament;
        this.round = round;
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

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public Integer getRound() {
        return round;
    }

    public void setRound(Integer round) {
        this.round = round;
    }

    public Player getWhitePlayer() {
        return whitePlayer;
    }

    public void setWhitePlayer(Player whitePlayer) {
        this.whitePlayer = whitePlayer;
    }

    public MatchResult getResult() {
        return result;
    }

    public void setResult(MatchResult result) {
        this.result = result;
    }

    public Player getBlackPlayer() {
        return blackPlayer;
    }

    public void setBlackPlayer(Player blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    @Override
    public int compareTo(Match o) {
        if (this.round < o.getId()) {
            return -1;
        }
        if (this.round > o.getId()) {
            return 1;
        }
        return 0;
    }
}
