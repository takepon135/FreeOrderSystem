
INSERT INTO shopMenu( menu_name, detail, price) 
VALUES( 'たこ焼き', '美味しいたこ焼きです', '500');
INSERT INTO shopMenu(menu_name, detail, price) 
VALUES('ステーキ', 'ステーキです', '2000');

INSERT INTO seat(seat_id, status) 
VALUES('1', '1');
INSERT INTO seat(seat_id, status) 
VALUES('2', '1');
INSERT INTO seat(seat_id, status) 
VALUES('3', '1');
INSERT INTO seat(seat_id, status) 
VALUES('4', '1');
INSERT INTO seat(seat_id, status) 
VALUES('5', '1');
   
  
INSERT INTO completeOrder(c_seat_id, c_menu_id, c_create_time)
VALUES('1', '1', '2022-10-11 17:00:00');

INSERT INTO completeOrder(c_seat_id, c_menu_id, c_create_time)
VALUES('1', '1', '2022-10-11 17:00:00');

INSERT INTO completeOrder(c_seat_id, c_menu_id, c_create_time)
VALUES('1', '2', '2022-08-11 17:00:00');
