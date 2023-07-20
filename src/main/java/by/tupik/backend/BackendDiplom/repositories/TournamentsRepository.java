package by.tupik.backend.BackendDiplom.repositories;

import by.tupik.backend.BackendDiplom.models.Tournament;
import by.tupik.backend.BackendDiplom.servicies.TournamentsService;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentsRepository extends JpaRepository<Tournament, Integer> {
    //TODO: реализация поиска турнира по его названию (использовать LIKE)
}
