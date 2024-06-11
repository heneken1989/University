
ALTER TABLE QuizQuestion MODIFY content LONGTEXT;
ALTER TABLE News MODIFY content LONGTEXT;

INSERT IGNORE INTO  field (id, name) 
VALUES 
    (1, 'Biology'),
    (2, 'Economic'),
    (3, 'History'),
    (4, 'Information Technology');
    
INSERT IGNORE INTO semeter (name, year, day_end, day_start, id,startRegisDate,closeRegisDate) VALUES
    (1, 2024, '2024-6-01', '2024-01-01', 1,'2024-05-01','2024-05-11'),
    (2, 2024, '2024-12-01', '2024-06-02', 2,'2024-06-01','2024-06-28');

    
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
    
    
    
INSERT IGNORE INTO  user (id, address, avatar, email, infomation, name, password, phone, role,status) 
VALUES 
    (2, 'admin', 'nobita.jpg', 'admin@gmail.com', 'ad', 'admin', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'ADMIN',0),
    (3, 'st', 'nobita.jpg', 'st', 'ad', 'student', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'STUDENT',0),
    (4, 'st1', 'nobita.jpg', 'st1', 'ad', 'student1', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'STUDENT',0),
    (5, 'tc', 'nobita.jpg', 'teacher', 'ad', 'teacher', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'TEACHER',0),
    (6, 'emp', 'nobita.jpg', 'employee', 'im employee', 'employee', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '1', 'EMPLOYEE',0),
    (7, 'st2', 'nobita.jpg', 'st2', 'ad', 'teacher1', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '2', 'STUDENT',0),
    (8, 'tc2', 'nobita.jpg', 'teacher2', 'ad', 'teacher2', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '3', 'TEACHER',0),
    (9, 'st3', 'nobita.jpg', 'st3', 'ad', 'teacher3', '$2a$12$DRd7H8xiJr9ktFHr4YFDBOWMl7PwdHpmo3GYdHsf8PW4ZetaBM0Iu', '4', 'STUDENT',0);
    

  

INSERT IGNORE INTO  subject (id, name, credit, field_id, subjectlevel_id,type,creditAction) 
VALUES 
    (1, 'Computer Science', '2', '4', '1',0,1),
    (2, 'Software Engineering', '3', '4', '1',1,2),
    (3, 'Network Security', '3', '4', '2',2,1),
    (4, 'Database Management', '1', '4', '2',0,0),
    (5, 'Web Development', '2', '4', '2',2,1),
    (6, 'Artificial Intelligence', '3', '4', '2',0,0),
    (7, 'Cybersecurity', '3', '4', '3',0,0),
    (8, 'Cloud Computing', '4', '4', '3',0,0),
    (9, 'Mobile Application Development', '3', '4', '4',0,0),
    (10, 'Operating Systems', '2', '4', '4',2,1), 
    (11, 'Macroeconomics', '2', '2', '1',0,0),
    (12, 'Microeconomics', '3', '2', '1',0,0),
    (13, 'International Economics', '4', '2', '2',0,0),
    (14, 'Development Economics', '1', '2', '2',0,0),
    (15, 'Econometrics', '2', '2', '2',0,0),
    (16, 'Behavioral Economics', '3', '2', '2',0,0),
    (17, 'Game Theory', '3', '2', '3',0,0),
    (18, 'Financial Economics', '4', '2', '3',0,0),
    (19, 'Labor Economics', '3', '2', '4',0,0),
    (20, 'Environmental Economics', '2', '2', '4',0,0),
    
    (21, 'Ancient History', '2', '3', '1',0,0),
    (22, 'Medieval History', '3', '3', '1',0,0),
    (23, 'Modern History', '4', '3', '2',0,0),
    (24, 'European History', '1', '3', '2',0,0),
    (25, 'American History', '2', '3', '2',0,0),
    (26, 'Asian History', '3', '3', '2',0,0),
    (27, 'African History', '3', '3', '3',0,0),
    (28, 'Art History', '4', '3', '3',0,0),
    (29, 'Military History', '3', '3', '4',0,0),
    (30, 'Political History', '2', '3', '4',0,0),
    
    
    (31, 'Genetics', '2', '1', '1',1,2),
    (32, 'Microbiology', '3', '1', '1',2,1),
    (33, 'Ecology', '4', '1', '2',2,2),
    (34, 'Anatomy', '1', '1', '2',1,1),
    (35, 'Biochemistry', '2', '1', '2',1,2),
    (36, 'Botany', '3', '1', '2',1,3),
    (37, 'Zoology', '3', '1', '3',0,0),
    (38, 'Immunology', '4', '1', '3',0,0),
    (39, 'Evolutionary Biology', '3', '1', '4',0,0),
    (40, 'Physiology', '2', '1', '4',0,0);
    
    
    
 
    
        INSERT IGNORE INTO  user_field (field_id,users_id) 
VALUES 
    (1,6);
    

        INSERT IGNORE INTO  teacher_subject (id, subject_id,teacher_id) 
VALUES 
    (1,36,5);

    
    
            INSERT IGNORE INTO  class_subject (currentQuantity, minQuantity, quantity, slotEnd, slotStart, status, weekDay, dateEnd, dateStart, id, room_id, semeter_id, subject_id, teacher_id, description, name, type) 
VALUES 
    (0,5,50,3,1,NULL,1,'2024-06-25 12:31:29.000000','2024-01-17 12:31:29.000000',1,1,1,36,5,'Botany class','BOTANY CLASS',2);
         
    
    
        INSERT IGNORE INTO  studentclass (status,class_id,id,student_id) 
VALUES 
      (4,1,1,3),
      (4,1,2,4),
      (4,1,3,7);

    
   
    

    
    






    