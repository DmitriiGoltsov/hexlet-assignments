FROM gradle:7.4.0-jdk17

WORKDIR /java-web-ru/deploy .

COPY / .

RUN gradle installDist

CMD ./build/install/deploy/bin/deploy