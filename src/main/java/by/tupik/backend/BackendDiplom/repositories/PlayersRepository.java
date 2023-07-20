package by.tupik.backend.BackendDiplom.repositories;

import by.tupik.backend.BackendDiplom.models.Player;
import by.tupik.backend.BackendDiplom.models.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PlayersRepository extends JpaRepository<Player, Integer> {
}
