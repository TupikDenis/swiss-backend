package by.tupik.backend.BackendDiplom.controllers;

import by.tupik.backend.BackendDiplom.dto.NewsDTO;
import by.tupik.backend.BackendDiplom.dto.NewsResponse;
import by.tupik.backend.BackendDiplom.dto.PersonDTO;
import by.tupik.backend.BackendDiplom.models.News;
import by.tupik.backend.BackendDiplom.models.Person;
import by.tupik.backend.BackendDiplom.servicies.NewsService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/news")
public class NewsController {
    private final NewsService newsService;
    private final ModelMapper modelMapper;

    @Autowired
    public NewsController(NewsService newsService, ModelMapper modelMapper) {
        this.newsService = newsService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public News createNews(@RequestBody @Valid News news, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        newsService.create(news);

        return news;
    }

    @GetMapping()
    public NewsResponse getAllNews(){
        return new NewsResponse(newsService.findAll().stream().map(this::convertToNewsDTO).collect(Collectors.toList()));
    }

    private News convertToNews(NewsDTO newsDTO) {
        return modelMapper.map(newsDTO, News.class);
    }

    private NewsDTO convertToNewsDTO(News news) {
        return modelMapper.map(news, NewsDTO.class);
    }

    @GetMapping("/{id}")
    public News getNews(@PathVariable int id){
        return newsService.findOne(id);
    }

    @PutMapping("/{id}")
    public News updateNews(@PathVariable int id, @RequestBody @Valid News updatedNews, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        newsService.update(id, updatedNews);

        return updatedNews;
    }

    @DeleteMapping("/{id}")
    public int deleteNews(@PathVariable int id){
        newsService.delete(id);
        return id;
    }
}
