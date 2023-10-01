Александр, добрый день, в приложении notebook внёс следующие изменения:
- Добавил вывод меню с командами для пользователя;
- Обернул код в слое UserView в try\catch, чтобы приложени не падало при появлении исключений;
- Добавил метод Delete в UserRepository;
- Исправил проверку на ввод пустого значения в методе saveUser;
- Перенёс логику метода createUser в слой UserRepository;
- Добавил метод createNewUserNoId в UserRepository
- Вынес логику "dao" в слой UserRepository;
- Перенёс логиику из метода readUser в метод findById слоя UserRepository и связал их через UserController
