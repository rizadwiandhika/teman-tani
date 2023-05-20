INSERT INTO "user".admins VALUES 
  ('9cc10842-a101-4976-9641-29cc9db071b1', 'Super Admin', 'superadmin@mail.com', '$2a$12$5oz2wx6Z6aHyivMn4hNGse1SRFNiDlGbgEDpBCh9Xlwn609qoXPKK', '1234567891011', 'ADMIN_SUPER'),
  ('9cc10842-a101-4976-9641-29cc9db071b3', 'Admin Land', 'adminland@mail.com', '$2a$12$5oz2wx6Z6aHyivMn4hNGse1SRFNiDlGbgEDpBCh9Xlwn609qoXPKK', '1234567891011', 'ADMIN_LANDOWNER');

INSERT INTO "user".users (id, email, name, password, phone_number) VALUES
  ('8e41043a-b55d-4ac9-b349-e1c68468b4d3', 'borrower1@mail.com', 'Borrower 1', '$2a$12$5oz2wx6Z6aHyivMn4hNGse1SRFNiDlGbgEDpBCh9Xlwn609qoXPKK', '0812282828282828'),
  ('8e41043a-b55d-4ac9-b349-e1c68468b4d4', 'investor1@mail.com', 'Investor 1', '$2a$12$5oz2wx6Z6aHyivMn4hNGse1SRFNiDlGbgEDpBCh9Xlwn609qoXPKK', '0812282828282828');