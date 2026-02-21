package utils;

import io.qameta.allure.listener.TestLifecycleListener;
import io.qameta.allure.model.TestResult;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AllureLogListener implements TestLifecycleListener {

    private static final Logger logger = Logger.getLogger("AllureTestLogger");

    static {
        try {
            // Configure the logger to write to a file in the project root directory
            FileHandler fileHandler = new FileHandler("xyz-bank-tests.log", true);
            fileHandler.setFormatter(new SimpleFormatter());
            logger.addHandler(fileHandler);
            logger.setLevel(Level.INFO);
        } catch (IOException e) {
            System.err.println("Failed to initialize file logger for Allure: " + e.getMessage());
        }
    }

    @Override
    public void beforeTestSchedule(TestResult result) {
        logger.info("---------------------------------------------------");
        logger.info("TEST SCHEDULED: " + result.getName());
    }

    @Override
    public void beforeTestStart(TestResult result) {
        logger.info("TEST STARTED: " + result.getName());
    }

    @Override
    public void afterTestStop(TestResult result) {
        String status = result.getStatus() != null ? result.getStatus().name() : "UNKNOWN";
        logger.info("TEST FINISHED: " + result.getName() + " -> Status: " + status);
        logger.info("---------------------------------------------------\n");
    }
}
