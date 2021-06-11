INSERT INTO `smartcookbook`.`receitas` (`id`, `modo_preparo`, `nome`) VALUES ('1', 'Modo de preparo do Arroz', 'Arroz branco');
INSERT INTO `smartcookbook`.`receitas` (`id`, `modo_preparo`, `nome`) VALUES ('2', 'Modo de preparo do Feijão', 'Feijão');

INSERT INTO `smartcookbook`.`ingredientes` (`id`, `nome`, `receita_id`) VALUES ('1', 'Arroz', '1');
INSERT INTO `smartcookbook`.`ingredientes` (`id`, `nome`, `receita_id`) VALUES ('2', 'Alho', '1');
INSERT INTO `smartcookbook`.`ingredientes` (`id`, `nome`, `receita_id`) VALUES ('3', 'Feijão', '2');