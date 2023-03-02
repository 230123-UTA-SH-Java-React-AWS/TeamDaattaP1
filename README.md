# TeamDattaP1
Team DATTA Project 1
Java Backend

Endpoints
The urls shown should be affixed to either the local host or the online host, depending on whether you are running a local version of the back-end or not.

Depending on your website's host URL (ex. http://localhost:8080 or on a website URL by a hosting service), add the website URL to the @CrossOrigin annotation in the controller files.


Authentication
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

POST /auth/forgot-password
	- Requires body to contain JSON with the Question-Request object (the email and password of the user, and the 3 security questions and the user's answer.
	- Returns text saying the "Password successfully updated" if successful.
	- Returns nothing (other than a bad-status code) if unsuccessful.


Posts
Responsible for all information on forum posts.

GET /post
	- Returns all posts and comments.
  
POST /post
  - Requires body to contain JSON with the post object (userID and content)
