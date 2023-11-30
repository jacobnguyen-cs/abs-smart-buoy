# abs-smart-buoy
## Prerequisites
The services used on Amazon Web Services were all used under a _root user_ account which is **not** recommended and should instead be used as an **IAM user** account to ensure security.\
Before setting up the Docker images make sure of the following:
1. At least [Node.js v18.15.0](https://nodejs.org/en/download) is installed
2. At least [Python 3.11.3](https://www.python.org/downloads/) is installed
3. At least [Docker version 24.0.6](https://www.docker.com/get-started/) is installed

## Setting Up the Docker Images
### Setting Up the ADS-B Service Image
1. Install the required packages.
```
cd adsb-service
cd app
npm insall
```
2. Build the Docker image.
```
cd ..
docker build -t image-name .
```
3. Upload the Docker image to AWS ECR
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```

### Setting Up the Air Temperature Service Image
1. Install the required packages.
```
cd air-temperature-service
cd app
npm insall
```
2. Build the Docker image.
```
cd ..
docker build -t image-name .
```
3. Upload the Docker image to AWS ECR
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```

### Setting Up the Decryption Service Image
1. Build the Docker image.
```
cd decryption-service
docker build -t image-name .
```
2. Upload the Docker image to AWS ECR
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```

### Setting Up the Humidity Service Image
1. Install the required packages.
```
cd humidity-service
cd app
npm insall
```
2. Build the Docker image.
```
cd ..
docker build -t image-name .
```
3. Upload the Docker image to AWS ECR
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```

### Setting Up the Logging Service Image
1. Install the required packages.
```
cd logs-service
cd app
npm insall
```
2. Build the Docker image.
```
cd ..
docker build -t image-name .
```
3. Upload the Docker image to AWS ECR
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```

### Setting Up the Satellite Communication Service Image
1. Install the required packages.
```
cd satcomm-service
cd app
npm insall
```
2. Build the Docker image.
```
cd ..
docker build -t image-name .
```
3. Upload the Docker image to AWS ECR
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```

### Setting Up the Water Temperature Service Image
1. Install the required packages.
```
cd water-temperature-service
cd app
npm insall
```
2. Build the Docker image.
```
cd ..
docker build -t image-name .
```
3. Upload the Docker image to AWS ECR
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```

## Setting Up Services on AWS Elastic Container Services
Follow this guide to setup the application load balancer with AWS ECS Fargate: \
[https://www.youtube.com/watch?v=gVTdcR5bdUk](https://www.youtube.com/watch?v=gVTdcR5bdUk) \
**Note:** Each microservice has its own application load balancer
## UNFINISHED
### Decryption Service Over Satellite
https://github.com/jacobnguyen-cs/abs-smart-buoy/blob/f2da6becd3c86c476988b15fad14c02cacf3df2c/decryption-service/run.py#L27-L46
In this code snippet from the `run.py` file in the decryption-service directory, the ST2100 message is first decoded, decrypted, and then routed to the proper microservice. When testing the messages coming from the IGWS2, the team concluded that this is the proper way to initially check if the from-mobile message is a system response or a data point sent by the Raspberry Pi. From-mobile messages sent by AT commands were contained in the attribute of "RawPaylod". According to documenation provided by ORBCOMM, the attribute "RawPayload" is only used when from-mobile messages are sent by AT commands but the team noticed that the "RawPayload" attribute would be filled with random encoded messages. Whether they were of any use is undetermined. It should also be noted that the encryption/decryption service is using symmetric key encryption, as the buoy will be operating remotely with no proper way to use asymmetric key encryption.
### IGWS2 Data Flow
https://github.com/jacobnguyen-cs/abs-smart-buoy/blob/8291792f6bf41a1e74fc7851e072b17b78d35cc6/satcomm-service/src/IGWSRestClient.java#L567-L604
This code snippet is a function created to send all from-mobile messages sent by the ST2100 Modem no matter if the message is encrypted or not. This function is based on the `SubmitModemMessageSample()` function so the response should be handled accordingly. 
https://github.com/jacobnguyen-cs/abs-smart-buoy/blob/8291792f6bf41a1e74fc7851e072b17b78d35cc6/satcomm-service/src/IGWSRestClient.java#L450-L472
The only line changed from the sample code provided by ORBCOMM is line 467 which sends the message to the decryption service. Within the decryption service, the messages should be handled according to the type of message it is.
