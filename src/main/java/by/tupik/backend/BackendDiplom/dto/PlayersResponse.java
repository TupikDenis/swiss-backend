package by.tupik.backend.BackendDiplom.dto;

import java.util.List;

public class PlayersResponse {
    private List<PlayerDTO> players;

    public PlayersResponse(List<PlayerDTO> players) {
        this.players = players;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }
}
