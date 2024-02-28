DROP TABLE IF EXISTS `Tipo_Usuario`;
CREATE TABLE `Tipo_Usuario` (
  `Id` int NOT NULL,
  `Descricao` varchar(20) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `Tipo_Usuario` VALUES (1,'Colaborador'),
                                  (2,'Gestor'),
                                  (3,'Administrador');

DROP TABLE IF EXISTS `Modalidade`;
CREATE TABLE `Modalidade` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(20) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `Modalidade` VALUES (1,'Hora Extra'),
                                (2,'Sobreaviso');

DROP TABLE IF EXISTS `Etapa_Extrato`;
CREATE TABLE `Etapa_Extrato` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Descricao` varchar(40) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
INSERT INTO `Etapa_Extrato` VALUES (1,'Em Aprovação'),
                                   (2,'Aprovada'),
                                   (3,'Reprovada'),
                                   (4,'Cancelada');
DROP TABLE IF EXISTS `Cliente`;
CREATE TABLE `Cliente` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Razao_Social` varchar(100) NOT NULL,
  `Cnpj` varchar(14) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Cr`;
CREATE TABLE `Cr` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(40) NOT NULL,
  `Sigla` varchar(50) DEFAULT NULL,
  `Codigo_CR` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Usuario`;
CREATE TABLE `Usuario` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Id_Tipo_Usuario` int NOT NULL,
  `Cpf_Cnpj` varchar(14) NOT NULL,
  `Nome` varchar(150) NOT NULL,
  `Telefone` int DEFAULT NULL,
  `Email` varchar(100) NOT NULL,
  PRIMARY KEY (`Id`),
  KEY `Id_Tipo_Usuario` (`Id_Tipo_Usuario`),
  CONSTRAINT `Usuario_ibfk_1` FOREIGN KEY (`Id_Tipo_Usuario`) REFERENCES `Tipo_Usuario` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `Cr_Usuario`;
CREATE TABLE `Cr_Usuario` (
  `Id_Usuario` int NOT NULL,
  `Id_Cr` int NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `Extrato_Hora`;
CREATE TABLE `Extrato_Hora` (
  `Id` int NOT NULL AUTO_INCREMENT,
  `Projeto` varchar(100) NOT NULL,
  `Id_Cr` int NOT NULL,
  `Id_Usuario` int NOT NULL,
  `Id_Etapa_Extrato` int NOT NULL,
  `Id_Modalidade` int NOT NULL,
  `Motivo` varchar(300) DEFAULT NULL,
  `DataHora_Inicio` datetime NOT NULL,
  `DataHora_Fim` datetime NOT NULL,
  `Justificativa` varchar(300) DEFAULT NULL,
  `Id_Cliente` int DEFAULT NULL,
  PRIMARY KEY (`Id`),
  KEY `Id_Cr` (`Id_Cr`),
  KEY `Id_Usuario` (`Id_Usuario`),
  KEY `Id_Etapa_Extrato` (`Id_Etapa_Extrato`),
  KEY `Id_Modalidade` (`Id_Modalidade`),
  KEY `Id_Cliente` (`Id_Cliente`),
  CONSTRAINT `Extrato_Hora_ibfk_1` FOREIGN KEY (`Id_Cr`) REFERENCES `Cr` (`Id`),
  CONSTRAINT `Extrato_Hora_ibfk_2` FOREIGN KEY (`Id_Usuario`) REFERENCES `Usuario` (`Id`),
  CONSTRAINT `Extrato_Hora_ibfk_3` FOREIGN KEY (`Id_Etapa_Extrato`) REFERENCES `Etapa_Extrato` (`Id`),
  CONSTRAINT `Extrato_Hora_ibfk_4` FOREIGN KEY (`Id_Modalidade`) REFERENCES `Modalidade` (`Id`),
  CONSTRAINT `Extrato_Hora_ibfk_6` FOREIGN KEY (`Id_Cliente`) REFERENCES `Cliente` (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE Cliente ADD COLUMN Ativo bit DEFAULT 1;
ALTER TABLE Cr_Usuario ADD COLUMN Ativo bit DEFAULT 1;
ALTER TABLE Usuario ADD COLUMN Ativo bit DEFAULT 1;

