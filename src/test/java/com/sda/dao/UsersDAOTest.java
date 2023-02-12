package com.sda.dao;

import com.github.javafaker.Faker;
import com.github.javafaker.Internet;
import com.github.javafaker.Name;
import com.sda.db.HibernateUtils;
import com.sda.model.User;
import org.hibernate.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class UsersDAOTest {

    private final UsersDAO usersDAO = new UsersDAO();

    @Test
    void testCreateHappyPath() {
        // given
        String username = UUID.randomUUID().toString();
        User expectedUser = createUser(username);

        // when
        usersDAO.create(expectedUser);

        // then
        Session session = HibernateUtils.openSession();
        User actualUser = session.find(User.class, username);
        session.close();

        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertEquals(expectedUser.getUsername(), actualUser.getUsername());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
    }

    @Test
    void testDeleteUserNotExist() {
        // given
        String nonExistingUsername = "NON EXISTING USER";

        // when
        boolean deleted = usersDAO.delete(nonExistingUsername);

        // then
        Assertions.assertFalse(deleted);
    }

    @Test
    void testDeleteUserExist() {
        // given
        String username = "username";
        User expectedUser = createUser(username);

        usersDAO.create(expectedUser);

        // when
        boolean deleted = usersDAO.delete(username);

        // then
        Assertions.assertTrue(deleted);
    }


    @Test
    void testFindAll() {
        // given
        final List<User> expectedList = List.of(
                createUser(UUID.randomUUID().toString()),
                createUser(UUID.randomUUID().toString()),
                createUser(UUID.randomUUID().toString())
        );

        expectedList.forEach(usersDAO::create);

        // when
        List<User> usersList = usersDAO.findAll();

        // then
        Assertions.assertNotNull(usersList);
        Assertions.assertEquals(expectedList.size(), usersList.size());
        Assertions.assertIterableEquals(expectedList, usersList);
    }

    @Test
    void testFindByUsername() {
        // given
        String username = UUID.randomUUID().toString();
        User expectedUser = createUser(username);
        usersDAO.create(expectedUser);

        // when
        User actualUser = usersDAO.findByUsername(username);

        // then
        Assertions.assertNotNull(actualUser);
        Assertions.assertEquals(expectedUser, actualUser);
        Assertions.assertEquals(expectedUser.getAge(), actualUser.getAge());
        Assertions.assertEquals(expectedUser.getEmail(), actualUser.getEmail());
        Assertions.assertEquals(expectedUser.getName(), actualUser.getName());
        Assertions.assertEquals(expectedUser.getPassword(), actualUser.getPassword());
        Assertions.assertEquals(expectedUser.getSurname(), actualUser.getSurname());
    }

    @Test
    void testUpdateHappyPath() {
        //given
        int updatedAge = 100;
        String updatedName = "Lukasz";
        String userName = UUID.randomUUID().toString();

        User user = createUser(userName);
        usersDAO.create(user);

        user.setAge(updatedAge);
        user.setName(updatedName);

        //when
        User updatedUser = usersDAO.update(user);

        //then
        Assertions.assertNotNull(updatedUser);
        Assertions.assertEquals(updatedAge, updatedUser.getAge());
        Assertions.assertEquals(updatedName, updatedUser.getName());
        Assertions.assertEquals(user.getUsername(), updatedUser.getUsername());
        Assertions.assertEquals(user.getSurname(), updatedUser.getSurname());
        Assertions.assertEquals(user.getPassword(), updatedUser.getPassword());
        Assertions.assertEquals(user.getEmail(), updatedUser.getEmail());
    }

    @Test
    void testExistsUserNotFound() {
        // given
        String nonExistingUsername = "NON EXISTING USERNAME";

        // when
        boolean exists = usersDAO.exists(nonExistingUsername);

        // then
        Assertions.assertFalse(exists);
    }

    @Test
    void testExistsUserFound() {
        // given
        String existingUsername = UUID.randomUUID().toString();
        User userToCreate = createUser(existingUsername);

        usersDAO.create(userToCreate);

        // when
        boolean exists = usersDAO.exists(existingUsername);

        // then
        Assertions.assertTrue(exists);
    }

    private User createUser(String username) {
        Faker faker = new Faker();
        Name name = faker.name();
        Internet internet = faker.internet();

        User user = new User();
        user.setUsername(username);
        user.setName(name.firstName());
        user.setSurname(name.lastName());
        user.setPassword(internet.password());
        user.setEmail(internet.emailAddress());
        user.setAge(faker.number().numberBetween(1, 150));
        return user;
    }
}
