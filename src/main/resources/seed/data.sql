

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
    
    
    
   

    
INSERT IGNORE INTO  quiz (duration, totalMark, createDate, id, name, type) 
VALUES 
    (NULL, 100, NULL, 1, 'FinalTest', 'Midle');
    
   
    

    
    






    