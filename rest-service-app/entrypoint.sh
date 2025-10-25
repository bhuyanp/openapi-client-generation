
echo "JDK Version"
java -version

echo "Extracting Boot jar"
java -Djarmode=tools -jar  app.jar extract --force


echo "Starting the application"
java -jar app/app.jar --spring.config.location=classpath:/application.yaml
