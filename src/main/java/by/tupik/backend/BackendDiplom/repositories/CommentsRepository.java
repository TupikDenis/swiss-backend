package by.tupik.backend.BackendDiplom.repositories;

import by.tupik.backend.BackendDiplom.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentsRepository extends JpaRepository<Comment, Integer> {
}
