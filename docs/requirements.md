# Super Offers
## _Requirements_

This is an requirements document of Super Offers project.

## Features

What are the features required?

####  Feature: Manage day offers
 
```
 Story: Create a day offer
 In order to manage the characteristics of a day offer
 As the Supermarket Manager
 I want to create a day offer
 Acceptance Criteria
  - the form must contains at least the following input fields: product name, price, quantity available, product unit, offer expiration date, supermarket name.
  - the system must to store the "offer id" (which is unique identifier) and "user id" (who created the offer, that is, the supermarket manager user logged in the application).
```
 
```
 Story: Edit the day offer
 In order to manage the characteristics of a day offer
 As the Supermarket Manager
 I want to edit a day offer
 Acceptance Criteria
  - the form must contains at least the following input fields: product name, price, quantity available, product unit, offer expiration date, supermarket name.
```
 
```
 Story: Remove the day offer
 In order to no manage a day offer register anymore
 As the Supermarket Manager
 I want to remove a day offer register
 Acceptance Criteria
  - _description of condition or non-functional requirement, for example_
```
 
```
 Story: List all day offers
 In order to see which day offers needs to be managed
 As the Supermarket Manager
 I want to list the day offers stored
 Acceptance Criteria
  - the system should retrieve only all offers created by respective supermarket manager, that is, given the "user id".
```

####  Feature: View day offers
 
```
 Story: View day offers by product
 In order to find the best prices of a product offered by the supermarkets
 As the Consumer
 I want to view the day offers by specific product
 Acceptance Criteria
  - Given a view day offers form, when the user types a parcial product name, then the system will show the day offers sorted by minor price.
```
 
```
 Story: View day offers by supermarket
 In order to find the best prices of a products offered by the supermarket
 As the Consumer
 I want to view the day offers by specific supermarket
 Acceptance Criteria
  - Given a view day offers form, when the user types a parcial supermarket name, then the system will show the day offers sorted by minor price.
```

## References

Project Artifacts

| FILE |  LINK |
| ------ | ------ |
| Architecture | [architecture.png](https://github.com/dancodingbr/superoffers/tree/main/docs/architecture) |
| CI/CD Pipeline | [ci-cd-pipeline.png](https://github.com/dancodingbr/superoffers/tree/main/docs/devops) |


