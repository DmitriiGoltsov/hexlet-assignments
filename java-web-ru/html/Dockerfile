FROM gradle:7.4.0-jdk17

WORKDIR /java-web-ru/html .

COPY / .

RUN gradle installDist

CMD ./build/install/html/bin/html