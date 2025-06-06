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
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
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
        InvalidUserDataException invalidUserDataException = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals(String.format("User with login %s already exist.",
                user.getLogin()), invalidUserDataException.getMessage());
    }

    @Test
    void registerWithNullUser_Fail() {
        user = null;
        InvalidUserDataException invalidUserDataException = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals("User cannot be null",
                invalidUserDataException.getMessage());
    }

    @Test
    void registerUserWithPassword_Fail() {
        user.setPassword("pass");
        InvalidUserDataException invalidUserDataException = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals(String.format("Password length is less than %d characters.",
                        MIN_PASSWORD_LENGTH), invalidUserDataException.getMessage());
    }

    @Test
    void registerUserWithNullPassword_Fail() {
        user.setPassword(null);
        InvalidUserDataException thrownException = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Expected register() to throw InvalidUserDataException for null password"
        );
        assertEquals("Password cannot be null", thrownException.getMessage(),
                "The exception message for null password was not as expected");
    }

    @Test
    void registerUserWithEmptyPassword_Fail() {
        user.setPassword("");
        InvalidUserDataException invalidUserDataException = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals(String.format("Password length is less than %d characters.",
                MIN_PASSWORD_LENGTH), invalidUserDataException.getMessage());
    }

    @Test
    void registerUserWithNullLogin_Fail() {
        user.setLogin(null);
        InvalidUserDataException thrownException = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user),
                "Expected register() to throw InvalidUserDataException for null login"
        );
        assertEquals("Login cannot be null", thrownException.getMessage(),
                "The exception message for null login was not as expected");
    }

    @Test
    void registerUserWithEmptyLogin_Fail() {
        user.setLogin("");
        InvalidUserDataException invalidUserDataException = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals(String.format("Login length is less than %d characters.",
                MIN_LOGIN_LENGTH), invalidUserDataException.getMessage());
    }

    @Test
    void registerUserWithLogin_Fail() {
        user.setLogin("Len");
        InvalidUserDataException invalidUserDataException = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals(String.format("Login length is less than %d characters.",
                MIN_LOGIN_LENGTH), invalidUserDataException.getMessage());
    }

    @Test
    void registerUserWithAge_Fail() {
        user.setAge(17);
        InvalidUserDataException invalidUserDataException = assertThrows(
                InvalidUserDataException.class,
                () -> registrationService.register(user));
        assertEquals(String.format("User is too young. Min age is %d.",
                MIN_AGE), invalidUserDataException.getMessage());
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

    @AfterEach
    void tearDown() {
        Storage.people.clear();
    }
}
