<h1 align = "center"> Employee Management </h1>
<p align="center">
<a href="Java url">
    <img alt="Java" src="https://img.shields.io/badge/Java->=8-darkblue.svg" />
</a>
<a href="Maven url" >
    <img alt="Maven" src="https://img.shields.io/badge/maven-3.0.5-brightgreen.svg" />
</a>
<a href="Spring Boot url" >
    <img alt="Spring Boot" src="https://img.shields.io/badge/Spring Boot-3.0.6-brightgreen.svg" />
</a>
</p>

The Employee Management System is a Java-based application designed for managing a employee's book inventory. 
It provides functionalities for adding, updating, and deleting employee, as well as reporting to manager via mail.

### Tech Stack :-
  * Spring Boot
  * Java
  * MongoDB
## Dependencies
The following dependencies are required to run the project:

* Spring Boot Dev Tools
* Spring Web
* Spring mail
* MongoDB
* Lombok
* Validation
* Swagger

<br>


*Assignment of Add Employee to a Database**
![image](https://github.com/poojagurnule/employeeWise/assets/102051371/fe1d9310-cad7-4c7b-8f2b-2eae16764290)
# EmployWise

**I use Mongodb no SQL database**

**How run project:**

1. SETUP DATABASE

![image](https://github.com/poojagurnule/employeeWise/assets/102051371/5bbf6b99-b3c4-46f6-a0e6-1761400f2579)





2. How run API
         . Use Postman (is an API platform that simplifies each step of the API lifecycle and streamlines collaboration) or your comfort plateform for testing API.
         . Test Api one by one .
         . Use JSON format for testing .
   
   a. **POST**: use for store data in a database
    (http://localhost:8091/api/employees)
   
   /add -is used for add an Employee in Database ,you can see there code in Controller package 
  ![image](https://github.com/poojagurnule/employeeWise/assets/102051371/d0162ec1-e0fa-4d74-82e4-f531dd5700c4)


   b.** GET**: use for get data from a database
   (http://localhost:8091/api/employees)
   Provide you all employee that are available in your database
  ![image](https://github.com/poojagurnule/employeeWise/assets/102051371/6d503662-3661-44f9-a5b9-d3cf9d3cf237)


  

   c. **DELETE **: just provide id of employee you want to delete in uri
   We want to delete pooja from database
  ![image](https://github.com/poojagurnule/employeeWise/assets/102051371/148af763-a744-4d9b-97e6-3a1bad88df44)

   After Delete data will be delete from database
   
   d. **PUT**: use for update database
   you just have to provide the id in uri of that Employee you want to change and provide new body in JSON format
  ![image](https://github.com/poojagurnule/employeeWise/assets/102051371/24c83350-cbd2-4c43-bf90-c5a45b6caf41)



----------------------------------------------------------------------Intermediate -------------------------------------------------------------------
a. GET nth LEVEL Manager of an Employeer:

 if you are using Postman:
Open Postman.
Set the request type to "GET".
Enter the URL: http://localhost:8091/employees/manager.
Add query parameters: employeeId and level with their respective values.
Click on the "Send" button.

Find LEVEL 1 Manager for Employee id =08345b34-fdef-4557-b2d6-dec34a58b87f 

Enter the URL: (http://localhost:8091/api/employees/08345b34-fdef-4557-b2d6-dec34a58b87f/manager?level=1)
![image](https://github.com/poojagurnule/employeeWise/assets/102051371/7fea9b8f-59dd-45ee-97d8-888d92a80b28)

It return --> **rani** means it reports to  at Level 1

Find LEVEL 2 Manager for Employee id =08345b34-fdef-4557-b2d6-dec34a58b87f 
Enter the URL: (http://localhost:8091/api/employees/08345b34-fdef-4557-b2d6-dec34a58b87f/manager?level=2)

![image](https://github.com/poojagurnule/employeeWise/assets/102051371/a6bd536f-9017-4393-88ac-71e62b19eae0)
It return -- >** pooja** that is the manager at Level 2


b. Get Employee with pagination and sorting :

To send a request to the getEmployeesWithPaginationAndSorting endpoint, you can use a tool like curl, Postman, or a browser.
Here's an example using curl in the command line:
curl -X 'GET' \
  'http://localhost:8091/api/employees/paged?page=1&size=3&sortBy=employeeName' \
  -H 'accept: */*'

Replace the values of page, size, and sortBy with your desired parameters.

Open Postman.
Set the request type to "GET".
Enter the URL: http://localhost:8091/employees/paged.
Add query parameters: page, size, and sortBy with their respective values.
Click on the "Send" button.
For example we want 
page=1,
size=5,
sortBy=employeeName
Then URL will be :
(http://localhost:8091/api/employees/paged?page=1&size=2&sortBy=employeeName)

Here is Output:
![image](https://github.com/poojagurnule/employeeWise/assets/102051371/36a20da5-3e5d-422a-9a83-163e3aa8e666)

Send Email to Level 1 Manager on New Employee Addition:
![image](https://github.com/poojagurnule/employeeWise/assets/102051371/9509bb95-a70e-41aa-9dbd-32b052652816)



Thank You .....
