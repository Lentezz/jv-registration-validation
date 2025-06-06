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
        if (!validatePassword(user.getPassword())) {
            throw new InvalidUserDataException(String.format(
                    "Password is null or password length is less %d than  characters.",
                    MIN_PASSWORD_LENGTH));
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException(String.format(
                    "User is too young. Min age is %d.", MIN_AGE));
        }
        if (!validateLogin(user.getLogin())) {
            throw new InvalidUserDataException(String.format(
                    "User already exist or login is invalid. Min login length is %d.",
                    MIN_LOGIN_LENGTH));
        }
        return storageDao.add(user);
    }

    private boolean validatePassword(String password) {
        return password != null && password.length() >= MIN_PASSWORD_LENGTH;
    }

    private boolean validateLogin(String login) {
        return login != null && storageDao.get(login) == null && login.length() >= MIN_LOGIN_LENGTH;
    }
}
