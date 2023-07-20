package by.tupik.backend.BackendDiplom.repositories;

import by.tupik.backend.BackendDiplom.models.News;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Integer> {
}
