# OrangeHRM__QA_Testing
# OrangeHRM QA Automation Framework

Automated UI testing framework for [OrangeHRM](https://opensource-demo.orangehrmlive.com/) built with Java, Selenium WebDriver, and TestNG.
[![Run Selenium Tests](https://github.com/BermetB16/OrangeHRM__QA_Testing.git)](https://github.com/BermetB16/QA_Platform_Progect/actions/workflows/test.yml)
## Tech Stack
- Java 17
- Selenium WebDriver 4.41.0
- TestNG 7.12.0
- WebDriverManager
- Allure Reports
- Log4j2
- Maven
- Page Object Model (POM)

## Project Structure
src/
├── main/java/com/learnaqa/
│   ├── driver/          # Singleton WebDriver management
│   ├── entity/          # Data models (User)
│   ├── exception/       # Custom exceptions
│   ├── fileUtils/       # Config reader
│   ├── helper/          # WebElementActions, BrowserManager
│   ├── pages/           # Page Object classes
│   └── utils/           # Enums, Screenshot utils, Random generators
└── test/java/com/learnaqa/tests/
├── LoginTest.java
├── DashBoardTest.java
├── AdminTest.java
├── PIMTest.java
└── LeaveTest.java
## Test Coverage
| Module | Tests |
|--------|-------|
| Login | Valid login, Invalid credentials, Empty fields |
| Dashboard | Title verification, Header visibility |
| Admin | User Management navigation |
| PIM | Header visibility, Add employee |
| Leave | Header visibility |

## How to Run

### Run all tests:
mvn clean test
### Generate Allure Report:
mvn allure:serve

## Allure Report
![Allure Report](allure-report.png)

## Design Patterns
- **Page Object Model** — separates test logic from page interactions
- **Singleton Pattern** — single WebDriver instance across all tests
- **Data Provider** — parameterized tests with multiple data sets
