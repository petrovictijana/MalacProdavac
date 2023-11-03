INSERT IGNORE INTO role (role_id,name) VALUES (1,'User');
INSERT IGNORE INTO role (role_id,name) VALUES (2,'Deliverer');
INSERT IGNORE INTO role (role_id,name) VALUES (3,'Seller');

INSERT IGNORE INTO category (category_id,name,picture) VALUES (1,'Sveze meso','aa');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (2,'Jaja','aaaa');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (3,'Mlecni prozivodi','aaaa');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (4,'Voce','aaaaaaaa');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (5,'Povrce','aaaaa');

INSERT IGNORE INTO deliverers (deliverer_id,location,latitude,longitude) VALUES (2,'Kragujevac',20.90,44.02);

INSERT IGNORE INTO measurement (measurement_id,name) VALUES (1,'bottle');
INSERT IGNORE INTO measurement (measurement_id,name) VALUES (2,'liter');
INSERT IGNORE INTO measurement (measurement_id,name) VALUES (3,'piece');
INSERT IGNORE INTO measurement (measurement_id,name) VALUES (4,'kg');

INSERT IGNORE INTO users (user_id,name,surname,username,password,email,picture,role_id) VALUES (1,'Bogdan','Lukic','Boki037','Bokii037','bogdan037@gmail.com','aaaa',1);
INSERT IGNORE INTO users (user_id,name,surname,username,password,email,picture,role_id) VALUES (2,'Pera','Peric','Peki034','Peki034','pekan037@gmail.com','bbbb',2);
INSERT IGNORE INTO users (user_id,name,surname,username,password,email,picture,role_id) VALUES (3,'Aleksandra','Jankovic','aleksaleks','Grosnica04','saska.jankovic19@gmail.com','tttt',3);

INSERT IGNORE INTO sellers (seller_id,pib,address,latitude,longitude) VALUES (3,'103920327','Crkvinska 2',20.87, 43.94);

INSERT IGNORE INTO products (product_id, description, picture, price, product_name, category_id, measurment_id, seller_id) VALUES (1,'Sveze i ukusno','aaa',200,'Jabuke',4,4,3);
INSERT IGNORE INTO products (product_id, description, picture, price, product_name, category_id, measurment_id, seller_id) VALUES (2,'Sveze i ukusno','aaa',250,'Jaja',2,3,3);
INSERT IGNORE INTO products (product_id, description, picture, price, product_name, category_id, measurment_id, seller_id) VALUES (3,'Sveze i ukusno','aaa',290,'Kruske',4,4,3);
INSERT IGNORE INTO products (product_id, description, picture, price, product_name, category_id, measurment_id, seller_id) VALUES (4,'Sveze i ukusno','aaa',300,'Jagode',4,4,3);

INSERT IGNORE INTO product_comment (product_comment_id,date, grade, text, product_id, user_id) VALUES (1,null,5,'Vrlo dobar proizvod',1,2);
INSERT IGNORE INTO product_comment (product_comment_id,date, grade, text, product_id, user_id) VALUES (2,null,4,'Zadovoljan sam proizvodom',3,1);
INSERT IGNORE INTO product_comment (product_comment_id,date, grade, text, product_id, user_id) VALUES (3,null,3,'Prosli put su bile bolje',1,2);

