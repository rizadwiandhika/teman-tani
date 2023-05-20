DROP SCHEMA IF EXISTS "user" CASCADE;
CREATE SCHEMA "user";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS user_role;
CREATE TYPE user_role AS ENUM ('LANDOWNER', 'INVESTOR', 'WORKER', 'BUYER', 'ADMIN_LANDOWNER', 'ADMIN_INVESTOR', 'ADMIN_PROJECT', 'ADMIN_WORKER', 'ADMIN_BUYER');

DROP TYPE IF EXISTS admin_role;
CREATE TYPE admin_role AS ENUM ('ADMIN_SUPER', 'ADMIN_LANDOWNER', 'ADMIN_PROJECT', 'ADMIN_WORKER', 'ADMIN_BUYER');

DROP TABLE IF EXISTS "user".admins CASCADE;
CREATE TABLE "user".admins (
	id uuid NOT NULL,
	name character varying COLLATE pg_catalog."default" NOT NULL,
	email character varying COLLATE pg_catalog."default" NOT NULL,
	password character varying COLLATE pg_catalog."default" NOT NULL,
	phone_number character varying COLLATE pg_catalog."default" NOT NULL,
	role admin_role NOT NULL,
	
	CONSTRAINT admins_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "user".users CASCADE;
CREATE TABLE "user".users (
	id uuid NOT NULL,
	name character varying COLLATE pg_catalog."default" NOT NULL,
	email character varying COLLATE pg_catalog."default" NOT NULL,
	password character varying COLLATE pg_catalog."default" NOT NULL,
	phone_number character varying COLLATE pg_catalog."default" NOT NULL,
	profile_picture_url character varying COLLATE pg_catalog."default",
	identity_card_url character varying COLLATE pg_catalog."default",
	identity_card_number character varying COLLATE pg_catalog."default",
	
	CONSTRAINT users_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "user".user_roles CASCADE;
CREATE TABLE "user".user_roles (
	user_id uuid NOT NULL,
	role user_role NOT NULL,

	CONSTRAINT user_roles_pkey PRIMARY KEY (user_id, role)
);

DROP TABLE IF EXISTS "user".addresses CASCADE;
CREATE TABLE "user".addresses (
	id uuid NOT NULL,
  street character varying COLLATE pg_catalog."default" NOT NULL,
  city character varying COLLATE pg_catalog."default" NOT NULL,
  postal_code character varying COLLATE pg_catalog."default" NOT NULL,

	CONSTRAINT addresses_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "user".bank_accounts CASCADE;
CREATE TABLE "user".bank_accounts (
	id uuid NOT NULL,
  bank character varying COLLATE pg_catalog."default" NOT NULL,
  account_holder_name character varying COLLATE pg_catalog."default" NOT NULL,
  account_number character varying COLLATE pg_catalog."default" NOT NULL,

	CONSTRAINT bank_accounts_pkey PRIMARY KEY (id)
);

ALTER TABLE "user".user_roles
	ADD CONSTRAINT "FK_USERS_ROLES" FOREIGN KEY (user_id)
	REFERENCES "user".users (id) MATCH SIMPLE
	ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;

-- ALTER TABLE "user".addresses
-- 	ADD CONSTRAINT "FK_USERS_ADDRESSES" FOREIGN KEY (user_id)
-- 	REFERENCES "user".users (id) MATCH SIMPLE
-- 	ON UPDATE NO ACTION
-- 	ON DELETE CASCADE 
-- 	NOT VALID;
-- ALTER TABLE "user".bank_accounts
-- 	ADD CONSTRAINT "FK_USERS_ADDRESSES" FOREIGN KEY (user_id)
-- 	REFERENCES "user".users (id) MATCH SIMPLE
-- 	ON UPDATE NO ACTION
-- 	ON DELETE CASCADE 
-- 	NOT VALID;