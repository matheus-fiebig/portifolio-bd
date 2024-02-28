drop table parametrizacao;

CREATE TABLE `Parametrizacao` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dt_inicio_pagamento` int NOT NULL,
  `dt_fim_pagamento` int NOT NULL,
  `inicio_horario_noturno` varchar(50) NOT NULL,
  `fim_horario_noturno` varchar(50) NOT NULL,
  `V1601` int not null,
  `V1602` int not null,
  `V3000` int not null,
  `V3001` int not null,
  `V1809` int not null,
  `V3016` int not null,
  PRIMARY KEY (`Id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO PARAMETRIZACAO VALUES(0, '16', '15', '19:00', '04:59', 1,1,1,1,1,1);