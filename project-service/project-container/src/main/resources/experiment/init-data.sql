INSERT INTO "project".projects (id, status) VALUES 
  ('edbcaaab-7541-4680-9686-db97d22247e1', 'CLOSING'), 
  ('edbcaaab-7541-4680-9686-db97d22247e2', 'CLOSING'), 
  ('edbcaaab-7541-4680-9686-db97d22247e3', 'FUNDRAISING');

INSERT INTO "project".investments (id, status, project_id) VALUES ('3019e4b0-3c7d-4b62-8a0b-2e656af00fa1', 'PAID', 'edbcaaab-7541-4680-9686-db97d22247e1');
INSERT INTO "project".investments (id, status, project_id) VALUES ('3019e4b0-3c7d-4b62-8a0b-2e656af00fa2', 'PAID', 'edbcaaab-7541-4680-9686-db97d22247e1');
INSERT INTO "project".investments (id, status, project_id) VALUES ('3019e4b0-3c7d-4b62-8a0b-2e656af00fa3', 'EXPIRED', 'edbcaaab-7541-4680-9686-db97d22247e1');

INSERT INTO "project".investments (id, status, project_id) VALUES ('3019e4b0-3c7d-4b62-8a0b-2e656af00fa4', 'PENDING', 'edbcaaab-7541-4680-9686-db97d22247e2');
INSERT INTO "project".investments (id, status, project_id) VALUES ('3019e4b0-3c7d-4b62-8a0b-2e656af00fa5', 'PAID', 'edbcaaab-7541-4680-9686-db97d22247e2');
INSERT INTO "project".investments (id, status, project_id) VALUES ('3019e4b0-3c7d-4b62-8a0b-2e656af00fa6', 'EXPIRED', 'edbcaaab-7541-4680-9686-db97d22247e2');

INSERT INTO "project".investments (id, status, project_id) VALUES ('3019e4b0-3c7d-4b62-8a0b-2e656af00fa7', 'PAID', 'edbcaaab-7541-4680-9686-db97d22247e3');
INSERT INTO "project".investments (id, status, project_id) VALUES ('3019e4b0-3c7d-4b62-8a0b-2e656af00fa8', 'PAID', 'edbcaaab-7541-4680-9686-db97d22247e3');
INSERT INTO "project".investments (id, status, project_id) VALUES ('3019e4b0-3c7d-4b62-8a0b-2e656af00fa9', 'PAID', 'edbcaaab-7541-4680-9686-db97d22247e3');