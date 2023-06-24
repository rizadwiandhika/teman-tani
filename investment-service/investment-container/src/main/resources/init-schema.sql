DROP SCHEMA IF EXISTS "investment" CASCADE;
CREATE SCHEMA "investment";

CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS investment_status;
CREATE TYPE investment_status AS ENUM ('PENDING', 'PAID', 'CANCELED');

DROP TYPE IF EXISTS fundraising_status;
CREATE TYPE fundraising_status AS ENUM ('OPEN', 'CLOSING', 'CLOSED');

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED', 'FAILED', 'COMPLETED');

DROP TABLE IF EXISTS "investment".fundraisings CASCADE;
CREATE TABLE "investment".fundraisings (
  id uuid NOT NULL,
  status fundraising_status NOT NULL,
  fundraising_target double precision NOT NULL,
  collected_funds double precision NOT NULL,
  booked_funds double precision NOT NULL,
  description character varying COLLATE pg_catalog."default" NOT NULL,
  tenor_deadline timestamp with time zone NOT NULL,
  created_at timestamp with time zone,
  version integer NOT NULL,

  CONSTRAINT fundraisings_pkey PRIMARY KEY (id)
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
  expired_at timestamp with time zone,
  created_at timestamp with time zone NOT NULL,
  failure_reasons character varying COLLATE pg_catalog."default",

  CONSTRAINT investments_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "investment".fundraising_closure_outbox CASCADE;
CREATE TABLE "investment".fundraising_closure_outbox (
  id uuid NOT NULL,
  version integer NOT NULL,
  outbox_status outbox_status NOT NULL,
  payload jsonb NOT NULL,
  created_at timestamp with time zone NOT NULL,
  processed_at timestamp with time zone,

  CONSTRAINT fundraising_closure_outbox_pkey PRIMARY KEY (id)
);

ALTER TABLE "investment".investments
	ADD CONSTRAINT "FK_INVESTMENT_PROJECT" FOREIGN KEY (project_id)
	REFERENCES "investment".fundraisings (id) MATCH SIMPLE
	ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;

ALTER TABLE "investment".investments
	ADD CONSTRAINT "FK_INVESTMENT_INVESTOR" FOREIGN KEY (investor_id)
	REFERENCES "investment".investors (id) MATCH SIMPLE
	ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;

INSERT INTO "investment".fundraisings VALUES ('10cdae59-99e5-475f-9810-6cf03dfb1556', 'OPEN', 50000000, 0, 0, 'Lorem ipsum dolor sit amet consectetur adipisicing elit. Accusamus, autem velit doloribus corporis facere maxime maiores sunt vitae deleniti impedit!', '2023-10-12 00:00:00', '2024-06-10 00:00:00', 0);

-- INSERT INTO "investment".fundraisings VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', 'OPEN', 30000000, 0, 0, 'Project 1', '2021-12-31 23:59:59', '2021-01-01 00:00:00', 0);
-- INSERT INTO "investment".fundraisings VALUES ('a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', 'OPEN', 50000000, 0, 0, 'Project 2', '2021-12-31 23:59:59', '2021-01-01 00:00:00', 0);

-- INSERT INTO "investment".investors VALUES ('9c0fb0bd-ccea-41c1-b3b0-7271a0949f01', 'mail@mail.com', 'Name', '');
-- INSERT INTO "investment".investors VALUES ('9c0fb0bd-ccea-41c1-b3b0-7271a0949f02', 'mail2@mail.com', 'Name 2', '');

-- INSERT INTO "investment".investments VALUES ('bb8051aa-552e-4565-b794-dbda51eb0721', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '9c0fb0bd-ccea-41c1-b3b0-7271a0949f01', 1000000, 'PENDING', 0, '2023-06-03 10:26:35', null);
-- INSERT INTO "investment".investments VALUES ('bb8051aa-552e-4565-b794-dbda51eb0722', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', '9c0fb0bd-ccea-41c1-b3b0-7271a0949f01', 1000000, 'PENDING', 0, '2023-06-03 10:26:35', null);
-- INSERT INTO "investment".investments VALUES ('bb8051aa-552e-4565-b794-dbda51eb0723', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11', '9c0fb0bd-ccea-41c1-b3b0-7271a0949f02', 2000000, 'PAID', 0, '2023-06-03 10:26:35', null);
-- INSERT INTO "investment".investments VALUES ('bb8051aa-552e-4565-b794-dbda51eb0724', 'a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a12', '9c0fb0bd-ccea-41c1-b3b0-7271a0949f02', 2000000, 'PAID', 0, '2023-06-03 10:26:35', null);
