# Super Offers

This project shows an example of a web application developed on a serverless scenario, using the AWS Cloud ecosystem for this purpose.

The sample application consists of a Supermarket Offers Viewer. As from day offers launched by the supermarket managers, the consumers will be able to find the best product prices which it's interesting to buy.

## Description

The main features that's application is going to provide are:

- The Supermarket Manager User stores the day offers of the products.

- The Consumer User visualizes the day offers ranking, launched by all envolved supermarkets. Furthermore, the system shows the available quantity and expiration date of each offer.

## Technologies

#### AWS Serverless

- Presentation: CloudFront, S3
- Logic: API Gateway, Lambda
- Data Store: DynamoDB

## Architecture

![Architecture](https://github.com/dancodingbr/superoffers/blob/main/docs/architecture/architecture.png)


#### Notes

- The client communicates with the web application hosted in AWS Cloud, via a HTTPS protocol to send requests and receive responses.

- When the client access the application, CloudFront distributes the static content, that is stored in S3 bucket, and it'll provide low-latency access.

- If the request, received by API Gateway, needs to pass to an authentication process (like a login), then API Gateway forwards the request to Cognito.

- Checking the project requirements, it's supposed that only a supermarket manager should be able to create, edit or delete the offers. So, in this case Cognito can creates a new user or retrieve existing user credentials, during the sign-in to the application, using as approach an User Pool and the JWT Authorizer.

- When the user authentication is successful, Cognito will deliver a token access, and so, the client will be able to make the API calls.

- On the other hand, supermarket customers can view day offers as an anonymous user, that is, it's not necessary to them to log in the system. So, the request can be passed away to API Gateway directly.

- In API Gateway, it'll be used the HTTP API product, to create the RESTFul API and to integrate with Lambda functions. 

- Each lambda function will be responsible for compute the requests on backend tier. The Offers service will be composed by basic CRUD operations over offers records (which the supermarket manager should control) and to handle "View Day Offers" resources to supermarket customers. 

- An IAM role is attached to each lambda function, providing only the necessary permissions for the Offers service tasks that will be executed, like read or write data in the DynamoDB table.

- DynamoDb was choosed to store and retrieve the offer records. In this architeture proposed, this choice was made for study motivations, but a relational database solution could be applied as well, like RDS or Aurora.

- Optionally, DAX can be enabled to improve the application performance at scale, caching the day offers results if it will occurs an overhead of read requests made by the supermarket customers.

## CI/CD Pipeline

![CI/CD Pipeline](https://github.com/dancodingbr/superoffers/blob/main/docs/devops/ci-cd-pipeline.png)

#### Notes

- In their local machines environment, developers will make code changes locally in the project using SAM CLI, mainly for purpose of test lambda functions, to avoid of incurring charges.

- Then, developers will commit changes to the Github's project repository. At this point, a Github webhook is invoking to start automatically the CI/CD process and uploads the source code package to a S3 bucket.

- From this source code package, CodeBuild makes the build and tests, both backend (lambda functions) and frontend codes. If the tests will be passed successfully, CloudFormation will create or update the application stack in production environment (S3 for Static Content, API Gateway, Lambda functions, DynamoDB database, and so on).

## Installation

_installation_

## Contributing

You can contribute in several ways to this project, such as: reporting issues like bugs, feature requests; review source code, documentation; make pull requests, fixing bugs, etc.

## Documentation

The project documentation and artifacts can be found [here](https://github.com/dancodingbr/superoffers/tree/main/docs).

## License

MIT

