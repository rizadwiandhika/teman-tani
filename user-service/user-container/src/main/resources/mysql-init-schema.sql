DROP TABLE IF EXISTS `user_roles`;
CREATE TABLE `user_roles` (
	user_id char(36) NOT NULL,
	role enum('LANDOWNER', 'INVESTOR', 'WORKER', 'BUYER', 'ADMIN_LANDOWNER', 'ADMIN_INVESTOR', 'ADMIN_PROJECT', 'ADMIN_WORKER', 'ADMIN_BUYER') NOT NULL,
	PRIMARY KEY (user_id, role)
);

DROP TABLE IF EXISTS `admins`;
CREATE TABLE `admins` (
	id char(36) NOT NULL,
	name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	phone_number varchar(255) NOT NULL,
	role enum('ADMIN_SUPER', 'ADMIN_LANDOWNER', 'ADMIN_PROJECT', 'ADMIN_WORKER', 'ADMIN_BUYER') NOT NULL,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
	id char(36) NOT NULL,
	name varchar(255) NOT NULL,
	email varchar(255) NOT NULL,
	password varchar(255) NOT NULL,
	phone_number varchar(255) NOT NULL,
	profile_picture_url varchar(255),
	identity_card_url varchar(255),
	identity_card_number varchar(255),
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `addresses`;
CREATE TABLE `addresses` (
	id char(36) NOT NULL,
	street varchar(255) NOT NULL,
	city varchar(255) NOT NULL,
	postal_code varchar(255) NOT NULL,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `bank_accounts`;
CREATE TABLE `bank_accounts` (
	id char(36) NOT NULL,
	bank varchar(255) NOT NULL,
	account_holder_name varchar(255) NOT NULL,
	account_number varchar(255) NOT NULL,
	PRIMARY KEY (id)
);

DROP TABLE IF EXISTS `user_outbox`;
CREATE TABLE `user_outbox` (
	id char(36) NOT NULL,
	payload json NOT NULL,
	version int NOT NULL,
	outbox_status enum('STARTED', 'FAILED', 'COMPLETED') NOT NULL,
	created_at timestamp NOT NULL,
	processed_at timestamp,
	PRIMARY KEY (id)
);

ALTER TABLE `user_roles`
	ADD CONSTRAINT FK_user_role
	FOREIGN KEY (user_id)
		REFERENCES `users` (id)
		ON UPDATE NO ACTION
		ON DELETE CASCADE;

INSERT INTO `admins` VALUES 
  ('9cc10842-a101-4976-9641-29cc9db071b1', 'Super Admin OK', 'superadmin@mail.com', '$2a$12$5oz2wx6Z6aHyivMn4hNGse1SRFNiDlGbgEDpBCh9Xlwn609qoXPKK', '1234567891011', 'ADMIN_SUPER');
