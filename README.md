# TeamDattaP1
Team DATTA Project 1

React Frontend - Java Backend

Community Chest is a social media app where registered users may post small, 240 character, messages. Users are able set the theme of the website by toggling the dark mode setting, register, and login in addition to making new posts. The homepage is a two column spread of posts, sorted by recency, and is decorated with a Windows 95 theme. The frontend of this project is build in React TypeScript and CSS. With React, we were able to utilize React95 and Styled Components libraries to quickly generate a cohesive themed UI. This project connected to our REST API backend that we created in Java using Javalin and JDBC.

The Java backend includes Controllers that route incoming HTTP requests to the correct methods in the Service layer. Here, we execute business logic for Account authentication, as well as Post validation. The service methods interact with the Repository layer to send/receive data from our database. There is a ConnectionUtil Class, using JDBC, that allows us to send SQL queries to our Postgresql Database. Queries are specified in the Repository layer, and are sent via the connection. Any SQL database can be used with this project, but the environment variables ($datta_url, $datta_username, $datta_password) should be set on the machine before attempting to connect. After the Service layer executes all logic and recieves data from the database, the Controller layer sends a response back to the frontend.


# Endpoints
The urls shown should be affixed to either the local host or the online host, depending on whether you are running a local version of the back-end or not.


# Authentication
Responsible for login, logout, registration. Basically, covers everything you would see from the login page in the front end.

POST /login
	- Requires body to contain JSON with the login object (email and password).
	- Returns JSON of full user object/information (password excluded) if login is successful.
	- Returns JSON of an exception detailing the issue (bad email/password) if login is unsuccessful.

POST /logout
	- Requires nothing (if the user is recognized by the system, they are removed; otherwise they are not logged into the back-end to begin with)
	- Returns nothing (other than a success status)

POST /register
	- Requires body to contain JSON with the Register-Request object (user's Email and Password)
	- Returns JSON of the created user (password excluded)


# Posts
Responsible for all information on forum posts.

GET /post
	- Returns all posts and comments.
  
POST /post
  	- Requires body to contain JSON with the post object (userID and content)
	- Returns JSON of the postid from the created post
