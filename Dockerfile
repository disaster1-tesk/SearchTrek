FROM openjdk:8
MAINTAINER SeachTrek
LABEL version="v1" \
      author="disaster" \
      description="this is search trek"

ENV PORT=8080
ENV PROJECTNAME="SearchTrek.jar"
ENV PROFILES="default"

ADD /target/*.jar /$PROJECTNAME

ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/$PROJECTNAME --spring.profiles.active=$PROFILES"]
EXPOSE $PORT