-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3306
-- Generation Time: Mar 27, 2025 at 03:27 PM
-- Server version: 9.1.0
-- PHP Version: 8.3.14

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `studentdb`
--

-- --------------------------------------------------------

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
CREATE TABLE IF NOT EXISTS `students` (
  `student_id` varchar(10) NOT NULL,
  `name` varchar(100) NOT NULL,
  `dob` date NOT NULL,
  `email` varchar(100) NOT NULL,
  `contact_no` varchar(15) NOT NULL,
  `address` text NOT NULL,
  `gender` enum('Male','Female','Other') NOT NULL,
  `attendance` int DEFAULT '0',
  `course` varchar(100) NOT NULL,
  PRIMARY KEY (`student_id`),
  UNIQUE KEY `email` (`email`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `students`
--

INSERT INTO `students` (`student_id`, `name`, `dob`, `email`, `contact_no`, `address`, `gender`, `attendance`, `course`) VALUES
('2', 'akash', '2003-07-22', 'ak409876@gmail.com', '916682686', 'Bangalore', 'Male', 1, 'MCA'),
('3', 'vaishuu', '2004-01-04', 'gdxddtrc', '8529631478', 'sagara', 'Female', 2, 'mca'),
('1', 'divya', '2003-07-21', 'ak4094486@gmail.com', '8532855871', 'bareilly', 'Male', 5, 'MCA'),
('ddd10', 'dd', '2000-02-12', 'poa@gmail.com', '1234569877', 'hgffd', 'Female', 0, 'mnb');
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
