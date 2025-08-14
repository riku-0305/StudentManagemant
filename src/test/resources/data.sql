INSERT INTO students (name, reading, nick_name, mail_address, address, age, gender, remark) VALUES ('うずまきナルト', 'うずまきナルト', 'ナルト', 'naruto@example.com', '木の葉隠れの里', 16, '男', '');
INSERT INTO students (name, reading, nick_name, mail_address, address, age, gender, remark) VALUES ('うちはサスケ', 'うちはサスケ', 'サスケ', 'sasuke@example.com', '木の葉隠れの里', 16, '男', '');
INSERT INTO students (name, reading, nick_name, mail_address, address, age, gender, remark) VALUES ('春野サクラ', 'はるのサクラ', 'サクラ', 'sakura@example.com', '木の葉隠れの里', 16, '女', '');

INSERT INTO students_courses (students_id, courses_name, start_date, expected_end_date) VALUES (1,'Java', '2025-08-09', '2025-12-31');
INSERT INTO students_courses (students_id, courses_name, start_date, expected_end_date) VALUES (2,'Aws', '2025-08-09', '2025-12-31');
INSERT INTO students_courses (students_id, courses_name, start_date, expected_end_date) VALUES (3,'デザイン', '2025-08-09', '2025-12-31');

INSERT INTO enrollment_status (students_id, students_courses_id, status) VALUES (1,1,'仮登録');
INSERT INTO enrollment_status (students_id, students_courses_id, status) VALUES (2,2,'仮登録');
INSERT INTO enrollment_status (students_id, students_courses_id, status) VALUES (3,3,'仮登録');