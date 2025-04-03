Feature: Covered Call Strategy API Tests

  Scenario: Validate Covered Call BEP calculation for Bourse with Commission
    Given I am authenticated
    When I send a GET request to "https://kahkeshanapi.ramandtech.com/Strategies/v1/CoveredCall?PageSize=100&PageNumber=1&PriceType=BestLimitPrice&SymbolBasis=BestLimit&WithCommission=true"
    Then I verify the coveredCallBEP calculation for "bourse" with a tolerance of 10

  Scenario: Validate Covered Call BEP calculation for Bourse without Commission
    Given I am authenticated
    When I send a GET request to "https://kahkeshanapi.ramandtech.com/Strategies/v1/CoveredCall?PageSize=100&PageNumber=1&PriceType=BestLimitPrice&SymbolBasis=BestLimit&WithCommission=false"
    Then I verify the coveredCallBEP calculation for "bourse" with a tolerance of 10

  Scenario: Validate Covered Call BEP calculation for Farabourse
    Given I am authenticated
    When I send a GET request to "https://kahkeshanapi.ramandtech.com/Strategies/v1/CoveredCall?PageSize=100&PageNumber=1&PriceType=BestLimitPrice&SymbolBasis=BestLimit&WithCommission=true"
    Then I verify the coveredCallBEP calculation for "farabourse" with a tolerance of 10

  Scenario: Validate Covered Call BEP calculation for Farabourse without Commission
    Given I am authenticated
    When I send a GET request to "https://kahkeshanapi.ramandtech.com/Strategies/v1/CoveredCall?PageSize=100&PageNumber=1&PriceType=BestLimitPrice&SymbolBasis=BestLimit&WithCommission=false"
    Then I verify the coveredCallBEP calculation for "farabourse" with a tolerance of 10

  Scenario: Validate Covered Call BEP calculation for ETF with Commission
    Given I am authenticated
    When I send a GET request to "https://kahkeshanapi.ramandtech.com/Strategies/v1/CoveredCall?PageSize=100&PageNumber=1&PriceType=BestLimitPrice&SymbolBasis=BestLimit&WithCommission=true"
    Then I verify the coveredCallBEP calculation for "etf" with a tolerance of 10

  Scenario: Validate Covered Call BEP calculation for ETF without Commission
    Given I am authenticated
    When I send a GET request to "https://kahkeshanapi.ramandtech.com/Strategies/v1/CoveredCall?PageSize=100&PageNumber=1&PriceType=BestLimitPrice&SymbolBasis=BestLimit&WithCommission=false"
    Then I verify the coveredCallBEP calculation for "etf" with a tolerance of 10
