package by.tupik.backend.BackendDiplom.dto;

import java.util.List;

public class ActionsResponse {
    private List<ActionDTO> actions;

    public ActionsResponse(List<ActionDTO> actions) {
        this.actions = actions;
    }

    public List<ActionDTO> getActions() {
        return actions;
    }

    public void setActions(List<ActionDTO> actions) {
        this.actions = actions;
    }
}
