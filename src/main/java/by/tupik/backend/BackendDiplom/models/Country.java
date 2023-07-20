package by.tupik.backend.BackendDiplom.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "countries")
public class Country {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "full_country_name")
    private String fullCountryName;

    @Column(name = "short_country_name")
    private String shortCountryName;

    @OneToMany(mappedBy = "country")
    @JsonManagedReference
    private List<Person> people;

    @OneToMany(mappedBy = "location")
    @JsonManagedReference
    private List<Tournament> tournaments;

    @OneToMany(mappedBy = "federation")
    @JsonManagedReference
    private List<Player> players;

    public Country(){}

    public Country(int id, String fullCountryName, String shortCountryName, List<Person> people, List<Tournament> tournaments, List<Player> players) {
        this.id = id;
        this.fullCountryName = fullCountryName;
        this.shortCountryName = shortCountryName;
        this.people = people;
        this.tournaments = tournaments;
        this.players = players;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullCountryName() {
        return fullCountryName;
    }

    public void setFullCountryName(String fullCountryName) {
        this.fullCountryName = fullCountryName;
    }

    public String getShortCountryName() {
        return shortCountryName;
    }

    public void setShortCountryName(String shortCountryName) {
        this.shortCountryName = shortCountryName;
    }

    public List<Person> getPeople() {
        return people;
    }

    public void setPeople(List<Person> people) {
        this.people = people;
    }

    public List<Tournament> getTournaments() {
        return tournaments;
    }

    public void setTournaments(List<Tournament> tournaments) {
        this.tournaments = tournaments;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public void addPerson(Person person){
        people.add(person);
        person.setCountry(this);
    }

    public void removePerson(Person person){
        people.remove(person);
        person.setCountry(null);
    }

    public void addTournament(Tournament tournament){
        tournaments.add(tournament);
        tournament.setLocation(this);
    }

    public void removeTournament(Tournament tournament){
        tournaments.remove(tournament);
        tournament.setLocation(null);
    }

    public void addPlayer(Player player){
        players.add(player);
        player.setFederation(this);
    }

    public void removePlayer(Player player){
        players.remove(player);
        player.setFederation(null);
    }
}
