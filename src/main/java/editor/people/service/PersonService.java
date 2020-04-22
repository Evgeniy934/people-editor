package editor.people.service;

import editor.people.entity.Person;
import editor.people.error.PersonNotFoundException;
import editor.people.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PersonService {
    @Autowired
    private PersonRepository repository;

    public List<Person> findAll() {
        return repository.findAll();
    }

    public Person save(Person person) {
        return repository.save(person);
    }

    public Person get(Long id) {
        return repository.findById(id).orElseThrow(() -> new PersonNotFoundException(id));
    }

    public void delete(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
        } else {
            throw new PersonNotFoundException(id);
        }
    }

    public Person update(Person person, Long id) {
        if (repository.existsById(id)) {
            person.setId(id);
            return repository.save(person);
        }

        throw new PersonNotFoundException(id);
    }
}
