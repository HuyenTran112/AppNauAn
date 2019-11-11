-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Nov 11, 2019 at 11:08 AM
-- Server version: 10.4.8-MariaDB
-- PHP Version: 7.3.11

SET FOREIGN_KEY_CHECKS=0;
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cooking`
--

-- --------------------------------------------------------

--
-- Table structure for table `loaimonan`
--
-- Creation: Nov 07, 2019 at 03:08 PM
--

CREATE TABLE `loaimonan` (
  `maloaimonan` int(11) NOT NULL,
  `tenloaimonan` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- RELATIONSHIPS FOR TABLE `loaimonan`:
--

--
-- Dumping data for table `loaimonan`
--

INSERT INTO `loaimonan` (`maloaimonan`, `tenloaimonan`) VALUES
(1, 'Món xào'),
(2, 'Món canh'),
(3, 'Món kho'),
(4, 'Lẩu'),
(5, 'Lẩu'),
(6, 'Bánh');

-- --------------------------------------------------------

--
-- Table structure for table `loainguoidung`
--
-- Creation: Nov 07, 2019 at 03:28 PM
--

CREATE TABLE `loainguoidung` (
  `maloainguoidung` int(11) NOT NULL,
  `tenloainguoidung` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- RELATIONSHIPS FOR TABLE `loainguoidung`:
--

--
-- Dumping data for table `loainguoidung`
--

INSERT INTO `loainguoidung` (`maloainguoidung`, `tenloainguoidung`) VALUES
(1, 'admin'),
(2, 'user');

-- --------------------------------------------------------

--
-- Table structure for table `monan`
--
-- Creation: Nov 07, 2019 at 03:30 PM
-- Last update: Nov 11, 2019 at 09:30 AM
--

CREATE TABLE `monan` (
  `mamonan` int(11) NOT NULL,
  `tenmonan` varchar(255) NOT NULL,
  `nguyenlieu` text NOT NULL,
  `congthuc` text NOT NULL,
  `hinhanh` varchar(255) NOT NULL,
  `maloaimonan` int(11) NOT NULL,
  `manguoidung` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- RELATIONSHIPS FOR TABLE `monan`:
--   `maloaimonan`
--       `loaimonan` -> `maloaimonan`
--   `manguoidung`
--       `nguoidung` -> `manguoidung`
--   `manguoidung`
--       `nguoidung` -> `manguoidung`
--

--
-- Dumping data for table `monan`
--

INSERT INTO `monan` (`mamonan`, `tenmonan`, `nguyenlieu`, `congthuc`, `hinhanh`, `maloaimonan`, `manguoidung`) VALUES
(6, 'Lẩu', 'Bún, rau, thịt, cá', 'Nấu', 'http://10.80.255.137:8080/dbappnauan/imagemonan/6.jpg', 5, 1),
(7, 'Thịt bò xào rau muống', 'Thịt bò, rau muống', 'Xào', 'http://10.80.255.137:8080/dbappnauan/imagemonan/7.jpg', 1, 1),
(8, 'Lẩu thái chua cay', 'Thịt, cá, rau', 'Nấu', 'http://10.80.255.137:8080/dbappnauan/imagemonan/8.jpg', 1, 1),
(9, 'Bánh xèo', 'Bột', 'Chiên', 'http://10.80.255.137:8080/dbappnauan/imagemonan/9.jpg', 1, 1),
(10, 'Cải xào', 'Cải, dầu', 'Xào', 'http://10.80.255.137:8080/dbappnauan/imagemonan/10.png', 1, 1),
(15, 'Thịt kho', 'Thịt, trứng, nước dừa', 'Kho', 'http://10.80.255.137:8080/dbappnauan/imagemonan/11.jpg', 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `monanyeuthich`
--
-- Creation: Nov 11, 2019 at 09:53 AM
--

CREATE TABLE `monanyeuthich` (
  `mamonan` int(11) NOT NULL,
  `manguoidung` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- RELATIONSHIPS FOR TABLE `monanyeuthich`:
--   `mamonan`
--       `monan` -> `mamonan`
--   `manguoidung`
--       `nguoidung` -> `manguoidung`
--

-- --------------------------------------------------------

--
-- Table structure for table `nguoidung`
--
-- Creation: Nov 07, 2019 at 03:30 PM
--

CREATE TABLE `nguoidung` (
  `manguoidung` int(11) NOT NULL,
  `email` varchar(100) NOT NULL,
  `tenhienthi` varchar(100) NOT NULL,
  `matkhau` varchar(50) NOT NULL,
  `maloainguoidung` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- RELATIONSHIPS FOR TABLE `nguoidung`:
--   `maloainguoidung`
--       `loainguoidung` -> `maloainguoidung`
--

--
-- Dumping data for table `nguoidung`
--

INSERT INTO `nguoidung` (`manguoidung`, `email`, `tenhienthi`, `matkhau`, `maloainguoidung`) VALUES
(1, 'huyentranbui112@gmail.com', 'Huyền Trân', '123456', 1);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `loaimonan`
--
ALTER TABLE `loaimonan`
  ADD PRIMARY KEY (`maloaimonan`);

--
-- Indexes for table `loainguoidung`
--
ALTER TABLE `loainguoidung`
  ADD PRIMARY KEY (`maloainguoidung`);

--
-- Indexes for table `monan`
--
ALTER TABLE `monan`
  ADD PRIMARY KEY (`mamonan`),
  ADD KEY `fk_monan_loaimonan` (`maloaimonan`),
  ADD KEY `fk_monan_nguoidung_` (`manguoidung`);

--
-- Indexes for table `monanyeuthich`
--
ALTER TABLE `monanyeuthich`
  ADD PRIMARY KEY (`mamonan`,`manguoidung`),
  ADD KEY `fk_monanyeuthich_nguoidung` (`manguoidung`);

--
-- Indexes for table `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD PRIMARY KEY (`manguoidung`),
  ADD KEY `fk_nguoidung_loainguoidung` (`maloainguoidung`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `loaimonan`
--
ALTER TABLE `loaimonan`
  MODIFY `maloaimonan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT for table `loainguoidung`
--
ALTER TABLE `loainguoidung`
  MODIFY `maloainguoidung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=3;

--
-- AUTO_INCREMENT for table `monan`
--
ALTER TABLE `monan`
  MODIFY `mamonan` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT for table `nguoidung`
--
ALTER TABLE `nguoidung`
  MODIFY `manguoidung` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `monan`
--
ALTER TABLE `monan`
  ADD CONSTRAINT `fk_monan_loaimonan` FOREIGN KEY (`maloaimonan`) REFERENCES `loaimonan` (`maloaimonan`),
  ADD CONSTRAINT `fk_monan_nguoidung` FOREIGN KEY (`manguoidung`) REFERENCES `nguoidung` (`manguoidung`),
  ADD CONSTRAINT `fk_monan_nguoidung_` FOREIGN KEY (`manguoidung`) REFERENCES `nguoidung` (`manguoidung`);

--
-- Constraints for table `monanyeuthich`
--
ALTER TABLE `monanyeuthich`
  ADD CONSTRAINT `fk_monanyeuthich_monan` FOREIGN KEY (`mamonan`) REFERENCES `monan` (`mamonan`),
  ADD CONSTRAINT `fk_monanyeuthich_nguoidung` FOREIGN KEY (`manguoidung`) REFERENCES `nguoidung` (`manguoidung`);

--
-- Constraints for table `nguoidung`
--
ALTER TABLE `nguoidung`
  ADD CONSTRAINT `fk_nguoidung_loainguoidung` FOREIGN KEY (`maloainguoidung`) REFERENCES `loainguoidung` (`maloainguoidung`);


--
-- Metadata
--
USE `phpmyadmin`;

--
-- Metadata for table loaimonan
--

--
-- Metadata for table loainguoidung
--

--
-- Metadata for table monan
--

--
-- Metadata for table monanyeuthich
--

--
-- Metadata for table nguoidung
--

--
-- Metadata for database cooking
--
SET FOREIGN_KEY_CHECKS=1;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
