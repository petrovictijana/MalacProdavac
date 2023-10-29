INSERT IGNORE INTO role (role_id,name) VALUES (1,'User');
INSERT IGNORE INTO role (role_id,name) VALUES (2,'Deliverer');
INSERT IGNORE INTO role (role_id,name) VALUES (3,'Seller');

INSERT IGNORE INTO category (category_id,name) VALUES (1,'Sveze meso');
INSERT IGNORE INTO category (category_id,name) VALUES (2,'Jaja');
INSERT IGNORE INTO category (category_id,name) VALUES (3,'Mlecni prozivodi');
INSERT IGNORE INTO category (category_id,name) VALUES (4,'Voce');
INSERT IGNORE INTO category (category_id,name) VALUES (5,'Povrce');

INSERT IGNORE INTO deliverers (deliverer_id,location,latitude,longitude) VALUES (2,'Kragujevac',20.90,44.02);

INSERT IGNORE INTO measurement (measurement_id,name) VALUES (1,'bottle');
INSERT IGNORE INTO measurement (measurement_id,name) VALUES (2,'liter');
INSERT IGNORE INTO measurement (measurement_id,name) VALUES (3,'piece');
INSERT IGNORE INTO measurement (measurement_id,name) VALUES (4,'kg');

INSERT IGNORE INTO users (user_id,name,surname,username,password,email,picture,role_id) VALUES (1,'Bogdan','Lukic','Boki037','Bokii037','bogdan037@gmail.com','aaaa',1);
INSERT IGNORE INTO users (user_id,name,surname,username,password,email,picture,role_id) VALUES (2,'Pera','Peric','Peki034','Peki034','pekan037@gmail.com','bbbb',2);
INSERT IGNORE INTO users (user_id,name,surname,username,password,email,picture,role_id) VALUES (3,'Aleksandra','Jankovic','aleksaleks','Grosnica04','saska.jankovic19@gmail.com','tttt',3);

INSERT IGNORE INTO sellers (seller_id,pib,address,latitude,longitude) VALUES (3,'103920327','Crkvinska 2',20.87, 43.94);


