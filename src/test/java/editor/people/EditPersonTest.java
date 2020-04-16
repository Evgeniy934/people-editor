package editor.people;

import com.fasterxml.jackson.databind.ObjectMapper;
import editor.people.entity.Gender;
import editor.people.entity.Person;
import editor.people.repository.PersonRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class EditPersonTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    public void givenPersonDoesNotExist_whenUpdate_thenReturnError() throws Exception {
        var person = new Person("test", "test", Gender.MALE, LocalDate.now());

        mvc.perform(put("/people/{id}", 1)
                .content(objectMapper.writeValueAsString(person))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    @Transactional
    public void givenPersonInRepository_whenUpdate_thenPersonUpdated() throws Exception {
        var person = repository.save(new Person("test", "test", Gender.MALE, LocalDate.now()));
        person.setFirstName("completely new name");

        var result = mvc.perform(put("/people/{id}", person.getId())
                .content(objectMapper.writeValueAsString(person))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var updatedPerson = objectMapper.readValue(result.getResponse().getContentAsString(), Person.class);
        assertEquals(updatedPerson.getFirstName(), person.getFirstName());
    }
}
