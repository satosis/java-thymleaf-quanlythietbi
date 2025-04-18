-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: data_website
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `borrow_histories`
--

DROP TABLE IF EXISTS `borrow_histories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow_histories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `actual_return_date` datetime DEFAULT NULL,
  `borrow_date` datetime NOT NULL,
  `created_at` datetime NOT NULL,
  `expected_return_date` datetime NOT NULL,
  `status_device` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `borrow_request_id` int(11) NOT NULL,
  `devices_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKrc472uw0x0f4j3x0sgjk6arqr` (`borrow_request_id`),
  KEY `FK2umj4cdr640ts4yonof5yovb0` (`devices_id`),
  KEY `FKaqvx45q063nlp9vrg7hyucv5o` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow_histories`
--

/*!40000 ALTER TABLE `borrow_histories` DISABLE KEYS */;
INSERT INTO `borrow_histories` VALUES (1,'2025-05-04 00:00:00','2025-04-11 19:18:32','2025-04-11 19:18:32','2025-05-04 00:00:00','MINOR_DAMAGE','2025-04-11 19:18:32',1,1,1),(2,'2025-05-10 00:00:00','2025-04-21 09:00:00','2025-04-21 09:00:00','2025-05-10 00:00:00','GOOD','2025-04-21 09:00:00',21,12,5),(3,'2025-05-12 00:00:00','2025-04-22 10:00:00','2025-04-22 10:00:00','2025-05-12 00:00:00','MINOR_DAMAGE','2025-04-22 10:00:00',22,13,6),(4,'2025-05-15 00:00:00','2025-04-23 11:00:00','2025-04-23 11:00:00','2025-05-15 00:00:00','GOOD','2025-04-23 11:00:00',23,14,7),(5,'2025-05-20 00:00:00','2025-04-24 12:00:00','2025-04-24 12:00:00','2025-05-20 00:00:00','MAJOR_DAMAGE','2025-04-24 12:00:00',24,15,8),(6,'2025-05-18 00:00:00','2025-04-25 13:00:00','2025-04-25 13:00:00','2025-05-18 00:00:00','GOOD','2025-04-25 13:00:00',25,16,9),(7,'2025-05-22 00:00:00','2025-04-26 14:00:00','2025-04-26 14:00:00','2025-05-22 00:00:00','MINOR_DAMAGE','2025-04-26 14:00:00',26,17,10),(8,'2025-05-08 00:00:00','2025-04-27 15:00:00','2025-04-27 15:00:00','2025-05-08 00:00:00','GOOD','2025-04-27 15:00:00',27,18,1),(9,'2025-05-25 00:00:00','2025-04-28 16:00:00','2025-04-28 16:00:00','2025-05-25 00:00:00','GOOD','2025-04-28 16:00:00',28,19,2),(10,'2025-05-30 00:00:00','2025-04-29 17:00:00','2025-04-29 17:00:00','2025-05-30 00:00:00','MAJOR_DAMAGE','2025-04-29 17:00:00',29,20,3),(11,'2025-06-01 00:00:00','2025-04-30 18:00:00','2025-04-30 18:00:00','2025-04-12 22:55:31','GOOD','2025-04-12 22:55:31',30,21,4);
/*!40000 ALTER TABLE `borrow_histories` ENABLE KEYS */;

--
-- Table structure for table `borrow_requests`
--

DROP TABLE IF EXISTS `borrow_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `borrow_requests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `due_date` datetime NOT NULL,
  `reason` varchar(255) DEFAULT NULL,
  `request_date` datetime NOT NULL,
  `status` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `devices_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKjflip925d4yyj59xqeupdr6ll` (`devices_id`),
  KEY `FKhw6n6naouqvpx1r0lbo211fu6` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `borrow_requests`
--

/*!40000 ALTER TABLE `borrow_requests` DISABLE KEYS */;
INSERT INTO `borrow_requests` VALUES (1,'2025-04-11 19:18:32','2025-05-04 00:00:00','Tôi muốn mượn','2025-04-11 19:18:32','PENDING','2025-04-11 19:18:32',1,2),(2,'2025-04-11 19:18:32','2025-05-04 00:00:00','Tôi muốn mượn','2025-04-11 19:18:32','PENDING','2025-04-11 19:18:32',2,4),(3,'2025-04-12 09:15:00','2025-05-10 00:00:00','Phục vụ học tập nhóm','2025-04-12 09:15:00','APPROVED','2025-04-12 09:15:00',5,7),(4,'2025-04-13 14:30:00','2025-05-15 00:00:00','Làm đồ án tốt nghiệp','2025-04-13 14:30:00','PENDING','2025-04-13 14:30:00',8,3),(6,'2025-04-15 16:20:00','2025-05-20 00:00:00','Nghiên cứu khoa học','2025-04-15 16:20:00','APPROVED','2025-04-15 16:20:00',10,2),(8,'2025-04-17 13:25:00','2025-06-01 00:00:00','Lập trình ứng dụng','2025-04-17 13:25:00','APPROVED','2025-04-17 13:25:00',7,8),(10,'2025-04-19 08:50:00','2025-04-28 00:00:00','Báo cáo thực tập','2025-04-19 08:50:00','REJECTED','2025-04-19 08:50:00',9,4),(9,'2025-04-18 15:40:00','2025-05-25 00:00:00','Thử nghiệm phần mềm','2025-04-18 15:40:00','PENDING','2025-04-18 15:40:00',4,6),(11,'2025-04-12 09:15:00','2025-05-15 00:00:00','Phục vụ làm bài tập nhóm môn Cơ sở dữ liệu','2025-04-12 09:15:00','APPROVED','2025-04-12 09:15:00',3,5),(12,'2025-04-12 14:30:00','2025-05-20 00:00:00','Thực hiện đồ án tốt nghiệp chuyên ngành CNTT','2025-04-12 14:30:00','PENDING','2025-04-12 14:30:00',1,7),(13,'2025-04-13 10:45:00','2025-04-30 00:00:00','Thực hành môn Lập trình ứng dụng di động','2025-04-13 10:45:00','REJECTED','2025-04-13 10:45:00',5,2),(14,'2025-04-14 16:20:00','2025-06-01 00:00:00','Nghiên cứu khoa học về Trí tuệ nhân tạo','2025-04-14 16:20:00','APPROVED','2025-04-14 16:20:00',4,8),(15,'2025-04-15 11:10:00','2025-05-10 00:00:00','Thiết kế đồ họa cho dự án môn học','2025-04-15 11:10:00','PENDING','2025-04-15 11:10:00',6,3),(16,'2025-04-16 13:25:00','2025-05-25 00:00:00','Phát triển ứng dụng web thương mại điện tử','2025-04-16 13:25:00','APPROVED','2025-04-16 13:25:00',7,9),(17,'2025-04-17 15:40:00','2025-05-18 00:00:00','Kiểm thử phần mềm hệ thống quản lý','2025-04-17 15:40:00','PENDING','2025-04-17 15:40:00',8,1),(18,'2025-04-18 08:50:00','2025-04-28 00:00:00','Báo cáo thực tập tại doanh nghiệp','2025-04-18 08:50:00','REJECTED','2025-04-18 08:50:00',9,6),(19,'2025-04-19 14:15:00','2025-06-05 00:00:00','Xử lý dữ liệu lớn cho luận văn thạc sĩ','2025-04-19 14:15:00','APPROVED','2025-04-19 14:15:00',10,4),(20,'2025-04-20 10:05:00','2025-06-15 00:00:00','Thiết kế website giới thiệu sản phẩm','2025-04-20 10:05:00','PENDING','2025-04-20 10:05:00',2,10),(21,'2025-04-21 09:00:00','2025-05-21 00:00:00','Tổ chức hội thảo khoa học','2025-04-21 09:00:00','APPROVED','2025-04-21 09:00:00',12,5),(22,'2025-04-22 10:00:00','2025-05-10 00:00:00','Sự kiện văn nghệ','2025-04-22 10:00:00','PENDING','2025-04-22 10:00:00',13,6),(23,'2025-04-23 11:00:00','2025-06-01 00:00:00','Họp phụ huynh','2025-04-23 11:00:00','REJECTED','2025-04-23 11:00:00',14,7),(24,'2025-04-24 12:00:00','2025-05-30 00:00:00','Đào tạo nhân viên mới','2025-04-24 12:00:00','APPROVED','2025-04-24 12:00:00',15,8),(25,'2025-04-25 13:00:00','2025-05-15 00:00:00','Kiểm tra sức khỏe định kỳ','2025-04-25 13:00:00','PENDING','2025-04-25 13:00:00',16,9),(26,'2025-04-26 14:00:00','2025-06-20 00:00:00','Giải bóng bàn nội bộ','2025-04-26 14:00:00','APPROVED','2025-04-26 14:00:00',17,10),(27,'2025-04-27 15:00:00','2025-05-05 00:00:00','Khám sức khỏe học sinh','2025-04-27 15:00:00','REJECTED','2025-04-27 15:00:00',18,1),(28,'2025-04-28 16:00:00','2025-06-10 00:00:00','Tiệc liên hoan','2025-04-28 16:00:00','APPROVED','2025-04-28 16:00:00',19,2),(29,'2025-04-29 17:00:00','2025-05-25 00:00:00','Vệ sinh khuôn viên','2025-04-29 17:00:00','PENDING','2025-04-29 17:00:00',20,3),(30,'2025-04-30 18:00:00','2025-06-05 00:00:00','Giám sát an ninh','2025-04-30 18:00:00','RETURNED','2025-04-12 22:55:31',21,4);
/*!40000 ALTER TABLE `borrow_requests` ENABLE KEYS */;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `name` varchar(45) NOT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'2025-04-11 18:57:34','Thiết bị phòng học','thiet-bi-phong-hoc','2025-04-11 18:57:34'),(2,'2025-04-11 18:57:51','Thiết bị phòng máy tính & CNTT','thiet-bi-phong-may-tinh--cntt','2025-04-11 18:57:51'),(3,'2025-04-11 18:58:08','Thiết bị phòng thí nghiệm','thiet-bi-phong-thi-nghiem','2025-04-11 18:58:08'),(4,'2025-04-11 18:58:13','Thiết bị thư viện','thiet-bi-thu-vien','2025-04-11 18:58:13'),(5,'2025-04-11 18:58:17','Thiết bị hỗ trợ sinh viên','thiet-bi-ho-tro-sinh-vien','2025-04-11 18:58:17'),(6,'2025-04-11 18:58:24','Thiết bị hành chính','thiet-bi-hanh-chinh','2025-04-11 18:58:24'),(7,'2025-04-11 18:58:28','Thiết bị đặc thù theo ngành học','thiet-bi-dac-thu-theo-nganh-hoc','2025-04-11 18:58:28'),(8,'2025-04-11 18:58:28','Thiết bị thể dục','thiet-bi-the-duc','2025-04-11 18:58:28'),(9,'2025-04-11 19:00:00','Thiết bị văn phòng','thiet-bi-van-phong','2025-04-11 19:00:00'),(10,'2025-04-11 19:01:00','Thiết bị âm thanh','thiet-bi-am-thanh','2025-04-11 19:01:00'),(11,'2025-04-11 19:02:00','Thiết bị chiếu sáng','thiet-bi-chieu-sang','2025-04-11 19:02:00'),(12,'2025-04-11 19:03:00','Thiết bị mạng','thiet-bi-mang','2025-04-11 19:03:00'),(13,'2025-04-11 19:04:00','Thiết bị đo lường','thiet-bi-do-luong','2025-04-11 19:04:00'),(14,'2025-04-11 19:05:00','Dụng cụ thể thao','dung-cu-the-thao','2025-04-11 19:05:00'),(15,'2025-04-11 19:06:00','Thiết bị y tế','thiet-bi-y-te','2025-04-11 19:06:00'),(16,'2025-04-11 19:07:00','Thiết bị bếp','thiet-bi-bep','2025-04-11 19:07:00'),(17,'2025-04-11 19:08:00','Thiết bị vệ sinh','thiet-bi-ve-sinh','2025-04-11 19:08:00'),(18,'2025-04-11 19:09:00','Thiết bị an ninh','thiet-bi-an-ninh','2025-04-11 19:09:00');
/*!40000 ALTER TABLE `category` ENABLE KEYS */;

--
-- Table structure for table `contacts`
--

DROP TABLE IF EXISTS `contacts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contacts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `email` varchar(255) DEFAULT NULL,
  `is_reply` int(11) DEFAULT NULL,
  `message` varchar(255) DEFAULT NULL,
  `subject` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKna8bddygr3l3kq1imghgcskt8` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contacts`
--

/*!40000 ALTER TABLE `contacts` DISABLE KEYS */;
INSERT INTO `contacts` VALUES (1,'2025-04-11 19:18:32','tranthiB@eaut.edu.vn',0,'Tôi cần hỗ trợ','Tôi cần hỗ trợ','2025-04-11 19:18:32',1);
/*!40000 ALTER TABLE `contacts` ENABLE KEYS */;

--
-- Table structure for table `devices`
--

DROP TABLE IF EXISTS `devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `devices` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `availability_status` varchar(255) DEFAULT NULL,
  `avatar` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `location` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `operational_status` varchar(255) DEFAULT NULL,
  `serial_number` varchar(255) DEFAULT NULL,
  `slug` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `category_id` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKh16eok4vsc6owa2wc0kkw0a7k` (`category_id`),
  KEY `FKrfbri1ymrwywdydc4dgywe1bt` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=32 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
INSERT INTO `devices` VALUES (1,'AVAILABLE','uploads\\1744469738266_SONY-VPL-VW5000ES-1cc.jpg','2025-04-11 19:00:56','Sử dụng chip DLP với hàng nghìn gương siêu nhỏ để phản chiếu ánh sáng.\r\nHình ảnh mượt, độ tương phản cao, phù hợp cho phim ảnh và trình chiếu văn phòng.','Phòng học','Máy chiếu (Projector)','WORKING','X7B9-2F4K-8R3Q-1M6P','may-chieu-projector','2025-04-12 21:55:38',1,1),(2,'AVAILABLE','uploads\\1744469868987_asus-rog-zephyrus-g14-2024-3-1714811607.jpg','2025-04-11 19:02:18','Thiết bị xử lý dữ liệu, kết nối với máy chiếu để trình chiếu bài giảng.','Bàn giáo viên','Máy tính','WORKING','N5L2-9T8Y-4D7H-6J1C','may-tinh','2025-04-12 21:57:48',1,1),(3,'AVAILABLE','uploads\\1744469943813_Untitled.png','2025-04-11 19:02:38','Thiết bị phát âm thanh hỗ trợ giảng dạy, xem phim, thuyết trình.','Góc lớp, treo tường hoặc trong tủ thiết bị.','Loa','WORKING','3K9P-7W2E-5R6A-4Z8Q','loa','2025-04-12 21:59:03',1,1),(4,'AVAILABLE','uploads\\1744469978480_Untitled.png','2025-04-11 19:02:54','Thiết bị thu âm giọng nói, hỗ trợ giáo viên giảng bài trong lớp lớn.','Hộp đựng micro, ngăn kéo bàn giáo viên.','Micro','WORKING','Q4V1-8B3M-9N6X-2L7S','micro','2025-04-12 21:59:38',1,1),(5,'AVAILABLE','uploads\\1744470005044_Untitled.png','2025-04-11 19:03:10','Bảng cảm ứng kết nối với máy tính, cho phép viết/vẽ bằng tay hoặc bút điện tử.','Cố định trên tường, có giá đỡ đi kèm.','Bảng tương tác','WORKING','J8H5-1D3F-7G2K-9T4R','bang-tuong-tac','2025-04-12 22:00:05',1,1),(6,'AVAILABLE','uploads\\1744470033368_Untitled.png','2025-04-11 19:03:31',' In tài liệu, bài tập cho học sinh','Góc lớp hoặc phòng phụ trợ.','Máy in','WORKING','6Y2T-4S9L-8P3M-1V6B','may-in','2025-04-12 22:00:33',1,1),(7,'AVAILABLE','uploads\\1744470063240_Untitled.png','2025-04-11 19:03:48','Chiếu hình ảnh tài liệu, vật thể lên màn hình thông qua máy chiếu.','Bàn giáo viên hoặc tủ thiết bị.','Camera ghi hình','WORKING','F3C7-9K2N-5X8R-4Q1J','camera-ghi-hinh','2025-04-12 22:01:03',1,1),(8,'AVAILABLE','uploads\\1744470126838_Untitled.png','2025-04-11 19:04:00','Dùng để bật/tắt hoặc điều chỉnh máy chiếu, loa từ xa.','Ngăn kéo bàn giáo viên, hộp nhỏ gắn tường.','Điều khiển từ xa','WORKING','M1R6-4P9W-3L2Z-7D5H','dieu-khien-tu-xa','2025-04-12 22:02:06',1,1),(9,'AVAILABLE','uploads\\1744470170516_Untitled.png','2025-04-11 19:04:16','Tủ sạc laptop/máy tính bảng cho học sinh (nếu áp dụng công nghệ số).','Góc lớp, có khóa bảo vệ.','Tủ sạc thiết bị','WORKING','T8G4-2V7Q-6B3N-9J1X','tu-sac-thiet-bi','2025-04-12 22:02:50',1,1),(10,'AVAILABLE','uploads\\1744470209888_Untitled.png','2025-04-11 19:04:43','Cung cấp Internet cho lớp học trực tuyến.','Trên cao hoặc tủ kỹ thuật tránh va chạm.','Thiết bị mạng','WORKING','5S9D-1H4K-7F2Y-3W6L','thiet-bi-mang','2025-04-12 22:03:29',1,1),(11,'AVAILABLE','uploads\\1744470250656_Untitled.png','2025-04-11 19:04:58','Dự phòng điện cho máy tính, máy chiếu khi mất điện.','Dưới gầm bàn hoặc tủ thiết bị.','Bộ lưu điện','WORKING','A7F3-9K2J-5L8P-4M1Q','bo-luu-dien','2025-04-12 22:04:10',1,1),(12,'BORROWED','uploads\\1744470282046_Untitled.png','2025-04-11 19:05:00','Máy chiếu Full HD, độ sáng 4000 lumen','Phòng họp A','Máy chiếu Panasonic PT-VX430','WORKING','B8G4-1H9K-3L7M-2N5P','may-chieu-panasonic-pt-vx430','2025-04-12 22:04:42',2,1),(13,'UNDER_MAINTENANCE','uploads\\1744470315066_Untitled.png','2025-04-11 19:06:00','Loa bluetooth công suất 50W','Phòng hội trường','Loa di động','NEEDS_REPAIR','C9H5-2I0L-4M8N-3O6Q','loa-di-dong','2025-04-12 22:05:15',3,1),(14,'AVAILABLE','uploads\\1744470345594_Untitled.png','2025-04-11 19:07:00','Máy tính bảng 10 inch, RAM 4GB','Tủ thiết bị VP','Máy tính bảng','WORKING','D0I6-3J1M-5N9O-4P7R','may-tinh-bang','2025-04-12 22:05:45',1,1),(15,'BORROWED','uploads\\1744470369038_Untitled.png','2025-04-11 19:08:00','Switch mạng 24 port','Phòng server','Switch mạng','WORKING','E1J7-4K2N-6O0P-5Q8S','switch-mang','2025-04-12 22:06:09',4,1),(16,'AVAILABLE','uploads\\1744470401348_Untitled.png','2025-04-11 19:09:00','Máy đo nhiệt độ không tiếp xúc','Phòng y tế','Máy đo nhiệt độ','WORKING','F2K8-5L3O-7P1Q-6R9T','may-do-nhiet-do','2025-04-12 22:06:41',5,1),(17,'BORROWED','uploads\\1744470431121_Untitled.png','2025-04-11 19:10:00','Bàn bóng bàn tiêu chuẩn','Nhà thể chất','Bàn bóng bàn','WORKING','G3L9-6M4P-8Q2R-7S0U','ban-bong-ban','2025-04-12 22:07:11',6,1),(18,'UNDER_MAINTENANCE','uploads\\1744470457400_Untitled.png','2025-04-11 19:11:00','Máy đo huyết áp điện tử','Phòng y tế','Máy đo huyết áp','BROKEN','H4M0-7N5Q-9R3S-8T1V','may-do-huyet-ap','2025-04-12 22:07:37',7,1),(19,'AVAILABLE','uploads\\1744470480271_Untitled.png','2025-04-11 19:12:00','Lò vi sóng 20 lít','Phòng ăn','Lò vi sóng','WORKING','I5N1-8O6R-0S4T-9U2W','lo-vi-song','2025-04-12 22:08:00',8,1),(20,'AVAILABLE','uploads\\1744470522021_Untitled.png','2025-04-11 19:13:00','Máy hút bụi công nghiệp','Kho dụng cụ','Máy hút bụi','WORKING','J6O2-9P7S-1T5U-0V3X','may-hut-bui','2025-04-12 22:08:42',9,1),(21,'AVAILABLE','uploads\\1744470553627_Untitled.png','2025-04-11 19:14:00','Camera an ninh HD','Cổng ra vào','Camera an ninh','WORKING','K7P3-0Q8T-2U6V-1W4Y','camera-an-ninh','2025-04-12 22:55:31',10,1),(22,'BORROWED','uploads\\1744470589738_Untitled.png','2025-04-12 09:15:22','Máy chiếu Full HD 1080p, 4000 lumen','Phòng họp A201','Máy chiếu Epson EB-E01','WORKING','B8G4-1H9K-3L7M-2N5P','may-chieu-epson-eb-e01','2025-04-12 22:09:49',2,1),(23,'AVAILABLE','uploads\\1744470620710_Untitled.png','2025-04-12 10:20:33','Loa công suất 500W, bluetooth','Kho thiết bị âm thanh','Loa hội trường','WORKING','C9H5-2I0L-4M8N-3O6Q','loa-hoi-truong','2025-04-12 22:10:20',3,1),(24,'UNDER_MAINTENANCE','uploads\\1744470655446_Untitled.png','2025-04-12 11:25:44','Máy in laser đen trắng A4','Phòng hành chính','Máy photocopy Canon IR 2925','NEEDS_REPAIR','D0I6-3J1M-5N9O-4P7R','may-photocopy-canon-ir-2925','2025-04-12 22:10:55',1,1),(25,'AVAILABLE','uploads\\1744470677608_Untitled.png','2025-04-12 12:30:55','Máy quét tài liệu tự động','Phòng IT','Máy quét','WORKING','E1J7-4K2N-6O0P-5Q8S','may-quet','2025-04-12 22:11:17',1,1),(26,'BORROWED','uploads\\1744470704839_Untitled.png','2025-04-12 13:35:11','Camera an ninh IP 4K','Cổng chính','Camera an ninh','WORKING','F2K8-5L3O-7P1Q-6R9T','camera-an-ninh','2025-04-12 22:11:44',4,1),(27,'AVAILABLE','uploads\\1744470727758_Untitled.png','2025-04-12 14:40:22','Máy đo nhiệt độ không tiếp xúc','Phòng y tế','Máy đo nhiệt độ','WORKING','G3L9-6M4P-8Q2R-7S0U','may-do-nhiet-do','2025-04-12 22:12:07',5,1),(28,'UNDER_MAINTENANCE','uploads\\1744470750484_Untitled.png','2025-04-12 15:45:33','Bàn bóng bàn tiêu chuẩn Olympic','Nhà thể chất','Bàn bóng bàn','BROKEN','H4M0-7N5Q-9R3S-8T1V','ban-bong-ban','2025-04-12 22:12:30',6,1),(29,'AVAILABLE','uploads\\1744470771000_Untitled.png','2025-04-12 16:50:44','Máy hút bụi công nghiệp 2000W','Kho vệ sinh','Máy hút bụi','WORKING','I5N1-8O6R-0S4T-9U2W','may-hut-bui','2025-04-12 22:12:51',7,1),(30,'BORROWED','uploads\\1744470789722_Untitled.png','2025-04-12 17:55:55','Switch mạng 24 port Gigabit','Phòng server','Switch mạng','NEEDS_REPAIR','J6O2-9P7S-1T5U-0V3X','switch-mang','2025-04-12 22:13:09',8,1),(31,'AVAILABLE','uploads\\1744470819514_Untitled.png','2025-04-12 18:00:11','Máy tính bảng 10 inch, RAM 6GB','Tủ thiết bị phòng họp','Máy tính bảng Huawei MatePad Pro','WORKING','K7P3-0Q8T-2U6V-1W4Y','may-tinh-bang-huawei-matepad-pro','2025-04-12 22:13:39',1,1);
/*!40000 ALTER TABLE `devices` ENABLE KEYS */;

--
-- Table structure for table `maintenance_records`
--

DROP TABLE IF EXISTS `maintenance_records`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `maintenance_records` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `loi_thiet_bi` varchar(255) DEFAULT NULL,
  `maintenance_status` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `devices_id` int(11) NOT NULL,
  `maintenance_by` int(11) DEFAULT NULL,
  `reported_by` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKqx5cvbn3iuavoyaacy7f3e2ay` (`devices_id`),
  KEY `FKcpsqv30u95d0jsf8fp3loqg8u` (`maintenance_by`),
  KEY `FK557i2sg2lhj32u3grcb9yahyf` (`reported_by`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `maintenance_records`
--

/*!40000 ALTER TABLE `maintenance_records` DISABLE KEYS */;
INSERT INTO `maintenance_records` VALUES (2,'2025-04-12 08:00:00','Không bật được nguồn','IN_PROGRESS','2025-04-12 08:00:00',6,2,5),(3,'2025-04-12 09:00:00','Âm thanh bị rè','COMPLETED','2025-04-12 09:00:00',2,3,6),(4,'2025-04-12 10:00:00','Màn hình bị vỡ','PENDING','2025-04-12 10:00:00',2,4,7),(5,'2025-04-12 11:00:00','Mất kết nối mạng','IN_PROGRESS','2025-04-12 11:00:00',4,5,8),(6,'2025-04-12 12:00:00','Không đo chính xác','COMPLETED','2025-04-12 12:00:00',6,6,9),(7,'2025-04-12 13:00:00','Lưới bàn bị rách','PENDING','2025-04-12 13:00:00',7,7,10),(8,'2025-04-12 14:00:00','Không hiển thị kết quả','IN_PROGRESS','2025-04-12 14:00:00',8,8,1),(9,'2025-04-12 15:00:00','Không nóng','COMPLETED','2025-04-12 15:00:00',1,9,2),(10,'2025-04-12 16:00:00','Mất hút','PENDING','2025-04-12 16:00:00',2,10,3),(11,'2025-04-12 17:00:00','Không ghi hình','IN_PROGRESS','2025-04-12 17:00:00',2,1,4);
/*!40000 ALTER TABLE `maintenance_records` ENABLE KEYS */;

--
-- Table structure for table `tbl_token`
--

DROP TABLE IF EXISTS `tbl_token`;
CREATE TABLE `tbl_token` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `created_at` datetime NOT NULL,
  `expired` bit(1) NOT NULL,
  `revoked` bit(1) NOT NULL,
  `token` varchar(1000) DEFAULT NULL,
  `token_exp_date` datetime DEFAULT NULL,
  `token_type` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `user_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK8ph52r8g3h955l3pww4da91w5` (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

INSERT INTO `tbl_token` VALUES (1,'2025-04-11 18:48:00',_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJleHAiOjE3NDQzOTAwODAsInVzZXIiOnsicm9sZSI6IlVTRVIiLCJzdHVkZW50X2lkIjoiMTIzMjEiLCJhdmF0YXIiOiJodHRwOlwvXC9sb2NhbGhvc3Q6ODA4MVwvdmlld1wvaW1nXC9uby1pbWFnZS5wbmciLCJjcmVhdGVkQXQiOiJGcmkgQXByIDExIDE4OjQ4OjAwIElDVCAyMDI1IiwicGFzc3dvcmQiOiIkMmEkMTAkOWhmbHJ3bHIyQkJoV2xvN005WjBRLkY5aVpNVzhVMlwvd2lpeVpQbjZUZ2ltVmVkd0tHemlxIiwicHJvdmlkZXIiOm51bGwsInBob25lIjoiMDk0ODU2MTY2OCIsIm5hbWUiOiJOR1VZRU4gTUFOSCBIVU5HIiwicHJvdmlkZXJfaWQiOm51bGwsImlkIjoxLCJlbWFpbCI6Imh1bmcwOTEzMDAzMzU4QGdtYWlsLmNvbSIsInN0YXR1cyI6IklOQUNUSVZFIiwidXBkYXRlZEF0IjoiRnJpIEFwciAxMSAxODo0ODowMCBJQ1QgMjAyNSJ9fQ.zf6EZQBYevBB25eRNZZkaUSOuGawkCpMDfntlW9JmLs','2025-04-11 23:48:00','BEARER','2025-04-11 18:48:32',1),(2,'2025-04-11 18:48:32',_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJodW5nMDkxMzAwMzM1OEBnbWFpbC5jb20iLCJpYXQiOjE3NDQzNzIxMTEsImV4cCI6MTc0NDM5MDExMX0.S4alO5AXUGz3JTrhujORH1cysgj5kpCFhCuAuHwaIug',NULL,'BEARER','2025-04-11 19:17:50',1),(3,'2025-04-11 19:17:50',_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlYXV0LmVkdS52biIsImlhdCI6MTc0NDM3Mzg3MCwiZXhwIjoxNzQ0MzkxODcwfQ.Ez3M9hWYiDuPVTdniglENgPGQRErOux4eskv7qeVn6A',NULL,'BEARER','2025-04-11 22:08:51',1),(4,'2025-04-11 22:08:51',_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlYXV0LmVkdS52biIsImlhdCI6MTc0NDM4NDEzMSwiZXhwIjoxNzQ0NDAyMTMxfQ.ZPtlQuRqEC2iqyHI2XLJCrysX8aD3nhnGfPhnJYZjmY',NULL,'BEARER','2025-04-11 22:22:28',1),(5,'2025-04-11 22:22:28',_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlYXV0LmVkdS52biIsImlhdCI6MTc0NDM4NDk0OCwiZXhwIjoxNzQ0NDAyOTQ4fQ.CeWk0mhGPGV-IFylL1Z06eSOerv-Sosd7qEgQCwtYn8',NULL,'BEARER','2025-04-12 21:49:41',1),(6,'2025-04-12 21:49:41',_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlYXV0LmVkdS52biIsImlhdCI6MTc0NDQ2OTM4MSwiZXhwIjoxNzQ0NDg3MzgxfQ.ZSL-P8hLdt7-gQD1d4sRBB3VlSAYYsUP6lLTEcXv0UU',NULL,'BEARER','2025-04-13 07:39:09',1),(7,'2025-04-13 07:39:09',_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlYXV0LmVkdS52biIsImlhdCI6MTc0NDUwNDc0OSwiZXhwIjoxNzQ0NTIyNzQ5fQ.2Hn-I4ACvgQFz51qfHoLsPWhn894pXFoP5PUqcvE83Y',NULL,'BEARER','2025-04-13 21:30:58',1),(8,'2025-04-13 21:30:58',_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlYXV0LmVkdS52biIsImlhdCI6MTc0NDU1NDY1OCwiZXhwIjoxNzQ0NTcyNjU4fQ.EyjnaHJc-xrbXB1JjqkmHesv54QhWS8j_znFXwhOaN8',NULL,'BEARER','2025-04-17 22:04:26',1),(9,'2025-04-17 22:04:26',_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBlYXV0LmVkdS52biIsImlhdCI6MTc0NDkwMjI2NiwiZXhwIjoxNzQ0OTIwMjY2fQ.L3kux7_1g_yZ2VdZ0xeuujY6Icy-vybNbWmwl4oimOI',NULL,'BEARER','2025-04-17 22:04:26',1),(10,'2025-04-17 22:22:28',_binary '',_binary '','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWtlLmpvaG5zb25AZXhhbXBsZS5jb20iLCJpYXQiOjE3NDQ5MDMzNDgsImV4cCI6MTc0NDkyMTM0OH0.eZrk4ePFczVavRcUo7TwwdP9xMrjDXMb92UD5HkVdgY',NULL,'BEARER','2025-04-17 22:22:48',5),(11,'2025-04-17 22:22:48',_binary '\0',_binary '\0','eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJtaWtlLmpvaG5zb25AZXhhbXBsZS5jb20iLCJpYXQiOjE3NDQ5MDMzNjgsImV4cCI6MTc0NDkyMTM2OH0.qyonpYj5KmTo-ym8i58Dzg_MxuWz6t6U2EX_75RAq-Q',NULL,'BEARER','2025-04-17 22:22:48',5);


DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `avatar` varchar(255) DEFAULT NULL,
  `created_at` datetime NOT NULL,
  `email` varchar(255) NOT NULL,
  `name` varchar(45) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `phone` varchar(255) DEFAULT NULL,
  `provider` varchar(255) DEFAULT NULL,
  `provider_id` varchar(12) DEFAULT NULL,
  `role` varchar(255) DEFAULT NULL,
  `status` varchar(255) DEFAULT NULL,
  `student_id` varchar(255) DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_6dotkott2kjsp8vw4d0m25fb7` (`email`) USING HASH
) ENGINE=MyISAM AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;


INSERT INTO `users` VALUES (1,NULL,'2025-04-11 18:48:00','admin@eaut.edu.vn','Adminstrator','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','01234567','direct',NULL,'ADMIN','ACTIVE','12321','2025-04-11 18:48:31'),(2,'uploads\\1744902782959_Untitled.png','2025-04-11 18:48:00','user@eaut.edu.vn','user','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','01234567','direct',NULL,'USER','ACTIVE','431413','2025-04-17 22:13:02'),(3,NULL,'2025-04-11 18:48:00','nguyenvanA@eaut.edu.vn','Nguyễn Văn A','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0912345678','direct',NULL,'USER','ACTIVE','EA12345','2025-04-11 18:48:31'),(4,NULL,'2025-04-11 18:48:00','tranthiB@eaut.edu.vn','Trần Thị B','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0987654321','direct',NULL,'USER','ACTIVE','EA23456','2025-04-11 18:48:31'),(5,'uploads\\1744902790575_Untitled.png','2025-04-11 18:48:00','mike.johnson@example.com','Mike Johnson','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0345678912','direct',NULL,'USER','ACTIVE','431413','2025-04-17 22:13:10'),(6,NULL,'2025-04-11 18:48:00','phamthuyD@eaut.edu.vn','Phạm Thùy D','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0896541237','direct',NULL,'USER','ACTIVE','EA45678','2025-04-11 18:48:31'),(8,NULL,'2025-04-11 18:48:00','vuthiF@eaut.edu.vn','Vũ Thị F','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0915874632','direct',NULL,'USER','ACTIVE','EA67890','2025-04-11 18:48:31'),(9,NULL,'2025-04-11 18:48:00','dangquangG@eaut.edu.vn','Đặng Quang G','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0963258741','direct',NULL,'USER','ACTIVE','EA78901','2025-04-11 18:48:31'),(10,NULL,'2025-04-11 18:48:00','buitrangH@eaut.edu.vn','Bùi Trang H','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0936587412','direct',NULL,'USER','ACTIVE','EA89012','2025-04-11 18:48:31'),(11,NULL,'2025-04-11 18:48:00','nguyenthaiI@eaut.edu.vn','Nguyễn Thái I','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0974125368','direct',NULL,'USER','ACTIVE','EA90123','2025-04-11 18:48:31'),(12,NULL,'2025-04-11 18:48:00','dotuanK@eaut.edu.vn','Đỗ Tuấn K','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0357894126','direct',NULL,'USER','ACTIVE','EA01234','2025-04-11 18:48:31'),(7,NULL,'2025-04-11 18:48:00','hoangvanE@eaut.edu.vn','Hoàng Văn E','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0374125896','direct',NULL,'USER','ACTIVE','EA56789','2025-04-11 18:48:31'),(13,NULL,'2025-04-12 09:15:00','tranthihong@eaut.edu.vn','Trần Thị Hồng','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0912345678','direct',NULL,'USER','ACTIVE','431414','2025-04-12 09:15:00'),(14,NULL,'2025-04-12 10:30:00','nguyenvananh@eaut.edu.vn','Nguyễn Văn Anh','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0987654321','direct',NULL,'USER','ACTIVE','431415','2025-04-12 10:30:00'),(15,NULL,'2025-04-12 11:45:00','phamthithao@eaut.edu.vn','Phạm Thị Thảo','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0369874123','direct',NULL,'USER','ACTIVE','431416','2025-04-12 11:45:00'),(16,NULL,'2025-04-12 14:20:00','levanhung@eaut.edu.vn','Lê Văn Hùng','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0896541237','direct',NULL,'USER','ACTIVE','431417','2025-04-12 14:20:00'),(17,NULL,'2025-04-12 15:35:00','vuthituyet@eaut.edu.vn','Vũ Thị Tuyết','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0374125896','direct',NULL,'USER','ACTIVE','431418','2025-04-12 15:35:00'),(18,NULL,'2025-04-13 08:50:00','hoangminhduc@eaut.edu.vn','Hoàng Minh Đức','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0915874632','direct',NULL,'USER','ACTIVE','431419','2025-04-13 08:50:00'),(20,NULL,'2025-04-13 13:40:00','buitiennam@eaut.edu.vn','Bùi Tiến Nam','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0936587412','direct',NULL,'USER','ACTIVE','431421','2025-04-13 13:40:00'),(19,NULL,'2025-04-13 10:05:00','dangthimy@eaut.edu.vn','Đặng Thị Mỹ','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0963258741','direct',NULL,'USER','ACTIVE','431420','2025-04-13 10:05:00'),(22,NULL,'2025-04-14 09:10:00','dotuananh@eaut.edu.vn','Đỗ Tuấn Anh','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0357894126','direct',NULL,'USER','ACTIVE','431423','2025-04-14 09:10:00'),(21,NULL,'2025-04-13 15:55:00','nguyenquanghuy@eaut.edu.vn','Nguyễn Quang Huy','$2a$10$9hflrwlr2BBhWlo7M9Z0Q.F9iZMW8U2/wiiyZPn6TgimVedwKGziq','0974125368','direct',NULL,'USER','ACTIVE','431422','2025-04-13 15:55:00');
