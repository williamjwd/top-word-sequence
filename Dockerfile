FROM openjdk:19
ADD target/top-word-sequence-0.0.1-SNAPSHOT.jar /top-word-sequence/top-word-sequence-0.0.1-SNAPSHOT.jar
ADD src/test/resources/moby-dick.txt /top-word-sequence/resources/moby-dick.txt
WORKDIR /top-word-sequence
ENTRYPOINT ["java","-jar","top-word-sequence-0.0.1-SNAPSHOT.jar"]
CMD ["/top-word-sequence/resources/moby-dick.txt"]