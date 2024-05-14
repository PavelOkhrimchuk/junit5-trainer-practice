package ohrim.mapper;

import ohrim.dto.CreateUserDto;
import ohrim.entity.Gender;
import ohrim.entity.Role;
import ohrim.entity.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

class CreateUserMapperTest {
    private final CreateUserMapper mapper = CreateUserMapper.getInstance();
    @Test
    void map() {
        CreateUserDto dto = CreateUserDto.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .birthday("2000-01-01")
                .role(Role.USER.name())
                .gender(Gender.MALE.name())
                .build();


        User actualResult = mapper.map(dto);
        User expectedResult = User.builder()
                .name("Ivan")
                .email("test@gmail.com")
                .password("123")
                .role(Role.USER)
                .gender(Gender.MALE)
                .birthday(LocalDate.of(2000,01,01))
                .build();
        assertThat(actualResult).isEqualTo(expectedResult);


    }

}