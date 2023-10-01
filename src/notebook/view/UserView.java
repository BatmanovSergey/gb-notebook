package notebook.view;

import notebook.controller.UserController;
import notebook.model.User;
import notebook.util.Commands;

import java.util.List;
import java.util.ArrayList;
import java.util.Scanner;

public class UserView {
    private final UserController userController;

    public UserView(UserController userController) {
        this.userController = userController;
    }

    public void run() {
        Commands com;

        while (true) {
            String command = prompt("----------------\n" +
                    "'Доступные команды':\n" +
                    "----------------\n" +
                    "CREATE: Создать пользователя.\n" +
                    "READ: Показать пользователя.\n" +
                    "UPDATE: Обновить информацию о пользователе.\n" +
                    "LIST: Вывести список всех пользователей.\n" +
                    "DELETE: далить пользователя.\n" +
                    "EXIT: Выйти из приложения.\n" +
                    "----------------\n" +
                    "Введите команду: ");

            try {
                com = Commands.valueOf(command);
                if (com == Commands.EXIT) {
                    System.out.println("Вы завершили работу приложения!");
                    return;
                }
                switch (com) {
                    case CREATE:
                        List<String> userinitials = enterinitials();
                        User u = userController.getNewUser(userinitials);
                        userController.saveUser(u);
                        break;
                    case READ:
                        String id = prompt("Идентификатор пользователя: ");
                        if (id.isEmpty()) {
                            throw new RuntimeException("Идентификатор не может быть пустым");
                        } else {
                            User user = userController.readUser(Long.parseLong(id));
                            System.out.println(user);
                            System.out.println();
                        }
                        break;
                    case UPDATE:
                        String userId = prompt("Enter user id: ");
                        if (userId.isEmpty()) {
                            throw new RuntimeException("Идентификатор не может быть пустым");
                        } else {
                            List<String> initials = enterinitials();
                            User u1 = userController.getNewUser(initials);
                            userController.updateUser(userId, u1);
                        }
                        break;
                    case DELETE:
                        String delid = prompt("Идентификатор пользователя: ");
                        if (delid.isEmpty()) {
                            throw new RuntimeException("Идентификатор не может быть пустым");
                        } else {
                            userController.deleteUser(delid);
                        }
                        break;
                    case LIST:
                        System.out.println(userController.getAllUsers());
                        break;
                }
            } catch (IllegalArgumentException e) {
                System.out.println("----------------\n" +
                        "Ошибка ввода данных. Повторите попытку!!!\n" +
                        "----------------");
            } catch (RuntimeException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    private String prompt(String message) {
        Scanner in = new Scanner(System.in);
        System.out.print(message);
        return in.nextLine();
    }

    private List<String> enterinitials() {
        List<String> initials = new ArrayList<>();

        String firstName = prompt("Имя: ");
        initials.add(firstName.replaceAll(" ", ""));

        String lastName = prompt("Фамилия: ");
        initials.add(lastName.replaceAll(" ", ""));

        String phone = prompt("Номер телефона: ");
        initials.add(phone.replaceAll(" ", ""));

        return initials;
    }

    //    private User createUser() {
//
//        String firstName = prompt("Имя: ");
////        if (firstName.isEmpty()) {
////            throw new RuntimeException("Имя не может быть пустым");
////        }
//        String lastName = prompt("Фамилия: ");
////        if (lastName.isEmpty()) {
////            throw new RuntimeException("Фамилия не может быть пустым");
////        }
//        String phone = prompt("Номер телефона: ");
////        if (phone.isEmpty()) {
////            throw new RuntimeException("Телефон не может быть пустым");
////        }
//        return new User(firstName.replaceAll(" ", ""),
//                lastName.replaceAll(" ", ""),
//                phone.replaceAll(" ", ""));
//    }
}
