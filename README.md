# truextend S4

Base on Spring boot standalone Spring base app to support an embed Tomcat to publish JAX-RS Rest services.

Application contains 2 main resources:
* S4ClassResource
* StudentResource

That resources provides all required operations over Classes and Students.

On implementation I take advantage of Spring boot framework and features.

- Used Component from springframework stereotype for Jersey and Tomcat resource auto register.
- Javax persistence for the management for persistence and object/relational mapping.
- Spring jpa repository for object-relational mapping.
- Spring transactional support for relational database.

# API Doc

https://documenter.getpostman.com/view/1578682/S1M3tjKV?version=latest

# Restore Postman test collection

If Postman is not installed: https://www.getpostman.com/downloads/

To restore Postman collection:

1. Press on import button.
2. Select S4.postman_collection file.

To execute test:

1. Open request i.e. Get all students.
2. If its necessary change body in request i.e. Create student.
3. Press Send button to execute request.

# DB requirement

S4 require MySQL database and 3 tables:

create table s4classes( code VARCHAR(255), title VARCHAR(255), description TEXT, PRIMARY KEY (code)) ENGINE=INNODB;

create table s4students( id INT AUTO_INCREMENT, first_name VARCHAR(255), last_name VARCHAR(255), PRIMARY KEY (id)) ENGINE=INNODB;

create table class_student (class_code VARCHAR(255), student_id INT) engine=innodb;


