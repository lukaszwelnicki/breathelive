[![CircleCI](https://circleci.com/gh/lukaszwelnicki/breathelive/tree/master.svg?style=svg)](https://circleci.com/gh/lukaszwelnicki/breathelive/tree/master) [![codecov](https://codecov.io/gh/lukaszwelnicki/breathelive/branch/master/graph/badge.svg)](https://codecov.io/gh/lukaszwelnicki/breathelive)

# Breathelive

This is a fully reactive web server written in Kotlin. The main purpose of it is to serve air pollution level information
 based on the city or geolocation. It can also send emails with air pollution level data.
 
 This application can be reached using the following url: https://breathelive.herokuapp.com
 
 # Endpoints
 
 Fetch pollution details by city: GET /api/pollution/city/{city}
  
 https://breathelive.herokuapp.com/api/pollution/city/shanghai
 
 
 Fetch pollution details by geolocation: GET /api/pollution/geo?lat={latitude}&lon={longitude}
  
 https://breathelive.herokuapp.com/api/pollution/geo?lat=20.0&lon=20.0
 
 
 Subscribe user to receive emails: POST /api/subscribe/user
 
 example request body;
 {
 	"email": "name.surname@mail.com",
 	"firstName": "Name",
 	"geolocation": {
 		"latitude": 20.0025,
 		"longitude": 20.0055
 	},
 	"subscribes": true,
 	"notificationTimes": [
 		"06:30",
 		"14:30"
 	]
 }
 
 # Tests execution
 
 Testcontainers framework was used to provide mongoDB Docker container for the purpose of integration testing.
 
# CI/CD

CI/CD has been established using CircleCI. Creating an Docker image out of the Springboot app and pushing it to 
Docker-Hub has been integrated into CI cycle. App deployment to Heroku has also been included.

# Technologies used

- Spring Boot 2.0
- Spring Webflux
- Project Reactor
- SpockFramework
- Testcontainers
- MongoDB
- Kotlin
- Docker / Docker Compose
- Gradle
- CircleCI
- JaCoCo
- Heroku

