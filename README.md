# kosh-microservice-demoapp
Microservice demo with spring boot, spring cloud gateway, spring cloud load balancer, eureka service discovery, mysql ,jdbc , jpa, swagger docs, spring security jwt.


Instructions to run
1.Goto The folder location and run maven build command  mvn clean install
2.Make sure mysql is running on port 3306 of local machine and make sure to change the mysql password in application.properties of auth and blog service is password is not empty
3.First run eureka server , jar location is eurekaserver\target\eurekaserver-0.0.1-SNAPSHOT.jar , run as java -jar eurekaserver-0.0.1-SNAPSHOT.jar 
4.Run Blogservice, authservice and gateway service similarly as 
   jar location for blog service is blogservice\target\blogservice-0.0.1-SNAPSHOT.jar , run as java -jar blogservice-0.0.1-SNAPSHOT.jar 
  NOTE(if you want multiple instances of a service to run pass different post in environment variable for each run, it will override the one in application.properties)
  follow similar steps for auth and gateway service
5.After run is successful check the eureka ui on http://localhost:8761  , verify all the services with desired instances are running
6.Gateway url would be at http://localhost:8081
7.Api docs
   Docs are avaiable in the form of swagger ui at
   1.
   http://localhost:8081/docs/auth/swagger-ui.html for auth service, fist register a user using the register endpoint token is sent in response , keep it for further api access
   Also there is login endpoint to regain token for regustered user
   2.The docs for crud operation of blog service is at 
    http://localhost:8081/docs/auth/swagger-ui.html
    
    
    
   
   
