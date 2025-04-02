CREATE TABLE user_tb (
  id SERIAL PRIMARY KEY NOT NULL,
  created_date timestamp(0) DEFAULT NULL,
  modified_date timestamp(0) DEFAULT NULL,
  first_name varchar(250) DEFAULT NULL,
  last_name varchar(250) DEFAULT NULL,
  gender varchar(6) DEFAULT NULL,
  age int DEFAULT '0'
);

CREATE INDEX IDX_full_name ON user_tb (first_name,last_name);
CREATE INDEX IDX_age ON user_tb (age);