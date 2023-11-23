DELETE FROM lesson where true;
DELETE FROM student_groups where true;
DELETE FROM teacher_subjects where true;
DELETE FROM users_roles where true;
DELETE FROM groups where true;
DELETE FROM course where true;
DELETE FROM subject where true;
DELETE FROM student where true;
DELETE FROM teacher where true;
DELETE FROM users where true;
DELETE FROM user_file where true;

INSERT INTO users(username, locked, password) VALUES
    ('admin', false, '$2a$10$enfwuy1Wo3utyFXdLK.bBO7UzZ7wEwkVb162HYfQp7RKlXoEHLimy');

INSERT INTO teacher(username, confirmed) VALUES
    ('admin', false);

INSERT INTO users_roles(users_username, roles) VALUES
   ('admin', 'ADMIN'),
   ('admin', 'TEACHER');

INSERT INTO users(username, birthdate, firstname, lastname, locked, password)  VALUES
    ('student', '2004-03-02', 'Студент', 'Студентиков', false, '$2a$10$enfwuy1Wo3utyFXdLK.bBO7UzZ7wEwkVb162HYfQp7RKlXoEHLimy');

INSERT INTO student(grade, username) VALUES
    (11, 'student');

INSERT INTO users_roles(users_username, roles) VALUES
    ('student', 'STUDENT');

INSERT INTO subject(name) VALUES ('physics');

INSERT INTO course(name, price, subject_name, image_key) VALUES
    ('Ядерная физика', 100500, 'physics', null);

INSERT INTO groups(id, course_name, teacher_username) VALUES
    (1, 'Ядерная физика', 'admin');

INSERT INTO student_groups(student_username, group_id) VALUES
    ('student', 1);

INSERT INTO teacher_subjects(teacher_username, subject_name) VALUES
    ('admin', 'physics');