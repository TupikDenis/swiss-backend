package by.tupik.backend.BackendDiplom.dto;

import java.util.List;

public class MatchesResponse {
    private List<MatchDTO> matches;

    public MatchesResponse(List<MatchDTO> matches) {
        this.matches = matches;
    }

    public List<MatchDTO> getMatches() {
        return matches;
    }

    public void setMatches(List<MatchDTO> matches) {
        this.matches = matches;
    }
}
