package ohrim.validator;

import ohrim.dto.CreateUserDto;
import ohrim.entity.Gender;
import ohrim.entity.Role;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;

class CreateUserValidatorTest {

    private final CreateUserValidator createUserValidator = CreateUserValidator.getInstance();

    @Test
    void shouldPassValidation() {

        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role(Role.USER.name())
                .gender(Gender.MALE.name())
                .build();
        var actualResult = createUserValidator.validate(dto);
        assertFalse(actualResult.hasErrors());
    }

    @Test
    void InvalidBirthday() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("2000-01-01 12:25")
                .role(Role.USER.name())
                .gender(Gender.MALE.name())
                .build();
        var actualResult = createUserValidator.validate(dto);
        assertAll(
                () -> assertTrue(actualResult.hasErrors()),
                () -> assertThat(actualResult.getErrors()).hasSize(1)
        );
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo("invalid.birthday");


    }
    @Test
    void InvalidGender() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role(Role.USER.name())
                .gender("fake")
                .build();
        var actualResult = createUserValidator.validate(dto);
        assertAll(
                () -> assertTrue(actualResult.hasErrors()),
                () -> assertThat(actualResult.getErrors()).hasSize(1)
        );
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo("invalid.gender");


    }

    @Test
    void InvalidRole() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role("fake")
                .gender(Gender.MALE.name())
                .build();
        var actualResult = createUserValidator.validate(dto);
        assertAll(
                () -> assertTrue(actualResult.hasErrors()),
                () -> assertThat(actualResult.getErrors()).hasSize(1)
        );
        assertThat(actualResult.getErrors().get(0).getCode()).isEqualTo("invalid.role");


    }

    @Test
    void InvalidRoleGenderBirthday() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("2000-01-01 13:77")
                .role("fake")
                .gender("fake")
                .build();
        var actualResult = createUserValidator.validate(dto);
        assertAll(
                () -> assertTrue(actualResult.hasErrors()),
                () -> assertThat(actualResult.getErrors()).hasSize(3)
        );
        var errorsCode = actualResult.getErrors().stream().map(Error::getCode).toList();
        assertThat(errorsCode).contains("invalid.role", "invalid.gender", "invalid.birthday");

    }


}