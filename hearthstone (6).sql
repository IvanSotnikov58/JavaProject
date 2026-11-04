-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Dec 04, 2025 at 01:00 PM
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
(3, 'Лечение', 0, 0, 'SPELL', 'Лечит 4. ед здоровья', 'heal', '4', 4),
(4, 'Маленький гоблин', 1, 1, 'MINION', 'Мелкий, слабый гоблин', 'none', '0', 1),
(5, 'Каменный голем', 3, 8, 'MINION', 'Голем с большим запасом здоровья', 'none', '0', 6),
(6, 'Золотой голем', 1, 6, 'MINION', 'Голем послабее который приманивает вражеских миньонов атаковать его', 'taunt', '1', 6),
(7, 'Убийца', 5, 5, 'MINION', 'Наносит большой урон', 'none', '0', 7),
(8, 'Целитель', 0, 2, 'MINION', 'Лечит одну единицу здоровья миньону каждый ход', 'heal', '1', 3),
(9, 'Рыцарь', 2, 3, 'MINION', 'Воин с средним количеством здоровья', 'none', '0', 2),
(10, 'Слабое лечение', 0, 0, 'SPELL', 'Слабое лечение. Лечит всех миньонов на 1 ед. здоровья', 'heal', '1', 2),
(11, 'Великан', 1, 5, 'MINION', 'Воин с cредним количеством здоровья но малым уроном', 'none', '0', 4),
(12, 'Лесной страж', 2, 5, 'MINION', 'Страж леса имеет среднее здоровье и небольшой урон', '', '', 5),
(13, 'Заморозка', 0, 0, 'SPELL', 'Замораживает миньона на один ход. Не наносит урона', 'freeze', '1', 3),
(15, 'Йети', 3, 7, 'MINION', 'Йети имеет большое количество здоровья и большой урон. Также замораживает двух миньонов на один ход при входе на арену.', 'freeze', '2', 9),
(16, 'Страж', 1, 4, 'MINION', 'Страж с клоунским гримом.', 'taunt', '1', 3),
(17, 'Огромный комар', 3, 1, 'MINION', 'Наносит много урона за свою цену, но умирает буквально от чего угодно', 'none', '0', 3),
(18, 'Ёж', 1, 2, 'MINION', 'У ежа мало здоровья и урона, но миньоны наносящие ему урон получат тот урон обратно. ', 'thorns', '1', 3),
(19, 'Огненный маг', 3, 4, 'MINION', 'Маг с средними характеристиками', 'none', '0', 5),
(20, 'Болотная ведьма', 0, 3, 'MINION', 'Ведьма, которая вместо стандартного урона наносит проклятье на миьнонов, которые потом получают 1 ед урона каждый ход', 'poison', '1', 4),
(21, 'Ётун', 4, 12, 'MINION', 'Самый сильный танк, наносит большой урон и имеет большое количество здоровья', 'none', '', 7),
(22, 'Мистик', 3, 5, 'MINION', 'В первом ходу у мистика щит, и он не получает урон', 'shield', '1', 5),
(23, 'Монах', 5, 4, 'MINION', 'Монах атакует каждый два хода для того чтобы зарядить свою сильную атаку. Заряжает свою атаку 1 ход, атакуя на второй', 'charge_attack', '2', 4),
(24, 'Великий монах', 10, 5, 'MINION', 'Более сильная версия монаха. Заряжает свою атаку 2 хода, атакуя на третьем', 'charge_attack', '3', 7),
(25, 'Лесная ведьма', 2, 4, 'MINION', 'Лесная ведьма наносит мало урона, но её задача не в этом, а в том чтобы все союзные миньоны наносили на 1 ед. урона больше', 'damage_buff', '1', 4),
(26, 'Вредитель', 1, 1, 'MINION', 'Мелкий вредитель. Зачем он нужен если есть мелкий миньон? Ну хз навреное для прокрута колоды или типо того (или если вы расист по отношению к гоблинам)', 'none', '0', 1),
(27, 'Безумный волк', 3, 3, 'MINION', 'Может атаковать сразу как появляется на арене', 'charge', '1', 3),
(28, 'Бешенный', 3, 5, 'MINION', 'Это просто чел который бомжевал на улице. Он давно не ел, поэтому будет есть трупы убитых карт для восполнения 3 ед. здоровья', 'heal_on_kill', '3', 4),
(29, 'Щит мистика', 0, 0, 'SPELL', 'Мистик поделился своей магией и теперь вы можете выбрать одного миньона чтобы сделать его неуязвимым на 1 ход', 'shield', '1', 3),
(30, 'Монолитный воин', 1, 1, 'MINION', 'Улучшает свои характеристики на 1 уровень при убийстве миньона (То есть 1 убийство = +1 к здоровью и +1 к урону)', 'upgrade_on_kill', '1', 3),
(31, 'Шут', 1, 1, 'MINION', 'Не обращайте внимания на указанные характеристики, у шута всё время разный урон и здоровья. Один раз у него может быть 1 ед. здоровья и урона, а в другой все 10 (максимум)', 'random_stats', '1', 5),
(32, 'Коробка пандоры', 0, 0, 'SPELL', 'Коробка пандоры всё время наносит разное количество урона миньону при открытии, от одного до десяти', 'random_damage', '10', 5),
(33, 'Наркозаклинание', 0, 0, 'SPELL', 'Заставляет вражеского миньона поменять сторону, и стать вашим союзником на один ход', 'switch_team', '1', 5),
(34, 'Суицидник', 2, 3, 'MINION', 'При смерти взрывается и наносит 3 ед. урона карте которая убила его. Если он был убит заклинанием, то не нанесёт урон никому', 'death_damage', '1', 3),
(35, 'Опиумная птица', 1, 5, 'MINION', 'Эта арктическая птица напилась опиума. Имеет большое количество здоровья и средний урон, а также повышает урон миньонов на +2 ед. урона', 'damage_buff', '2', 6),
(36, 'Феникс', 3, 5, 'MINION', 'Феникс имеет средние характеристики, но после смерти оставляет яйцо, которое через 1 ход переродит феникса.', 'rebirth', '1', 7),
(37, 'Мимик', 0, 5, 'MINION', 'Мимик сам по себе не наносит урона, но он отражает весь полученный урон пока сам не умрёт', 'thorns', '1', 5);

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
(18, 2),
(19, 3),
(20, 3),
(21, 3),
(22, 4),
(23, 4),
(24, 4),
(25, 4),
(26, 5),
(27, 6),
(28, 6),
(29, 6),
(30, 7),
(31, 8);

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
(18, 3),
(18, 4),
(18, 5),
(18, 6),
(18, 7),
(18, 8),
(18, 9),
(18, 10),
(18, 11),
(19, 7),
(19, 8),
(20, 2),
(20, 3),
(20, 4),
(20, 5),
(20, 6),
(20, 7),
(20, 8),
(20, 9),
(20, 10),
(20, 11),
(21, 2),
(21, 3),
(22, 2),
(22, 3),
(23, 4),
(23, 5),
(24, 2),
(24, 3),
(25, 2),
(25, 3),
(25, 9),
(25, 10),
(26, 3),
(26, 8),
(26, 10),
(27, 2),
(27, 3),
(27, 4),
(27, 5),
(27, 6),
(27, 7),
(27, 8),
(27, 9),
(27, 10),
(27, 11),
(28, 2),
(29, 2),
(29, 3),
(29, 4),
(29, 5),
(29, 6),
(29, 7),
(29, 8),
(29, 9),
(29, 10),
(29, 11),
(30, 13),
(30, 15),
(31, 31),
(31, 32);

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
  `winner` bigint(11) NOT NULL,
  `status` varchar(30) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

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
  `matches` bigint(20) NOT NULL
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
  `losses` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `users`
--

INSERT INTO `users` (`id`, `name`, `password`, `rating`, `wins`, `losses`) VALUES
(1, 'Aleksei', '4442', 120, 11, 3),
(2, 'tomar753', '753', 0, 0, 0),
(3, 'KiryEshka', 'Kiry', 0, 0, 0),
(4, 'test', '1234', 0, 0, 0),
(5, 'support_only', 'i_love_heals', 0, 0, 0),
(6, 'test2', '1234', 0, 0, 0),
(7, 'freeze_only', 'freeze', 0, 0, 0),
(8, 'gambler', 'ilovegambling', 0, 0, 0),
(9, 'test3', '1234', 0, 0, 0);

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
  ADD UNIQUE KEY `player01_id` (`player01_id`,`player02_id`,`winner`),
  ADD KEY `player02-users` (`player02_id`),
  ADD KEY `winner-users` (`winner`);

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
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT for table `decks`
--
ALTER TABLE `decks`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=32;

--
-- AUTO_INCREMENT for table `log_entry`
--
ALTER TABLE `log_entry`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `matches`
--
ALTER TABLE `matches`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `turn`
--
ALTER TABLE `turn`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

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
  ADD CONSTRAINT `winner-users` FOREIGN KEY (`winner`) REFERENCES `users` (`id`);

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
