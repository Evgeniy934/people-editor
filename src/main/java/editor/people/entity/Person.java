package editor.people.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "people")
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long Id;

    @NonNull
    @NotBlank(message = "first name should not be empty")
    private String firstName;

    @NonNull
    @NotBlank(message = "last name should not be empty")
    private String lastName;

    @NonNull
    @NotNull(message = "gender should not be empty")
    private Gender gender;

    @NonNull
    @NotNull(message = "birthday should not be empty")
    @PastOrPresent(message = "date of birth cannot be in future")
    private LocalDate birthday;
}
