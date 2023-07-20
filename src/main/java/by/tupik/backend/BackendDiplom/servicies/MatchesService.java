package by.tupik.backend.BackendDiplom.servicies;

import by.tupik.backend.BackendDiplom.dto.MatchDTO;
import by.tupik.backend.BackendDiplom.dto.MatchResult;
import by.tupik.backend.BackendDiplom.dto.RoundMatching;
import by.tupik.backend.BackendDiplom.models.Match;
import by.tupik.backend.BackendDiplom.models.Player;
import by.tupik.backend.BackendDiplom.models.Tournament;
import by.tupik.backend.BackendDiplom.repositories.MatchesRepository;
import by.tupik.backend.BackendDiplom.utils.MappingUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class MatchesService {
    private final MatchesRepository matchesRepository;
    private final TournamentsService tournamentsService;
    private final MappingUtils mappingUtils;

    @Autowired
    public MatchesService(MatchesRepository matchesRepository,
                          @Lazy TournamentsService tournamentsService, MappingUtils mappingUtils) {
        this.matchesRepository = matchesRepository;
        this.tournamentsService = tournamentsService;
        this.mappingUtils = mappingUtils;
    }

    @Transactional
    public void create(Match match){
        matchesRepository.save(match);
    }

    @Transactional
    public void createAllMatches(List<RoundMatching> allRoundMatches){
        List<List<Match>> allMatches = new ArrayList<>();

        for(RoundMatching currentRoundMatches : allRoundMatches){
            List<MatchDTO> roundMatchesDTO = currentRoundMatches.getMatches();

            List<Match> roundMatches = new ArrayList<>();
            for(MatchDTO matchDTO : roundMatchesDTO){
                Match match = mappingUtils.convertToMatch(matchDTO);
                roundMatches.add(match);
            }

            allMatches.add(roundMatches);
        }

        for(List<Match> roundMatches : allMatches){
            matchesRepository.saveAll(roundMatches);
        }
    }

    public List<Match> findAll() {
        return matchesRepository.findAllByOrderByIdAsc();
    }

    public List<Match> findMatchesByPlayer(Player player){
        List<Match> white = matchesRepository.findByWhitePlayer(player);
        List<Match> black = matchesRepository.findByBlackPlayer(player);
        white.addAll(black);
        white.sort(null);
        return white;
    }

    public List<Match> findMatchesByTournament(Tournament tournament){
        return matchesRepository.findByTournamentOrderByIdAsc(tournament);
    }

    public Match findOne(int id) {
        return matchesRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Match updatedMatch){
        updatedMatch.setId(id);
        matchesRepository.save(updatedMatch);
        checkCurrentRound();
    }

    @Transactional
    public void delete(int id){
        matchesRepository.deleteById(id);
    }

    /*@Transactional
    public void deleteAllTournamentMatches(Tournament tournament){
        matchesRepository.deleteAll(tournament);
    }*/

    public void checkCurrentRound(){
        List<Match> allMatches = matchesRepository.findAllByOrderByIdAsc();

        for (Match match : allMatches) {
            if(match.getResult() == MatchResult.NO_RESULT){
                match.getTournament().setCurrentRound(match.getRound());
                tournamentsService.update(match.getTournament().getId(), match.getTournament());
                break;
            }
        }
    }
}
