package by.tupik.backend.BackendDiplom.controllers;

import by.tupik.backend.BackendDiplom.dto.MatchDTO;
import by.tupik.backend.BackendDiplom.dto.MatchesResponse;
import by.tupik.backend.BackendDiplom.models.Match;
import by.tupik.backend.BackendDiplom.models.Tournament;
import by.tupik.backend.BackendDiplom.servicies.MatchesService;
import by.tupik.backend.BackendDiplom.servicies.TournamentsService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/matches")
public class MatchesController {
    private final MatchesService matchesService;
    private final TournamentsService tournamentsService;
    private final ModelMapper modelMapper;

    @Autowired
    public MatchesController(MatchesService matchesService, TournamentsService tournamentsService, ModelMapper modelMapper) {
        this.matchesService = matchesService;
        this.tournamentsService = tournamentsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public Match createMatch(@RequestBody @Valid Match match, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        matchesService.create(match);

        return match;
    }

    @GetMapping()
    public MatchesResponse getAllMatches(){
        return new MatchesResponse(matchesService.findAll().stream().map(this::convertToMatchesDTO).collect(Collectors.toList()));
    }

    private Match convertToMatches(MatchDTO matchesDTO) {
        return modelMapper.map(matchesDTO, Match.class);
    }

    private MatchDTO convertToMatchesDTO(Match matches) {
        return modelMapper.map(matches, MatchDTO.class);
    }

    @GetMapping("/{id}")
    public MatchDTO getMatch(@PathVariable int id){
        return convertToMatchesDTO(matchesService.findOne(id));
    }

    @GetMapping("/tournament/{id}")
    public MatchesResponse getAllTournamentMatches(@PathVariable int id){
        Tournament tournament = tournamentsService.findOne(id);
        return new MatchesResponse(matchesService.findMatchesByTournament(tournament).stream().map(this::convertToMatchesDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public Match updateMatch(@PathVariable int id, @RequestBody @Valid MatchDTO updatedMatchDTO, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        Match updatedMatch = convertToMatches(updatedMatchDTO);
        matchesService.update(id, updatedMatch);

        return updatedMatch;
    }

    @DeleteMapping("/{id}")
    public int deleteMatches(@PathVariable int id){
        matchesService.delete(id);
        return id;
    }
}
