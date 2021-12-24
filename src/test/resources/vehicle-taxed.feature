@Ui
Feature: GOV.UK - Taxed

  Scenario: Check Tax and MOT -
    Given a user opens "https://www.gov.uk/check-mot-status"
    When a user enters registration "WG56WHH"
    Then a page is displayed with vehicle information: "HONDA" and "GREY"
    And the vehicle found page is displayed with "Taxed" and "MOT" information