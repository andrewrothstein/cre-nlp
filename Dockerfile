FROM andrewrothstein/docker-scala
MAINTAINER Andrew Rothstein "andrew.rothstein@gmail.com"

ADD . /sbt-project
WORKDIR /sbt-project
RUN ["sbt", "assembly"]
CMD ["java", "-jar", "target/scala-2.10/cre-nlp.jar", "--esclustername", "elasticsearch", "--eshost", "elasticsearch"]