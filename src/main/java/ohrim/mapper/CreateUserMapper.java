package ohrim.mapper;

import ohrim.dto.CreateUserDto;
import ohrim.entity.Gender;
import ohrim.entity.Role;
import ohrim.entity.User;
import ohrim.util.LocalDateFormatter;
import lombok.NoArgsConstructor;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class  CreateUserMapper implements Mapper<CreateUserDto, User> {

    private static final CreateUserMapper INSTANCE = new CreateUserMapper();

    public static CreateUserMapper getInstance() {
        return INSTANCE;
    }

    @Override
    public User map(CreateUserDto object) {
        return User.builder()
                .name(object.getName())
                .birthday(LocalDateFormatter.format(object.getBirthday()))
                .email(object.getEmail())
                .password(object.getPassword())
                .gender(Gender.findOpt(object.getGender()).orElse(null))
                .role(Role.findOpt(object.getRole()).orElse(null))
                .build();
    }
}
