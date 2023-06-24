DROP SCHEMA IF EXISTS "land" CASCADE;
CREATE SCHEMA "land";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS land_status;
CREATE TYPE land_status AS ENUM ('PROPOSED', 'REQUIRES_REVISION', 'REVISED', 'REQUIRES_CLEANING', 'AVAILABLE', 'REJECTED', 'CANCELED');

DROP TYPE IF EXISTS water_availability_status;
CREATE TYPE water_availability_status AS ENUM ('ABUNDANT', 'LIMITED', 'SEASONAL', 'SCARCITY');

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED', 'FAILED', 'COMPLETED');

DROP TABLE IF EXISTS "land".approvers CASCADE;
CREATE TABLE "land".approvers (
  id uuid NOT NULL,
  email character varying COLLATE pg_catalog."default" NOT NULL,
  name character varying COLLATE pg_catalog."default" NOT NULL,

  CONSTRAINT approvers_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "land".borrowers CASCADE;
CREATE TABLE "land".borrowers (
  id uuid NOT NULL,
  email character varying COLLATE pg_catalog."default" NOT NULL,
  name character varying COLLATE pg_catalog."default" NOT NULL,
  profile_picture_url character varying COLLATE pg_catalog."default",

  CONSTRAINT borrowers_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "land".lands CASCADE;
CREATE TABLE "land".lands (
	id uuid NOT NULL,
	owner_id uuid NOT NULL,
	approver_id uuid,
  area_value double precision NOT NULL,
  area_unit character varying COLLATE pg_catalog."default" NOT NULL,
  certificate_url character varying COLLATE pg_catalog."default" NOT NULL,
  photos character varying COLLATE pg_catalog."default" NOT NULL,
  land_status land_status NOT NULL,

	CONSTRAINT lands_pkey PRIMARY KEY (id)
);


DROP TABLE IF EXISTS "land".proposals CASCADE;
CREATE TABLE "land".proposals (
	id uuid NOT NULL,
  revision_messages character varying COLLATE pg_catalog."default" NOT NULL,
  failure_messages character varying COLLATE pg_catalog."default" NOT NULL,
  proposed_at timestamp with time zone NOT NULL,
  approved_at timestamp with time zone,

	CONSTRAINT proposals_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "land".assessments CASCADE;
CREATE TABLE "land".assessments (
	id uuid NOT NULL,
  harvest_suitabilities character varying COLLATE pg_catalog."default" NOT NULL,
  height_value double precision NOT NULL,
  height_unit character varying COLLATE pg_catalog."default" NOT NULL,
  soil_ph double precision NOT NULL,
  land_usage_history character varying COLLATE pg_catalog."default" NOT NULL,
  water_availability_status water_availability_status NOT NULL,

	CONSTRAINT assessments_pkey PRIMARY KEY (id)
);


DROP TABLE IF EXISTS "land".addresses CASCADE;
CREATE TABLE "land".addresses (
	id uuid NOT NULL,
  street character varying COLLATE pg_catalog."default" NOT NULL,
  city character varying COLLATE pg_catalog."default" NOT NULL,
  postal_code character varying COLLATE pg_catalog."default" NOT NULL,

	CONSTRAINT addresses_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "land".land_outbox CASCADE;
CREATE TABLE "land".land_outbox (
	id uuid NOT NULL,
  payload jsonb NOT NULL,
  version integer NOT NULL,
  outbox_status outbox_status NOT NULL,
  created_at timestamp with time zone NOT NULL,
  processed_at timestamp with time zone,

	CONSTRAINT land_outbox_pkey PRIMARY KEY (id)
);

ALTER TABLE "land".lands
	ADD CONSTRAINT "FK_LAND_APPROVER" FOREIGN KEY (approver_id)
	REFERENCES "land".approvers (id) MATCH SIMPLE
	ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;

ALTER TABLE "land".lands
	ADD CONSTRAINT "FK_LAND_BORROWER" FOREIGN KEY (owner_id)
	REFERENCES "land".borrowers (id) MATCH SIMPLE
	ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;


INSERT INTO "land".approvers VALUES 
  ('9cc10842-a101-4976-9641-29cc9db071b1', 'superadmin@mail.com', 'Super Admin');

-- ALTER TABLE "land".addresses
-- 	ADD CONSTRAINT "FK_ADDRESS_LAND" FOREIGN KEY (id)
-- 	REFERENCES "land".lands (id) MATCH SIMPLE
-- 	ON UPDATE NO ACTION
-- 	ON DELETE CASCADE 
-- 	NOT VALID;

-- ALTER TABLE "land".assessments
-- 	ADD CONSTRAINT "FK_ASSESMENT_LAND" FOREIGN KEY (id)
-- 	REFERENCES "land".lands (id) MATCH SIMPLE
-- 	ON UPDATE NO ACTION
-- 	ON DELETE CASCADE 
-- 	NOT VALID;

--   ALTER TABLE "land".proposals
-- 	ADD CONSTRAINT "FK_PROPOSAL_LAND" FOREIGN KEY (id)
-- 	REFERENCES "land".lands (id) MATCH SIMPLE
-- 	ON UPDATE NO ACTION
-- 	ON DELETE CASCADE 
-- 	NOT VALID;
