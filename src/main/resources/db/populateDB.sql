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
VALUES ('Admin', 'admin@gmail.com', '{noop}admin'),
       ('User1', 'user1@gmail.com', '{noop}password1'),
       ('User2', 'user2@gmail.com', '{noop}password2');

INSERT INTO user_roles (role, user_id)
VALUES ('USER1', 100000),
       ('USER2', 100001),
       ('ADMIN', 100002);

INSERT INTO restaurant (name)
VALUES ('One_Two_Pizza'),
       ('DODO_Pizza');

INSERT INTO menu (name, date, restaurant_id)
VALUES ('Saturday', '2020-05-10', 100003),
       ('Sunday', '2020-05-11', 100004);

INSERT INTO dish (name, price, menu_id)
VALUES ('Breakfast', 7.00, 100005),
       ('Afternoon tea', 5.00, 100005),
       ('Lunch', 23.00, 100005),
       ('Pizza', 12.00, 100005),
       ('Evening meal', 10.00, 100005);

INSERT INTO VOTE (date, time, restaurant_id, user_id)
VALUES ('2020-05-11', '13:00:00', 100003, 100000),
       ('2020-05-10', '17:00:00', 100003, 100001),
       ('2020-05-11', '14:00:00', 100003, 100002),
       ('2020-05-10', '09:00:00', 100004, 100000),
       ('2020-05-09', '10:00:00', 100004, 100001),
       ('2020-05-10', '13:00:00', 100004, 100002);


