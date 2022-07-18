-- table ROLE
INSERT INTO `role` VALUES (1,'Admin','manage everything'),
                          (2,'Salesperson','manage product price, customers, shippin'),
                          (3,'Editor','manage categories, brands, products, art'),
                          (4,'Shipper','view products, view orders and update or'),
                          (5,'Assistant','manage questions and reviews');

-- table USER
INSERT INTO `user` VALUES (1,'spring@gmail.com',_binary '\0','spring','boot','$2a$10$tPNMFB6MWYcWuBLHrsKv.O4Gg22D8dUKKU/iQRrNiuq3FA6EspY6i','spring.jpg'),
                          (2,'thong@gmail.com',_binary '','thông','hoàng','$2a$10$O7cfhEGrui8AZIpfK2PlUu3LOuAnqO4TwEfzq2j.JtjBuSPlZ.d3W',NULL),
                          (3,'man@gmail.com',_binary '','mẫn','trần','$2a$10$Fpcramu3yXeEh.czbKE43O9ogAMBLSvsVRypV5uPFTl9UqNzUXhf2',NULL),
                          (4,'quan@gmail.com',_binary '','quân','nguyễn','$2a$10$EY/jY5NwePyy6IvxPyQNUOQg4fFJ6BQeKra/Nc8Pk6BrgzjIzTjh6',NULL),
                          (5,'dung@gmail.com',_binary '','dung','nguyễn','$2a$10$49AaDOxI4o07Y1PoMbUGxu2CbcuCelhHfs13.rCe10ilxGLaF1D36',NULL),
                          (6,'vinh@gmail.com',_binary '','vinh','nguyễn','$2a$10$VrU0o4q5w0/wnNyyN4zA.OT2Fm1rWdoNZqVeFIFYlwfd2VQp2P6m6',NULL),
                          (7,'thanh@gmail.com',_binary '\0','thành','nguyễn','$2a$10$zFHzXwBoH/7A6scgk1EevOl/NIk/9acwJ54TF32mVKrZ2dCPi4fRa',NULL),
                          (8,'truong@gmail.com',_binary '','trường','nguyễn','$2a$10$NMxj4wT5WETXt0JDoXV0kuzjIMAvxekdRsdmDyY/2l0Byv9tpzlq6',NULL),
                          (9,'vien@gmail.com',_binary '','viên','huỳnh','$2a$10$4B9S81qFyP7xuCd1aQykwuzh/blOv/YGHpxnL7WFCG8TrHERuvL.e',NULL),
                          (10,'quoc@gmail.com',_binary '','quốc','huỳnh','$2a$10$v8sxYaTy4W32Lhtsfv2Bqu2MgpqQT.N/yWUpa1XOovtriJj3mWGQu',NULL),
                          (11,'tuan@gmail.com',_binary '','tuấn','bùi','$2a$10$NLJ6RrijJw3sqtlcxtq5qul8hkPgSFlO/YjMD3Q2R8vYGeeOhFsJy',NULL),
                          (12,'duy@gmail.com',_binary '','duy','hoàng','$2a$10$NbtSk0LZf2eLlvZolChiK.FKiazqG.GCNJXq.PMUU/C1b2pb.EbFO',NULL),
                          (13,'kiet@gmail.com',_binary '\0','kiệt','tạ','$2a$10$ZVmwBtZGiPCCX7B3DyJyMe.ZZq9IsUqDxFALTezkD9sPnIWy1/9/C',NULL),
                          (14,'bao@gmail.com',_binary '','bảo','trần','$2a$10$O1N210pygjUeB/8fJMyF5.S4x5hla79aAyMDeWUHxSMIANk51kuu.',NULL),
                          (15,'vi@gmail.com',_binary '','vi','trương','$2a$10$gWs5P145x7O4DZe1DFTtLOxLevGw/a1nq6ybtgopDu8qXDbiWH9kW',NULL),
                          (16,'nhan@gmail.com',_binary '','nhân','võ','$2a$10$lHSgfFbWAD82PMOlyBDfiekZ0EgRTP8moSFCPznzqA7LjeShyIFb.',NULL),
                          (17,'tan@gmail.com',_binary '','tấn','huỳnh','$2a$10$NI.4KzLikqkPXax2Ag41QeRIBcLaCB/llaHhaZAj2qC078lDPZj1e',NULL),
                          (18,'cuong@gmail.com',_binary '','cương','nguyễn','$2a$10$JOn32.0cE2YrqqOMHZCCqecvIh5xlj9XsYFnhO7Y.wr0p/7jaiqKy',NULL),
                          (19,'nhat@gmail.com',_binary '\0','nhật','phan','$2a$10$8hQc5AdecwNewmqcU2nMGefRhwSpvzI9PKH3YxnBJTWLVrBJ7/6tK',NULL),
                          (20,'ha@gmail.com',_binary '','ha','phạm','$2a$10$VZaqB8MIW75PxaSV8NpIHuqLGqGV5GRI3ibLiFVFwVukP50EHV6Pq',NULL),
                          (21,'nghia@gmail.com',_binary '','nghĩa','phạm','$2a$10$jhyAI7WYxVOfsQKUMNJ04etacnRTRPSPDutjVHJVHZkM35dM1vmTO',NULL),
                          (22,'toan@gmail.com',_binary '','toàn','nguyễn','$2a$10$zUqPOOLG7q9iKwk8DMDTAeGJzygPlfBBWHLjZ3H5iHnjAEUiupQKW',NULL),
                          (23,'long@gmail.com',_binary '','long','nguyễn','$2a$10$pwjE/aEPEBUOs.Y7k5PNuu0QW0EvewGNPKMxr8/Nu14auv5S1a9na',NULL),
                          (24,'duong@gmail.com',_binary '','dương','nguyễn','$2a$10$4tNe4GpQoSGEQWW8.XZXKezd8dgq2G7YyBBAto.cI9yGRCMoiLQFW',NULL);

-- table USER_ROLE
INSERT INTO `user_role` VALUES (1,1),(2,1),(3,1),(4,2),(8,2),(9,2),(10,2),(14,2),(15,2),(17,2),
                               (21,2),(22,2),(24,2),(5,3),(8,3),(11,3),(12,3),(14,3),(15,3),(16,3),
                               (18,3),(21,3),(6,4),(9,4),(11,4),(13,4),(14,4),(16,4),(19,4),(22,4),
                               (23,4),(7,5),(10,5),(12,5),(13,5),(15,5),(16,5),(20,5);

