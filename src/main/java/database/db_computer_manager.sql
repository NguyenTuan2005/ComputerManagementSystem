
CREATE DATABASE db_computer_manager ;


CREATE TABLE suppliers (
                           id SERIAL PRIMARY KEY,
                           company_name VARCHAR(255),
                           email VARCHAR(100),
                           phone_number VARCHAR(10),
                           address VARCHAR(255),
                           contact_date DATE
);

CREATE TABLE product (
                         id  SERIAL PRIMARY KEY,
                         suppliers_id INT REFERENCES suppliers(id),
                         name VARCHAR(255),
                         quality INT,
                         price INT,
                         genre VARCHAR(50),
                         brand VARCHAR(50),
                         operating_system VARCHAR,
                         cpu VARCHAR,
                         memory VARCHAR(50),
                         ram VARCHAR(50),
                         made_in VARCHAR,
                         status VARCHAR(50)
);


CREATE TABLE manager (
                         id SERIAL PRIMARY KEY,
                         fullname VARCHAR(255),
                         address VARCHAR(255),
                         birthday DATE,
                         phone_number VARCHAR(10)
);

CREATE TABLE customer (
                          id SERIAL PRIMARY KEY,
                          fullName VARCHAR(255),
                          email VARCHAR(255),
                          address VARCHAR(255),
                          password VARCHAR(255)
);

ALTER TABLE customer ADD CONSTRAINT customer_email_unique UNIQUE(email);

CREATE TABLE "order" (
                         id SERIAL PRIMARY KEY,
                         manager_id INT REFERENCES Manager(id),
                         customer_id INT REFERENCES Customer(id),
                         orderData DATE,
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
                         id SERIAL PRIMARY KEY,
                         username VARCHAR(255),
                         password VARCHAR(255),
                         email VARCHAR(255),
                         create_date DATE
);

create table image(
                        id serial primary key
                        ,product_id int  references product(id)
                        , alt varchar
                        , url varchar not null
);

ALTER TABLE account ADD CONSTRAINT account_email_unique UNIQUE(email);

ALTER TABLE account ADD CONSTRAINT username_unique UNIQUE(username);

-- user admin

INSERT INTO public.manager (fullname , address, birthday, phone_number)
VALUES('admin', 'nlu','10-10-2024','01233456');
-- select  * from account;
INSERT INTO public.account (manage_id, username,password,email, create_date )
values (1,'admin', '1111','23130370@st.hcmuaf.edu.vn','10-10-2024');
