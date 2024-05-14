package ohrim.validator;

public interface Validator<T> {

    ValidationResult validate(T object);
}
