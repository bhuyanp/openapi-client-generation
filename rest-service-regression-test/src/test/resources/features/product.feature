@Product
Feature: Product Service
  Users should be able to add, update, get and and delete products using this feature

  @AddProduct
  Scenario: Add new product should return 201
    When user adds new product with title 'Test Product New', type ELECTRONICS and price 45.45
    Then response status is 201
    And product id is not blank
    And product id is UUID
    And product title is 'Test Product New'
    And product type is ELECTRONICS
    And product price is 45.45

  @AddProduct @Negative
  Scenario: Add new product should fail with 400
    When user adds new product with title "", type ELECTRONICS and price 12.12
    Then response status is 400
    When user adds new product with title "Prod A", type ELECTRONICS and price 0
    Then response status is 400

  @GetProduct
  Scenario: Get product by existing product id should return valid product
    Given a product exists with title 'Test Product New', type ELECTRONICS and price 45.47
    When user fetches existing product
    Then response status is 200
    And product id is not blank
    And product id is UUID
    And product title is 'Test Product New'
    And product type is ELECTRONICS
    And product price is 45.47

  @GetProduct @Negative
  Scenario: Get product by non existing product id should fail with 404
    When user fetches a non existing product
    Then response status is 404


  @DeleteProduct
  Scenario: Delete existing product id should return 204
    Given a product exists with title 'Test Product New', type ELECTRONICS and price 45.47
    When user deletes an existing product
    Then response status is 204

  @DeleteProduct @Negative
  Scenario: Delete non existing product id should fail with 404
    When user deletes a non existing product
    Then response status is 404
