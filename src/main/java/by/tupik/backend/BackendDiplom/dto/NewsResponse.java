package by.tupik.backend.BackendDiplom.dto;

import java.util.List;

public class NewsResponse {
    private List<NewsDTO> news;

    public NewsResponse(List<NewsDTO> news) {
        this.news = news;
    }

    public List<NewsDTO> getNews() {
        return news;
    }

    public void setNews(List<NewsDTO> news) {
        this.news = news;
    }
}
