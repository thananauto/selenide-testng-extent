# Selenide-TestNG-Allure
Sample demo framework with the combination of Selenide, TestNG and Allure report.

## Installation
* A `JDK` > 1.8 and `maven` is required
* Install the `allure` in your platform
* Clone this project
* Open your terminal and execute `mvn test`
* For generating the report run `mvn allure:serve`

For the demo purpose the website [Orange HRM](https://opensource-demo.orangehrmlive.com/web/index.php/auth/login) is used to automate

## Details
[Selenide](https://selenide.org/quick-start.html) is used as library to automate the UI and 
[Allure](https://docs.qameta.io/allure/) is used to display the HTML report with screenshot.