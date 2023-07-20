package by.tupik.backend.BackendDiplom.servicies;

import by.tupik.backend.BackendDiplom.models.Comment;
import by.tupik.backend.BackendDiplom.models.News;
import by.tupik.backend.BackendDiplom.repositories.CommentsRepository;
import by.tupik.backend.BackendDiplom.repositories.NewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentsService {
    private final CommentsRepository commentsRepository;

    @Autowired
    public CommentsService(CommentsRepository commentsRepository) {
        this.commentsRepository = commentsRepository;
    }

    @Transactional
    public void create(Comment comment){
        commentsRepository.save(comment);
    }

    public List<Comment> findAll(){
        return commentsRepository.findAll();
    }

    public Comment findOne(int id){
        return commentsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Comment updatedComment){
        updatedComment.setId(id);
        commentsRepository.save(updatedComment);
    }

    @Transactional
    public void delete(int id){
        commentsRepository.deleteById(id);
    }
}
