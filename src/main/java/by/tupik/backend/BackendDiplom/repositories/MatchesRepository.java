package by.tupik.backend.BackendDiplom.repositories;

import by.tupik.backend.BackendDiplom.models.Match;
import by.tupik.backend.BackendDiplom.models.Player;
import by.tupik.backend.BackendDiplom.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchesRepository extends JpaRepository<Match, Integer> {
    List<Match> findAllByOrderByIdAsc();
    List<Match> findByWhitePlayer(Player player);
    List<Match> findByBlackPlayer(Player player);
    List<Match> findByTournamentOrderByIdAsc(Tournament tournament);
}
