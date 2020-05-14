DELETE
FROM user_roles;
DELETE
FROM user;
DELETE
FROM restaurant;
DELETE
FROM menu;
DELETE
FROM dish;
DELETE
FROM vote;
ALTER SEQUENCE GLOBAL_SEQ
    RESTART WITH 100000;

INSERT INTO user (name, email, password)
VALUES ('USER', 'user1@gmail.com', '{noop}password1'),
       ('ADMIN', 'admin@gmail.com', '{noop}admin');

INSERT INTO user_roles (role, user_id)
VALUES ('USER', 100000),
       ('ADMIN', 100001);

INSERT INTO restaurant (name)
VALUES ('One_Two_Pizza');

INSERT INTO menu (name, date, restaurant_id)
VALUES ('Saturday', '2020-05-10', 100002),
       ('Sunday', '2020-05-11', 100002);

INSERT INTO dish (name, price, menu_id)
VALUES ('Breakfast', 7.00, 100003),
       ('Afternoon tea', 5.00, 100003),
       ('Lunch', 23.00, 100003),
       ('Pizza', 12.00, 100003),
       ('Evening meal', 10.00, 100003);

INSERT INTO VOTE (date, time, restaurant_id, user_id)
VALUES ('2020-05-13', '13:00:00', 100002, 100000),
       ('2020-05-14', '09:00:00', 100002, 100000),
       ('2020-05-09', '10:00:00', 100002, 100001),
       ('2020-05-10', '13:00:00', 100002, 100000);


