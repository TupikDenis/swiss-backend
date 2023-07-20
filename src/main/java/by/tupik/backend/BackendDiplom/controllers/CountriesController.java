package by.tupik.backend.BackendDiplom.controllers;

import by.tupik.backend.BackendDiplom.dto.CountryDTO;
import by.tupik.backend.BackendDiplom.dto.CountriesResponse;
import by.tupik.backend.BackendDiplom.models.Country;
import by.tupik.backend.BackendDiplom.servicies.CountriesService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/countries")
public class CountriesController {
    private final CountriesService countriesService;
    private final ModelMapper modelMapper;

    @Autowired
    public CountriesController(CountriesService countriesService, ModelMapper modelMapper) {
        this.countriesService = countriesService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public Country createCountries(@RequestBody @Valid Country country, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        countriesService.create(country);

        return country;
    }

    @GetMapping()
    public CountriesResponse getAllCountries(){
        return new CountriesResponse(countriesService.findAll().stream().map(this::convertToCountriesDTO).collect(Collectors.toList()));
    }

    private Country convertToCountries(CountryDTO countriesDTO) {
        return modelMapper.map(countriesDTO, Country.class);
    }

    private CountryDTO convertToCountriesDTO(Country countries) {
        return modelMapper.map(countries, CountryDTO.class);
    }

    @GetMapping("/{id}")
    public Country getCountries(@PathVariable int id){
        return countriesService.findOne(id);
    }

    @PutMapping("/{id}")
    public Country updateCountries(@PathVariable int id, @RequestBody @Valid Country updatedCountry, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        countriesService.update(id, updatedCountry);

        return updatedCountry;
    }

    @DeleteMapping("/{id}")
    public int deleteCountries(@PathVariable int id){
        countriesService.delete(id);
        return id;
    }
}
