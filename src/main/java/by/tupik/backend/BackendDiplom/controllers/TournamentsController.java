package by.tupik.backend.BackendDiplom.controllers;

import by.tupik.backend.BackendDiplom.dto.*;
import by.tupik.backend.BackendDiplom.models.Match;
import by.tupik.backend.BackendDiplom.models.Player;
import by.tupik.backend.BackendDiplom.models.Tournament;
import by.tupik.backend.BackendDiplom.servicies.TournamentsService;
import by.tupik.backend.BackendDiplom.utils.MappingUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/tournaments")
public class TournamentsController {
    private final TournamentsService tournamentsService;
    private final MappingUtils mappingUtils;

    @Autowired
    public TournamentsController(TournamentsService tournamentsService, MappingUtils mappingUtils) {
        this.tournamentsService = tournamentsService;
        this.mappingUtils = mappingUtils;
    }

    @PostMapping()
    public Tournament createTournament(@RequestBody @Valid Tournament tournament, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        tournamentsService.create(tournament);

        return tournament;
    }

    @PostMapping("/generate/{type}/{robin}/{rule}")
    public void addMatches(@RequestBody @Valid TournamentDTO tournamentDTO,
                           @PathVariable String type,
                           @PathVariable int robin,
                           @PathVariable String rule,
                           BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return;
        }

        Tournament tournament = mappingUtils.convertToTournament(tournamentDTO);
        tournamentsService.generateScheduler(tournament, type, rule, robin);
    }

    @GetMapping()
    public TournamentsResponse getTournaments() {
        List<Tournament> tournaments = tournamentsService.findAll();
        List<TournamentDTO> tournamentDTOs = new ArrayList<>();

        for (Tournament tournament : tournaments) {
            tournamentDTOs.add(mappingUtils.convertToTournamentDTO(tournament, null, null));
        }

        return new TournamentsResponse(tournamentDTOs);
    }

    @GetMapping("/{id}")
    public TournamentDTO getTournament(@PathVariable("id") int id) {
        Tournament tournament = tournamentsService.findOne(id);
        List<Player> players = tournament.getPlayers();
        List<Match> matches = tournament.getMatches();

        if(tournament.getRounds() == null){
            tournament.setRounds(0);
        }

        if(tournament.getCurrentRound() == null){
            tournament.setCurrentRound(0);
        }

        List<MatchDTO> matchDTOs = new ArrayList<>();
        for(Match match : matches){
            matchDTOs.add(mappingUtils.convertToMatchDTO(match));
        }

        List<PlayerDTO> table = tournamentsService.getTable(players, tournament.getCurrentRound(), tournament.getRounds());
        List<RoundMatching> timetable = tournamentsService.getTimetable(matchDTOs);

        return mappingUtils.convertToTournamentDTO(tournament, table, timetable);
    }

    @PutMapping("/{id}")
    public Tournament updateTournament(@PathVariable("id") int id, @RequestBody @Valid Tournament updatedTournament, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return null;
        }

        tournamentsService.update(id, updatedTournament);

        return updatedTournament;
    }

    @DeleteMapping("/{id}")
    public int deleteTournament(@PathVariable("id") int id){
        tournamentsService.delete(id);
        return id;
    }
}
