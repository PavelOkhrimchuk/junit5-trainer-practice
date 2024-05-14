package ohrim.service;

import ohrim.dao.UserDao;
import ohrim.dto.CreateUserDto;
import ohrim.dto.UserDto;
import ohrim.entity.Gender;
import ohrim.entity.Role;
import ohrim.entity.User;
import ohrim.exception.ValidationException;
import ohrim.mapper.CreateUserMapper;
import ohrim.mapper.UserMapper;
import ohrim.validator.CreateUserValidator;
import ohrim.validator.Error;
import ohrim.validator.ValidationResult;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    private CreateUserValidator createUserValidator;
    @Mock
    private UserDao userDao;
    @Mock
    private CreateUserMapper createUserMapper;
    @Mock
    private UserMapper userMapper;
    @InjectMocks
    private UserService userService;


    @Test
    void loginSuccess() {
        User user = getUser();
        UserDto userDto = getUserDto();
        doReturn(Optional.of(user)).when(userDao).findByEmailAndPassword(user.getEmail(), user.getPassword());
        doReturn(userDto).when(userMapper).map(user);

        Optional<UserDto> actualResult = userService.login(user.getEmail(), user.getPassword());

        assertThat(actualResult).isPresent();
    }

    @Test
    void loginFail() {
        doReturn(Optional.empty()).when(userDao).findByEmailAndPassword(any(),any());

        Optional<UserDto> actualResult = userService.login("dummy", "123");

        assertThat(actualResult).isEmpty();
        verifyNoInteractions(userMapper);

    }

    @Test
    void create() {
        CreateUserDto dto = getDto();
        User user = getUser();
        UserDto userDto = getUserDto();
        doReturn(new ValidationResult()).when(createUserValidator).validate(dto);
        doReturn(user).when(createUserMapper).map(dto);
        doReturn(userDto).when(userMapper).map(user);

        var actualResult = userService.create(dto);

        assertThat(actualResult).isEqualTo(userDto);
        verify(userDao).save(user);


    }

    @Test
    void shouldThrowException() {
        CreateUserDto createUserDto = getDto();
        ValidationResult validationResult = new ValidationResult();
        validationResult.add(Error.of("invalid.role","message"));
        doReturn(validationResult).when(createUserValidator).validate(createUserDto);
        assertThrows(ValidationException.class,()->userService.create(createUserDto));
        verifyNoInteractions(userDao,createUserMapper,userMapper);
    }

    private CreateUserDto getDto() {
        return CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role(Role.USER.name())
                .gender(Gender.MALE.name())
                .build();
    }

    private User getUser() {
        return User.builder()
                .id(99)
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .role(Role.USER)
                .gender(Gender.MALE)
                .birthday(LocalDate.of(2000, 01, 01))
                .build();
    }

    private UserDto getUserDto() {
        return UserDto.builder()
                .id(99)
                .name("Ivan")
                .email("test@gmail.com")
                .role(Role.USER)
                .gender(Gender.MALE)
                .birthday(LocalDate.of(2000, 01, 01))
                .build();
    }



}