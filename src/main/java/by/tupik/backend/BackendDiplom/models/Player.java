package by.tupik.backend.BackendDiplom.models;

import by.tupik.backend.BackendDiplom.dto.Gender;
import by.tupik.backend.BackendDiplom.dto.Title;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.Date;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "players")
public class Player {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    @JsonBackReference
    private Tournament tournament;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "title")
    @Enumerated(EnumType.STRING)
    private Title title;

    @Column(name = "local_rating")
    private Integer localRating;

    @Column(name = "global_rating")
    private Integer globalRating;

    @ManyToOne
    @JoinColumn(name = "country_id")
    @JsonBackReference
    private Country federation;

    @Column(name = "club")
    private String club;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "whitePlayer")
    @JsonManagedReference
    private List<Match> whitePlayers;

    @OneToMany(mappedBy = "blackPlayer")
    @JsonManagedReference
    private List<Match> blackPlayers;

    public Player(){}

    public Player(int id, Tournament tournament, String lastName, String firstName, Date birthday,
                  Title title, Integer localRating, Integer globalRating, Country federation, String club,
                  Gender gender, List<Match> whitePlayers, List<Match> blackPlayers) {
        this.id = id;
        this.tournament = tournament;
        this.lastName = lastName;
        this.firstName = firstName;
        this.birthday = birthday;
        this.title = title;
        this.localRating = localRating;
        this.globalRating = globalRating;
        this.federation = federation;
        this.club = club;
        this.gender = gender;
        this.whitePlayers = whitePlayers;
        this.blackPlayers = blackPlayers;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Tournament getTournament() {
        return tournament;
    }

    public void setTournament(Tournament tournament) {
        this.tournament = tournament;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Title getTitle() {
        return title;
    }

    public void setTitle(Title title) {
        this.title = title;
    }

    public Integer getLocalRating() {
        return localRating;
    }

    public void setLocalRating(Integer localRating) {
        this.localRating = localRating;
    }

    public Integer getGlobalRating() {
        return globalRating;
    }

    public void setGlobalRating(Integer globalRating) {
        this.globalRating = globalRating;
    }

    public Country getFederation() {
        return federation;
    }

    public void setFederation(Country federation) {
        this.federation = federation;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public List<Match> getWhitePlayers() {
        return whitePlayers;
    }

    public void setWhitePlayers(List<Match> whitePlayers) {
        this.whitePlayers = whitePlayers;
    }

    public List<Match> getBlackPlayers() {
        return blackPlayers;
    }

    public void setBlackPlayers(List<Match> blackPlayers) {
        this.blackPlayers = blackPlayers;
    }

    public void addWhitePlayer(Match whitePlayer){
        whitePlayers.add(whitePlayer);
        whitePlayer.setWhitePlayer(this);
    }

    public void removeWhitePlayer(Match whitePlayer){
        whitePlayers.remove(whitePlayer);
        whitePlayer.setWhitePlayer(null);
    }

    public void addBlackPlayer(Match blackPlayer){
        blackPlayers.add(blackPlayer);
        blackPlayer.setBlackPlayer(this);
    }

    public void removeBlackPlayer(Match blackPlayer){
        blackPlayers.remove(blackPlayer);
        blackPlayer.setBlackPlayer(this);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Player player = (Player) o;
        return id == player.id && tournament.equals(player.tournament) &&
                lastName.equals(player.lastName) &&
                firstName.equals(player.firstName) &&
                birthday.equals(player.birthday) &&
                title == player.title &&
                localRating.equals(player.localRating) &&
                globalRating.equals(player.globalRating) &&
                federation.equals(player.federation) &&
                club.equals(player.club) &&
                gender == player.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, tournament, lastName, firstName, birthday, title, localRating, globalRating, federation, club, gender);
    }
}
