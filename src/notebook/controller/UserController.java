package notebook.controller;

import notebook.model.User;
import notebook.model.repository.GBRepository;

import java.util.List;
import java.util.Objects;

public class UserController {
    private final GBRepository repository;

    public UserController(GBRepository repository) {

        this.repository = repository;
    }

    public void saveUser(User user) {
        if (user.getFirstName().isEmpty()) {
            throw new RuntimeException("Не может быть пустых полей");
        }
        if (user.getLastName().isEmpty()) {
            throw new RuntimeException("Не может быть пустых полей");
        }
        if (user.getPhone().isEmpty()) {
            throw new RuntimeException("Не может быть пустых полей");
        }
        repository.create(user);
    }

    public User readUser(Long userId) {
        return repository.findById(userId);
    }

    public void updateUser(String userId, User update) {
//        update.setId(Long.parseLong(userId));
        repository.update(Long.parseLong(userId), update);
    }

    public void deleteUser(String userId) {
        repository.delete(Long.parseLong(userId));
    }

    public List<User> getAllUsers() {
        return repository.findAll();
    }

    public User getNewUser(List<String> list) {
        return repository.createNewUserNoId(list);
    }
}
