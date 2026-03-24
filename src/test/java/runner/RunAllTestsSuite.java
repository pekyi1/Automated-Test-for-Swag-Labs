package runner;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("Swag Labs Automation - Full Test Suite")
@SelectPackages("tests")
public class RunAllTestsSuite {
    // This class remains empty.
    // It is used only as a holder for the Suite annotations to run all tests at
    // once.
}
