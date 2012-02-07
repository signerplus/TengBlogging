1. install java 6, maven 3, mysql 5.5 and tomcat 6.0.35
2. settings for mysql
  2.1 create user 'demo' with password 'demo'
  2.2 create database 'demo' and assign all rights to the user 'demo'
  2.3 start mysql at localhost:3306
3. settings for tomcat
  3.1 start tomcat in 8080
  3.2 change installation address of tomcat folder in pom.xml, e.g.:
    for linux
      <tomcat.path>/home/jhan/java/apache-tomcat-6.0.35</tomcat.path>
    for windows
      <tomcat.path>D:\\java\\tomcat6\\</tomcat.path>
4. run maven build command 
   >mvn install cargo:redeploy
5. open browser and go to http://hostname:8080/blogging/