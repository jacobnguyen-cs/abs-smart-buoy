# abs-smart-buoy
### Setting Up the ADS-B Image
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
3. Upload the Docker image to AWS ECR.
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```
### Setting Up the Air Temperature Image
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
3. Upload the Docker image to AWS ECR.
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```
### Setting Up the Water Temperature Image
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
### Setting Up the Decryption Image for Over-the-Internet
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
