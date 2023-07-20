package by.tupik.backend.BackendDiplom.servicies;

import by.tupik.backend.BackendDiplom.algorithms.RobinRound.RobinRound;
import by.tupik.backend.BackendDiplom.algorithms.Swiss.Swiss;
import by.tupik.backend.BackendDiplom.dto.*;
import by.tupik.backend.BackendDiplom.models.Match;
import by.tupik.backend.BackendDiplom.models.Player;
import by.tupik.backend.BackendDiplom.models.Tournament;
import by.tupik.backend.BackendDiplom.repositories.TournamentsRepository;
import by.tupik.backend.BackendDiplom.utils.MappingUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Service
public class TournamentsService {
    private final TournamentsRepository tournamentsRepository;
    private final MatchesService matchesService;
    private final MappingUtils mappingUtils;

    @Autowired
    public TournamentsService(TournamentsRepository tournamentsRepository, MatchesService matchesService, MappingUtils mappingUtils) {
        this.tournamentsRepository = tournamentsRepository;
        this.matchesService = matchesService;
        this.mappingUtils = mappingUtils;
    }

    @Transactional
    public void create(Tournament tournament){
        tournamentsRepository.save(tournament);
    }

    public List<Tournament> findAll() {
        return tournamentsRepository.findAll();
    }

    public Tournament findOne(int id) {
        Tournament tournament = tournamentsRepository.findById(id).orElse(null);
        /*List<Match> allMatches = Objects.requireNonNull(tournament).getMatches();

        boolean isSorted = false;
        int buf;

        while(!isSorted) {
            isSorted = true;

            for (int i = 0; i < allMatches.size() - 1; i++) {
                if(allMatches.get(i).getId() > allMatches.get(i+1).getId()){
                    isSorted = false;

                    buf = allMatches.get(i).getId();
                    allMatches.get(i).setId(allMatches.get(i+1).getId());
                    allMatches.get(i+1).setId(buf);
                }
            }
        }

        tournament.setMatches(allMatches);*/
        return tournament;
    }

    public List<RoundMatching> getTimetable(List<MatchDTO> matchDTOs){
        List<RoundMatching> timetable = new ArrayList<>();

        for(int i = 0; i < matchDTOs.get(0).getTournament().getRounds(); i++){
            int currentRound = matchDTOs.get(i).getRound();
            RoundMatching roundMatching = new RoundMatching(currentRound);
            int roundCount = matchDTOs.get(0).getTournament().getPlayers().size() / 2;

            for(int j = i; j < roundCount + i; j++){
                roundMatching.addMatch(matchDTOs.get(j).getWhitePlayer(), matchDTOs.get(j).getBlackPlayer(), matchDTOs.get(j).getTournament());
            }

            timetable.add(roundMatching);
        }

        return timetable;
    }

    public List<PlayerDTO> getTable(List<Player> players, int currentRound, int rounds){
        List<PlayerDTO> playerDTOs = new ArrayList<>();

        for (Player player : players) {
            playerDTOs.add(mappingUtils.convertToPlayerDTO(player));
        }

        for(int i = 0; i < playerDTOs.size(); i++){
            playerDTOs.get(i).setScore(calculateScore(players.get(i)));
        }

        playerDTOs.sort(null);

        for(int i = 0; i < playerDTOs.size(); i++){
            playerDTOs.get(i).setBuchholz(calculateBuchholz(players.get(i)));
            playerDTOs.get(i).setAverageBuchholz(calculateAverageBuchholz(players.get(i), rounds, currentRound));
            playerDTOs.get(i).setTruncatedBuchholz(calculateTruncatedBuchholz(players.get(i), rounds, currentRound));
            playerDTOs.get(i).setBerger(calculateBerger(players.get(i)));
            playerDTOs.get(i).setKoya(calculateKoya(players.get(i), currentRound));
        }

        playerDTOs.sort(null);

        return playerDTOs;
    }

    private double calculateScore(Player player){
        double score = 0.0;
        List<Match> playerMatches = matchesService.findMatchesByPlayer(player);

        for (Match match : playerMatches) {
            if(match.getWhitePlayer().equals(player)){
                switch (match.getResult()){
                    case WHITE_PLAYER_WON:
                        score += 1.0;
                        break;
                    case DRAW:
                        score += 0.5;
                        break;
                    default:
                        break;
                }
            } else if (match.getBlackPlayer().equals(player)) {
                switch (match.getResult()){
                    case BLACK_PLAYER_WON:
                        score += 1.0;
                        break;
                    case DRAW:
                        score += 0.5;
                        break;
                    default:
                        break;
                }
            }
        }

        return score;
    }

    public double calculateBuchholz(Player player){
        double buchholz = 0.0;
        List<Player> opponents = getPlayerOpponents(player);

        for (Player opponent : opponents) {
            buchholz += calculateScore(opponent);
        }

        return buchholz;
    }

    private List<Player> getPlayerOpponents(Player player){
        List<Match> playerMatches = matchesService.findMatchesByPlayer(player);
        List<Player> opponents = new ArrayList<>();

        for (Match match : playerMatches) {
            if(match.getWhitePlayer().equals(player)) {
                if(match.getResult() != MatchResult.NO_RESULT){
                    opponents.add(match.getBlackPlayer());
                }
            } else if(match.getBlackPlayer().equals(player)) {
                if(match.getResult() != MatchResult.NO_RESULT){
                    opponents.add(match.getWhitePlayer());
                }
            }
        }

        return opponents;
    }

    public double calculateAverageBuchholz(Player player, int rounds, int currentRound){
        double averageBuchholz = 0.0;
        if(currentRound > 2){
            List<Player> opponents = getPlayerOpponents(player);
            List<Double> scores = new ArrayList<>();

            for (Player opponent : opponents) {
                scores.add(calculateScore(opponent));
            }

            Collections.sort(scores, Collections.reverseOrder());

            for(int i = 1; i < scores.size() - 1; i++){
                averageBuchholz += scores.get(i);
            }
        }

        return averageBuchholz;
    }

    public double calculateTruncatedBuchholz(Player player, int rounds, int currentRound){
        double truncatedBuchholz = 0.0;
        if(currentRound > 2){
            List<Player> opponents = getPlayerOpponents(player);
            List<Double> scores = new ArrayList<>();

            for (Player opponent : opponents) {
                scores.add(calculateScore(opponent));
            }

            Collections.sort(scores, Collections.reverseOrder());

            for(int i = 0; i < scores.size() - 1; i++){
                truncatedBuchholz += scores.get(i);
            }
        }

        return truncatedBuchholz;
    }

    public double calculateBerger(Player player){
        double berger = 0.0;
        List<Match> playerMatches = matchesService.findMatchesByPlayer(player);

        for (Match match : playerMatches) {
            if(match.getWhitePlayer().equals(player)){
                switch (match.getResult()){
                    case NO_RESULT:
                        break;
                    case WHITE_PLAYER_WON:
                        berger += calculateScore(match.getWhitePlayer());
                        break;
                    case BLACK_PLAYER_WON:
                        break;
                    case DRAW:
                        berger += calculateScore(match.getWhitePlayer()) / 2;
                        break;
                    default:
                        break;
                }
            } else if (match.getBlackPlayer().equals(player)) {
                switch (match.getResult()){
                    case NO_RESULT:
                        break;
                    case WHITE_PLAYER_WON:
                        break;
                    case BLACK_PLAYER_WON:
                        berger += calculateScore(match.getWhitePlayer());
                        break;
                    case DRAW:
                        berger += calculateScore(match.getWhitePlayer()) / 2;
                        break;
                    default:
                        break;
                }
            }
        }

        return berger;
    }

    public double calculateKoya(Player player, int currentRound){
        double koya = 0.0;
        List<Match> playerMatches = matchesService.findMatchesByPlayer(player);

        for (Match match : playerMatches) {
            if(match.getWhitePlayer().equals(player)){
                double opponentScore = calculateScore(match.getBlackPlayer());
                switch (match.getResult()){
                    case NO_RESULT:
                        break;
                    case WHITE_PLAYER_WON:
                        if(currentRound / 2. <= opponentScore){
                            koya += 1.0;
                        }
                        break;
                    case BLACK_PLAYER_WON:
                        break;
                    case DRAW:
                        if(currentRound / 2. <= opponentScore){
                            koya += 0.5;
                        }
                        break;
                    default:
                        break;
                }
            } else if (match.getBlackPlayer().equals(player)) {
                double opponentScore = calculateScore(match.getWhitePlayer());

                switch (match.getResult()){
                    case NO_RESULT:
                        break;
                    case WHITE_PLAYER_WON:
                        break;
                    case BLACK_PLAYER_WON:
                        if(currentRound / 2. <= opponentScore) {
                            koya += 1.0;
                        }
                        break;
                    case DRAW:
                        if(currentRound / 2. <= opponentScore) {
                            koya += 0.5;
                        }
                        break;
                    default:
                        break;
                }
            }
        }

        return koya;
    }

    @Transactional
    public void update(int id, Tournament updatedTournament){
        updatedTournament.setId(id);
        tournamentsRepository.save(updatedTournament);
    }

    @Transactional
    public void delete(int id){
        tournamentsRepository.deleteById(id);
    }

    public void generateScheduler(Tournament tournament, String typeString, String ruleString, int robin){
        TournamentType tournamentType = setTournamentType(typeString);

        switch (tournamentType){
            case ROBIN_ROUND:
                robinRoundSystem(tournament, ruleString, robin);
                break;
            case SWISS:
                swissSystem(tournament, ruleString);
                break;
            default:
                throw new IllegalArgumentException("do not set tournament type");
        }

    }

    @Transactional
    private void robinRoundSystem(Tournament tournament, String ruleString, int robin){
        List<Match> matches = tournament.getMatches();
        List<MatchDTO> matchDTOs = new ArrayList<>();

        for (Match match: matches) {
            matchDTOs.add(mappingUtils.convertToMatchDTO(match));
        }

        List<Player> players = tournament.getPlayers();

        int allRounds = (players.size() - 1) * robin;

        if(tournament.getRounds() != null){
            if(tournament.getRounds() == allRounds){
                throw new IllegalArgumentException("also scheduler");
            } else {
                //matchesService.deleteAll(tournament);
                //TODO: удаление матчей
            }
        }

        tournament.setRounds(allRounds);
        tournament.setCurrentRound(1);
        tournamentsRepository.save(tournament);

        MatchingRule rule = setMatchingRule(ruleString);

        TournamentDTO tournamentDTO = mappingUtils.convertToTournamentDTO(tournament, getTable(players, tournament.getCurrentRound(), tournament.getRounds()), getTimetable(matchDTOs));

        List<PlayerDTO> playerDTOs = new ArrayList<>();

        for(int i = 0; i < players.size(); i++){
            playerDTOs.add(mappingUtils.convertToPlayerDTO(players.get(i)));
        }

        List<RoundMatching> allRoundMatches = RobinRound.createSchedule(rule, playerDTOs, allRounds, tournamentDTO);
        matchesService.createAllMatches(allRoundMatches);
    }

    @Transactional
    private void swissSystem(Tournament tournament, String ruleString){
        List<Match> matches = tournament.getMatches();
        List<MatchDTO> matchDTOs = new ArrayList<>();

        for (Match match: matches) {
            matchDTOs.add(mappingUtils.convertToMatchDTO(match));
        }

        MatchingRule rule = setMatchingRule(ruleString);

        List<Player> players = tournament.getPlayers();
        List<PlayerDTO> playerDTOs = new ArrayList<>();

        for(int i = 0; i < players.size(); i++){
            playerDTOs.add(mappingUtils.convertToPlayerDTO(players.get(i)));
        }

        int rounds = tournament.getRounds();
        int currentRound = tournament.getCurrentRound();



        TournamentDTO tournamentDTO = mappingUtils.convertToTournamentDTO(tournament, getTable(players, tournament.getCurrentRound(), tournament.getRounds()), getTimetable(matchDTOs));

        Swiss.pairNextRound(rule, playerDTOs, rounds, currentRound, tournamentDTO);
    }

    private MatchingRule setMatchingRule(String rule){
        switch(rule){
            case "order":
                return MatchingRule.MATCH_ORDERED;
            case "random":
                return MatchingRule.MATCH_RANDOM;
            default:
                throw new IllegalArgumentException("do not set rule for creating scheduler (service)");
        }
    }

    private TournamentType setTournamentType(String type){
        switch(type){
            case "swiss":
                return TournamentType.SWISS;
            case "robin":
                return TournamentType.ROBIN_ROUND;
            default:
                throw new IllegalArgumentException("do not set tournament type");
        }
    }
}
