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
public class AddPersonTest {
    @Autowired
    private MockMvc mvc;

    @Autowired
    private PersonRepository repository;

    @Autowired
    ObjectMapper objectMapper;

    @Test
    @Transactional
    public void givenNewPerson_whenAdd_thenShouldBeInRepository() throws Exception {
        var person = new Person("test", "test", Gender.MALE, LocalDate.now());

        var result = mvc.perform(post("/people")
                .content(objectMapper.writeValueAsString(person))
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn();

        var addedPerson = objectMapper.readValue(result.getResponse().getContentAsString(), Person.class);
        assertTrue(repository.findById(addedPerson.getId()).isPresent());
    }

    @Test
    public void givenInvalidPerson_whenAdd_thenReturnError() throws Exception {
        var person = "\"firstName\": \"\", \"lastName\": \"\", \"gender\": \"\", \"birthday\": \"2100-01-01\"";

        mvc.perform(post("/people")
                .content(person)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    public void givenInvalidRequest_whenAdd_thenReturnError() throws Exception {
        var person = "{";

        mvc.perform(post("/people")
                .content(person)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }
}
