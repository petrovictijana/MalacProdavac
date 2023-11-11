    INSERT IGNORE INTO role (role_id,name) VALUES (1,'User');
    INSERT IGNORE INTO role (role_id,name) VALUES (2,'Deliverer');
    INSERT IGNORE INTO role (role_id,name) VALUES (3,'Seller');

INSERT IGNORE INTO category (category_id,name,picture) VALUES (1,'Mlečni proizvodi','melcni_proizvodi.jpg');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (2,'Voće i povrće','voce_i_povrce.jpg');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (3,'Mesne prerađevine','mesne_prerađevine.jpg');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (4,'Sveže meso','sveze_meso.jpg');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (5,'Žitarice','zitarice.jpg');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (6,'Napici','napici.jpg');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (7,'Biljna ulja','biljna_ulja.jpg');
INSERT IGNORE INTO category (category_id,name,picture) VALUES (8,'Namazi','namazi.jpg');

    INSERT IGNORE INTO measurement (measurement_id,name) VALUES (1,'bottle');
    INSERT IGNORE INTO measurement (measurement_id,name) VALUES (2,'liter');
    INSERT IGNORE INTO measurement (measurement_id,name) VALUES (3,'piece');
    INSERT IGNORE INTO measurement (measurement_id,name) VALUES (4,'kg');

    INSERT IGNORE INTO users (name,surname,username,password,email,picture,role_id) VALUES ('Bogdan','Lukic','Boki037','$2a$10$QnURZMX.ibbhUxlcMmNvcu4W9RYCmxEDzpM3/Ius8XI77sgIUtjoW','bogdan037@gmail.com','aaaa',1);
    INSERT IGNORE INTO users (name,surname,username,password,email,picture,role_id) VALUES ('Pera','Peric','Peki034','$2a$10$A6W9fL2l8W9q0yeo6AjT0eml3Rol7zuyVpqRawFaZdhm94xSDo2Rm','pekan037@gmail.com','bbbb',2);
    INSERT IGNORE INTO users (name,surname,username,password,email,picture,role_id) VALUES ('Aleksandra','Jankovic','aleksaleks','$2a$10$UltGnOoE5oP925RUxQ7UHuTAcBYWsQCJbIr3EFKmwLxTUC2AhDn.u','saska.jankovic19@gmail.com','tttt',3);
    INSERT IGNORE INTO users (name,surname,username,password,email,picture,role_id) VALUES ('Mateja','Kovacevic','matejakovacevic','$2a$10$xit5sVgqsm66qkhDI8Eygea.Va0KXSxEWbMwEmOvtp411ygz53wa2','mateja.kovacevic@gmail.com','sffds',3);
    INSERT IGNORE INTO users (name,surname,username,password,email,picture,role_id) VALUES ('Miroslav','Pekic','pekic','$2a$10$r9vW.lOOXweKo4mgOFl7P.FL7IQ4hwGb16HTDWdTjZJu2llXyYYfC','miroslav.pekic80@gmail.com','bbbb',3);
    INSERT IGNORE INTO users (name,surname,username,password,email,picture,role_id) VALUES ('Nemanja','Vidic','vidicnemanja','$2a$10$1BN8HbVhM1ECgyUmpAuDVuCtIPLm64zlyjT6ep/cREdKUC.BEYBj6','vidic.nemanja20@gmail.com','tttt',1);
    INSERT IGNORE INTO users (name,surname,username,password,email,picture,role_id) VALUES ('Igor','Nikodijevic','nikodijevici','$2a$10$d7.XTG9OY7CT7CJCB9b4g.UO0hQleGv/oy2FQDpgXW8jreunTAh0m','nikodijevicikg@gmail.com','tttt',3);


    INSERT IGNORE INTO sellers (seller_id,pib,address,latitude,longitude) VALUES (3,'103920327','Crkvinska 2', 43.944544, 20.872520);
    INSERT IGNORE INTO sellers (seller_id,pib,address,latitude,longitude) VALUES (4,'159554583','Tanaska Rajica 12', 44.653996, 20.263756);
    INSERT IGNORE INTO sellers (seller_id,pib,address,latitude,longitude) VALUES (5,'856235485','Filipa Visnjica 55', 44.277296, 19.914113);
    INSERT IGNORE INTO sellers (seller_id,pib,address,latitude,longitude) VALUES (7,'951485315','Ljubomira Jovanovica 70',43.641911, 20.915729);


    INSERT IGNORE INTO deliverers (deliverer_id,location,latitude,longitude) VALUES (2,'Kragujevac',20.90,44.02);

    insert IGNORE into order_status (order_status_id, name) values (1, "dostavljeno");
    insert IGNORE into order_status (order_status_id, name) values (2, "naruceno");
    insert IGNORE into order_status (order_status_id, name) values (3, "slanje");

    INSERT IGNORE INTO products(product_name, category_id, seller_id, measurement_id, picture, price, description) values ("Jabuke", 4, 3, 4, "fedf", 60, "Naša organska jabuka je savršen spoj ukusa i zdravlja, bez pesticida i hemijskih đubriva.");
    INSERT IGNORE INTO products(product_name, category_id, seller_id, measurement_id, picture, price, description) values ("Kruske", 4, 3, 4, "fedf", 125, "Naše organske kruške su sočne i prirodno uzgajane, pružajući osvežavajući ukus bez upotrebe pesticida i hemijskih đubriva.");
    INSERT IGNORE INTO products(product_name, category_id, seller_id, measurement_id, picture, price, description) values ("Maline", 4, 3, 4, "fedf", 390, "Sveže organske maline - prirodno uzgojene, ukusne i bez štetnih hemikalija");
    INSERT IGNORE INTO products(product_name, category_id, seller_id, measurement_id, picture, price, description) values ("Svinjski but", 1, 4, 4, "fedf", 1200, "Svinjski but - vrhunski komad mesa sa bogatim ukusom i mekoćom koji će zadovoljiti svačije gurmanluk.");
    INSERT IGNORE INTO products(product_name, category_id, seller_id, measurement_id, picture, price, description) values ("Jagnjeci kotleti", 4, 4, 4, "fedf", 2420, "Jagnjeći kotleti - delikatesno jelo s neodoljivim ukusom i sočnošću koji će vas oduševiti.");
    INSERT IGNORE INTO products(product_name, category_id, seller_id, measurement_id, picture, price, description) values ("Guscja jaja", 2, 5, 3, "fedf", 20, "Gusčja jaja - prirodna poslastica s bogatim i intenzivnim ukusom, savršena za različite kulinarske kreacije.");

    INSERT IGNORE INTO orders (seller_id, latitude_buyer, longitude_buyer, order_date, buyer_id, order_status_id) VALUES (3, 45.5, 41.5, "2023-10-30 18:23:00.000000", 1, 1);
    INSERT IGNORE INTO orders (seller_id, latitude_buyer, longitude_buyer, order_date, buyer_id, order_status_id) VALUES (4, 45.5, 41.5, "2023-10-24 18:23:00.000000", 6, 1);
    INSERT IGNORE INTO orders (seller_id, latitude_buyer, longitude_buyer, order_date, buyer_id, order_status_id) VALUES (5, 45.5, 41.5, "2023-11-04 18:23:00.000000", 1, 1);
    INSERT IGNORE INTO orders (seller_id, latitude_buyer, longitude_buyer, order_date, buyer_id, order_status_id) VALUES (3, 45.5, 41.5, "2023-11-01 18:23:00.000000", 6, 1);
    INSERT IGNORE INTO orders (seller_id, latitude_buyer, longitude_buyer, order_date, buyer_id, order_status_id) VALUES (4, 45.5, 41.5, "2023-11-03 18:23:00.000000", 1, 1);
    INSERT IGNORE INTO orders (seller_id, latitude_buyer, longitude_buyer, order_date, buyer_id, order_status_id) VALUES (5, 45.5, 41.5, "2023-11-01 18:23:00.000000", 6, 1);
    INSERT IGNORE INTO orders (seller_id, latitude_buyer, longitude_buyer, order_date, buyer_id, order_status_id) VALUES (3, 45.5, 41.5, "2023-10-27 18:23:00.000000", 1, 1);
    INSERT IGNORE INTO orders (seller_id, latitude_buyer, longitude_buyer, order_date, buyer_id, order_status_id) VALUES (4, 45.5, 41.5, "2023-10-15 18:23:00.000000", 6, 1);
    INSERT IGNORE INTO orders (seller_id, latitude_buyer, longitude_buyer, order_date, buyer_id, order_status_id) VALUES (4, 45.5, 41.5, "2023-11-03 18:23:00.000000", 67, 1);

    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (1, 1, 2);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (1, 2, 1);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (2, 4, 3);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (3, 4, 2);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (4, 4, 5);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (5, 6, 10);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (6, 4, 2);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (6, 5, 3);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (7, 1, 2);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (7, 3, 3);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (7, 2, 1);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (8, 4, 2);
    INSERT IGNORE INTO purchase_order (order_id, product_id, quantity) VALUES (9, 5, 1);

INSERT IGNORE INTO products (product_id, description, picture, price, product_name, category_id, measurement_id, seller_id) VALUES (1,'Sveze i ukusno','aaa',200,'Jabuke',4,4,3);
INSERT IGNORE INTO products (product_id, description, picture, price, product_name, category_id, measurement_id, seller_id) VALUES (2,'Sveze i ukusno','aaa',250,'Jaja',2,3,3);
INSERT IGNORE INTO products (product_id, description, picture, price, product_name, category_id, measurement_id, seller_id) VALUES (3,'Sveze i ukusno','aaa',290,'Kruske',4,4,3);
INSERT IGNORE INTO products (product_id, description, picture, price, product_name, category_id, measurement_id, seller_id) VALUES (4,'Sveze i ukusno','aaa',300,'Jagode',4,4,3);

INSERT IGNORE INTO product_comment (product_comment_id,date, grade, text, product_id, user_id) VALUES (1,null,5,'Vrlo dobar proizvod',4,2);
INSERT IGNORE INTO product_comment (product_comment_id,date, grade, text, product_id, user_id) VALUES (2,null,4,'Zadovoljan sam proizvodom. Zadovoljan sam proizvodom',4,1);
INSERT IGNORE INTO product_comment (product_comment_id,date, grade, text, product_id, user_id) VALUES (3,null,3,'Prosli put su bile bolje',5,2);

