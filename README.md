# Пример системы аутентификации на Tarantool Cartidge и Java с использованием коннектора cartridge-java

## Состав проекта
- **authentication** - проект на Tarantool Cartridge, реализующий хранилище данных пользователей для последующей аутентификации
- **Authentication-Example** - проект на java, предоставляющий API для регистрации/аутентификации/работы с пользовательской информацией. Использует коннектор cartridge-java.

## Сборка и запуск проектов
### authentication
Для сборки проекта перейти в директорию **authentication** и выполнить следюущую последовательность команд:

```bash
cartridge build # сборка приложения
cartridge start # запуск кластера
cartridge replicasets setup --bootstrap-vshard # настройка репликасетов
curl -X POST http://localhost:8081/migrations/up # применение миграций
```
после чего проект будет полностью настроен.

### Authentication-Example
В корневой папке проекта выполнить:

```bash
mvn clean package # для сборки проекта
java -jar ./target/authentication-example-1.0-SNAPSHOT.jar # для запуска приложения
```

После этого можно отправлять запросы на **http://localhost:8080** по эндпойнтам:

```bash
POST -> /register # регистрация

#
# {
#   "login":"login",
#   "password":"password"
# }
#

POST -> /login # аутентификация пользователя

#
# {
#   "login":"login",
#   "password":"password"
# }
#

GET -> /{login} # получение информации о пользователе с логином {login}

PUT -> /{login} # обновление пароля пользователя с логином {login}

#
# {
#   "password":"password"
# }
#

DELETE -> /{login} # удаление пользователя с логином {login}
