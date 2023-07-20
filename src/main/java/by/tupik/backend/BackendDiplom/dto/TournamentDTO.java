package by.tupik.backend.BackendDiplom.dto;

import com.fasterxml.jackson.annotation.*;

import java.util.Date;
import java.util.List;

public class TournamentDTO {
    private int id;
    private String name;
    @JsonIdentityInfo(
            generator = ObjectIdGenerators.PropertyGenerator.class,
            property = "id"
    )
    private PersonDTO person;
    private CountryDTO location;
    private TournamentType tournamentType;
    private String organizerName;
    private String refereeName;
    private String tournamentPosition;
    private Integer currentRound;
    private Integer rounds;
    private String coefficients;
    private Date start;
    private Date endDate;
    private Status status;
    private String control;
    private List<ActionDTO> actions;
    private List<NewsDTO> news;
    private List<PlayerDTO> players;
    private List<RoundMatching> roundMatching;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersonDTO getPerson() {
        return person;
    }

    public void setPerson(PersonDTO person) {
        this.person = person;
    }

    public CountryDTO getLocation() {
        return location;
    }

    public void setLocation(CountryDTO location) {
        this.location = location;
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

    public List<ActionDTO> getActions() {
        return actions;
    }

    public void setActions(List<ActionDTO> actions) {
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

    public List<NewsDTO> getNews() {
        return news;
    }

    public void setNews(List<NewsDTO> news) {
        this.news = news;
    }

    public List<PlayerDTO> getPlayers() {
        return players;
    }

    public void setPlayers(List<PlayerDTO> players) {
        this.players = players;
    }

    public List<RoundMatching> getRoundMatching() {
        return roundMatching;
    }

    public void setRoundMatching(List<RoundMatching> roundMatching) {
        this.roundMatching = roundMatching;
    }
}
