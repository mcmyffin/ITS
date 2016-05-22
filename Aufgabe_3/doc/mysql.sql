-- phpMyAdmin SQL Dump
-- version 4.0.10deb1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Erstellungszeit: 22. Mai 2016 um 16:42
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
) ENGINE=InnoDB  DEFAULT CHARSET=latin1 AUTO_INCREMENT=15 ;

--
-- Daten für Tabelle `benutzer`
--

INSERT INTO `benutzer` (`id`, `email`, `password`, `isValid`) VALUES
(14, 'test@test.com', '4ecae203034bdf9b14aff61fd6d1b83f', 1);

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `passreset`
--

CREATE TABLE IF NOT EXISTS `passreset` (
  `token` varchar(254) NOT NULL,
  `userID` int(11) NOT NULL,
  `validtime` bigint(20) NOT NULL,
  `password` text NOT NULL,
  `isUsed` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`token`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Daten für Tabelle `passreset`
--

INSERT INTO `passreset` (`token`, `userID`, `validtime`, `password`, `isUsed`) VALUES
('bd1f4029e598931592723f2d6ebcaac4a5e00132373a7031000fd987a3c9f87b', 14, 62720588940, '4ecae203034bdf9b14aff61fd6d1b83f', 1);

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
('100d6b0b19e883b6377f5428f8fbe373903ce9225fca3e988c2af215d4e544d3', 62720585640, 'test@test.de', 14, 1, 0),
('87714a4a37a1c525fc33b8e5d9b744861385974ed5904a438616ff7bdb3f7439', 62720588460, 'test@test.com', 14, 0, 0),
('8a7d4ecc20403798049bcf3a9b2866c5903ce9225fca3e988c2af215d4e544d3', 62720588400, 'test@test.com', 14, 0, 0),
('ae3c0bf7ce611c7b223cd198ab7c45591385974ed5904a438616ff7bdb3f7439', 62720588880, 'test@test.com', 14, 0, 1),
('b6fa31033800162ac6ab017407a9de302b24d495052a8ce66358eb576b8912c8', 62720588340, 'test@test.com', 14, 0, 0),
('ccb9b467a462dea0e536154a267cfb992b24d495052a8ce66358eb576b8912c8', 62720588100, 'test@test.de', 14, 1, 1);

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
