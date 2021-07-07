DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);
INSERT INTO meals (date_time, user_id, description, calories)
VALUES (timestamp '2021-06-30 10:00:00', 100000, 'Завтрак', '500'),
       (timestamp '2021-06-30 13:00:00', 100000, 'Обед', '1000'),
       (timestamp '2021-06-30 20:00:00', 100000, 'Ужин', '500'),
       (timestamp '2021-07-01 00:00:00', 100000, 'Еда на граничное значение', '100'),
       (timestamp '2021-07-01 10:00:00', 100000, 'Завтрак', '1000'),
       (timestamp '2021-07-01 13:00:00', 100000, 'Обед', '500'),
       (timestamp '2021-07-01 20:00:00', 100000, 'Ужин', '410');