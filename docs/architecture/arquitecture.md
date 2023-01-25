# Super Offers
## _Arquitecture_

This document describes some characteristics of the project architecture proposed. For now, it's an initial diagram, so changes (like fixes or improvements) could be done during the development lyfecycle.

![Architecture](https://github.com/dancodingbr/superoffers/tree/main/docs/architecture/architecture.png)


## Notes

- The client communitcates with the web application hosted in AWS Cloud, via a HTTPS protocol to send requests and receive responses.

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
