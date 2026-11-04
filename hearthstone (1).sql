-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 09, 2025 at 07:44 AM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `hearthstone`
--

-- --------------------------------------------------------

--
-- Table structure for table `cards`
--

CREATE TABLE `cards` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `damage` int(11) NOT NULL,
  `health` int(11) NOT NULL,
  `type` varchar(50) NOT NULL,
  `description` mediumtext NOT NULL,
  `effect_key` varchar(300) NOT NULL,
  `effect_payload` varchar(50) NOT NULL,
  `cost` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `cards`
--

INSERT INTO `cards` (`id`, `name`, `damage`, `health`, `type`, `description`, `effect_key`, `effect_payload`, `cost`) VALUES
(2, 'Фаербол', 0, 0, 'SPELL', 'Наносит 6 ед. урона выбранному миньону', 'damage', '6', 4),
(3, 'Лечение', 0, 0, 'SPELL', 'Лечит 5. ед здоровья', 'heal', '5', 4),
(4, 'Маленький гоблин', 1, 1, 'MINION', 'Мелкий, слабый гоблин', 'none', '0', 1),
(5, 'Каменный голем', 2, 8, 'MINION', 'Голем с большим запасом здоровья', 'none', '0', 6),
(6, 'Золотой голем', 1, 7, 'MINION', 'Голем послабее который приманивает вражеских миньонов атаковать его', 'taunt', '1', 6),
(7, 'Убийца', 5, 5, 'MINION', 'Наносит большой урон', 'none', '0', 7),
(8, 'Целитель', 0, 2, 'MINION', 'Лечит одну единицу здоровья миньону каждый ход', 'heal', '1', 3),
(9, 'Рыцарь', 2, 3, 'MINION', 'Воин с средним количеством здоровья', 'none', '0', 2),
(10, 'Слабое лечение', 0, 0, 'SPELL', 'Слабое лечение. Лечит всех миньонов на 1 ед. здоровья', 'heal', '1', 2),
(11, 'Великан', 1, 5, 'MINION', 'Воин с большим количеством здоровья но малым уроном', 'none', '0', 4);

-- --------------------------------------------------------

--
-- Table structure for table `decks`
--

CREATE TABLE `decks` (
  `id` bigint(20) NOT NULL,
  `user_id` bigint(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `decks`
--

INSERT INTO `decks` (`id`, `user_id`) VALUES
(3, 1),
(13, 1),
(14, 1),
(15, 1),
(16, 2),
(17, 2),
(18, 1),
(19, 3);

-- --------------------------------------------------------

--
-- Table structure for table `decks_cards`
--

CREATE TABLE `decks_cards` (
  `deck_id` bigint(20) NOT NULL,
  `card_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `decks_cards`
--

INSERT INTO `decks_cards` (`deck_id`, `card_id`) VALUES
(3, 2),
(13, 2),
(13, 3),
(13, 4),
(13, 5),
(13, 6),
(13, 7),
(13, 8),
(13, 9),
(13, 10),
(13, 11),
(14, 3),
(14, 4),
(14, 5),
(14, 6),
(14, 7),
(14, 8),
(14, 9),
(14, 10),
(14, 11),
(15, 2),
(15, 3),
(15, 4),
(15, 5),
(15, 6),
(15, 7),
(15, 8),
(15, 9),
(15, 10),
(15, 11),
(16, 2),
(16, 7),
(16, 9),
(16, 11),
(17, 2),
(17, 3),
(17, 4),
(18, 2),
(18, 6),
(18, 7),
(18, 8),
(18, 10),
(18, 11),
(19, 2),
(19, 3),
(19, 4),
(19, 5),
(19, 6),
(19, 7),
(19, 8),
(19, 9),
(19, 10),
(19, 11);

-- --------------------------------------------------------

--
-- Table structure for table `log_entry`
--

CREATE TABLE `log_entry` (
  `id` bigint(20) NOT NULL,
  `matches` bigint(20) NOT NULL,
  `turn_id` bigint(20) NOT NULL,
  `description` mediumtext NOT NULL,
  `timestamp` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `matches`
--

CREATE TABLE `matches` (
  `id` bigint(20) NOT NULL,
  `player01_id` bigint(11) NOT NULL,
  `player02_id` bigint(11) NOT NULL,
  `winner_id` bigint(11) DEFAULT NULL,
  `status` varchar(30) NOT NULL,
  `player01_health` int(11) DEFAULT NULL,
  `player02_health` int(11) DEFAULT NULL,
  `decks1_id` bigint(20) DEFAULT NULL,
  `decks2_id` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `matches`
--

INSERT INTO `matches` (`id`, `player01_id`, `player02_id`, `winner_id`, `status`, `player01_health`, `player02_health`, `decks1_id`, `decks2_id`) VALUES
(1, 3, 1, NULL, 'ONGOING', 100, 100, NULL, NULL),
(2, 3, 1, NULL, 'ONGOING', 100, 100, NULL, NULL),
(3, 3, 1, NULL, 'ONGOING', 100, 100, NULL, NULL);

-- --------------------------------------------------------

--
-- Table structure for table `turn`
--

CREATE TABLE `turn` (
  `id` bigint(20) NOT NULL,
  `turn_number` int(11) NOT NULL,
  `player_id` bigint(20) NOT NULL,
  `cards_id` bigint(20) NOT NULL,
  `target` varchar(255) NOT NULL,
  `result` varchar(255) NOT NULL,
  `timestamp` datetime NOT NULL,
  `matches` bigint(20) NOT NULL,
  `action` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` bigint(20) NOT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `rating` int(11) NOT NULL,
  `wins` int(11) NOT NULL,
  `losses` int(11) NOT NULL,
  `health` int(11) DEFAULT 100
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `password`, `rating`, `wins`, `losses`, `health`) VALUES
(1, 'Aleksei', '4442', 120, 11, 3, 100),
(2, 'tomar753', '753', 0, 0, 0, 100),
(3, 'Andrei2', '1234', 0, 0, 0, 0);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `cards`
--
ALTER TABLE `cards`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `decks`
--
ALTER TABLE `decks`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `decks_cards`
--
ALTER TABLE `decks_cards`
  ADD PRIMARY KEY (`deck_id`,`card_id`);

--
-- Indexes for table `log_entry`
--
ALTER TABLE `log_entry`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `matches` (`matches`,`turn_id`),
  ADD KEY `log-turn` (`turn_id`);

--
-- Indexes for table `matches`
--
ALTER TABLE `matches`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `player01_id` (`player01_id`,`player02_id`,`winner_id`),
  ADD KEY `player02-users` (`player02_id`),
  ADD KEY `winner-users` (`winner_id`);

--
-- Indexes for table `turn`
--
ALTER TABLE `turn`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `player` (`player_id`,`cards_id`,`matches`),
  ADD KEY `turn-cards` (`cards_id`),
  ADD KEY `turn` (`matches`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `cards`
--
ALTER TABLE `cards`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=12;

--
-- AUTO_INCREMENT for table `decks`
--
ALTER TABLE `decks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT for table `log_entry`
--
ALTER TABLE `log_entry`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `matches`
--
ALTER TABLE `matches`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `turn`
--
ALTER TABLE `turn`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `log_entry`
--
ALTER TABLE `log_entry`
  ADD CONSTRAINT `log-matches` FOREIGN KEY (`matches`) REFERENCES `matches` (`id`),
  ADD CONSTRAINT `log-turn` FOREIGN KEY (`turn_id`) REFERENCES `turn` (`id`);

--
-- Constraints for table `matches`
--
ALTER TABLE `matches`
  ADD CONSTRAINT `player01-users` FOREIGN KEY (`player01_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `player02-users` FOREIGN KEY (`player02_id`) REFERENCES `users` (`id`),
  ADD CONSTRAINT `winner-users` FOREIGN KEY (`winner_id`) REFERENCES `users` (`id`);

--
-- Constraints for table `turn`
--
ALTER TABLE `turn`
  ADD CONSTRAINT `turn` FOREIGN KEY (`matches`) REFERENCES `matches` (`id`),
  ADD CONSTRAINT `turn-cards` FOREIGN KEY (`cards_id`) REFERENCES `cards` (`id`),
  ADD CONSTRAINT `turn-player` FOREIGN KEY (`player_id`) REFERENCES `users` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
