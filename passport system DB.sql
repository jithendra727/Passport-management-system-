Create database Passport;
use Passport;

create table user_registration(
	nic varchar(20) primary key,
    username varchar (50),
    email varchar(80),
    password varchar(50)
    );
    
CREATE TABLE user_information (
    nic VARCHAR(20) NOT NULL PRIMARY KEY,
    username VARCHAR(50) NOT NULL,
    given_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    nationality VARCHAR(50) NOT NULL,
    birth_date DATE NOT NULL,
    birth_place VARCHAR(100) NOT NULL,
    address VARCHAR(200) NOT NULL
    
);

create table user_image(
	nic varchar (20) not null primary key,
    username varchar(50)not null,
	nic_image varchar (50)not null,
    birth_certificate_image varchar(50),
    passport_photo_image varchar(50),
    FOREIGN KEY (nic) REFERENCES user_information(nic)
);

CREATE TABLE emp_register (
  nic varchar(20) PRIMARY KEY not null,
  username VARCHAR(255) UNIQUE not null,
  role VARCHAR(255) not null,
  email VARCHAR(255) not null,
  password VARCHAR(255) not null
);


create table police_verification(
	police_nic varchar(20)  ,
    user_nic varchar(20)PRIMARY KEY ,
    police_validity varchar(50),
    police_description varchar(100)
    );
    
    create table administrator_verification(
	administrator_nic varchar(20) ,
    user_nic varchar(20) PRIMARY KEY,
    administrator_validity varchar(50),
    administrator_description varchar(100)
    );
    
	create table regional_verification(
	regional_nic varchar(20) ,
    user_nic varchar(20) PRIMARY KEY,
    regional_validity varchar(50),
    regional_description varchar(100)
    );
    
    
    
    CREATE TABLE appointments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    user_nic VARCHAR(20) NOT NULL,
    appointment_date DATE not null,
    CONSTRAINT fk_user_id FOREIGN KEY (user_nic) REFERENCES user_information(nic)
);
    
   