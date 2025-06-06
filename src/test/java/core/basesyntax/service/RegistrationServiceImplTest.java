package core.basesyntax.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import core.basesyntax.db.Storage;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class RegistrationServiceImplTest {
    private RegistrationService registrationService;
    private User user;

    @BeforeEach
    void setUp() {
        registrationService = new RegistrationServiceImpl();
        user = new User();
        user.setAge(18);
        user.setLogin("lentez");
        user.setPassword("password");
    }

    @Test
    void registerWithValidData_Ok() throws InvalidUserDataException {
        System.out.println();
        User actual = registrationService.register(user);
        assertEquals(user, actual);
    }

    @Test
    void registerWithDuplicateUserLogin_Fail() throws InvalidUserDataException {
        registrationService.register(user);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWithEmptyUserData_Fail() {
        User user = new User();
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerWithNullUser_Fail() {
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(null));
    }

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }

    @Test
    void registerUserWithPassword_Fail() {
        user.setPassword("pass");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullPassword_Fail() {
        user.setPassword(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithEmptyPassword_Fail() {
        user.setPassword("");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithNullLogin_Fail() {
        user.setLogin(null);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithEmptyLogin_Fail() {
        user.setLogin("");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithLogin_Fail() {
        user.setLogin("Len");
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUserWithAge_Fail() {
        user.setAge(17);
        assertThrows(InvalidUserDataException.class, () -> registrationService.register(user));
    }

    @Test
    void registerUsersWithValidData_Ok() throws InvalidUserDataException {
        User user1 = new User();
        user1.setLogin("user_number1");
        user1.setAge(27);
        user1.setPassword("password123");
        User user2 = new User();
        user2.setLogin("user_number2");
        user2.setAge(19);
        user2.setPassword("password123");
        User user3 = new User();
        user3.setLogin("user_number3");
        user3.setAge(60);
        user3.setPassword("password123");
        User actual = registrationService.register(user1);
        assertEquals(user1, actual);
        actual = registrationService.register(user2);
        assertEquals(user2, actual);
        actual = registrationService.register(user3);
        assertEquals(user3, actual);
        assertDoesNotThrow(() -> registrationService.register(user));
    }
}
