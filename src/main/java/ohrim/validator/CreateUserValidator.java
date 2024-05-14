package ohrim.validator;

import ohrim.dto.CreateUserDto;
import ohrim.entity.Gender;
import ohrim.entity.Role;
import ohrim.util.LocalDateFormatter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class CreateUserValidator implements Validator<CreateUserDto> {

    private static final CreateUserValidator INSTANCE = new CreateUserValidator();

    public static CreateUserValidator getInstance() {
        return INSTANCE;
    }

    @Override
    public ValidationResult validate(CreateUserDto object) {
        var validationResult = new ValidationResult();
        if (!LocalDateFormatter.isValid(object.getBirthday())) {
            validationResult.add(Error.of("invalid.birthday", "Birthday is invalid"));
        }
        if (Gender.findOpt(object.getGender()).isEmpty()) {
            validationResult.add(Error.of("invalid.gender", "Gender is invalid"));
        }
        if (Role.findOpt(object.getRole()).isEmpty()) {
            validationResult.add(Error.of("invalid.role", "Role is invalid"));
        }
        return validationResult;
    }
}
