# elca-fptu-bootcamp-week2-exercise
ELCA Java exercises for University program

1.  Working with the Spring configuration in the project
In this step you are expected to understand clearly how Spring was applied. 
- Open ApplicationLauncher and start it.
- TaskRepository implementation has wrong name. Therefore Spring cannot find and wire it correctly. Can you find that class and rename it?
- From Postman, send a request to http://localhost:8080/main and you will see NullPointerException (actually 3 NullPointerException(s) respectively). 
- Can you find the causes and fix them? 

2.  Complete the query-by-name on “search” feature
Make the necessary changes in pim-tool-back to have an GET endpoint that receive a “keyword”. The response must return a list of projects with name containing the keyword.
Notes:
- The implementation of ProjectServiceImpl must be used.
- If you’re asking yourself how the application works without any “explicit” Spring MVC configuration (i.e. DispatcherServlet, HandlerMapping, ViewResolver, …), don’t worry.  
  We’re using Spring Boot for convenience and the framework is capable of configuring a Spring MVC Web application automatically for you. 
  More information can be found here: http://docs.spring.io/spring-boot/docs/current/reference/html/boot-features-developing-web-applications.html

3.  Implement new endpoints
In pim-tool-back, add new features:
- GET endpoint to find a project by its ID and return the following information: ID, name, customer, finishing date.
- POST endpoint to update a project, the following fields can be updated: name, customer, finishing date. Date format must be “dd/MM/yyyy”
Notes:
- The dummy implementation of ProjectService must be used.
- Postman: create new requests to prove your work.
