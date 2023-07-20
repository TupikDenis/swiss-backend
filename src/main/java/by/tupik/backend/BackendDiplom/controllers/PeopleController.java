package by.tupik.backend.BackendDiplom.controllers;

import by.tupik.backend.BackendDiplom.dto.PersonDTO;
import by.tupik.backend.BackendDiplom.dto.PeopleResponse;
import by.tupik.backend.BackendDiplom.models.Person;
import by.tupik.backend.BackendDiplom.servicies.PeopleService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/people")
public class PeopleController {
    private final PeopleService peopleService;
    private final ModelMapper modelMapper;

    @Autowired
    public PeopleController(PeopleService peopleService, ModelMapper modelMapper) {
        this.peopleService = peopleService;
        this.modelMapper = modelMapper;
    }

    @PostMapping()
    public Person createPerson(@RequestBody @Valid Person person, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            System.out.println(bindingResult.toString());
            return null;
        }

        peopleService.create(person);

        return person;
    }

    @GetMapping()
    public PeopleResponse getPeople() {
        return new PeopleResponse(peopleService.findAll().stream().map(this::convertToPersonDTO).collect(Collectors.toList()));
    }

    private Person convertToPerson(PersonDTO personDTO) {
        return modelMapper.map(personDTO, Person.class);
    }

    private PersonDTO convertToPersonDTO(Person person) {
        return modelMapper.map(person, PersonDTO.class);
    }

    @GetMapping("/{id}")
    public PersonDTO getPerson(@PathVariable("id") int id) {
        return convertToPersonDTO(peopleService.findOne(id));
    }

    @PutMapping("/{id}")
    public Person updatePerson(@PathVariable("id") int id, @RequestBody @Valid Person updatedPerson, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return null;
        }

        peopleService.update(id, updatedPerson);

        return updatedPerson;
    }

    @DeleteMapping("/{id}")
    public int deletePerson(@PathVariable("id") int id){
        peopleService.delete(id);
        return id;
    }
}
