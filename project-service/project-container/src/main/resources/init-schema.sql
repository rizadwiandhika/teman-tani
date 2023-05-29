DROP SCHEMA IF EXISTS "project" CASCADE;
CREATE SCHEMA "project";

-- uuid extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TABLE IF EXISTS "project".projects;
CREATE TABLE "project".projects (
  id uuid NOT NULL,
  status character varying COLLATE pg_catalog."default" NOT NULL,

  CONSTRAINT projects_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "project".investments;
CREATE TABLE "project".investments (
  id uuid NOT NULL,
  status character varying COLLATE pg_catalog."default" NOT NULL,
  project_id uuid NOT NULL,

  CONSTRAINT investments_pkey PRIMARY KEY (id)
);

ALTER TABLE "project".investments
  ADD CONSTRAINT "investments_projects_fkey" FOREIGN KEY (project_id) 
  REFERENCES "project".projects (id) MATCH SIMPLE
  ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;
