package ohrim.service;

import ohrim.dao.UserDao;
import ohrim.dto.CreateUserDto;
import ohrim.entity.Gender;
import ohrim.entity.Role;
import ohrim.entity.User;
import ohrim.integration.IntegrationTestBase;
import ohrim.mapper.CreateUserMapper;
import ohrim.mapper.UserMapper;
import ohrim.validator.CreateUserValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

public class UserServiceIT extends IntegrationTestBase {
    private UserService userService;
    private UserDao userDao;

    @BeforeEach
    void init() {
        userDao = UserDao.getInstance();
        userService  = new UserService(
                CreateUserValidator.getInstance(),
                userDao,
                CreateUserMapper.getInstance(),
                UserMapper.getInstance()
        );

    }

    @Test
    void login() {
        User user = getUser("test@gmail.com");
        userDao.save(user);

        var actualResult = userService.login(user.getEmail(), user.getPassword());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get().getId()).isEqualTo(user.getId());
    }

    @Test

    void create() {
        CreateUserDto createUserDto = getDto();

        var actualResult = userService.create(createUserDto);

        assertNotNull(actualResult.getId());
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

    private User getUser(String email) {
        return User.builder()
                .name("Ivan")
                .email(email)
                .password("123")
                .role(Role.USER)
                .gender(Gender.MALE)
                .birthday(LocalDate.of(2000, 01, 01))
                .build();
    }
}
