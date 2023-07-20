package by.tupik.backend.BackendDiplom.dto;

import java.util.List;

public class CountriesResponse {
    private List<CountryDTO> countries;

    public CountriesResponse(List<CountryDTO> countries) {
        this.countries = countries;
    }

    public List<CountryDTO> getCountries() {
        return countries;
    }

    public void setCountries(List<CountryDTO> countries) {
        this.countries = countries;
    }
}
