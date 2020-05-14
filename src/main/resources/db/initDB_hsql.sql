DROP TABLE user IF EXISTS;
DROP TABLE user_roles IF EXISTS;
DROP TABLE restaurant IF EXISTS;
DROP TABLE menu IF EXISTS;
DROP TABLE dish IF EXISTS;
DROP TABLE vote IF EXISTS;
DROP SEQUENCE global_seq IF EXISTS;

CREATE SEQUENCE GLOBAL_SEQ AS INTEGER START WITH 100000;

CREATE TABLE user
(
    id         INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(255)            NOT NULL,
    email      VARCHAR(255)            NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    registered TIMESTAMP DEFAULT now() NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL
);
CREATE UNIQUE INDEX user_unique_email_idx ON user (email);

CREATE TABLE user_roles
(
    user_id INTEGER NOT NULL,
    role    VARCHAR(255),
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES user (id)
        ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id   INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);
CREATE UNIQUE INDEX restaurants_unique_name_idx
    ON restaurant (name);

CREATE TABLE menu
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name          VARCHAR(255) NOT NULL,
    date          DATE         NOT NULL,
    restaurant_id INTEGER      NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id)
        ON DELETE CASCADE
);
CREATE UNIQUE INDEX menu_unique_restaurant_id_date_idx
    ON menu (restaurant_id, date);

CREATE TABLE dish
(
    id      INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    name    VARCHAR(255) NOT NULL,
    price   INTEGER      NOT NULL,
    menu_id INTEGER      NOT NULL,
    FOREIGN KEY (menu_id) REFERENCES menu (id)
        ON DELETE CASCADE
);

CREATE TABLE vote
(
    id            INTEGER GENERATED BY DEFAULT AS SEQUENCE GLOBAL_SEQ PRIMARY KEY,
    date          DATE    NOT NULL,
    time          TIME    NOT NULL,
    user_id       INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    FOREIGN KEY (user_id) REFERENCES USER (id)
        ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id)
        ON DELETE CASCADE
);
CREATE UNIQUE INDEX vote_unique_user_date_idx
    ON vote (user_id, date);