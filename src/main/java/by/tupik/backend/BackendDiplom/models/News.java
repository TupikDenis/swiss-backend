package by.tupik.backend.BackendDiplom.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "news")
public class News {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "is_main")
    private boolean isMain;

    @Column(name = "text")
    private String text;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    @JsonBackReference
    private Tournament tournament;

    @OneToMany(mappedBy = "news")
    @JsonManagedReference
    private List<Comment> comments;

    public News(){}

    public News(int id, String title, boolean isMain, String text, Tournament tournament, List<Comment> comments) {
        this.id = id;
        this.title = title;
        this.isMain = isMain;
        this.text = text;
        this.tournament = tournament;
        this.comments = comments;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public boolean isMain() {
        return isMain;
    }

    public void setMain(boolean main) {
        isMain = main;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public void addComment(Comment comment){
        comments.add(comment);
        comment.setNews(this);
    }

    public void removeComment(Comment comment){
        comments.remove(comment);
        comment.setNews(null);
    }
}
