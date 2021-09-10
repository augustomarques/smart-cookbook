INSERT INTO `smartcookbook`.`recipes` (`id`, `way_of_doing`, `name`) VALUES ('1', 'Rice preparation mode', 'White Rice');
INSERT INTO `smartcookbook`.`recipes` (`id`, `way_of_doing`, `name`) VALUES ('2', 'Bean preparation method', 'Beans');

INSERT INTO `smartcookbook`.`ingredients` (`id`, `name`, `recipe_id`) VALUES ('1', 'Rice', '1');
INSERT INTO `smartcookbook`.`ingredients` (`id`, `name`, `recipe_id`) VALUES ('2', 'Garlic', '1');
INSERT INTO `smartcookbook`.`ingredients` (`id`, `name`, `recipe_id`) VALUES ('3', 'Beans', '2');