package gruppo1.epicenergy.exceptions;

import java.util.List;

public class ValidationException extends RuntimeException {
    private List<String> errors;
    public ValidationException(List<String> errors) {
        super("Trovati i seguenti errori nella validazione");
        this.errors = errors;
    }

    public List<String> getErrors() {
        return errors;
    }
}
