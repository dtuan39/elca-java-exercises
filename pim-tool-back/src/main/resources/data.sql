INSERT INTO EMPLOYEE (ID, VISA, FIRST_NAME, LAST_NAME, BIRTH_DATE, VERSION)
VALUES
    (1, 'ABC', 'John', 'Doe', '1990-01-01', 1),
    (2, 'DEF', 'Jane', 'Smith', '1995-05-15', 1),
    (3, 'GHI', 'Michael', 'Johnson', '1988-12-10', 1),
    (4, 'JKL', 'Emily', 'Brown', '1992-07-20', 1),
    (5, 'MNO', 'David', 'Wilson', '1998-03-25', 1);

INSERT INTO "group"(GROUP_LEADER_ID,VERSION)
VALUES
    ('1','1'),
    ('2','1'),
    ('3','1'),
    ('4','1'),
    ('5','1');

INSERT INTO PROJECT (PROJECT_NUMBER, CUSTOMER, END_DATE, NAME, START_DATE, STATUS, VERSION,GROUP_ID)
VALUES
    ('2','TEST','2022-06-06','TEST2','2021-06-06','New','1','1'),
    ('3116','Les Retaites Populaires','2003-10-10','Facturation/Encasements','2004-02-25','New','1','1'),
    ('3118','GKB','2022-06-06','GKBWEB','2002-10-10','Finished','1','1'),
    ('7157','MGB Tourism','2022-06-06','MGBAHN-Main2015','2021-09-24','In progress','1','1'),
    ('7174','SOMED-SPITEX MAINT ','2022-06-06','SOMED SPITEX MAINT','2021-10-05','New','1','1'),
    ('7199','Les Retaites Populaires','2022-06-06','ARCHIMEDE-2015-3.14','2021-05-29','Planned','1','1');

INSERT INTO PROJECT_EMPLOYEE(PROJECT_ID, EMPLOYEE_ID)
VALUES
    ('1','1');



