Feature: Add smartphone to cart

  Scenario: Select a no contract smartphone and add to cart
    Given I am on the T-Mobile homepage
    And I accept the cookies policy
    When I navigate to the devices section
    And I select "Bez abonamentu"
    And I click on the first product in the list
    And I add the product to the cart
    Then I should see the product in the cart with correct prices
    When I am on the T-Mobile homepage
    Then I should see a badge on the basket icon with number 1
