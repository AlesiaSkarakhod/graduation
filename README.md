# Graduation
Design and implement a REST API using Hibernate/Spring/SpringMVC (or Spring-Boot) without frontend.

The task is:

Build a voting system for deciding where to have lunch.

 - 2 types of users: admin and regular users
 - Admin can input a restaurant and it's lunch menu of the day (2-5 items usually, just a dish name and price)
 - Menu changes each day (admins do the updates)
 - Users can vote on which restaurant they want to have lunch at
 - Only one vote counted per user
 - If user votes again the same day:
 -If it is before 11:00 we asume that he changed his mind.
 - If it is after 11:00 then it is too late, vote can't be changed
Each restaurant provides new menu each day.

As a result, provide a link to github repository. It should contain the code, README.md with API documentation and couple curl commands to test it.

##User

*Get all*
 - curl -s http://localhost:8080/graduation/rest/admin/users --user admin@gmail.com:admin
 
*Get by id*
 - curl -s http://localhost:8080/graduation/rest/admin/users/100001 --user admin@gmail.com:admin
 
*Register*
  - curl -s -i -X POST -d '{"name":"User3","email":"user3@gmail.com","password":"test-password"}' -H 'Content-Type:application/json;charset=UTF-8' http://localhost:8080/restaurant/rest/profile/register

*Get Profile*
 - curl -s http://localhost:8080/graduation/rest/profile --user user3@gmail.com:test-password

*Delete profile*  	
 - curl -s -X DELETE http://localhost:8080/restaurant/rest/profile --user user3@gmail.com:test-password
 
### Restaurant
*Get by id*
- curl -s http://localhost:8080/graduation/rest/restaurant/100003 --user admin@gmail.com:admin
 
*Get all*
 - curl -s http://localhost:8080/graduation/rest/restaurant --user admin@gmail.com:admin
  
*Create*	
 - URL: http://localhost:8080/graduation/rest/restaurant
 - Parameters: {"id": null, "name": "One_Two_Pizza", "menu": null, "vote": null}
 
*Delete*
 - curl -s -X DELETE http://localhost:8080/graduation/rest/restaurants/100003 --user admin@gmail.com:admin
 
*Update*
 - URL: http://localhost:8080/graduation/rest/restaurant/100003
 - Parameters: {"id": 100003, "name": "One_Two_Pizza", "menu": null, "vote": null}

### Menu
*Get by id* 
- curl -s http://localhost:8080/graduation/rest/restaurant/100003/menu/100005 --user admin@gmail.com:admin

*Get all*
- curl -s http://localhost:8080/graduation/rest/restaurant/100003/menu --user admin@gmail.com:admin

*Get today’s menu*
- curl -s http://localhost:8080/graduation/rest/restaurant/100003/menu/currentDate --user admin@gmail.com:admin

*Create* 	
- URL: http://localhost:8080/graduation/rest/restaurant/100003/menu
- Parameters: {"id": null, "name": "Sunday", "dish": null, "restaurant": null, "date": "2020-05-11"}

*Update* 	
- URL: http://localhost:8080/graduation/rest/restaurant/100003/menu/100005
- Parameters: {"id": 100003, "name": "Monday", "dish": null, "restaurant": null, "date":"2020-05-11"}

*Delete by id*
- curl -s -X DELETE http://localhost:8080/graduation/rest/restaurant/100003/menu/100005 --user admin@gmail.com:admin

### Dish
*Get by id*
 - curl -s http://localhost:8080/graduation/rest/restaurant/100003/menu/100005/dish/100006 --user admin@gmail.com:admin

*Get all*
- curl -s http://localhost:8080/graduation/rest/restaurant/100003/menu/100005/dish --user admin@gmail.com:admin

*Create*	
- URL: http://localhost:8080/graduation/rest/restaurant/100003/menu/100005/dish
- Parameters: {"id": null, "name": "Lunch", "price": 23.00}

*Update* 	
- URL: http://localhost:8080/graduation/rest/restaurant/100003/menu/100005/dish/100006
- Parameters: {"id": 100006, "name": "Lunch", "price": 45.00}

*Delete by id*
- curl -s -X DELETE http://localhost:8080/graduation/rest/restaurant/100003/menu/100005/dish/100006 --user admin@gmail.com:admin

### Vote
*Get user votes*
- curl -s http://localhost:8080/graduation/rest/votes/user/100000 --user admin@gmail.com:admin

*Get restaurant votes*
-- curl -s -X POST http://localhost:8080/graduation/rest/votes/restaurant/100003 --user admin@gmail.com:admin

*Delete by id*
- curl -s -X DELETE http://localhost:8080/graduation/rest/vote/100007 --user admin@gmail.com:admin
