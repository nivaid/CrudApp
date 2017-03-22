# --- !Ups
 
CREATE TABLE UserTable (
    uid bigint(20) NOT NULL AUTO_INCREMENT,
    fname varchar(255) NOT NULL,
    lname varchar(255) NOT NULL,
    pno varchar(255) NOT NULL,
    address varchar(255) NOT NULL,
    PRIMARY KEY (uid)
);
 
# --- !Downs
 
DROP TABLE UserTable;