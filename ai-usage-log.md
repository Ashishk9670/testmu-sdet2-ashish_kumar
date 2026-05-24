# AI Usage Log

## Tool Used

OpenAI Codex in the Codex desktop app.

## Prompt Used

The user provided the SDET-2 assessment PDF and a master prompt requesting a Java 21, Selenium, TestNG, Rest Assured, Maven, Allure, and GitHub Actions hybrid automation framework for an e-commerce checkout flow.

## Output Generated

- Maven project structure
- Selenium UI framework
- Rest Assured API framework
- Page objects
- API clients
- POJO models
- JSON test data
- JSON schemas
- TestNG tests and suites
- Retry analyzer
- TestNG listener
- Screenshot utility
- Allure integration
- Docker support
- GitHub Actions workflow
- README
- test strategy document

## Manual Design Decisions

- Used Page Object Model for UI maintainability.
- Used `ThreadLocal<WebDriver>` to support parallel browser tests.
- Used factory pattern for browser creation.
- Used builder pattern for address test data.
- Chose GitHub Actions CI over analytics dashboard because PR feedback gives immediate engineering value.
- Kept environment URLs externalized so the framework can run against QA, UAT, or PROD-like targets.

## Manual Modifications Made

The generated framework should be pointed at the real assigned e-commerce application before final execution by updating `base.ui.url`, `base.api.url`, and credentials in the config files or Maven system properties.
