-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 21. Mai 2016 um 18:58
-- Server Version: 5.5.49-0ubuntu0.14.04.1
-- PHP-Version: 5.5.9-1ubuntu4.16

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Datenbank: `app`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `benutzer`
--

CREATE TABLE IF NOT EXISTS `benutzer` (
  `id` int(100) NOT NULL AUTO_INCREMENT,
  `email` text NOT NULL,
  `password` text NOT NULL,
  `isValid` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=13 ;

--
-- Daten für Tabelle `benutzer`
--

INSERT INTO `benutzer` (`id`, `email`, `password`, `isValid`) VALUES
(6, 'dada@dada.de', '123456789', 1),
(7, 'dada@dada.com', 'b51c12f30edc0a75675e2e6ef63c4345', 0),
(8, 'hans@email.com', 'cf3f50426bf0a7e903e37e219b55ea46', 0),
(9, 'peter@hase.de', '9f4f4a3ec650293a7c22ce6050b3b278', 1),
(10, 'test@test.de', '9b1f20361c5c0a9cf12791fcbba73afc', 1),
(11, 'hansp@bla.de', '4adf09fc0ce9700cfe483af65722dccb', 1),
(12, 'test1@test.de', 'af672c03f52662b7a923bebab3807005', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `passreset`
--

CREATE TABLE IF NOT EXISTS `passreset` (
  `token` varchar(254) NOT NULL,
  `userID` int(11) NOT NULL,
  `validtime` bigint(20) NOT NULL,
  `isUsed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `unvalidbenutzer`
--

CREATE TABLE IF NOT EXISTS `unvalidbenutzer` (
  `token` varchar(255) NOT NULL,
  `validtime` bigint(20) NOT NULL,
  `email` text NOT NULL,
  `userID` int(11) NOT NULL,
  `isCreate` tinyint(1) NOT NULL DEFAULT '1' COMMENT 'Wenn false, dann UPDATE',
  `isUsed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `unvalidbenutzer`
--

INSERT INTO `unvalidbenutzer` (`token`, `validtime`, `email`, `userID`, `isCreate`, `isUsed`) VALUES
('26080f8a040b1f7abf655153d3efb85fe4da3b7fbbce2345d7772b0674a318d5', 62720509680, '', 0, 0, 1),
('60bc979ea9e1c065a78c2a03f276369dec5decca5ed3d6b8079e2e7e7bacc9f2', 62720509500, 'test1@test.de', 12, 1, 1),
('6b14885a93c6e765a81a398173120fb338b3eff8baf56627478ec76a704e9b52', 62720433780, 'test@test.de', 10, 0, 1),
('81ff09632ee9dda3ea48802a9a4592c1202cb962ac59075b964b07152d234b70', 62720509860, 'test1@test.de', 12, 0, 1),
('9af95d675ad998a9fc748e40f66501ad07e1cd7dca89a1678042477183b7ac3f', 62720496420, 'hansp@bla.de', 11, 0, 1),
('a5761e55bfa771a4bedd82b2e346f6be4c56ff4ce4aaf9573aa5dff913df997a', 62720509800, 'test1@test.com', 12, 0, 1),
('b9bc6b7e8e4d78a9ae2895ac51634244ed3d2c21991e3bef5e069713af9fa6ca', 62720425440, 'peter@hase.de', 9, 0, 1),
('da28falwrjfa92fadjafaakdai2fasfk18fa29ra2rj28ar', 91271480987, 'adawdawd', 0, 0, 0),
('e61c6762c4e9e10864af96293a2b7c49c9f0f895fb98ab9159f51fd0297e236d', 62720509500, '', 0, 0, 1),
('f282266a895b432aaa28c49ce565d4ae45c48cce2e2d7fbdea1afc51c7c6ad26', 62720508960, 'dudu@dudu.de', 0, 0, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
