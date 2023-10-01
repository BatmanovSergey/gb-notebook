package notebook.model.repository.impl;

import notebook.util.mapper.impl.UserMapper;
import notebook.model.User;
import notebook.model.repository.GBRepository;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class UserRepository implements GBRepository {
    private final UserMapper mapper;
    private final String fileName;

    public UserRepository(String fileName) {
        this.mapper = new UserMapper();
        this.fileName = fileName;
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public User createNewUserNoId(List<String> list) {
        User user = new User(list.get(0), list.get(1), list.get(2));
        return user;
    }

    @Override
    public List<String> readAll() {
        List<String> lines = new ArrayList<>();
        try {
            File file = new File(fileName);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            if (line != null) {
                lines.add(line);
            }
            while (line != null) {
                // считываем остальные строки в цикле
                line = reader.readLine();
                if (line != null) {
                    lines.add(line);
                }
            }
            fr.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    @Override
    public void saveAll(List<String> data) {
        try (FileWriter writer = new FileWriter(fileName, false)) {
            for (String line : data) {
                // запись всей строки
                writer.write(line);
                // запись по символам
                writer.append('\n');
            }
            writer.flush();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public List<User> findAll() {
        List<String> lines = readAll();
        List<User> users = new ArrayList<>();
        for (String line : lines) {
            users.add(mapper.toOutput(line));
        }
        return users;
    }

    @Override
    public User create(User user) {
//        if (user.getFirstName().isEmpty()) {
//            throw new RuntimeException("Имя не может быть пустым");
//        }
//        if (user.getLastName().isEmpty()) {
//            throw new RuntimeException("Фамилия не может быть пустым");
//        }
//        if (user.getPhone().isEmpty()) {
//            throw new RuntimeException("Телефон не может быть пустым");
//        }
        List<User> users = findAll();
        long max = 0L;
        for (User u : users) {
            long id = u.getId();
            if (max < id) {
                max = id;
            }
        }
        long next = max + 1;
        user.setId(next);
        users.add(user);
        write(users);
        return user;
    }

    @Override
    public User findById(Long id) {
        List<User> users = findAll();
        for (User user : users) {
            if (Objects.equals(user.getId(), id)) {
                return user;
            }
        }
        throw new RuntimeException("Пользователь не найден!");
    }

    @Override
    public Optional<User> update(Long userId, User update) {
//        if (userId == null) {
//            throw new RuntimeException("Идентификатор не может быть пустым");
//        }
        List<User> users = findAll();
        User editUser = users.stream()
                .filter(u -> u.getId()
                        .equals(userId))
                .findFirst().orElseThrow(() -> new RuntimeException("Пользователь не найден"));
        if (!update.getFirstName().isEmpty()) {
            editUser.setFirstName(update.getFirstName());
        }
        if (!update.getLastName().isEmpty()) {
            editUser.setLastName(update.getLastName());
        }
        if (!update.getPhone().isEmpty()) {
            editUser.setPhone(update.getPhone());
        }
        write(users);
        return Optional.of(update);
    }

    @Override
    public boolean delete(Long id) {
        List<User> users = findAll();
        List<User> reWriteUsers = new ArrayList<>();
        for (User user : users) {
            if (!user.getId().equals(id)) {
                reWriteUsers.add(user);
            }
        }
        if (users.size() == reWriteUsers.size()) {
            throw new RuntimeException("Такого пользователя не существует!!!");
        } else {
            write(reWriteUsers);
            System.out.println("Пользователь удалён в базы данных!!!");
            return true;
        }
    }

    private void write(List<User> users) {
        List<String> lines = new ArrayList<>();
        for (User u : users) {
            lines.add(mapper.toInput(u));
        }
        saveAll(lines);
    }
}
