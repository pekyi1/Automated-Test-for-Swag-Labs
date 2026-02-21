package tests;

import org.junit.platform.suite.api.SelectPackages;
import org.junit.platform.suite.api.Suite;
import org.junit.platform.suite.api.SuiteDisplayName;

@Suite
@SuiteDisplayName("XYZ Bank Test Suite")
@SelectPackages({
        "tests.manager",
        "tests.customer",
        "tests" // Includes XYZNavigationTest
})
public class XYZBankTestSuite {
    // This class remains empty.
    // It is used only as a holder for the above annotations.
}
