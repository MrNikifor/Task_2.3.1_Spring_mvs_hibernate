package web.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import web.dao.UserDao;
import web.exeption.UserNotFoundException;
import web.model.User;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public void saveUser(String firstName, String lastName, String email) {
        userDao.saveUser(firstName, lastName, email);
    }

    @Override
    @Transactional(readOnly = true)
    public User showUserById(int id) {
        User user = userDao.showUserById(id);
        if (user == null) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден.");
        }
        return user;
    }

    @Override
    @Transactional(readOnly = true)
    public List<User> getAllUsers() {
        return userDao.getAllUsers();
    }

    @Override
    @Transactional
    public void updateUserById(int id, User updatedUser) {
        User existingUser = userDao.showUserById(id);
        if (existingUser == null) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден.");
        }
        userDao.updateUserById(id, updatedUser);
    }

    @Override
    @Transactional
    public void removeUserById(int id) {
        User user = userDao.showUserById(id);
        if (user == null) {
            throw new UserNotFoundException("Пользователь с ID " + id + " не найден.");
        }
        userDao.removeUserById(id);
    }
}