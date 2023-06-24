DROP SCHEMA IF EXISTS "project" CASCADE;
CREATE SCHEMA "project";

-- uuid extension
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

DROP TYPE IF EXISTS land_status;
CREATE TYPE land_status AS ENUM ('PROPOSED', 'REQUIRES_REVISION', 'REVISED', 'REQUIRES_CLEANING', 'AVAILABLE', 'REJECTED', 'CANCELED');

DROP TYPE IF EXISTS land_availability_status;
CREATE TYPE land_availability_status AS ENUM ('AVAILABLE', 'RESERVED');

DROP TYPE IF EXISTS outbox_status;
CREATE TYPE outbox_status AS ENUM ('STARTED', 'COMPLETED', 'FAILED');

DROP TYPE IF EXISTS outbox_type;
CREATE TYPE outbox_type AS ENUM ('FUNDRAISING_REGISTERED', 'CLOSE_FUNDRAISING_REQUESTED');

DROP TYPE IF EXISTS distribution_type;
CREATE TYPE distribution_type AS ENUM ('LANDOWNER', 'INVESTOR');

DROP TYPE IF EXISTS distribution_status;
CREATE TYPE distribution_status AS ENUM ('WAITING', 'COMPLETED');

DROP TYPE IF EXISTS project_status;
CREATE TYPE project_status AS ENUM ('FUNDRAISING', 'WAITING_FOR_FUNDS', 'HIRING', 'ONGOING', 'FINISHED', 'CANCELED');

DROP TABLE IF EXISTS "project".expenses CASCADE;
CREATE TABLE "project".expenses (
  id uuid NOT NULL,
  project_id uuid NOT NULL,
  created_at timestamp with time zone NOT NULL,
  name character varying COLLATE pg_catalog."default" NOT NULL,
  description character varying COLLATE pg_catalog."default" NOT NULL,
  invoice_url character varying COLLATE pg_catalog."default" NOT NULL,
  amount double precision NOT NULL,

  CONSTRAINT expenses_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "project".investments CASCADE;
CREATE TABLE "project".investments (
  id uuid NOT NULL,
  project_id uuid NOT NULL,
  investor_id uuid NOT NULL,
  amount double precision NOT NULL,

  CONSTRAINT investments_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "project".lands CASCADE;
CREATE TABLE "project".lands (
  id uuid NOT NULL,
  availability_status land_availability_status NOT NULL,
  version integer NOT NULL,
  owner_id uuid NOT NULL,
  street character varying COLLATE pg_catalog."default" NOT NULL,
  city character varying COLLATE pg_catalog."default" NOT NULL,
  postal_code character varying COLLATE pg_catalog."default" NOT NULL,

  CONSTRAINT lands_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "project".managers CASCADE;
CREATE TABLE "project".managers (
  id uuid NOT NULL,
  email character varying COLLATE pg_catalog."default" NOT NULL,
  name character varying COLLATE pg_catalog."default" NOT NULL,

  CONSTRAINT managers_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "project".outbox CASCADE;
CREATE TABLE "project".outbox (
  id uuid NOT NULL,
  version integer NOT NULL,
  outbox_status outbox_status NOT NULL,
  type outbox_type NOT NULL,
  payload jsonb NOT NULL,
  created_at timestamp with time zone NOT NULL,
  processed_at timestamp with time zone,

  CONSTRAINT outbox_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "project".profit_distribution_details CASCADE;
CREATE TABLE "project".profit_distribution_details (
  id uuid NOT NULL,
  profit_distribution_id uuid NOT NULL,
  type distribution_type NOT NULL,
  devidend double precision NOT NULL,
  receiver_id uuid NOT NULL,
  bank character varying COLLATE pg_catalog."default" NOT NULL,
  account_number character varying COLLATE pg_catalog."default" NOT NULL,
  account_holder_name character varying COLLATE pg_catalog."default" NOT NULL,
  amount double precision NOT NULL,
  transfer_proof_url character varying COLLATE pg_catalog."default",

  CONSTRAINT profit_distribution_details_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "project".profit_distributions CASCADE;
CREATE TABLE "project".profit_distributions (
  id uuid NOT NULL,
  status distribution_status NOT NULL,
  project_id uuid NOT NULL,
  manager_id uuid NOT NULL,
  project_profit double precision NOT NULL,
  distributed_at timestamp with time zone,

  CONSTRAINT profit_distributions_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "project".profit_receivers CASCADE;
CREATE TABLE "project".profit_receivers (
  id bigserial NOT NULL,
  type distribution_type NOT NULL,
  project_id uuid NOT NULL,
  receiver_id uuid NOT NULL,
  devidend double precision NOT NULL,

  CONSTRAINT profit_receivers_pkey PRIMARY KEY (id)
  -- CONSTRAINT profit_receivers_pkey PRIMARY KEY (type, project_id, receiver_id)
);

DROP TABLE IF EXISTS "project".projects CASCADE;
CREATE TABLE "project".projects (
  id uuid NOT NULL,
  status project_status NOT NULL,
  version integer NOT NULL,
  land_id uuid NOT NULL,
  manager_id uuid NOT NULL,
  description character varying COLLATE pg_catalog."default" NOT NULL,
  details jsonb,
  harvest character varying COLLATE pg_catalog."default" NOT NULL,
  worker_needs integer NOT NULL,
  fundraising_target double precision NOT NULL,
  fundraising_deadline timestamp with time zone NOT NULL,
  estimated_finished timestamp with time zone NOT NULL,
  collected_funds double precision NOT NULL,
  income double precision,
  distributed_income double precision,
  created_at timestamp with time zone NOT NULL,
  executed_at timestamp with time zone,
  finished_at timestamp with time zone,
  failure_messages character varying COLLATE pg_catalog."default",

  CONSTRAINT projects_pkey PRIMARY KEY (id)
);

DROP TABLE IF EXISTS "project".receivers CASCADE;
CREATE TABLE "project".receivers (
  id uuid NOT NULL, 
  version integer NOT NULL, 
  bank character varying COLLATE pg_catalog."default" NOT NULL,
  account_number character varying COLLATE pg_catalog."default" NOT NULL,
  account_holder_name character varying COLLATE pg_catalog."default" NOT NULL,

  CONSTRAINT receivers_pkey PRIMARY KEY (id)
);

ALTER TABLE "project".expenses
  ADD CONSTRAINT "expenses_projects_fkey" FOREIGN KEY (project_id) 
  REFERENCES "project".projects (id) MATCH SIMPLE
  ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;

ALTER TABLE "project".investments
  ADD CONSTRAINT "investments_projects_fkey" FOREIGN KEY (project_id) 
  REFERENCES "project".projects (id) MATCH SIMPLE
  ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;

ALTER TABLE "project".profit_distribution_details
  ADD CONSTRAINT "detail_distribution_fkey" FOREIGN KEY (profit_distribution_id) 
  REFERENCES "project".profit_distributions (id) MATCH SIMPLE
  ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;

ALTER TABLE "project".profit_distributions
  ADD CONSTRAINT "distribution_project_fkey" FOREIGN KEY (project_id) 
  REFERENCES "project".projects (id) MATCH SIMPLE
  ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;

ALTER TABLE "project".profit_receivers
  ADD CONSTRAINT "profit_receiver_project_fkey" FOREIGN KEY (project_id) 
  REFERENCES "project".projects (id) MATCH SIMPLE
  ON UPDATE NO ACTION
	ON DELETE CASCADE 
	NOT VALID;
/* 
id uuid NOT NULL,
availability_status land_availability_status NOT NULL,
version integer NOT NULL,
owner_id uuid NOT NULL,
street character varying COLLATE pg_catalog."default" NOT NULL,
city character varying COLLATE pg_catalog."default" NOT NULL,
postal_code character varying COLLATE pg_catalog."default" NOT NULL, */

INSERT INTO "project".receivers VALUES 
  ('10cdae59-99e5-475f-9810-6cf03dfb1500', 0, 'BNI', '123456789', 'Riza'),
  ('10cdae59-99e5-475f-9810-6cf03dfb1501', 0, 'BCA', '123456789', 'Enzo'),
  ('10cdae59-99e5-475f-9810-6cf03dfb1502', 0, 'MANDIRI', '123456789', 'Hernowo');

INSERT INTO "project".managers VALUES 
  ('10cdae59-99e5-475f-9810-6cf03dfb1510', 'manager@mail.com', 'Manager'),
  ('9cc10842-a101-4976-9641-29cc9db071b1', 'superadmin@mail.com', 'Super Admin');

INSERT INTO "project".lands VALUES ('10cdae59-99e5-475f-9810-6cf03dfb1520','RESERVED',0,'10cdae59-99e5-475f-9810-6cf03dfb1500','Street','City','11111');
INSERT INTO "project".lands VALUES ('10cdae59-99e5-475f-9810-6cf03dfb1521','RESERVED',0,'10cdae59-99e5-475f-9810-6cf03dfb1500','Street 2','City 2','22222');

INSERT INTO "project".projects VALUES (
  '10cdae59-99e5-475f-9810-6cf03dfb1554',
  'FINISHED',
  0,
  '10cdae59-99e5-475f-9810-6cf03dfb1520',
  '9cc10842-a101-4976-9641-29cc9db071b1',
  'Description',
  '{}',
  'APPLE',
  18,
  75000000,
  '2023-10-12 00:00:00',
  '2024-10-12 00:00:00',
  60000000,
  35000000,
  30000000,
  '2023-06-10 00:00:00',
  '2023-08-10 00:00:00',
  '2023-10-10 00:00:00',
  NULL
);
INSERT INTO "project".projects VALUES (
  '10cdae59-99e5-475f-9810-6cf03dfb1555',
  'ONGOING',
  0,
  '10cdae59-99e5-475f-9810-6cf03dfb1520',
  '9cc10842-a101-4976-9641-29cc9db071b1',
  'Description',
  '{}',
  'BANANA',
  75,
  50000000,
  '2023-10-12 00:00:00',
  '2024-10-12 00:00:00',
  40000000,
  0,
  0,
  '2024-06-10 00:00:00',
  '2024-08-10 00:00:00',
  NULL,
  NULL
);
INSERT INTO "project".projects VALUES (
  '10cdae59-99e5-475f-9810-6cf03dfb1556',
  'FUNDRAISING',
  0,
  '10cdae59-99e5-475f-9810-6cf03dfb1521',
  '9cc10842-a101-4976-9641-29cc9db071b1',
  'Lorem ipsum dolor sit amet consectetur adipisicing elit. Accusamus, autem velit doloribus corporis facere maxime maiores sunt vitae deleniti impedit!',
  '{}',
  'RICE',
  50,
  50000000,
  '2023-10-12 00:00:00',
  '2024-10-12 00:00:00',
  0,
  0,
  0,
  '2024-06-10 00:00:00',
  NULL,
  NULL,
  NULL
);

INSERT INTO "project".expenses VALUES
  ('5546915a-c6ed-4648-8b22-086e26fdf82a', '10cdae59-99e5-475f-9810-6cf03dfb1554', '2021-10-16 18:10:30', 'Pupuk 1', 'Description', 'INVOICE_URL', 2000000),
  ('5546915a-c6ed-4648-8b22-086e26fdf82b', '10cdae59-99e5-475f-9810-6cf03dfb1554', '2021-10-16 18:10:30', 'Obeng 2', 'Description', 'INVOICE_URL', 4000000),
  ('5546915a-c6ed-4648-8b22-086e26fdf82c', '10cdae59-99e5-475f-9810-6cf03dfb1554', '2021-10-16 18:10:30', 'Kayu 3', 'Description', 'INVOICE_URL', 8000000),
  ('5546915a-c6ed-4648-8b22-086e26fdf82d', '10cdae59-99e5-475f-9810-6cf03dfb1555', '2021-10-16 18:10:30', 'Traktor 4', 'Description', 'INVOICE_URL', 1000000),
  ('5546915a-c6ed-4648-8b22-086e26fdf82e', '10cdae59-99e5-475f-9810-6cf03dfb1555', '2021-10-16 18:10:30', 'Bensin 5', 'Description', 'INVOICE_URL', 4000000),
  ('5546915a-c6ed-4648-8b22-086e26fdf82f', '10cdae59-99e5-475f-9810-6cf03dfb1555', '2021-10-16 18:10:30', 'Garam 6', 'Description', 'INVOICE_URL', 500000);

INSERT INTO "project".profit_receivers VALUES 
  (1, 'LANDOWNER', '10cdae59-99e5-475f-9810-6cf03dfb1554', '10cdae59-99e5-475f-9810-6cf03dfb1500', 0.15),
  (2, 'INVESTOR', '10cdae59-99e5-475f-9810-6cf03dfb1554', '10cdae59-99e5-475f-9810-6cf03dfb1501', 0.35),
  (3, 'INVESTOR', '10cdae59-99e5-475f-9810-6cf03dfb1554', '10cdae59-99e5-475f-9810-6cf03dfb1502', 0.50),
  (4, 'LANDOWNER', '10cdae59-99e5-475f-9810-6cf03dfb1555', '10cdae59-99e5-475f-9810-6cf03dfb1500', 0.15),
  (5, 'INVESTOR', '10cdae59-99e5-475f-9810-6cf03dfb1555', '10cdae59-99e5-475f-9810-6cf03dfb1501', 0.35),
  (6, 'INVESTOR', '10cdae59-99e5-475f-9810-6cf03dfb1555', '10cdae59-99e5-475f-9810-6cf03dfb1502', 0.50);

SELECT setval('profit_receivers_id_seq', 100);

INSERT INTO "project".profit_distributions VALUES 
  ('10cdae59-99e5-475f-9810-6cf03dfb1564', 'COMPLETED', '10cdae59-99e5-475f-9810-6cf03dfb1554', '9cc10842-a101-4976-9641-29cc9db071b1', 20000000, '2024-08-10 00:00:00'),
  ('10cdae59-99e5-475f-9810-6cf03dfb1565', 'COMPLETED', '10cdae59-99e5-475f-9810-6cf03dfb1554', '9cc10842-a101-4976-9641-29cc9db071b1', 10000000, '2024-09-10 00:00:00'),
  ('10cdae59-99e5-475f-9810-6cf03dfb1566', 'WAITING', '10cdae59-99e5-475f-9810-6cf03dfb1554', '9cc10842-a101-4976-9641-29cc9db071b1', 5000000, '2024-10-10 00:00:00');

INSERT INTO "project".profit_distribution_details VALUES
  ('10cdae59-99e5-475f-9810-6cf03dfb1700', '10cdae59-99e5-475f-9810-6cf03dfb1564', 'LANDOWNER', 0.15, '10cdae59-99e5-475f-9810-6cf03dfb1500', 'BNI', '123123123', 'Riza', 3000000, 'SOME_URL'),
  ('10cdae59-99e5-475f-9810-6cf03dfb1701', '10cdae59-99e5-475f-9810-6cf03dfb1564', 'INVESTOR', 0.35, '10cdae59-99e5-475f-9810-6cf03dfb1501', 'BCA', '123123123', 'Enzo', 7000000, 'SOME_URL'),
  ('10cdae59-99e5-475f-9810-6cf03dfb1702', '10cdae59-99e5-475f-9810-6cf03dfb1564', 'INVESTOR', 0.55, '10cdae59-99e5-475f-9810-6cf03dfb1502', 'MANDIRI', '123123123', 'Hernowo', 10000000, 'SOME_URL'),
  ('10cdae59-99e5-475f-9810-6cf03dfb1703', '10cdae59-99e5-475f-9810-6cf03dfb1565', 'LANDOWNER', 0.15, '10cdae59-99e5-475f-9810-6cf03dfb1500', 'BNI', '123123123', 'Riza', 1500000, 'SOME_URL'),
  ('10cdae59-99e5-475f-9810-6cf03dfb1704', '10cdae59-99e5-475f-9810-6cf03dfb1565', 'INVESTOR', 0.35, '10cdae59-99e5-475f-9810-6cf03dfb1501', 'BCA', '123123123', 'Enzo', 3500000, 'SOME_URL'),
  ('10cdae59-99e5-475f-9810-6cf03dfb1705', '10cdae59-99e5-475f-9810-6cf03dfb1565', 'INVESTOR', 0.55, '10cdae59-99e5-475f-9810-6cf03dfb1502', 'MANDIRI', '123123123', 'Hernowo', 500000, 'SOME_URL'),
  ('10cdae59-99e5-475f-9810-6cf03dfb1706', '10cdae59-99e5-475f-9810-6cf03dfb1566', 'LANDOWNER', 0.15, '10cdae59-99e5-475f-9810-6cf03dfb1500', 'BNI', '123123123', 'Riza', 750000, NULL),
  ('10cdae59-99e5-475f-9810-6cf03dfb1707', '10cdae59-99e5-475f-9810-6cf03dfb1566', 'INVESTOR', 0.35, '10cdae59-99e5-475f-9810-6cf03dfb1501', 'BCA', '123123123', 'Enzo', 1750000, NULL),
  ('10cdae59-99e5-475f-9810-6cf03dfb1708', '10cdae59-99e5-475f-9810-6cf03dfb1566', 'INVESTOR', 0.55, '10cdae59-99e5-475f-9810-6cf03dfb1502', 'MANDIRI', '123123123', 'Hernowo', 2500000, NULL);