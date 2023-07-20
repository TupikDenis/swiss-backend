package by.tupik.backend.BackendDiplom.servicies;

import by.tupik.backend.BackendDiplom.models.Country;
import by.tupik.backend.BackendDiplom.repositories.CountriesRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CountriesService {
    private final CountriesRepository countriesRepository;

    @Autowired
    public CountriesService(CountriesRepository countriesRepository) {
        this.countriesRepository = countriesRepository;
    }

    @Transactional
    public void create(Country country){
        countriesRepository.save(country);
    }

    public List<Country> findAll() {
        return countriesRepository.findAll();
    }

    public Country findOne(int id) {
        return countriesRepository.findById(id).orElse(null);
    }

    @Transactional
    public void update(int id, Country updatedCountry){
        updatedCountry.setId(id);
        countriesRepository.save(updatedCountry);
    }

    @Transactional
    public void delete(int id){
        countriesRepository.deleteById(id);
    }
}
