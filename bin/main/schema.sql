CREATE TABLE shopMenu
(
    id INT NOT NULL AUTO_INCREMENT,
    menu_name VARCHAR(50) NOT NULL,
    detail VARCHAR(100) NOT NULL,
    price  INT NOT NULL,
    PRIMARY KEY(id)
);

CREATE TABLE customer
(
  id INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  menu_id INT NOT NULL,
  order_time datetime NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE basket
(
  id INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  menu_id INT NOT NULL,
  order_time datetime NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE orders
(
  id INT NOT NULL AUTO_INCREMENT,
  customer_id INT NOT NULL,
  menu_id INT NOT NULL,
  order_time datetime NOT NULL,
  delivery_flg int NOT NULL,
  pay_flg int NOT NULL,
  PRIMARY KEY(id)
);

CREATE TABLE seat
(
  seat_id INT NOT NULL,
  status INT NOT NULL,
  PRIMARY KEY(seat_id)
);

CREATE TABLE completeOrder
(
  c_id INT NOT NULL AUTO_INCREMENT,
  c_seat_id INT NOT NULL,
  c_menu_id INT NOT NULL,
  c_create_time datetime NOT NULL,
  PRIMARY KEY(c_id)
);
