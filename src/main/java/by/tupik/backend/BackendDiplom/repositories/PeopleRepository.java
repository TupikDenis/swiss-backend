package by.tupik.backend.BackendDiplom.repositories;

import by.tupik.backend.BackendDiplom.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PeopleRepository extends JpaRepository<Person, Integer> {
}
