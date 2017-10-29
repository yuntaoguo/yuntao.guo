FROM ibmjava:8
RUN mkdir /var/app
WORKDIR /var/app/
ADD ./build/libs /var/app/libs
EXPOSE 8080
CMD ["java","-jar","libs/app-1.0-SNAPSHOT-shadow.jar"]