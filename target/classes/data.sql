INSERT INTO "EMPLOYEE" (ID, VERSION, NAME) VALUES
(1, 1, 'Jan Main'),
(2, 1, 'Alaina faisal'),
(3, 1, 'Nouman Rasool'),
(4, 1, 'Resanti Potter'),
(5, 1, 'Good Person');
INSERT INTO "PROJECT" (ID, VERSION, NAME, DESCRIPTION) VALUES
(6, 1, 'Phillips Van Heusen Corp.','A nice good project'),
(7, 1, 'Avaya Inc.','Also a good project'),
(8, 1, 'Laboratory Corporation', ' America Holdings'),
(9, 1, 'AutoZone, Inc.', 'A fintech project'),
(10, 1, 'Linens n Things Inc.', 'Lesss go');
INSERT INTO "TIME_ENTRY" (ID, VERSION, DATE2, arrival_Time, departure_Time, break_Duration, time_Category, employee_id, project_id)
VALUES
(70, 1, '2023-05-01', '08:00:00', '17:00:00', 60, 'Full Time', 1, 6),
(72, 1, '2023-05-02', '08:30:00', '17:30:00', 45, 'Full Time', 3, 7),
(73, 1 ,'2023-05-03', '09:00:00', '18:00:00', 45, 'Part Time', 2, 8),
(74, 1, '2023-05-04', '10:00:00', '19:00:00', 15, 'Consulting', 4, 9);


