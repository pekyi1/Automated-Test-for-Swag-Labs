
# Getting Started with Allure JUnit 5

Generate beautiful HTML reports using **Allure Report** and your **JUnit 5 (Jupiter)** tests.

---

## Information

Check out example projects at:
https://github.com/allure-examples

---

## Setting Up Allure

To integrate Allure into an existing JUnit 5 project, you need to:

1. Add Allure dependencies to your project.
2. Set up AspectJ for `@Step` and `@Attachment` annotation support.

---

## Add Allure Dependencies

### Maven

```xml
<properties>
  <allure.version>2.25.0</allure.version>
</properties>

<dependencyManagement>
  <dependencies>
    <dependency>
      <groupId>io.qameta.allure</groupId>
      <artifactId>allure-bom</artifactId>
      <version>${allure.version}</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>

<dependencies>
  <dependency>
    <groupId>io.qameta.allure</groupId>
    <artifactId>allure-junit5</artifactId>
    <scope>test</scope>
  </dependency>
</dependencies>
```

---

## Configure AspectJ

```xml
<properties>
  <aspectj.version>1.9.21</aspectj.version>
</properties>

<plugin>
  <groupId>org.apache.maven.plugins</groupId>
  <artifactId>maven-surefire-plugin</artifactId>
  <version>3.2.5</version>
  <configuration>
    <argLine>
      -javaagent:${settings.localRepository}/org/aspectj/aspectjweaver/${aspectj.version}/aspectjweaver-${aspectj.version}.jar
    </argLine>
  </configuration>
  <dependencies>
    <dependency>
      <groupId>org.aspectj</groupId>
      <artifactId>aspectjweaver</artifactId>
      <version>${aspectj.version}</version>
    </dependency>
  </dependencies>
</plugin>
```

---

## Specify Allure Results Location

Create an `allure.properties` file under:

```
src/test/resources
```

Example:
```
allure.results.directory=build/allure-results
```

---

## Running Tests

### Gradle
```bash
./gradlew test
```

### Maven
```bash
./mvnw verify
```

After execution, results are saved in the `allure-results` directory.

---

## Generate the Report

```bash
allure generate
allure open
```

Or run:

```bash
allure serve
```

---

## Writing Tests with Allure

Allure enhances JUnit 5 by allowing you to:

- Add descriptions, links, and metadata
- Organize tests hierarchically
- Divide tests into steps
- Work with parameterized tests
- Attach screenshots and files
- Control test execution via test plans
- Add environment information

---

## Metadata Annotations Example

```java
@Test
@DisplayName("Test Authentication")
@Description("This test attempts to log into the website")
@Severity(SeverityLevel.CRITICAL)
@Owner("John Doe")
@Link(name = "Website", url = "https://dev.example.com")
@Issue("AUTH-123")
@TmsLink("TMS-456")
void testAuthentication() {
}
```

---

## Organizing Tests

### Behavior-Based Hierarchy

```java
@Epic("Web interface")
@Feature("Essential features")
@Story("Authentication")
@Test
void testAuthentication() {}
```

### Suite-Based Hierarchy

```java
Allure.label("parentSuite", "Web interface");
Allure.label("suite", "Essential features");
Allure.label("subSuite", "Authentication");
```

---

## Divide Tests into Steps

```java
@Step("Step 1")
void step1() {}

@Step("Sub-step 1")
void subStep1() {}
```

---

## Parameterized Tests

```java
@ParameterizedTest(name = "{displayName} {argumentsWithNames}")
@ValueSource(strings = {"johndoe", "johndoe@example.com"})
void testAuthentication(String login) {}
```

---

## Attachments

```java
Allure.addAttachment("data.txt", "This is the file content");
```

---

## Select Tests via Test Plan

```bash
export ALLURE_TESTPLAN_PATH=testplan.json
./gradlew test
```

---

## Environment Information

Create `environment.properties` in `allure-results` directory:

```
os_version=Windows 11
java_version=17
```

---

© 2026 Qameta Software Inc.
