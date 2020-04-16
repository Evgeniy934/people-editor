package editor.people;

import com.fasterxml.jackson.databind.ObjectMapper;
import editor.people.entity.Gender;
import editor.people.entity.Person;
import editor.people.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class DeletePersonTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void givenPersonDoesNotExist_whenDelete_thenReturnError() throws Exception {
        mvc.perform(delete("/people/{id}", 1))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @Transactional
    public void givenPersonInRepository_whenDelete_thenRemovePersonFromRepository() throws Exception {
        var person = repository.save(new Person("test", "test", Gender.MALE, LocalDate.now()));

        mvc.perform(delete("/people/{id}", person.getId()))
                .andDo(print())
                .andExpect(status().isOk());

        assertFalse(repository.existsById(person.getId()));
    }
}
