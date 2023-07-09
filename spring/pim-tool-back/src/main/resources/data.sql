INSERT INTO Employee (VISA, FIRST_NAME, LAST_NAME, BIRTH_DATE,VERSION)
VALUES
    ('JOD', 'John', 'Doe', '1990-01-01',0),
    ('JAS', 'Jane', 'Smith', '1985-05-12',0),
    ('MIJ', 'Michael', 'Johnson', '1992-09-23',0),
    ('EMW', 'Emma', 'Williams', '1998-07-04',0),
    ('DAB', 'David', 'Brown', '1982-11-15',0),
    ('OLT', 'Olivia', 'Taylor', '1995-03-28',0),
    ('DAD', 'Daniel', 'Davis', '1991-12-09',0),
    ('SOW', 'Sophia', 'Wilson', '1989-06-19',0),
    ('ETA', 'Ethan', 'Anderson', '1994-02-07',0),
    ('AVA', 'Ava', 'Jackson', '1997-10-31',0),
    ('LIM', 'Liam', 'Martinez', '1987-04-17',0);

INSERT INTO tGROUP (GROUP_LEADER_ID, VERSION)
VALUES (1,0),(5,0),(9,0),(4,0),(8,0);

INSERT INTO PROJECT(GROUP_ID,PROJECT_NUMBER,NAME,CUSTOMER,STATUS,START_DATE,END_DATE,VERSION)
VALUES (1,1234,'Shopping Page Project','Les Raites Rouskt','NEW','2023-05-26','2026-01-01',0),
       (1,1952,'Project Phoenix','Innovix Solutions','NEW','2003-05-26','2028-01-01',0),
       (1,1953,'SynergyQuest','GlobalStrat','NEW','2023-05-26','2029-01-01',0),
       (2,2145,'Vanguard Ventures','TechNova Systems','PLA','2013-05-26','2014-01-01',0),
       (2,9642,'PrecisionTech','DigitalEdge Solutions','PLA','2013-05-26','2016-01-01',0),
       (2,4126,'Project Odyssey','EnterpriseXpert','PLA','1980-05-26','2000-01-01',0),
       (3,7825,'FusionTech','OptimumTech Services','INP','2000-05-26','2020-01-01',0),
       (3,9856,'Catalyst Connect','WebSphere Innovations','INP','2003-05-26','2021-01-01',0),
       (2,4523,'Endeavor Quest','IntegraSoft Consulting','INP','2024-05-26','2025-01-01',0),
       (1,4125,'StellarVision','DataPulse Technologies','NEW','2016-05-26','2028-01-01',0),
       (3,7569,'AlphaEdge','TechMaxim Analytics','FIN','2011-05-26','2029-01-01',0),
       (2,7436,'XXX Project','NexusNet Networks','FIN','2012-05-26','2024-01-01',0),
       (4,7539,'YSL Project','MasterTech Consulting','FIN','2019-05-26','2024-01-01',0),
       (3,1254,'Tottenham Project','QuantumTech Solutions','NEW','2001-05-26','2004-01-01',0),
       (1, 5678, 'E-commerce Platform', 'ABC Corporation', 'NEW', '2023-06-15', '2025-11-30', 0),
       (4, 9123, 'Mobile App Development', 'XYZ Enterprises', 'PLA', '2023-09-10', '2024-12-31', 0),
       (3, 4567, 'Data Analytics Project', 'Tech Solutions Inc.', 'INP', '2023-07-20', '2024-08-15', 0),
       (2, 7890, 'Cloud Migration Initiative', 'GlobalTech Services', 'FIN', '2023-08-05', '2024-07-31', 0),
       (3, 1357, 'Website Redesign Project', 'Beta Web Services', 'INP', '2023-09-01', '2024-06-30', 0),
       (4, 6789, 'Data Center Consolidation', 'Gamma Technologies', 'PLA', '2023-08-20', '2024-04-30', 0),
       (4, 8024, 'CRM Implementation Project', 'Delta Enterprises', 'NEW', '2023-06-10', '2024-03-31', 0),
       (4, 3690, 'Infrastructure Upgrade Project', 'Omega IT Solutions', 'INP', '2023-07-15', '2024-05-31', 0),
       (4, 9513, 'Security Audit Project', 'Sigma Security Services', 'FIN', '2023-09-15', '2024-08-31', 0);


INSERT INTO PROJECT_EMPLOYEE(PROJECT_ID, EMPLOYEE_ID)
VALUES
    (1,1),
    (1,2),
    (1,3),
    (1,4),
    (1,5),
    (2,6),
    (2,1),
    (2,2),
    (3,7),
    (4,9),
    (5,10),
    (5,11),
    (5,5),
    (5,6);

