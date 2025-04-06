Feature: Covered Call BEP

  Covered Call BEP

  @BEP
    @CoveredCall
    @WithCommission
  Scenario Outline: Validate Covered Call BEP With Commission
    When I Call Covered Call Strategy API With BestLimitPrice Price Type , BestLimit Symbol Basis And With Commission
    Then The Covered Call Strategy BEP Of <baseSymbol> With Commission Is Valid

    Examples:
      | baseSymbol |
          #   |  ETF       |
      | رویین      |
      | توان       |
      | اهرم       |
      | خودران     |
      | جهش        |
      | شتاب       |
      | آساس       |
      | موج        |
      | اطلس       |
      | نارنج اهرم |
      | تیام       |
      | پادا       |
      | ثمین       |
      | پتروآبان   |
      | بیدار      |
      | پناه       |
          #   |  Bourse    |
      | خساپا  |
      | خودرو  |
      | شستا   |
      | ذوب    |
      | وبصادق |
      | وبملت  |
      | وتجارت |
      | فملی   |
      | شپنا   |
      | خگستر  |
      | خبهمن  |
      | وبصادر |
      | فولاد  |
          #   |Fara Bourse |
      | کرمان  |
      | سامان  |
      | کوثر   |
      | خاور   |
      | خپارس  |
      | بساما  |
      | کرومیت |
      | فزر    |
      | وتعاون |

  @BEP
    @CoveredCall
    @WithoutCommission
  Scenario Outline: Validate Covered Call BEP Without Commission
    When I Call Covered Call Strategy API With BestLimitPrice Price Type , BestLimit Symbol Basis And Without Commission
    Then The Covered Call Strategy BEP Of <baseSymbol> Without Commission Is Valid

    Examples:
      | baseSymbol |
          #   |  ETF       |
      | رویین      |
      | توان       |
      | اهرم       |
      | خودران     |
      | جهش        |
      | شتاب       |
      | آساس       |
      | موج        |
      | اطلس       |
      | نارنج اهرم |
      | تیام       |
      | پادا       |
      | ثمین       |
      | پتروآبان   |
      | بیدار      |
      | پناه       |
          #   |  Bourse    |
      | خساپا  |
      | خودرو  |
      | شستا   |
      | ذوب    |
      | وبصادق |
      | وبملت  |
      | وتجارت |
      | فملی   |
      | شپنا   |
      | خگستر  |
      | خبهمن  |
      | وبصادر |
      | فولاد  |
          #   |Fara Bourse |
      | کرمان  |
      | سامان  |
      | کوثر   |
      | خاور   |
      | خپارس  |
      | بساما  |
      | کرومیت |
      | فزر    |
      | وتعاون |
