insert into department values (1,'CSE');
insert into department values (2,'ECE');
insert into department values (3,'CIVIL');

insert into course values (101,'AI/ML',1);
insert into course values (102,'POCS',2);
insert into course values (103,'Design',3);

insert into course_taken values(101,1);
insert into course_taken values(102,1);
insert into course_taken values(103,1);

insert into teacher values (201,'Aman',101);
insert into teacher values (202,'Rohan',102);
insert into teacher values (203,'Shubham',103);

insert into student values (301,'Madhav',3.4);
insert into student values (302,'Bhavya',4.0);
insert into student values (303,'Bijeet',3.55);

insert into student_course values (301,101);
insert into student_course values (303,102);
insert into student_course values (302,103);
