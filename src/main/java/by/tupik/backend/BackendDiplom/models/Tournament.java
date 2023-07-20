package by.tupik.backend.BackendDiplom.models;

import by.tupik.backend.BackendDiplom.dto.Status;
import by.tupik.backend.BackendDiplom.dto.TournamentType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;


@Entity
@Table(name = "tournaments")
public class Tournament {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "person_id")
    @JsonBackReference
    private Person person;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonBackReference
    private Country location;

    @Column(name = "name")
    private String name;

    @Column(name = "tournament_type")
    @Enumerated(EnumType.STRING)
    private TournamentType tournamentType;

    @Column(name = "organizer_name")
    private String organizerName;

    @Column(name = "referee_name")
    private String refereeName;

    @Column(name = "tournament_position")
    private String tournamentPosition;

    @Column(name = "current_round")
    private Integer currentRound;

    @Column(name = "rounds")
    private Integer rounds;

    @Column(name = "coefficients")
    private String coefficients;

    @Column(name = "start")
    private Date start;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "control")
    private String control;

    @OneToMany(mappedBy = "tournament")
    @JsonManagedReference
    private List<Action> actions;

    @OneToMany(mappedBy = "tournament")
    @JsonManagedReference
    private List<News> news;

    @OneToMany(mappedBy = "tournament")
    @JsonManagedReference
    private List<Player> players;

    @OneToMany(mappedBy = "tournament")
    @JsonManagedReference
    private List<Match> matches;

    public Tournament(){}

    public Tournament(int id, Person person, Country location, String name,
                      TournamentType tournamentType, String organizerName, String refereeName,
                      String tournamentPosition, Integer rounds, String coefficients,
                      List<Action> actions, List<News> news) {
        this.id = id;
        this.person = person;
        this.location = location;
        this.name = name;
        this.tournamentType = tournamentType;
        this.organizerName = organizerName;
        this.refereeName = refereeName;
        this.tournamentPosition = tournamentPosition;
        this.rounds = rounds;
        this.coefficients = coefficients;
        this.actions = actions;
        this.news = news;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Country getLocation() {
        return location;
    }

    public void setLocation(Country location) {
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TournamentType getTournamentType() {
        return tournamentType;
    }

    public void setTournamentType(TournamentType tournamentType) {
        this.tournamentType = tournamentType;
    }

    public String getOrganizerName() {
        return organizerName;
    }

    public void setOrganizerName(String organizerName) {
        this.organizerName = organizerName;
    }

    public String getRefereeName() {
        return refereeName;
    }

    public void setRefereeName(String refereeName) {
        this.refereeName = refereeName;
    }

    public String getTournamentPosition() {
        return tournamentPosition;
    }

    public void setTournamentPosition(String tournamentPosition) {
        this.tournamentPosition = tournamentPosition;
    }

    public Integer getCurrentRound() {
        return currentRound;
    }

    public void setCurrentRound(Integer currentRound) {
        this.currentRound = currentRound;
    }

    public Integer getRounds() {
        return rounds;
    }

    public void setRounds(Integer rounds) {
        this.rounds = rounds;
    }

    public List<Action> getActions() {
        return actions;
    }

    public void setActions(List<Action> actions) {
        this.actions = actions;
    }

    public String getCoefficients() {
        return coefficients;
    }

    public void setCoefficients(String coefficients) {
        this.coefficients = coefficients;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return endDate;
    }

    public void setEnd(Date end) {
        this.endDate = end;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public List<News> getNews() {
        return news;
    }

    public void setNews(List<News> news) {
        this.news = news;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public void setMatches(List<Match> matches) {
        this.matches = matches;
    }

    public void addAction(Action action){
        actions.add(action);
        action.setTournament(this);
    }

    public void removeAction(Action action){
        actions.remove(action);
        action.setTournament(null);
    }

    public void addNews(News news){
        this.news.add(news);
        news.setTournament(this);
    }

    public void removeNews(News news){
        this.news.remove(news);
        news.setTournament(null);
    }

    public void addPlayer(Player player){
        this.players.add(player);
        player.setTournament(this);
    }

    public void removePlayer(Player player){
        this.players.add(player);
        player.setTournament(null);
    }

    public void addMatch(Match match){
        this.matches.add(match);
        match.setTournament(this);
    }

    public void removeMatch(Match match){
        this.matches.remove(match);
        match.setTournament(null);
    }
}
