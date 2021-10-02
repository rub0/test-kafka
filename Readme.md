#Nexthink test instructions

####to execute this test you should do the following

- navigate to project's root directory
- execute <code>docker-compose up</code>. This will start kafka in port 29092 an zookeeper
- execute <code>./gradlew build</code>. This will generate JAR file under folder app/build/libs
- execute <code>java -jar app.jar -f < input file > -t < topic ></code>. The file will be read and results will be 
  stored in the topic provided
- to see the results use <code>./kafka-console-consumer --topic < topic > --bootstrap-server localhost:29092 
  --from-beginning</code>