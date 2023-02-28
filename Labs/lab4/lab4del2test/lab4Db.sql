
CREATE TABLE users (
	id INT NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
	username VARCHAR(32) UNIQUE NOT NULL,
	password VARCHAR(32) NOT NULL
);
INSERT INTO users (username,password) VALUES ('mhaao@kth.se', '12345');
INSERT INTO users (username,password) VALUES ('abdtra@kth.se', '12345');

CREATE TABLE questions (
	id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
	text VARCHAR(64) NOT NULL,
	options VARCHAR(64) NOT NULL,
	answer VARCHAR(64) NOT NULL
);
INSERT INTO questions (text,options,answer) VALUES ('Which planets are larger than earth?', 'Mercury/Mars/Saturn', '0/0/1');
INSERT INTO questions (text,options,answer) VALUES ('Which planets are farther away from the sun than earth?', 'Mercury/Mars/Saturn', '0/1/1');
INSERT INTO questions (text,options,answer) VALUES ('Which planets have rings?', 'Mercury/Mars/Saturn', '0/0/1');

INSERT INTO questions (text,options,answer) VALUES ('Who won the world cup 2022?', 'France/Morocco/Argentine', '0/0/1');
INSERT INTO questions (text,options,answer) VALUES ('Who is the best player in the world for 2021 in Football?', 'Messi/Ronaldo/Benzema', '0/0/1');
INSERT INTO questions (text,options,answer) VALUES ('What is the nr 1 player in the world?', 'Ibrahim mkhasi/Hammo Bika/Abdullbaset Hammodah', '1/0/0');

CREATE TABLE quizzes (
	id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
	subject VARCHAR(64) NOT NULL
);
INSERT INTO quizzes (subject) VALUES ('Astronomy');
INSERT INTO quizzes (subject) VALUES ('Sport');


CREATE TABLE selector(
	quiz_id INT NOT NULL REFERENCES quizzes(id),
	question_id INT NOT NULL REFERENCES questions(id)
);
INSERT INTO selector (quiz_id, question_id) VALUES (1,1);
INSERT INTO selector (quiz_id, question_id) VALUES (1,2);
INSERT INTO selector (quiz_id, question_id) VALUES (1,3);

INSERT INTO selector (quiz_id, question_id) VALUES (2,10);
INSERT INTO selector (quiz_id, question_id) VALUES (2,11);
INSERT INTO selector (quiz_id, question_id) VALUES (2,12);

CREATE TABLE results(
	id INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1 INCREMENT BY 1),
	user_id INT NOT NULL REFERENCES users(id),
	quiz_id INT NOT NULL REFERENCES quizzes(id),
	score INT NOT NULL
);
INSERT INTO results (user_id,quiz_id,score) VALUES (1,1,0);
INSERT INTO results (user_id,quiz_id,score) VALUES (2,1,0);

INSERT INTO results (user_id,quiz_id,score) VALUES (1,2,0);
INSERT INTO results (user_id,quiz_id,score) VALUES (2,2,0);

