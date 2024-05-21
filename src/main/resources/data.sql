INSERT INTO "EMPLOYEE" (ID, VERSION, NAME) VALUES
(1, 1, 'Jan Main'),
(2, 1, 'Alaina faisal'),
(3, 1, 'Nouman Rasool'),
(4, 1, 'Resanti Potter'),
(5, 1, 'Good Person');
INSERT INTO "PROJECT" (ID, VERSION, NAME) VALUES
(6, 1, 'Phillips Van Heusen Corp.'),
(7, 1, 'Avaya Inc.'),
(8, 1, 'Laboratory Corporation'),
(9, 1, 'AutoZone, Inc.'),
(10, 1, 'Linens n Things Inc.');

INSERT INTO "TIME_ENTRY"
(ID, VERSION, DATE2, arrival_Time, departure_Time, break_Duration, time_Category, DESCRIPTION, employee_id, project_id, hours)
VALUES
(70, 1, '2023-05-01', '08:00:00', '17:00:00', 60, 'Normal Workhour','A nice good project', 1, 6, 8.00),
(72, 1, '2023-05-02', '08:30:00', '17:30:00', 45, 'Normal Workhour','Also a good project', 3, 7, 8.25),
(73, 1, '2023-05-03', '09:00:00', '18:00:00', 45, 'Normal Workhour',' America Holdings', 2, 8, 8.25),
(74, 1, '2023-05-04', '10:00:00', '19:00:00', 15, 'Overtime','A fintech project', 4, 9, 8.75),
(75, 1, '2023-05-01', '08:00:00', '17:00:00', 10, 'Normal Workhour', ' America Holdings',1, 8, 8.83),
(76, 1, '2023-05-01', '08:00:00', '17:00:00', 20, 'Normal Workhour', 'A fintech project',1, 9, 8.67),
(77, 1, '2023-05-01', '08:00:00', '17:00:00', 30, 'Normal Workhour',' America Holdings', 1, 10, 8.50),
(78, 1, '2023-05-04', '11:00:00', '19:00:00', 15, 'Overtime','A fintech project 3', 2, 9, 8.75),
(79, 1, '2023-06-04', '11:00:00', '19:00:00', 15, 'Overtime','A fintech project 4', 5, 8, 8.75),
(80, 1, '2023-03-04', '12:00:00', '19:00:00', 15, 'Overtime','A fintech project', 2, 6, 8.75),
(81, 1, '2023-02-04', '10:00:00', '19:00:00', 15, 'Overtime','A fintech project', 3, 7, 8.75);


