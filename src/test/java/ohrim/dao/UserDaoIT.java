package ohrim.dao;

import ohrim.entity.Gender;
import ohrim.entity.Role;
import ohrim.entity.User;
import ohrim.integration.IntegrationTestBase;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;


import static org.junit.jupiter.api.Assertions.*;

import static org.assertj.core.api.Assertions.assertThat;

class UserDaoIT extends IntegrationTestBase {

    private final UserDao userDao = UserDao.getInstance();

    @Test
    void findAll() {
        var user1 = userDao.save(getUser("test1.gmail.com"));
        var user2 = userDao.save(getUser("test2.gmail.com"));
        var user3 = userDao.save(getUser("test3.gmail.com"));

        var actualResult = userDao.findAll();

        assertThat(actualResult).hasSize(3);
        var userIds = actualResult.stream().map(User::getId).toList();
        assertThat(userIds).contains(user1.getId(),user2.getId(),user3.getId());
    }

    @Test
    void findById() {
        var user1 = userDao.save(getUser("test1.gmail.com"));

        var actualResult = userDao.findById(user1.getId());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user1);

    }

    @Test
    void save() {
        var user1 = getUser("test1.gmail.com");

        var actualResult = userDao.save(user1);

        assertNotNull(actualResult.getId());
    }

    @Test
    void findByEmailAndPassword() {
        var user1 = userDao.save(getUser("test1.gmail.com"));


        var actualResult = userDao.findByEmailAndPassword(user1.getEmail(), user1.getPassword());

        assertThat(actualResult).isPresent();
        assertThat(actualResult.get()).isEqualTo(user1);
    }
    @Test
    void shouldNotFindByEmailAndPassword() {
        userDao.save(getUser("test1.gmail.com"));
        var actualResult = userDao.findByEmailAndPassword("dummy", "123");

        assertThat(actualResult).isPresent();


    }

    @Test
    void deleteExisting() {
        var user = userDao.save(getUser("test1.gmail.com"));

        var actualResult = userDao.delete(user.getId());

        assertTrue(actualResult);

    }
    @Test
    void deleteNotExisting() {
        userDao.save(getUser("test1.gmail.com"));

        var actualResult = userDao.delete(101010);

        assertFalse(actualResult);

    }



    @Test
    void update() {
        var user =getUser("test1.gmail.com");
        userDao.save(user);
        user.setName("Ivan2").setPassword("new");
        userDao.update(user);

        var updatedUser = userDao.findById(user.getId()).get();

        assertThat(updatedUser).isEqualTo(user);
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