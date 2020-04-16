package editor.people.error;

public class PersonNotFoundException extends RuntimeException {
    public PersonNotFoundException(Long id) {
        super("person not found " + id);
    }
}
