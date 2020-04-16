package editor.people.controller;

import editor.people.entity.Person;
import editor.people.error.PersonNotFoundException;
import editor.people.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {
    @Autowired
    private PersonRepository repository;

    @GetMapping
    public List<Person> all() {
        return repository.findAll();
    }

    @PostMapping
    public Person add(@Valid @RequestBody Person person) {
        return repository.save(person);
    }

    @GetMapping("/{id}")
    public Person get(@PathVariable("id") Long id) {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    @PutMapping("/{id}")
    public Person update(@Valid @RequestBody Person person, @PathVariable("id") Long id) {
        if (repository.existsById(id)) {
            person.setId(id);
            return repository.save(person);
        }

        throw new PersonNotFoundException(id);
    }
}
