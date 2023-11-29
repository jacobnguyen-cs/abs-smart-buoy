# abs-smart-buoy
### Setting Up The ADS-B Image
1. First the required packages must be installed so run these commands. \
```
cd adsb-service
cd app
npm insall
```
2. After the required packages have been installed, build the Docker image. \
```
cd ..
docker build -t image-name .
```
3. The Docker image will then need to be pushed to AWS Elastic Container Registry. Run these commands within the AWS CLI to push the Docker image to ECR. \
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```
### Setting Up The Air Temperature Image
1. First the required packages must be installed so run these commands. \
```
cd air-temperature-service
cd app
npm insall
```
2. After the required packages have been installed, build the Docker image. \
```
cd ..
docker build -t image-name .
```
3. The Docker image will then need to be pushed to AWS Elastic Container Registry. Run these commands within the AWS CLI to push the Docker image to ECR. \
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```
### Setting Up The Water Temperature Image
1. First the required packages must be installed so run these commands. \
```
cd water-temperature-service
cd app
npm insall
```
2. After the required packages have been installed, build the Docker image. \
```
cd ..
docker build -t image-name .
```
3. The Docker image will then need to be pushed to AWS Elastic Container Registry. Run these commands within the AWS CLI to push the Docker image to ECR. \
```
aws ecr get-login-password --region region | docker login --username AWS --password-stdin aws_account_id.dkr.ecr.region.amazonaws.com
docker tag image-name:latest aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
docker push aws_account_id.dkr.ecr.region.amazonaws.com/repository-name
```
