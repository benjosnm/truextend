# truextend
Truextend S4 project

Base on Spring boot standalone Spring base app to support an embed Tomcat to publish JAX-RS Rest services.

Application contains 2 main resources:
* S4ClassResource
* StundentResource

That resources provides all required operations over Classes and Students.

On implementation I take advantage of Spring boot framework and features.

- Used Component from springframework stereotype for Jersey and Tomcat resource auto register.
- Javax persistence for the management for persistence and object/relational mapping.
- Spring jpa repository for object-realtional mapping.
- Spring transactional support for renalational database.

# DB requirement

S4 require MySQL database and 3 tables:

create table s4classes( code VARCHAR(255), title VARCHAR(255), description TEXT, PRIMARY KEY (code)) ENGINE=INNODB;

create table s4students( id INT AUTO_INCREMENT, first_name VARCHAR(255), last_name VARCHAR(255), PRIMARY KEY (id)) ENGINE=INNODB;

create table class_student (class_code VARCHAR(255), student_id INT) engine=innodb;


