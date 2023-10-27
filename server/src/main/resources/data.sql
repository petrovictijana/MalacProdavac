INSERT IGNORE INTO role (role_id,name) VALUES (1,'User');
INSERT IGNORE INTO role (role_id,name) VALUES (2,'Deliverer');
INSERT IGNORE INTO role (role_id,name) VALUES (3,'Seller');

INSERT IGNORE INTO category (category_id,name) VALUES (1,'Sveze meso');
INSERT IGNORE INTO category (category_id,name) VALUES (2,'Jaja');
INSERT IGNORE INTO category (category_id,name) VALUES (3,'Mlecni prozivodi');
INSERT IGNORE INTO category (category_id,name) VALUES (4,'Voce');
INSERT IGNORE INTO category (category_id,name) VALUES (5,'Povrce');

INSERT IGNORE INTO deliverer (deliverer_id,location,latitude,longitude) VALUES (2,'Kragujevac',20.90,44.02);

INSERT IGNORE INTO measurement (measurement_id,name) VALUES (1,'bottle');
INSERT IGNORE INTO measurement (measurement_id,name) VALUES (2,'liter');
INSERT IGNORE INTO measurement (measurement_id,name) VALUES (3,'piece');
INSERT IGNORE INTO measurement (measurement_id,name) VALUES (4,'kg');
/*
INSERT IGNORE INTO user (user_id,name,surname,username,password,email,picture,role_id) VALUES (1,'Bogdan','Lukic','Boki037','Bokii037','bogdan037@gmail.com',null,1);
INSERT IGNORE INTO user (user_id,name,surname,username,password,email,picture,role_id) VALUES (2,'Pera','Peric','Peki034','Peki034','pekan037@gmail.com',null,2);
INSERT IGNORE INTO user (user_id,name,surname,username,password,email,picture,role_id) VALUES (3,'Aleksandra','Jankovic','aleksaleks','Grosnica04','saska.jankovic19@gmail.com',null,3);

INSERT IGNORE INTO seller (seller_id,pib,address,latitude,longitude) VALUES (3,'103920327','Crkvinska 2',20.87, 43.94);

INSERT IGNORE INTO product (product_id,seller_id,product_name,picture,price,category_id,measurement_id) VALUES (1,3,'jabuke','',250,4,4);
INSERT IGNORE INTO product (product_id,seller_id,product_name,picture,price,category_id,measurement_id) VALUES (1,3,'sljive','',150,4,4);
INSERT IGNORE INTO product (product_id,seller_id,product_name,picture,price,category_id,measurement_id) VALUES (1,3,'jagode','',450,4,4);
INSERT IGNORE INTO product (product_id,seller_id,product_name,picture,price,category_id,measurement_id) VALUES (1,3,'mleko','',150,3,2);
INSERT IGNORE INTO product (product_id,seller_id,product_name,picture,price,category_id,measurement_id) VALUES (1,3,'jaja','',300,2,3);
INSERT IGNORE INTO product (product_id,seller_id,product_name,picture,price,category_id,measurement_id) VALUES (1,3,'sir','',500,3,2);

*/



