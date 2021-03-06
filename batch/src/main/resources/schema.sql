CREATE TABLE BOOKS(ID IDENTITY PRIMARY KEY, NAME VARCHAR(255), AUTHOR_ID BIGINT, GENRE_ID BIGINT);
CREATE TABLE AUTHORS(ID IDENTITY PRIMARY KEY, FIRST_NAME VARCHAR(255), LAST_NAME VARCHAR(255));
CREATE TABLE GENRES(ID IDENTITY PRIMARY KEY, NAME VARCHAR(255));
CREATE TABLE USERS(ID IDENTITY PRIMARY KEY, LOGIN VARCHAR(255), NAME VARCHAR(255), PASSWORD VARCHAR(255));
CREATE TABLE AUTHORITY (NAME VARCHAR(255) PRIMARY KEY);
CREATE TABLE USER_AUTHORITY (USER_ID BIGINT NOT NULL, AUTHORITY_NAME VARCHAR(255) NOT NULL);

ALTER TABLE BOOKS ADD CONSTRAINT FK_AUTHORS FOREIGN KEY (AUTHOR_ID) REFERENCES AUTHORS(ID);
ALTER TABLE BOOKS ADD CONSTRAINT FK_GENRES FOREIGN KEY (GENRE_ID) REFERENCES GENRES(ID);
ALTER TABLE USER_AUTHORITY ADD CONSTRAINT PK_CONSTRAINT PRIMARY KEY (USER_ID, AUTHORITY_NAME);
ALTER TABLE USER_AUTHORITY ADD CONSTRAINT FK_USERS FOREIGN KEY (USER_ID) REFERENCES USERS(ID);
ALTER TABLE USER_AUTHORITY ADD CONSTRAINT FK_AUTHORITY FOREIGN KEY (AUTHORITY_NAME) REFERENCES AUTHORITY(NAME);
