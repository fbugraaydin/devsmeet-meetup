## DevsMeet Meetup

![Meetup Flow](meetupflow.png?raw=true)

Application Url: https://devsmeet-meetup.herokuapp.com/

### Dockerize

There are two types of dockerize:
- Dockerize via git located "dockerizeviagit" folder
- Dockerize with jar.

To build image:
```
docker build -t devsmeet-meetup .
```

To run image on a container:
```
docker run -p 8080:8080 --name devsmeet-meetup-server devsmeet-meetup
```

Note: Postman collections available in root directory with name: **Devs Meet Meetup.postman_collection.json**


## Licence

Developed by © [Fuat Buğra AYDIN](https://www.linkedin.com/in/fuatbugraaydin/)