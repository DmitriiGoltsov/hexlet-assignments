FROM gradle:7.4.0-jdk17

WORKDIR ./deploy

COPY /hexlet-assignments/java-web-ru/deploy/ .

RUN gradle installDist

CMD ./java-web-ru/deploy

# CMD ./build/install/deploy/bin/deploy