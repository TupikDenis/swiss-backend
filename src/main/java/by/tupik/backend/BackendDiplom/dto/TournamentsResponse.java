package by.tupik.backend.BackendDiplom.dto;

import java.util.List;

public class TournamentsResponse {
    private List<TournamentDTO> tournaments;

    public TournamentsResponse(List<TournamentDTO> tournaments) {
        this.tournaments = tournaments;
    }

    public List<TournamentDTO> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<TournamentDTO> tournaments) {
        this.tournaments = tournaments;
    }
}
