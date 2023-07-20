package by.tupik.backend.BackendDiplom.dto;

import java.util.List;

public class CountryDTO {
    private int id;
    private String fullCountryName;
    private String shortCountryName;

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
}
