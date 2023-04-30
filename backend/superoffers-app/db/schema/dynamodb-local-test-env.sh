#!/bin/bash

# Running dynamodb locally for testing using docker
#
# Usage: 
#
# 1. On terminal one, run: 
#
# docker run -p 8000:8000 amazon/dynamodb-local
#
# 2. On terminal two, run:
#
# ./dynamodb-local-test-env.sh --aws_region=us-east-1
#
# 3. On terminal three, run:
#
# cd backend/superoffers
# mvn clean package

for arg in "$@"
do
    case $arg in
        --aws_region=*)
        AWS_REGION="${arg#*=}"
        shift # past argument=value
        ;;
        *)
        # unknown option
        ;;
    esac
done

export AWS_REGION=$AWS_REGION

aws dynamodb create-table --cli-input-json file://day-offers.json --endpoint-url http://localhost:8000
