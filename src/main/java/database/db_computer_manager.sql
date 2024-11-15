-- Using PostgreSQL.
CREATE DATABASE db_computer_manager ;


CREATE TABLE suppliers (
                           id INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
                           company_name VARCHAR(255),
                           email VARCHAR(100),
                           phone_number VARCHAR(10),
                           address VARCHAR(255),
                           contract_date DATE,
                           delete_row INT DEFAULT '1',
);

CREATE TABLE product (
                         id INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
                         suppliers_id INT REFERENCES suppliers(id),
                         name VARCHAR(255),
                         quantity INT,
                         price INT,
                         genre VARCHAR(50),
                         brand VARCHAR(50),
                         operating_system VARCHAR,
                         cpu VARCHAR,
                         memory VARCHAR(50),
                         ram VARCHAR(50),
                         made_in VARCHAR,
                         status VARCHAR(50),
                         delete_row INT DEFAULT '1',
                         disk varchar,
                         monitor varchar,
                         weight varchar,
                         card varchar,
);

CREATE TABLE manager (
                         id INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
                         fullname VARCHAR(255),
                         address VARCHAR(255),
                         birthday DATE,
                         phone_number  VARCHAR(10)
);

CREATE TABLE customer (
                          id INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
                          fullName VARCHAR(255),
                          email VARCHAR(255),
                          address VARCHAR(255),
                          password VARCHAR(255),
                          avata_img VARCHAR
);

ALTER TABLE customer ADD CONSTRAINT customer_email_unique UNIQUE(email);

CREATE TABLE "order" (
                         id INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
                         manager_id INT REFERENCES Manager(id),
                         customer_id INT REFERENCES Customer(id),
                         order_date DATE,
                         ship_address VARCHAR(255),
                         status VARCHAR
);


CREATE TABLE order_detail (
                              order_id INT REFERENCES "order"(id),
                              product_id INT REFERENCES Product(id),
                              unit_price INT,
                              quantity INT,
                              PRIMARY KEY (order_id, product_id)
);


CREATE TABLE account (
                         id INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) PRIMARY KEY,
                         manage_id INT REFERENCES manager(id),
                         username VARCHAR(255),
                         password VARCHAR(255),
                         email VARCHAR(255),
                         create_date DATE,
                         avata_img VARCHAR
);

create table image(
                        id INT GENERATED BY DEFAULT AS IDENTITY (START WITH 1 INCREMENT BY 1) primary key,
                        product_id int  references product(id),
                        alt varchar,
                        url varchar not null
);

ALTER TABLE account ADD CONSTRAINT account_email_unique UNIQUE(email);

ALTER TABLE account ADD CONSTRAINT username_unique UNIQUE(username);

-- update
ALTER TABLE product RENAME COLUMN quality to quantity;
