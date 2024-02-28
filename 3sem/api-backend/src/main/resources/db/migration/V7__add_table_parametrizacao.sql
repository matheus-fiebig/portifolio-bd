CREATE TABLE `Parametrizacao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dt_inicio_pagamento` datetime NOT NULL,
  `dt_fim_pagamento` datetime NOT NULL,
  `inicio_horario_noturno` varchar(50) NOT NULL,
  `fim_horario_noturno` varchar(50) NOT NULL,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
