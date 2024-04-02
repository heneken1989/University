

INSERT IGNORE INTO  field (id, name) 
VALUES 
    (1, 'Biology'),
    (2, 'History'),
    (3, 'Economic'),
    (4, 'Information Technology');

    
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
    (2, 'awda', 'awdadw', 'admin', 'ad', 'admin', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'ADMIN'),
    (3, 'awda', 'awdadw', 'student', 'ad', 'student', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'STUDENT'),
    (4, 'awda', 'awdadw', 'student1', 'ad', 'student1', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'STUDENT'),
    (5, 'awda', 'awdadw', 'student2', 'ad', 'student2', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'STUDENT');
    
    
INSERT IGNORE INTO  subject (id, name, credit, field_id, subjectlevel_id) 
VALUES 
    (1, 'Computer Science', '2', '4', '1'),
    (2, 'Software Engineering', '3', '4', '1'),
    (3, 'Network Security', '3', '4', '2'),
    (4, 'Database Management', '1', '4', '2'),
    (5, 'Web Development', '2', '4', '2'),
    (6, 'Artificial Intelligence', '3', '4', '2'),
    (7, 'Cybersecurity', '3', '4', '3'),
    (8, 'Cloud Computing', '4', '4', '3'),
    (9, 'Mobile Application Development', '3', '4', '4'),
    (10, 'Operating Systems', '2', '4', '4'),
    
    (11, 'Macroeconomics', '2', '2', '1'),
    (12, 'Microeconomics', '3', '2', '1'),
    (13, 'International Economics', '4', '2', '2'),
    (14, 'Development Economics', '1', '2', '2'),
    (15, 'Econometrics', '2', '2', '2'),
    (16, 'Behavioral Economics', '3', '2', '2'),
    (17, 'Game Theory', '3', '2', '3'),
    (18, 'Financial Economics', '4', '2', '3'),
    (19, 'Labor Economics', '3', '2', '4'),
    (20, 'Environmental Economics', '2', '2', '4'),
    
    (21, 'Ancient History', '2', '3', '1'),
    (22, 'Medieval History', '3', '3', '1'),
    (23, 'Modern History', '4', '3', '2'),
    (24, 'European History', '1', '3', '2'),
    (25, 'American History', '2', '3', '2'),
    (26, 'Asian History', '3', '3', '2'),
    (27, 'African History', '3', '3', '3'),
    (28, 'Art History', '4', '3', '3'),
    (29, 'Military History', '3', '3', '4'),
    (30, 'Political History', '2', '3', '4'),
    
    
    (31, 'Genetics', '2', '1', '1'),
    (32, 'Microbiology', '3', '1', '1'),
    (33, 'Ecology', '4', '1', '2'),
    (34, 'Anatomy', '1', '1', '2'),
    (35, 'Biochemistry', '2', '1', '2'),
    (36, 'Botany', '3', '1', '2'),
    (37, 'Zoology', '3', '1', '3'),
    (38, 'Immunology', '4', '1', '3'),
    (39, 'Evolutionary Biology', '3', '1', '4'),
    (40, 'Physiology', '2', '1', '4');
    
    
    
    
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
    
    
    INSERT IGNORE INTO  quizexam (totalMark, endDate, id, quiz_id, startDate, user_id) 
VALUES 
    (0, NULL, 1, 1, NULL, 3);
    
    
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





    