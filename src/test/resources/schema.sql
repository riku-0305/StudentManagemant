CREATE TABLE students (
    id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    reading VARCHAR(100),
    nick_name VARCHAR(100),
    mail_address VARCHAR(100),
    address VARCHAR(100),
    age INT,
    gender VARCHAR(20),
    remark VARCHAR(300),
    is_delete TINYINT　NOT NULL DEFAULT 0
);

CREATE TABLE students_courses (
    courses_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    students_id BIGINT NOT NULL,
    courses_name VARCHAR(30),
    start_date DATE,
    expected_end_date DATE,

    FOREIGN KEY (students_id) REFERENCES students(id)
);

CREATE TABLE enrollment_status (
    status_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
    students_id BIGINT NOT NULL,
    students_courses_id BIGINT NOT NULL,
    status VARCHAR(50),
    FOREIGN KEY (students_id) REFERENCES students(id),
    FOREIGN KEY (students_courses_id) REFERENCES students_courses(courses_id)
);


