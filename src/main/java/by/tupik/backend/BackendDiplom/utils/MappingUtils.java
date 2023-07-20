package by.tupik.backend.BackendDiplom.utils;

import by.tupik.backend.BackendDiplom.dto.*;
import by.tupik.backend.BackendDiplom.models.Action;
import by.tupik.backend.BackendDiplom.models.Match;
import by.tupik.backend.BackendDiplom.models.Player;
import by.tupik.backend.BackendDiplom.models.Tournament;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MappingUtils {
    private final ModelMapper modelMapper;

    @Autowired
    public MappingUtils(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public Tournament convertToTournament(TournamentDTO tournamentDTO) {
        return modelMapper.map(tournamentDTO, Tournament.class);
    }

    public TournamentDTO convertToTournamentDTO(Tournament tournament, List<PlayerDTO> table, List<RoundMatching> roundMatching) {
        TournamentDTO tournamentDTO = modelMapper.map(tournament, TournamentDTO.class);
        tournamentDTO.setPlayers(table);
        tournamentDTO.setRoundMatching(roundMatching);
        return tournamentDTO;
    }

    public Action convertToActions(ActionDTO actionsDTO) {
        return modelMapper.map(actionsDTO, Action.class);
    }

    public ActionDTO convertToActionsDTO(Action actions) {
        return modelMapper.map(actions, ActionDTO.class);
    }

    public Player convertToPlayer(PlayerDTO playerDTO) {
        return modelMapper.map(playerDTO, Player.class);
    }

    public PlayerDTO convertToPlayerDTO(Player player) {
        return modelMapper.map(player, PlayerDTO.class);
    }

    public Match convertToMatch(MatchDTO matchDTO) {
        return modelMapper.map(matchDTO, Match.class);
    }

    public MatchDTO convertToMatchDTO(Match match) {
        return modelMapper.map(match, MatchDTO.class);
    }
}
