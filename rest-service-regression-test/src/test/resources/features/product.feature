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



  @DeleteProduct @Negative
  Scenario: Delete non existing product id should fail with 404
    When user deletes a non existing product
    Then response status is 404


#  @AddProduct @Negative
#  Scenario: Add new product with without title or price should return 200
#    When user adds new product with title 'test' and price 45.45
#    Then response status is 400
#    And error message contains 'Bad Request'
#    When user adds new product with title 'Test' and price -1
#    Then response status is 400
#    And error message contains 'Bad Request'
#    When user adds new product with title '' and price -1
#    Then response status is 400
#    And error message contains 'Bad Request'
#
#  @UpdateProduct
#  Scenario: Update an existing product should return 201
#    When user updates product with title 'Test Product New' and price 45.45
#    Then response status is 201
#    And product title is 'Test Product New'
#    And product price is 45.45
#
#  @UpdateProduct @Negative
#  Scenario: Update a non existing product should return 404
#    When user updates product with title 'Test Product New' and price 45.45
#    Then response status is 201
#    And product title is 'Test Product New'
#    And product price is 45.45
