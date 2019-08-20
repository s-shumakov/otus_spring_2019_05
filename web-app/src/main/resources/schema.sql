CREATE TABLE BOOKS(ID IDENTITY PRIMARY KEY, NAME VARCHAR(255), AUTHOR_ID BIGINT, GENRE_ID BIGINT);
CREATE TABLE AUTHORS(ID IDENTITY PRIMARY KEY, FIRST_NAME VARCHAR(255), LAST_NAME VARCHAR(255));
CREATE TABLE GENRES(ID IDENTITY PRIMARY KEY, NAME VARCHAR(255));
ALTER TABLE BOOKS ADD CONSTRAINT FK_AUTHORS FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS(ID);
ALTER TABLE BOOKS ADD CONSTRAINT FK_GENRES FOREIGN KEY (GENRE_ID) REFERENCES GENRES(ID);