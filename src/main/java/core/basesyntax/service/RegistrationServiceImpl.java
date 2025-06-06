package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;
    private final StorageDao storageDao = new StorageDaoImpl();

    @Override
    public User register(User user) throws InvalidUserDataException {
        if (user == null) {
            throw new InvalidUserDataException("User cannot be null");
        }
        validatePassword(user.getPassword());
        validateLogin(user.getLogin());

        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException(String.format(
                    "User is too young. Min age is %d.", MIN_AGE));
        }

        return storageDao.add(user);
    }

    private void validatePassword(String password) {
        if (password == null) {
            throw new InvalidUserDataException("Password cannot be null");
        }
        if (password.length() < MIN_PASSWORD_LENGTH) {
            throw new InvalidUserDataException(
                    String.format("Password length is less than %d characters.",
                            MIN_PASSWORD_LENGTH));
        }
    }

    private void validateLogin(String login) {
        if (login == null) {
            throw new InvalidUserDataException("Login cannot be null");
        }
        if (login.length() < MIN_LOGIN_LENGTH) {
            throw new InvalidUserDataException(
                    String.format("Login length is less than %d characters.", MIN_LOGIN_LENGTH));
        }
        if (storageDao.get(login) != null) {
            throw new InvalidUserDataException(
                    String.format("User with login %s already exist.", login));
        }
    }
}
