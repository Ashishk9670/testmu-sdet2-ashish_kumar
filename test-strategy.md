# Test Strategy

## Objective

Build a maintainable hybrid automation suite that gives fast feedback on e-commerce login, product discovery, cart, checkout, order, and API contract behavior.

## Test Pyramid

```text
          UI E2E
       Integration
    API / Contract
 Unit / Component
```

This framework focuses on API, UI, and integration automation because those are the layers requested in the assessment. In a real program, unit and component tests would remain owned by development teams and reported into the same quality view.

## Coverage Strategy

UI coverage:

- valid login
- invalid login
- empty credentials
- product search
- add to cart
- cart validation
- checkout completion
- invalid coupon
- unavailable product behavior
- cross-browser smoke

API coverage:

- authentication
- token extraction
- product read/search
- cart creation
- order create/fetch/update/cancel
- schema validation
- status code validation
- response time checks
- unauthorized access
- not found
- invalid payload

Integration coverage:

- create order through API
- verify created order through UI order history

## API vs UI Strategy

API tests cover most business rules because they are faster, less flaky, and easier to diagnose. UI tests cover critical user journeys and browser-specific behavior. Integration tests prove that backend-created data is visible through the frontend and that the system works across layers.

## Regression Strategy

- PR: API, contract, and UI smoke
- Main branch: API and UI regression
- Nightly: full UI/API/integration suite across browsers
- Release candidate: full suite plus exploratory testing around changed areas

## Risk Analysis

Top risks:

1. Checkout is revenue-critical and should receive the highest regression priority.
2. Environment data drift can break UI and integration tests if orders, inventory, or users are not controlled.
3. UI locator instability can create false failures unless the app exposes stable attributes such as `data-testid`.

## Flaky Test Mitigation

- No hardcoded sleeps
- Explicit waits and custom wait utilities
- Thread-safe driver lifecycle
- Isolated test data where the application allows it
- Retry analyzer limited to a small configured count
- Screenshot, logs, and API payloads attached to failures
- Flaky tests tagged and fixed, not silently ignored

## Shift-Left Testing

- Run API contracts early in CI
- Validate schemas before UI tests execute
- Use API setup for UI preconditions instead of long UI setup flows
- Encourage developers to add stable semantic selectors during feature work

## Parallel Execution Strategy

The framework uses TestNG parallel execution plus `ThreadLocal<WebDriver>`. API tests run by class, UI tests run by class/browser matrix, and integration tests run after API tests because they are slower and more environment-sensitive.

## Reporting Strategy

Allure is used because it gives readable history, steps, screenshots, and API request/response attachments. CI uploads Allure results, screenshots, and logs as artifacts for every run.

## What To Cover Next

- Payment gateway mocks
- Inventory reservation edge cases
- Tax/shipping calculations
- Order cancellation/refund flows
- Accessibility checks
- Visual regression on checkout pages
