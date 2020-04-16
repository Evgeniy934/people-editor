package editor.people.error;

import java.time.LocalDate;

public class Error {
    private String error;
    private LocalDate timestamp;

    public Error(String error) {
        this.error = error;
        this.timestamp = LocalDate.now();
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public LocalDate getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDate timestamp) {
        this.timestamp = timestamp;
    }
}
