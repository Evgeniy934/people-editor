package editor.people.controller;

import editor.people.entity.Person;
import editor.people.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/people")
public class PersonController {
    @Autowired
    private PersonService service;

    @GetMapping
    public List<Person> all() {
        return service.findAll();
    }

    @PostMapping
    public Person add(@Valid @RequestBody Person person) {
        return service.save(person);
    }

    @GetMapping("/{id}")
    public Person get(@PathVariable("id") Long id) {
        return service.get(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") Long id) {
        service.delete(id);
    }

    @PutMapping("/{id}")
    public Person update(@Valid @RequestBody Person person, @PathVariable("id") Long id) {
        return service.update(person, id);
    }
}
