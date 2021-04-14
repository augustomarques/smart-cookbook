CREATE TABLE `receitas` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `modo_preparo` TEXT DEFAULT NULL,
  `nome` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
);

CREATE TABLE `ingredientes` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(255) DEFAULT NULL,
  `receita_id` bigint DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `FK__ingrediente__receita_id` FOREIGN KEY (`receita_id`) REFERENCES `receitas` (`id`)
);