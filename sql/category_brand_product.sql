-- table BRAND
INSERT INTO `brands` VALUES (1,'Acer','Acer.png'),(2,'Samsung','Samsung.png'),(3,'Asus','Asus.png'),
                            (4,'Apple','Apple.png'),(5,'Dell','Dell.png'),(6,'Xiaomi','Xiaomi.png');

-- table BRAND_CATEGORY
INSERT INTO `brand_category` VALUES (1,6),(3,6),(4,6),(5,6),(2,7),(3,7),(4,7),(6,7),(2,10),(3,10),(4,10),(6,10),(2,20);

--
INSERT INTO `categories` VALUES (1,'Thiết-bị-điện-tử',_binary '','electronics.png','Thiết bị điện tử',NULL,NULL),
                                (2,'Camera',_binary '','camera.jpg','Camera',1,'-1-'),
                                (3,'Computer',_binary '','computers.png','Computer',NULL,NULL),
                                (4,'điện-thoại-phụ-kiện',_binary '','cellphones.png','Điện thoại & phụ kiện',NULL,NULL),
                                (5,'Máy-tính-bộ',_binary '','desktop_computers.png','Máy tính bộ',3,'-3-'),
                                (6,'Laptop',_binary '','laptop_computers.png','Laptop',3,'-3-'),
                                (7,'Máy-tính-bảng',_binary '','tablets.png','Máy tính bảng',4,'-4-'),
                                (8,'Linh-kiện-máy-tính',_binary '','computer_components.png','Linh kiện máy tính',3,'-3-'),
                                (9,'Máy-ảnh',_binary '','digital_cameras.png','Máy ảnh',2,'-1-2-'),
                                (10,'Điện-thoại',_binary '','carrier_cellphones.png','Điện thoại',4,'-4-'),
                                (11,'Linh-kiện-điện-thoại',_binary '','cellphone_accessories.png','Linh kiện điện thoại',4,'-4-'),
                                (12,'Cáp-sạc',_binary '\0','cables_and_adapters.png','Cáp sạc',11,'-4-11-'),
                                (13,'Thẻ-nhớ',_binary '\0','microsd_cards.png','Thẻ nhớ',11,'-4-11-'),
                                (14,'Ốp-lưng',_binary '\0','cellphone_cases.png','Ốp lưng',11,'-4-11-'),
                                (15,'Tai-nghe',_binary '','headphones.png','Tai nghe',11,'-4-11-'),
                                (16,'CPU',_binary '','computer_processors.png','CPU',8,'-3-8-'),
                                (17,'Card-đồ-hoạ',_binary '','graphic_cards.png','Card đồ hoạ',8,'-3-8-'),
                                (18,'Ổ-cứng-hdd',_binary '','hdd.png','Ổ cứng hdd',8,'-3-8-'),
                                (19,'Nguồn-máy-tính',_binary '','psu.png','Nguồn máy tính',8,'-3-8-'),
                                (20,'Ổ-cứng-ssd',_binary '','ssd.png','Ổ cứng ssd',8,'-3-8-'),
                                (21,'Ram',_binary '','ram.png','Ram',8,'-3-8-');