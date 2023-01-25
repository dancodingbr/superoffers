# Super Offers
## _CI/CD Pipeline_

This document describes some characteristics of the CI/CD pipeline proposed in this project. For now, it's an initial diagram, so changes (like fixes or improvements) could be done during the development lyfecycle.

![CI/CD Pipeline](https://github.com/dancodingbr/superoffers/tree/main/docs/architecture/ci-cd-pipeline.png)


## Notes

- In their local machines environment, developers will make code changes locally in the project using SAM CLI, mainly for purpose of test lambda functions, to avoid of incurring charges.

- Then, developers will commit changes to the Github's project repository. At this point, a Github webhook is invoking to start automatically the CI/CD process and uploads the source code package to a S3 bucket.

- From this source code package, CodeBuild makes the build and tests, both backend (lambda functions) and frontend codes. If the tests will be passed successfully, CloudFormation will create or update the application stack in production environment (S3 for Static Content, API Gateway, Lambda functions, DynamoDB database, and so on).


