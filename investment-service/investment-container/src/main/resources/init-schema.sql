DROP SCHEMA IF EXISTS "investment" CASCADE;
CREATE SCHEMA "investment";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS investment_status;
CREATE TYPE investment_status AS ENUM ('PENDING', 'PAID', 'CANCELED');

DROP TYPE IF EXISTS project_status;
CREATE TYPE project_status AS ENUM ('OPEN', 'CLOSED');

DROP TABLE IF EXISTS "investment".projects CASCADE;
CREATE TABLE "investment".projects (
  id uuid NOT NULL,
  status project_status NOT NULL,
  fundraising_target double precision NOT NULL,
  collected_funds double precision NOT NULL,
  description character varying COLLATE pg_catalog."default" NOT NULL,
  tenor_deadline timestamp with time zone NOT NULL,
  created_at timestamp with time zone,
  version integer NOT NULL,

  CONSTRAINT projects_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "investment".investors CASCADE;
CREATE TABLE "investment".investors (
  id uuid NOT NULL,
  email character varying COLLATE pg_catalog."default" NOT NULL,
  name character varying COLLATE pg_catalog."default" NOT NULL,
  profile_picture_url character varying COLLATE pg_catalog."default",

  CONSTRAINT investors_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "investment".investments CASCADE;
CREATE TABLE "investment".investments (
  id uuid NOT NULL,
  project_id uuid NOT NULL,
  investor_id uuid NOT NULL,
  amount double precision NOT NULL,
  status investment_status NOT NULL,
  version integer NOT NULL,
  failure_reasons character varying COLLATE pg_catalog."default",

  CONSTRAINT investments_pkey PRIMARY KEY (id)
);

ALTER TABLE "investment".investments
	ADD CONSTRAINT "FK_INVESTMENT_PROJECT" FOREIGN KEY (project_id)
	REFERENCES "investment".projects (id) MATCH SIMPLE
	ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;

ALTER TABLE "investment".investments
	ADD CONSTRAINT "FK_INVESTMENT_INVESTOR" FOREIGN KEY (investor_id)
	REFERENCES "investment".investors (id) MATCH SIMPLE
	ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;
