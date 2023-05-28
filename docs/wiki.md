# Super Offers

Welcome to Super Offers's wiki.

# Table of Contents

* [Development Notes](#development-notes)

## <a name="development-notes"></a>Development Notes

### Check List

| Label  | Description  |
|---|---|
| :pushpin: | not started  |
| :clock4: | doing  |
| :white_check_mark: | done  |

**> Developing backend lambda functions using SAM CLI (p.s. always remember to activate/deactivate access key admin user on aws console)**
- Installing and configuring [1]
:white_check_mark: -- VS Code 
:white_check_mark: -- AWS CLI
:white_check_mark: -- SAM CLI
:white_check_mark: -- Creating hello serverless app
:white_check_mark: -- Configuring aws credentials 
:white_check_mark: -- Running app locally
:white_check_mark: -- Creating superoffers serverless app [8] 
- Lambda Functions Implementation [4]
:white_check_mark: -- Generating sample event payload [5][7]
:white_check_mark: -- Writing Tests   [9][10][11]
:white_check_mark: -- Integrating with DynamoDB    [12]
:white_check_mark: -- Run DynamoDB locally    [12]
- Core Package Implementation   [13][14][15]
:white_check_mark: -- Use Cases, Entities
:white_check_mark: -- Writing Tests
... _(more subtasks not identified yet)_ ...

:pushpin: **> Developing frontend tier**
- Installing and configuring
-- VS Code
-- Empty angular project
- Core Package Implementation
-- Use Cases, Entities
-- Writing Tests (E2E, Integration and Unit Tests)
... _(more subtasks not identified yet)_ ...

:pushpin: **> Building on AWS Cloud**
- Architecture
-- CloudFront
-- Cognito
-- API Gateway
-- Lambda
--- IAM roles
-- DynamoDB
-- DAX
- CI/CD
-- S3
--- Get source code from Github and store backend zip package to S3
--- Get source code from Github and store frontend package to S3
-- CodeBuild (CodePipeline)
-- CloudFormation
... _(more subtasks not identified yet)_ ...


#### References
[1] https://docs.aws.amazon.com/serverless-application-model/index.html
[2] https://docs.aws.amazon.com/toolkit-for-jetbrains/index.html
[3] https://www.youtube.com/watch?v=KvBFFDYaqSM
[4] https://aws.amazon.com/blogs/opensource/testing-aws-lambda-functions-written-in-java/
[5] https://docs.aws.amazon.com/lambda/latest/dg/services-apigateway.html#apigateway-example-event
[6] https://catalog.workshops.aws/complete-aws-sam/en-US
[7] https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-using-generate-event.html
[8] https://github.com/aws/serverless-application-model/blob/master/versions/2016-10-31.md
[9] https://docs.aws.amazon.com/lambda/latest/dg/java-context.html
[10] https://github.com/google/gson
[11] https://javadoc.io/static/com.amazonaws/aws-lambda-java-events/3.10.0/com/amazonaws/services/lambda/runtime/events/APIGatewayProxyResponseEvent.html
[12] https://github.com/aws-samples/aws-sam-java-rest
[13] https://javadoc.io/doc/org.mockito/mockito-core/latest/org/mockito/Mockito.html
[14] https://docs.aws.amazon.com/amazondynamodb/latest/developerguide/Introduction.html
[15] https://docs.aws.amazon.com/sdk-for-java/latest/developer-guide/home.html


###  Useful Commands

**# aws cli version**
```sh
aws --version
which aws
```

**# aws sam cli version**
```sh
sam --version
which sam
```

**# create the app**

1. Download a sample application
```sh
sam init
```
2. Build your application
```sh
cd sam-app
sam build
```
3. Deploy your application
```sh
sam deploy --guided
```

**# host API locally**
```sh
sam local start-api
```

**# invoke lambda function locally (example)**
```sh
curl http://127.0.0.1:3000/hello
```

**# validating sam template files (template.yml)**
```sh
sam validate --region us-east-1
```

**# fetching logs by aws cloudformation stack (example)**
```sh
sam logs -n HelloWorldFunction --stack-name sam-app
```

**# integrating with automated tests (integration tests)**

1. Start local lamba endpoint (emulates aws lambda)
```sh
sam local start-lambda
```
2. See: https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-using-automated-tests.html

**# generating sample event payloads**

-- see: https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-sam-cli-using-generate-event.html

-- get event example
```sh
sam local generate-event apigateway http-api-proxy --method GET --path day-offers/10
```

**# list all configuration data (including credentials)**
```sh
aws configure list
```

**# list all profiles**
```sh
aws configure list-profiles
```

**# running unit tests (each module)**
```sh
mvn test
```

**# running unit and integration tests (each module)**
```sh
mvn verify
```

**# running all together (all modules)**
```sh
mvn package
```

**# testing and debug the project on vs code**

1. Install the Extension Pack for Java: https://code.visualstudio.com/docs/java/extensions

2. Testing lambda functions (handlers)
```sh
cd backend/superoffers-app
mvn test
```
3. Running integration and unit tests of core project
3.1 On VSCode, click on "Testing" panel option.
3.2 Click on "Run Test" button, of "core" project 

**# changing AWS_REGION environment variable**
```sh
export AWS_REGION=us-east-1
```

**# show current AWS_REGION environment variable defined**
```sh
echo $AWS_REGION
```

**# running dynamodb locally using docker**

1. Define the aws region:
```sh
export AWS_REGION=us-east-1
```

2. Run docker command
```sh
docker run -p 8000:8000 amazon/dynamodb-local
```

**# create a table on dynamodb**
```sh
aws dynamodb create-table --cli-input-json file://table.json --endpoint-url http://localhost:8000
```

**# list tables on dynamodb**
```sh
aws dynamodb list-tables --endpoint-url http://localhost:8000
```

**# view all items on a dynamodb table using query**
```sh
aws dynamodb query \
    --table-name my-table \
    --select ALL_ATTRIBUTES \
    --key-condition-expression "Artist = :v1" \
    --output json
```

**# view all items on a dynamodb table using scan**
```sh
aws dynamodb scan --endpoint-url http://localhost:8000 --table-name Users
```

**# count total items on a dynamodb table**
```sh
aws dynamodb describe-table --endpoint-url http://localhost:8000 --table-name <table-name> --query 'Table.ItemCount'
```

**# running local dynamodb database of superoffers sam project**

1. Start docker

2. Open a terminal one and run local dynamodb
```sh
docker run -p 8000:8000 amazon/dynamodb-local
```

3. Open a terminal two to create superoffers database
```sh
cd "superoffers/backend/superoffers-app/db/schema"
./dynamodb-local-test-env.sh --aws_region=us-east-1
```

**# examples of call on aws sam local api using curl**

-- get
```sh
curl -X GET http://127.0.0.1:3000/day-offers
curl -X GET http://127.0.0.1:3000/day-offers/1
curl -X GET "http://127.0.0.1:3000/day-offers?productName=test"
```

-- delete 
```sh
curl -X DELETE http://127.0.0.1:3000/day-offers/1
```

-- post
```sh
curl -X POST \
  -H "Content-Type: application/json" \
  -d '{"message": "create day offer"}' \
  http://127.0.0.1:3000/day-offers
```

-- put
```sh
curl -X PUT \                                 
  -H "Content-Type: application/json" \
  -d '{"message": "update day offer"}' \
  http://127.0.0.1:3000/day-offers
```
