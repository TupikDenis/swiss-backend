package by.tupik.backend.BackendDiplom.repositories;

import by.tupik.backend.BackendDiplom.models.Action;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActionsRepositry extends JpaRepository<Action, Integer> {
}
