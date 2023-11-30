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
**Note:** Each microservice has its own application load balancer \
## UNFINISHED
### Decryption Service Over Satellite
https://github.com/jacobnguyen-cs/abs-smart-buoy/blob/f2da6becd3c86c476988b15fad14c02cacf3df2c/decryption-service/run.py#L27-L46
In this code snippet from the run.py file in the decryption-service directory, the ST2100 message is first decoded, decrypted, and then routed to the proper microservice. When testing the messages coming from the IGWS2 Gateway, the team concluded that this is the proper way to initially check if the "from-mobile" message is a system response or a data point sent by the Raspberry Pi. It should also be noted that the encryption/decryption service is using symmetric key encryption, as the buoy will be operating remotely with no proper way to use asymmetric key encryption.
