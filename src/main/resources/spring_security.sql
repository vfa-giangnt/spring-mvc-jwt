CREATE TABLE `spring_security`.`users` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `username` VARCHAR(45) NULL,
  `password` VARCHAR(255) NULL,
  `enabled` INT(1) NULL,
  PRIMARY KEY (`id`),
  UNIQUE INDEX `username_UNIQUE` (`username` ASC));

CREATE TABLE `spring_security`.`role` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NULL,
  PRIMARY KEY (`id`));

CREATE TABLE `spring_security`.`users_roles` (
  `id` INT NOT NULL AUTO_INCREMENT,
  `user` INT NULL,
  `role` INT NULL,
  PRIMARY KEY (`id`),
  INDEX `_idx` (`user` ASC),
  INDEX `dfdf_idx` (`role` ASC),
  CONSTRAINT `user`
    FOREIGN KEY (`user`)
    REFERENCES `spring_security`.`users` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION,
  CONSTRAINT `role`
    FOREIGN KEY (`role`)
    REFERENCES `spring_security`.`role` (`id`)
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);

INSERT INTO `users` VALUES (1,'kai','$2a$04$GYGsaJj9l6kH2GikK6QVzO0v3sOCxt3vdkiA2/tcoSw8erI85ZYDG',1),(2,'sena','$2a$04$GYGsaJj9l6kH2GikK6QVzO0v3sOCxt3vdkiA2/tcoSw8erI85ZYDG',1);

INSERT INTO `role` VALUES (1,'ROLE_ADMIN'),(2,'ROLE_USER');
