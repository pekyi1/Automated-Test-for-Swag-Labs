$ErrorActionPreference = "Stop"

# Initialize standard git configurations if unset
git config user.email "qa.automation@xyzbank.com"
git config user.name "QA Automation Engineer"
git branch -M main

# Initial Commit mapped to Main
git add .gitignore .idea/
git commit -m "chore: initial project setup and gitignore configuration"

# Create Develop branch for integration
git checkout -B develop

# --- BRANCH 1: PROJECT SETUP ---
git checkout -B feature/QA-101-project-setup develop
git add pom.xml
git commit -m "build: configure maven compiler plugins and suite dependencies"
git add src/main/java/com/example/Main.java
git commit -m "feat: add main entry point placeholder"
git add src/test/java/base/BaseTest.java
git commit -m "test: configure BaseTest for Selenium WebDriver setup"
git add src/main/java/driver/DriverFactory.java
git commit -m "feat: implement DriverFactory with bonigarcia WebDriverManager"
git add src/main/java/utils/WaitUtils.java
git commit -m "feat: implement WaitUtils for explicit synchronisation"
git checkout develop
git merge feature/QA-101-project-setup --no-ff -m "Merge branch 'feature/QA-101-project-setup' into develop"

# --- BRANCH 2: MANAGER PAGE OBJECTS ---
git checkout -B feature/QA-102-manager-page-objects develop
git add src/main/java/pages/LoginPage.java
git commit -m "feat(pages): implement base LoginPage object"
git add src/main/java/pages/ManagerPage.java
git commit -m "feat(pages): implement Manager dashboard page object"
git add src/main/java/pages/AddCustomerPage.java
git commit -m "feat(pages): implement AddCustomerPage object"
git add src/main/java/pages/OpenAccountPage.java
git commit -m "feat(pages): implement OpenAccountPage object"
git add src/main/java/pages/CustomersPage.java
git commit -m "feat(pages): implement Customers list page object"
git checkout develop
git merge feature/QA-102-manager-page-objects --no-ff -m "Merge branch 'feature/QA-102-manager-page-objects' into develop"

# --- BRANCH 3: CUSTOMER PAGE OBJECTS ---
git checkout -B feature/QA-103-customer-page-objects develop
git add src/main/java/pages/CustomerLoginPage.java
git commit -m "feat(pages): implement CustomerLoginPage object"
git add src/main/java/pages/CustomerAccountPage.java
git commit -m "feat(pages): implement CustomerAccount dashboard page object"
git add src/main/java/pages/CustomerDepositPage.java
git commit -m "feat(pages): implement Deposit tab page object"
git add src/main/java/pages/CustomerWithdrawlPage.java
git commit -m "feat(pages): implement Withdrawal tab page object"
git add src/main/java/pages/CustomerTransactionsPage.java
git commit -m "feat(pages): implement Transactions tab page object"
git checkout develop
git merge feature/QA-103-customer-page-objects --no-ff -m "Merge branch 'feature/QA-103-customer-page-objects' into develop"

# --- BRANCH 4: TEST UTILITIES & DATA ---
git checkout -B feature/QA-104-test-utilities develop
git add src/test/resources/xyz_bank_data.json
git commit -m "test(data): add JSON test data records for bank workflows"
git add src/test/java/utils/JsonDataUtils.java
git commit -m "test(utils): implement parameterized JsonDataUtils reader"
git checkout develop
git merge feature/QA-104-test-utilities --no-ff -m "Merge branch 'feature/QA-104-test-utilities' into develop"

# --- BRANCH 5: MANAGER TESTS ---
git checkout -B feature/QA-105-manager-tests develop
git add src/test/java/tests/manager/ManagerAddCustomerTest.java
git commit -m "test(manager): implement test cases for adding new customers"
git add src/test/java/tests/manager/ManagerOpenAccountTest.java
git commit -m "test(manager): implement test cases for opening new accounts"
git add src/test/java/tests/manager/ManagerCustomersTest.java
git commit -m "test(manager): implement test cases for searching customers"
git checkout develop
git merge feature/QA-105-manager-tests --no-ff -m "Merge branch 'feature/QA-105-manager-tests' into develop"

# --- BRANCH 6: CUSTOMER TESTS ---
git checkout -B feature/QA-106-customer-tests develop
git add src/test/java/tests/customer/CustomerAuthTest.java
git commit -m "test(customer): implement test case for customer authentication flow"
git add src/test/java/tests/customer/CustomerDepositTest.java
git commit -m "test(customer): implement test case for depositing funds"
git add src/test/java/tests/customer/CustomerWithdrawalTest.java
git commit -m "test(customer): implement test case for withdrawing funds"
git add src/test/java/tests/customer/CustomerTransactionsTest.java
git commit -m "test(customer): implement test case for viewing transaction history"
git checkout develop
git merge feature/QA-106-customer-tests --no-ff -m "Merge branch 'feature/QA-106-customer-tests' into develop"

# --- BRANCH 7: SUITE & SMOKE ---
git checkout -B feature/QA-107-test-suite develop
git add src/test/java/tests/XYZNavigationTest.java
git commit -m "test(core): implement smoke tests for root portal navigation"
git add src/test/java/tests/AllTestsSuite.java
git commit -m "test(suite): configure unified JUnit 5 AllTestsSuite runner"
git checkout develop
git merge feature/QA-107-test-suite --no-ff -m "Merge branch 'feature/QA-107-test-suite' into develop"

# --- BRANCH 8: ALLURE REPORTING ---
git checkout -B feature/QA-108-allure-reporting develop
git add allure-junit5.md
git commit -m "docs(allure): import official Allure reporting MD documentation"
git add src/test/resources/allure.properties
git commit -m "chore(allure): configure allure.properties for results routing"
git add src/test/java/utils/AllureLogListener.java
git commit -m "feat(allure): implement custom AllureLogListener for standard logging"
git add src/test/resources/META-INF/services/io.qameta.allure.listener.TestLifecycleListener
git commit -m "chore(allure): register TestLifecycleListener SPI"
git checkout develop
git merge feature/QA-108-allure-reporting --no-ff -m "Merge branch 'feature/QA-108-allure-reporting' into develop"

# --- BRANCH 9: POLISHING & FINAL TOUCHES ---
git checkout -B hotfix/QA-109-final-touches develop
echo "# XYZ Bank Automation Framework" > README.md
git add README.md
git commit -m "docs: add standard project README scaffold"
echo "This repository contains the robust Selenium UI Test Framework." >> README.md
git add README.md
git commit -m "docs: update README with repository description"
git checkout develop
git merge hotfix/QA-109-final-touches --no-ff -m "Merge branch 'hotfix/QA-109-final-touches' into develop"

# Switch back to main and merge develop
git checkout main
git merge develop --no-ff -m "Merge branch 'develop' into main for release candidate"

# Output log statistics to verify
Write-Host "Total Commits Created:"
git rev-list --all --count
Write-Host "Branches Created:"
git branch
