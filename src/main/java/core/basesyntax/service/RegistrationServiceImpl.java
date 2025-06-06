package core.basesyntax.service;

import core.basesyntax.dao.StorageDao;
import core.basesyntax.dao.StorageDaoImpl;
import core.basesyntax.exception.InvalidUserDataException;
import core.basesyntax.model.User;

public class RegistrationServiceImpl implements RegistrationService {
    private final StorageDao storageDao = new StorageDaoImpl();
    private static final int MIN_AGE = 18;
    private static final int MIN_PASSWORD_LENGTH = 6;
    private static final int MIN_LOGIN_LENGTH = 6;

    @Override
    public User register(User user) throws InvalidUserDataException {
        if (user == null) {
            throw new InvalidUserDataException("User has invalid details.");
        }
        if (!validatePassword(user.getPassword())) {
            throw new InvalidUserDataException("Invalid password.");
        }
        if (user.getAge() < MIN_AGE) {
            throw new InvalidUserDataException("User is too young.");
        }
        if (!validateLogin(user.getLogin())) {
            throw new InvalidUserDataException("Invalid login or login already exists.");
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
