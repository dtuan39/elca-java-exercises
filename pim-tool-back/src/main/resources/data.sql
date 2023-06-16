INSERT INTO PROJECT (NAME, FINISHING_DATE)
VALUES
    ('EFV', '2020-04-20'),
    ('CXTRANET', '2020-04-25'),
    ('CRYSTAL BALL', '2020-04-28'),
    ('IOC CLIENT EXTRANET', '2020-06-07'),
    ('TRADEECO', '2020-06-08');

INSERT INTO USER (USERNAME)
VALUES
    ('USER1'),
    ('USER2'),
    ('USER3'),
    ('QMV'),
    ('TQP'),
    ('HNH'),
    ('NQN'),
    ('PLH'),
    ('HNL'),
    ('TBH'),
    ('TDN'),
    ('HPN'),
    ('HUN'),
    ('BNN'),
    ('PNH'),
    ('VVT');

INSERT INTO TASK(NAME, DEADLINE, PROJECT_ID, USER_ID)
VALUES
    ('EFV_TASK_1', '2020-03-05', 1, 1),
    ('EFV_TASK_2', '2020-03-10', 1, null),
    ('EFV_TASK_3', '2020-03-15', 1, null);


INSERT INTO GROUP_TABLE(LEADER_ID)
VALUES
    (4),
    (6);

INSERT INTO GROUP_PROJECT_TABLE(GROUP_ID, PROJECT_ID)
VALUES
    (1,1),
    (1,2),
    (1,3),
    (2,4),
    (2,5);
INSERT INTO PROJECT_MEMBER_TABLE(PROJECT_ID, MEMBER_ID, STATUS)
VALUES
    (1,5,'Developer'),
    (1,6,'Quality Agent'),
    (1,7,'Developer'),
    (2,8,'Quality Agent'),
    (2,9,'Developer'),
    (3,10,'Quality Agent'),
    (3,11,'Developer'),
    (4,12,'Developer'),
    (4,13,'Quality Agent'),
    (4,14,'Developer'),
    (4,15,'Developer'),
    (5,4,'Quality Agent'),
    (5,16,'Developer');