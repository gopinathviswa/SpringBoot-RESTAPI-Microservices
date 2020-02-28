# SpringBoot-RESTAPI-Microservices
Developed Microservice based Spring Boot REST API for following entity
1. Employees
2. EmployeeCards
3. Branches
4. Products
5. Towns

Developed Microservices, so that they can be generated on the database. You will have to implement a functionality for importing data from JSON and XML formats and exporting data in CSV format.

I have used MYSQL as my Database, Docker for containerization.

# Steps to Install Docker on Ubuntu machine
# install docker and docker-compose for ubuntu bionic
sudo apt update

sudo apt install apt-transport-https ca-certificates curl software-properties-common

curl -fsSL https://download.docker.com/linux/ubuntu/gpg | sudo apt-key add -

sudo add-apt-repository "deb [arch=amd64] https://download.docker.com/linux/ubuntu bionic stable"

sudo apt update

sudo apt install docker-ce

sudo apt install docker-compose

# install jdk 8
sudo apt-get install openjdk-8-jdk

# List of Microservice
1. Naming Server: Eureka discovery service allows micro services to find and communicate with each other. 
2. Gateway Service: Zuul acts as an API gateway or Edge service. It receives all the requests coming from the UI and then delegates the requests to internal microservices.
3. Emp Service
4. Empcard Service
5. Branch Service
6. Product Service
7. Town Service
8. Report Service

# Steps to execute the Project
1. Clone the git repo
2. Build the microservice using mvn cmd
3. Build the docker image of each microservice using following command

docker build -t gopinathviswa/service name .

Before executing the command move to respective microservice folder
4. Now execute the docker compose file using 

docker-compose up -d

Before executing the command come back to home folder of the project

