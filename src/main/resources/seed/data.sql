

INSERT IGNORE INTO  field (id, name) 
VALUES 
    (1, 'Biology'),
    (2, 'History'),
    (3, 'Economic'),
    (4, 'Information Technology');
    
INSERT IGNORE INTO semeter (name, year, day_end, day_start, id,startRegisDate,closeRegisDate) VALUES
    (1, 2024, '2024-6-01', '2024-01-01', 1,'2024-05-01','2024-05-11'),
    (2, 2024, '2024-12-01', '2024-06-02', 2,'2024-05-01','2024-05-11');

    
INSERT IGNORE INTO  room (id, name ,capacity) 
VALUES 
    (1, 'A101',40),
    (2, 'A102',40),
    (3, 'A103',20),
    (4, 'A104',60);
    
    
    INSERT IGNORE INTO  subjectlevel (id, name) 
VALUES 
    (1, 'Core'),
    (2, 'Level 100'),
    (3, 'Level 200'),
    (4, 'Level 300');
    
    
    
INSERT IGNORE INTO  user (id, address, avatar, email, infomation, name, password, phone, role) 
VALUES 
    (2, 'sg', 'nobita.jpg', 'admin@gmail.com', 'ad', 'admin', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'ADMIN'),
    (3, 'dl', 'nobita.jpg', 'student@gmail.com', 'ad', 'student', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'STUDENT'),
    (4, 'vt', 'nobita.jpg', 'student1@gmail.com', 'ad', 'student1', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'STUDENT'),
    (5, 'hcm', 'nobita.jpg', 'teacher@gmail.com', 'ad', 'teacher', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'TEACHER'),
    (6, 'hn', 'nobita.jpg', 'employee@gmail.com', 'im employee', 'employee', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'EMPLOYEE'),
    (7, 'hcm', 'nobita.jpg', 'teacher1@gmail.com', 'ad', 'teacher1', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '2', 'TEACHER'),
    (8, 'hcm', 'nobita.jpg', 'teacher2@gmail.com', 'ad', 'teacher2', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '3', 'TEACHER'),
    (9, 'hcm', 'nobita.jpg', 'teacher3@gmail.com', 'ad', 'teacher3', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '4', 'TEACHER');
    
    DELETE FROM user_field;
    INSERT IGNORE INTO user_field (users_id,field_id) 
    VALUES 
       (3, 1),
       (3, 2),
       (4, 3),
       (4, 4),
       (5, 1),
       (6, 1),
       (6, 2),
       (6, 3),
       (6, 4);

INSERT IGNORE INTO  subject (id, name, credit, field_id, subjectlevel_id,type,creditAction) 
VALUES 
    (1, 'Computer Science', '2', '4', '1','both',1),
    (2, 'Software Engineering', '3', '4', '1','both',2),
    (3, 'Network Security', '3', '4', '2','both',1),
    (4, 'Database Management', '1', '4', '2','Theory',0),
    (5, 'Web Development', '2', '4', '2','both',1),
    (6, 'Artificial Intelligence', '3', '4', '2','Theory',0),
    (7, 'Cybersecurity', '3', '4', '3','Theory',0),
    (8, 'Cloud Computing', '4', '4', '3','Theory',0),
    (9, 'Mobile Application Development', '3', '4', '4','Theory',0),
    (10, 'Operating Systems', '2', '4', '4','both',1), 
    (11, 'Macroeconomics', '2', '2', '1','Theory',0),
    (12, 'Microeconomics', '3', '2', '1','Theory',0),
    (13, 'International Economics', '4', '2', '2','Theory',0),
    (14, 'Development Economics', '1', '2', '2','Theory',0),
    (15, 'Econometrics', '2', '2', '2','Theory',0),
    (16, 'Behavioral Economics', '3', '2', '2','Theory',0),
    (17, 'Game Theory', '3', '2', '3','Theory',0),
    (18, 'Financial Economics', '4', '2', '3','Theory',0),
    (19, 'Labor Economics', '3', '2', '4','Theory',0),
    (20, 'Environmental Economics', '2', '2', '4','Theory',0),
    
    (21, 'Ancient History', '2', '3', '1','Theory',0),
    (22, 'Medieval History', '3', '3', '1','Theory',0),
    (23, 'Modern History', '4', '3', '2','Theory',0),
    (24, 'European History', '1', '3', '2','Theory',0),
    (25, 'American History', '2', '3', '2','Theory',0),
    (26, 'Asian History', '3', '3', '2','Theory',0),
    (27, 'African History', '3', '3', '3','Theory',0),
    (28, 'Art History', '4', '3', '3','Theory',0),
    (29, 'Military History', '3', '3', '4','Theory',0),
    (30, 'Political History', '2', '3', '4','Theory',0),
    
    
    (31, 'Genetics', '2', '1', '1','Action',2),
    (32, 'Microbiology', '3', '1', '1','both',1),
    (33, 'Ecology', '4', '1', '2','both',2),
    (34, 'Anatomy', '1', '1', '2','Action',1),
    (35, 'Biochemistry', '2', '1', '2','Action',2),
    (36, 'Botany', '3', '1', '2','Action',3),
    (37, 'Zoology', '3', '1', '3','Theory',0),
    (38, 'Immunology', '4', '1', '3','Theory',0),
    (39, 'Evolutionary Biology', '3', '1', '4','Theory',0),
    (40, 'Physiology', '2', '1', '4','Theory',0);
    
    
    
    INSERT IGNORE  INTO teacher_subject (id, subject_id, teacher_id) VALUES 
        (1, 1, 9),
        (2, 2, 9),
        (3, 3, 9),
        (4, 4, 9),
        (5, 5, 9),
        (6, 6, 9),
        (7, 7, 9),
        (8, 8, 9),
        (9, 9, 9),
        (10, 10, 9),
        
        (11, 11, 7),
        (12, 12, 7),
        (13, 13, 7),
        (14, 14, 7),
        (15, 15, 7),
        (16, 16,7),
        (17, 17, 7),
        (18, 18, 7),
        (19, 19, 7),
        (20, 20, 7),
        
        (21, 21, 8),
        (22, 22, 8),
        (23, 23, 8),
        (24, 24, 8),
        (25, 25, 8),
        (26, 26, 8),
        (27, 27, 8),
        (28, 28, 8),
        (29, 29, 8),
        (30, 30, 8),
        
        (31, 31, 5),
        (32, 32, 5),
        (33, 33, 5),
        (34, 34, 5),
        (35, 35, 5),
        (36, 36, 5),
        (37, 37, 5),
        (38, 38, 5),
        (39, 39, 5),
        (40, 40, 5);

INSERT IGNORE INTO  class_subject (id, dateEnd, dateStart, description, name, quantity, slotEnd, slotStart,status,type,weekDay,room_id,semeter_id,subject_id,teacher_id) 
VALUES 
    (1, NULL, NULL, 'Class Info', 'Class 1', '30', '3', '1', 'Opened', 'FullSemester', '1', '1', NULL, '1', '2'),
    (2, NULL, NULL, 'Class Info', 'Class 2', '30', '8', '6', 'Opened', 'FirstHaftSemester', '1', '1', NULL, '2', '2'),
    (3, NULL, NULL, 'Class Info', 'Class 3', '30', '9', '1', 'Opened', 'FullSemester', '1', '1', NULL, '4', '2'),
    (4, NULL, NULL, 'Class Info', 'Class 4', '30', '9', '1', 'Opened', 'FullSemester', '1', '1', NULL, '6', '2'),
    (5, NULL, NULL, 'Class Info', 'Class 5', '30', '7', '1', 'Opened', 'FullSemester', '1', '1', NULL, '12', '2'),
    (6, NULL, NULL, 'Class Info', 'Class 6', '30', '7', '1', 'Opened', 'FullSemester', '1', '1', NULL, '14', '2'),
    (7, NULL, NULL, 'Class Info', 'Class 7', '30', '7', '1', 'Opened', 'FullSemester', '1', '1', NULL, '16', '2'),
    (8, NULL, NULL, 'Class Info', 'Class 8', '30', '11', '10', 'Opened', 'FullSemester', '1', '1', NULL, '22', '2'),
    (9, NULL, NULL, 'Class Info', 'Class 9 ', '30', '11', '9', 'Opened', 'FullSemester', '1', '1', NULL, '24', '2'),
    (10, NULL, NULL, 'Class Info', 'Class 10 ', '30', '11', '9', 'Opened', 'FullSemester', '1', '1', NULL, '26', '2'),
    (11, NULL, NULL, 'Class Info', 'Class 11 ', '30', '10', '8', 'Opened', 'FullSemester', '1', '1', NULL, '28', '2'),
    (12, NULL, NULL, 'Class Info', 'Class 12 ', '30', '10', '6', 'Opened', 'FullSemester', '1', '1', NULL, '30', '2'),
    (13, NULL, NULL, 'Class Info', 'Class 13 ', '30', '5', '1', 'Opened', 'FullSemester', '1', '1', NULL, '32', '2'),
    (14, NULL, NULL, 'Class Info', 'Class 14 ', '30', '5', '1', 'Opened', 'FullSemester', '1', '1', NULL, '32', '2'),
    (15, NULL, NULL, 'Class Info', 'Class 15 ', '30', '5', '1', 'Opened', 'FullSemester', '1', '1', NULL, '34', '2'),
    (16, NULL, NULL, 'Class Info', 'Class 16 ', '30', '4', '1', 'Opened', 'FullSemester', '1', '1', NULL, '34', '2'),
    (17, NULL, NULL, 'Class Info', 'Class 17 ', '30', '4', '1', 'Opened', 'FullSemester', '1', '1', NULL, '37', '2'),
    (18, NULL, NULL, 'Class Info', 'Class 18 ', '30', '4', '1', 'Opened', 'FullSemester', '1', '1', NULL, '37', '2');
    
INSERT IGNORE INTO  quiz (duration, totalMark, createDate, id, name, type) 
VALUES 
    (NULL, 100, NULL, 1, 'FinalTest', 'Midle');
    
   
    
    INSERT IGNORE INTO  quizquestion (mark, id, quiz_id, content, type) 
VALUES 
    (20, 1, 1, 'Question 1 Content','Multi'),
    (20, 2, 1, 'Question 2 Content','Multi'),
    (20, 3, 1, 'Question 3 Content','Multi'),
    (20, 4, 1, 'Question 4 Content','Multi'),
    (20, 5, 1, 'Question 5 Content','Multi');
    
    
    
        INSERT IGNORE INTO  quizanswer (isTrue, id, quiz_question_id, content) 
VALUES 
    (1, 1, 1, 'Answer 1 Content'),
    (0, 2, 1, 'Answer 2 Content'),
    (1, 3, 1, 'Answer 3 Content'),
    (0, 4, 1, 'Answer 4 Content'),
    
    (1, 5, 2, 'Answer 1 Content'),
    (0, 6, 2, 'Answer 2 Content'),
    (1, 7, 2, 'Answer 3 Content'),
    (0, 8, 2, 'Answer 4 Content'),
    
    (1, 9, 3, 'Answer 1 Content'),
    (0, 10, 3, 'Answer 2 Content'),
    (1, 11, 3, 'Answer 3 Content'),
    (0, 12, 3, 'Answer 4 Content'),
    
    
    (1, 13, 4, 'Answer 1 Content'),
    (0, 14, 4, 'Answer 2 Content'),
    (1, 15, 4, 'Answer 3 Content'),
    (0, 16, 4, 'Answer 4 Content'),
    
    (1, 17, 5, 'Answer 1 Content'),
    (0, 18, 5, 'Answer 2 Content'),
    (1, 19, 5, 'Answer 3 Content'),
    (0, 20, 5, 'Answer 4 Content');





    