@Ui
Feature: Test - GOV.UK

  Scenario: Check Tax and MOT - Untaxed
    Given a user opens "https://www.gov.uk/check-mot-status"
    When a user enters registration "HW55DJU"
    Then a page is displayed with vehicle information: "TOYOTA" and "BLUE"
    And the vehicle found page is displayed with "Untaxed" and "MOT" information