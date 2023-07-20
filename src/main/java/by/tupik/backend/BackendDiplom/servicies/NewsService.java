package by.tupik.backend.BackendDiplom.servicies;

import by.tupik.backend.BackendDiplom.dto.MatchResult;
import by.tupik.backend.BackendDiplom.models.Match;
import by.tupik.backend.BackendDiplom.models.News;
import by.tupik.backend.BackendDiplom.models.Person;
import by.tupik.backend.BackendDiplom.repositories.NewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NewsService {
    private final NewsRepository newsRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    @Transactional
    public void create(News news){
        newsRepository.save(news);
    }

    public List<News> findAll(){
        return newsRepository.findAll();
    }

    public News findOne(int id){
        return newsRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, News updatedNews){
        updatedNews.setId(id);
        newsRepository.save(updatedNews);
    }

    @Transactional
    public void delete(int id){
        newsRepository.deleteById(id);
    }
}
