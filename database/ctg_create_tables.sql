PRAGMA foreign_keys = OFF;
drop table if exists PopulationStatistics;
drop table if exists Demographic;
drop table if exists Gender;
drop table if exists Indigenous_Status;
drop table if exists LGA;
drop table if exists Non_School_Education;
drop table if exists School_Education;
drop table if exists Labour_Force;
drop table if exists Age;
drop table if exists Age_Bandwidth;
drop table if exists Team_Members;
drop table if exists User_Personas;
PRAGMA foreign_keys = ON;

CREATE TABLE Demographic (
    demographic_id    INTEGER NOT NULL,
    sex_id            CHAR (1),
    indi_status       CHAR (1),
    lga_code          INTEGER NOT NULL,
    PRIMARY KEY       (demographic_id)
    FOREIGN KEY       (sex_id) REFERENCES Gender(sex_id)
    FOREIGN KEY       (indi_status) REFERENCES Indigenous_Status(indi_status)
    FOREIGN KEY       (lga_code) REFERENCES LGA(lga_code)
);

CREATE TABLE Gender (
    sex             CHAR (1),
    sex_description TEXT,
    PRIMARY KEY     (sex_id)
);

CREATE TABLE Indigenous_Status (
    status              CHAR (1),
    status_description  TEXT,
    PRIMARY KEY     (indi_status)
);

CREATE TABLE LGA (
    code              INTEGER NOT NULL,
    lga_name          TEXT,
    lga_type          CHAR (2),
    area              DOUBLE,
    latitude          DOUBLE,
    longitude         DOUBLE,
    PRIMARY KEY       (lga_code)
);

CREATE TABLE Non_School_Education (
    id              INTEGER, 
    category        TEXT,
    num_people      INTEGER,
    FOREIGN KEY     (id) REFERENCES Demographic(demographic_id)
);

CREATE TABLE School_Education (
    id              INTEGER, 
    category        TEXT,
    num_people      INTEGER,
    FOREIGN KEY     (id) REFERENCES Demographic(demographic_id)
);

CREATE TABLE Labour_Force (
    id              INTEGER, 
    category        TEXT,
    num_people      INTEGER,
    FOREIGN KEY     (id) REFERENCES Demographic(demographic_id)
);

CREATE TABLE Age (
    id              INTEGER, 
    category   TEXT,
    num_people      INTEGER,
    FOREIGN KEY     (id) REFERENCES Demographic(demographic_id)
);

CREATE TABLE Age_Bandwidth (
    category     TEXT, 
    lower_limit       INTEGER,
    upper_limit       INTEGER,
    PRIMARY KEY       (category)
);

CREATE TABLE Team_Members (
    student_number    INTEGER NOT NULL, 
    first_name        TEXT,
    last_name         TEXT,
    email_ID          TEXT,
    PRIMARY KEY       (student_number)
);

CREATE TABLE User_Personas (
    persona_id          TEXT, 
    name                INTEGER,
    age                 TEXT,
    ethinicity          TEXT,
    occupation          TEXT, 
    description         INTEGER,
    goals               TEXT,
    pain_points         TEXT,
    needs               TEXT,
    skills_experiences  TEXT,
    PRIMARY KEY         (persona_id)
);

-- Relational Database Schema

-- Demographic ({PK}Demographic_ID, {FK}Sex_ID, {FK}Indi_status, {FK}LGA_Code)

-- Gender ({PK}Sex_ID)

-- Indigenous_Status ({PK}Indi_status)

-- LGA ({PK}LGA_Code, LGA_Name, LGA_Type, Area, Latitude, Longitude)

-- Non_School_Education ({FK}ID, Category, Num_People)

-- School_Education ({FK}ID, Category, Num_People)

-- Labour_Force ({FK}ID, Category, Num_People)

-- Age ({FK}ID, {FK}category, Num_People)

-- Age_Bandwidth ({PK}category, Upper_Limit, Lower_Limit)

-- Team_Members ({PK}Student_number, First_Name, Last_Name, Email_ID)

-- User_Personas({PK}Persona_ID, Name,  Age,  Ethnicity,  Occupation,  Description,  Goals, Pain_Points,  Needs,  Skills_Experience)