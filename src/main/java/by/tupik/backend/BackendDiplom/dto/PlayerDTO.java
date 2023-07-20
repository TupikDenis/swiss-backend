package by.tupik.backend.BackendDiplom.dto;

import by.tupik.backend.BackendDiplom.models.Player;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class PlayerDTO implements Comparable<PlayerDTO>{
    private int id;
    private String lastName;
    private String firstName;
    private Date birthday;
    private Title title;
    private Integer localRating;
    private Integer globalRating;
    private CountryDTO country;
    private String club;
    private Gender gender;
    private double score;
    private double buchholz;
    private double averageBuchholz;
    private double truncatedBuchholz;
    private double berger;
    private double koya;

    private int place;
    private int byeRound;
    private int games;
    private List<Color> previousColors;
    private List<PlayerDTO> opponents;

    public PlayerDTO(){
        this.opponents = new ArrayList<>();
    }

    public PlayerDTO(String lastName){
        this.lastName = lastName;
        this.opponents = new ArrayList<>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public CountryDTO getCountry() {
        return country;
    }

    public void setCountry(CountryDTO country) {
        this.country = country;
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

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getBuchholz() {
        return buchholz;
    }

    public void setBuchholz(double buchholz) {
        this.buchholz = buchholz;
    }

    public double getAverageBuchholz() {
        return averageBuchholz;
    }

    public void setAverageBuchholz(double averageBuchholz) {
        this.averageBuchholz = averageBuchholz;
    }

    public double getTruncatedBuchholz() {
        return truncatedBuchholz;
    }

    public void setTruncatedBuchholz(double truncatedBuchholz) {
        this.truncatedBuchholz = truncatedBuchholz;
    }

    public double getBerger() {
        return berger;
    }

    public void setBerger(double berger) {
        this.berger = berger;
    }

    public double getKoya() {
        return koya;
    }

    public void setKoya(double koya) {
        this.koya = koya;
    }

    public List<PlayerDTO> getOpponents() {
        return opponents;
    }

    public void setOpponents(PlayerDTO opponent) {
        this.opponents.add(opponent);
    }

    public void removeLastOpponent(){
        this.opponents.remove(this.opponents.size() - 1);
    }

    public List<Color> getPreviousColors() {
        return previousColors;
    }

    public void setPreviousColors(Color color, int index) {
        this.previousColors.add(color);
    }

    public void removeLastColors() {
        this.previousColors.remove(this.previousColors.size() - 1);
    }

    public int getGames() {
        return games;
    }

    public void addGame() {
        this.games++;
    }

    public void cancelGame() {
        this.games--;
    }

    public int getByeRound() {
        return byeRound;
    }

    public void setByeRound(int byeRound) {
        this.byeRound = byeRound;
    }

    public int getPlace() {
        return place;
    }

    public void setPlace(int place) {
        this.place = place;
    }

    @Override
    public int compareTo(PlayerDTO o) {
        if (this.score < o.getScore()) {
            return 1;
        } else if(this.score > o.getScore()){
            return -1;
        } else if(this.score == o.getScore()){
            //TODO: понять последовательность
        }

        return 0;
    }
}
