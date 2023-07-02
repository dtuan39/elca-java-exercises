INSERT INTO EMPLOYEE (VISA, FIRST_NAME, LAST_NAME, BIRTH_DATE, VERSION)
VALUES
    ('ABC', 'John', 'Doe', '1990-01-01', 1),
    ('DEF', 'Jane', 'Smith', '1985-05-15', 1),
    ('GHI', 'Michael', 'Johnson', '1992-09-30', 1),
    ('JKL', 'Emily', 'Davis', '1988-07-20', 1),
    ('MNO', 'Robert', 'Anderson', '1995-03-12', 2),
    ('PQR', 'Jessica', 'Wilson', '1991-12-05', 2),
    ('STU', 'Christopher', 'Martinez', '1987-04-25', 2),
    ('VWX', 'Olivia', 'Taylor', '1993-10-10', 2),
    ('YZA', 'David', 'Thomas', '1994-08-18', 3),
    ('BCD', 'Sophia', 'Hernandez', '1989-06-08', 3);
INSERT INTO "group"(VERSION, GROUP_LEADER_ID)
VALUES
    (1, 1),
    (1, 2),
    (2, 2),
    (1, 3),
    (1, 3);
INSERT INTO PROJECT (GROUP_ID, PROJECT_NUMBER, NAME, CUSTOMER, STATUS, START_DATE, END_DATE, VERSION)
VALUES
    (1, 3116, 'Facturation / Encaissements', 'Les Retaites Populaires', 'NEW', '2004-02-25', NULL, 1),
    (2, 3118, 'GKBWEB', 'GKk8', 'FIN', '2002-10-10', NULL, 1),
    (3, 7157, 'MGBAHN-Maint2015', 'MGB Tourism', 'INP', '2006-09-24', NULL, 1),
    (2, 7174, 'SOMED-SPITEX MAINT', 'SOMED-SPITEX MAINT', 'NEW', '2015-10-05', NULL, 1),
    (3, 7199, 'ARCHIMEDE-2015-3.14', 'Les Retaites Populaires', 'PLA', '2005-05-29', NULL, 1),
    (4, 7201, 'E-COMMERCE', 'ABC Company', 'NEW', '2022-08-15', NULL, 1),
    (2, 7210, 'HR MANAGEMENT', 'XYZ Corporation', 'FIN', '2020-06-01', NULL, 1),
    (5, 7215, 'INVENTORY CONTROL', '123 Manufacturing', 'INP', '2021-01-10', NULL, 1),
    (1, 7223, 'MARKETING CAMPAIGN', 'Acme Marketing', 'PLA', '2019-03-20', NULL, 1),
    (3, 7227, 'CRM SYSTEM', 'Best Solutions', 'NEW', '2023-02-28', NULL, 1),
    (4, 7230, 'SALES ANALYTICS', 'Global Sales Inc.', 'FIN', '2022-11-12', NULL, 1),
    (5, 7236, 'SUPPLY CHAIN OPTIMIZATION', 'SupplyCo', 'INP', '2021-07-05', NULL, 1),
    (1, 7242, 'PRODUCT DEVELOPMENT', 'Innovation Corp', 'PLA', '2020-09-02', NULL, 1),
    (2, 7249, 'LOGISTICS MANAGEMENT', 'LogiCorp', 'NEW', '2023-04-18', NULL, 1),
    (3, 7254, 'FINANCIAL REPORTING', 'FinanceFirst', 'FIN', '2022-02-10', NULL, 1),
    (4, 7259, 'RETAIL STORE MANAGEMENT', 'Retail Solutions', 'INP', '2021-09-15', NULL, 1),
    (5, 7263, 'HOTEL BOOKING SYSTEM', 'Travel&Stay', 'PLA', '2020-12-08', NULL, 1),
    (1, 7267, 'EVENT MANAGEMENT', 'EventPlanners', 'NEW', '2023-07-01', NULL, 1),
    (2, 7272, 'EDUCATION PLATFORM', 'LearnNow', 'FIN', '2022-04-05', NULL, 1),
    (3, 7276, 'HEALTHCARE SYSTEM', 'MediCare', 'INP', '2021-11-20', NULL, 1),
    (4, 7281, 'ELECTRONIC PAYMENTS', 'PayEasy', 'PLA', '2020-08-25', NULL, 1),
    (5, 7285, 'SOCIAL MEDIA ANALYTICS', 'SocialInsights', 'NEW', '2023-03-12', NULL, 1),
    (1, 7291, 'TRAVEL PLANNING', 'Wanderlust', 'FIN', '2022-01-05', NULL, 1),
    (2, 7296, 'ORDER MANAGEMENT', 'OrderPro', 'INP', '2021-06-18', NULL, 1),
    (3, 7300, 'CUSTOMER SUPPORT', 'HelpDesk', 'PLA', '2020-11-30', NULL, 1);
-- Insert sample data for the ProjectEmployee table
INSERT INTO PROJECT_EMPLOYEE (ID, PROJECT_ID, EMPLOYEE_ID)
VALUES (1, 1, 1),
       (2, 1, 2),
       (3, 2, 4),
       (4, 2, 5),
       (5, 2, 6),
       (6, 2, 7),
       (7, 2, 8),
       (8, 3, 1),
       (9, 3, 2),
       (10, 3, 3);