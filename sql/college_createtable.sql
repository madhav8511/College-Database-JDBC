create table department(
    department_id int PRIMARY KEY,
    department_name varchar(50) NOT NULL
);

create table course(
    course_id int PRIMARY KEY,
    course_name varchar(50),
    department_id int NOT NULL,
    FOREIGN KEY (department_id) REFERENCES department(department_id)
);

create table teacher(
    teacher_id int PRIMARY KEY,
    teacher_name varchar(50),
    course_id int NOT NULL,
    FOREIGN KEY (course_id) REFERENCES course(course_id)
);

create table student(
    student_id int PRIMARY KEY,
    student_name varchar(50) NOT NULL,
    cgpa Decimal (10,2) NOT NULL
);

create table student_course(
    student_id int,
    course_id int,
    constraint pk_stcour PRIMARY KEY(student_id,course_id),
    FOREIGN KEY (student_id) REFERENCES student(student_id),
    FOREIGN KEY (course_id) REFERENCES course(course_id)
);

create table course_taken(
    course_id int PRIMARY KEY,
    total_enrollment int,
    FOREIGN KEY (course_id) REFERENCES course(course_id)
);