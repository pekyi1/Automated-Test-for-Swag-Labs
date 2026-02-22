package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonDataUtils {

    public static Stream<Arguments> provideNewCustomers() {
        return readArrayFromJson("newCustomers", "firstName", "lastName", "postCode");
    }

    public static Stream<Arguments> provideInvalidCustomers() {
        return readArrayFromJson("invalidCustomers", "firstName", "lastName", "postCode");
    }

    public static Stream<Arguments> provideOpenAccounts() {
        return readArrayFromJson("openAccounts", "customerName", "currency");
    }

    public static Stream<Arguments> provideInvalidOpenAccounts() {
        return readArrayFromJson("invalidOpenAccounts", "customerName", "currency");
    }

    public static Stream<Arguments> provideSearchCustomers() {
        return readArrayFromJson("searchCustomers", "firstName", "lastName");
    }

    public static Stream<Arguments> provideCustomerNoAccount() {
        return readArrayFromJson("customerNoAccount", "firstName", "lastName", "postCode");
    }

    public static Stream<Arguments> provideCustomerAuth() {
        return readArrayFromJson("customerAuth", "userName");
    }

    public static Stream<Arguments> provideCustomerDeposit() {
        return readArrayFromJson("customerDeposit", "userName", "amount", "expectedMessage");
    }

    public static Stream<Arguments> provideCustomerDepositNegative() {
        return readArrayFromJson("customerDepositNegative", "userName", "amount", "expectedMessage");
    }

    public static Stream<Arguments> provideCustomerWithdrawal() {
        return readArrayFromJson("customerWithdrawal", "userName", "depositAmount", "withdrawalAmount",
                "expectedMessage");
    }

    public static Stream<Arguments> provideCustomerWithdrawalNegative() {
        return readArrayFromJson("customerWithdrawalNegative", "userName", "depositAmount", "withdrawalAmount",
                "expectedMessage");
    }

    public static Stream<Arguments> provideCustomerWithdrawalMoreThanBalance() {
        return readArrayFromJson("customerWithdrawalMoreThanBalance", "userName", "depositAmount", "withdrawalAmount",
                "expectedMessage");
    }

    private static Stream<Arguments> readArrayFromJson(String arrayName, String... properties) {
        ObjectMapper mapper = new ObjectMapper();
        List<Arguments> arguments = new ArrayList<>();
        try (InputStream is = JsonDataUtils.class.getClassLoader().getResourceAsStream("xyz_bank_data.json")) {
            if (is == null) {
                throw new RuntimeException("Could not find xyz_bank_data.json in classpath");
            }
            JsonNode rootNode = mapper.readTree(is);
            JsonNode arrayNode = rootNode.get(arrayName);
            if (arrayNode != null && arrayNode.isArray()) {
                for (JsonNode node : arrayNode) {
                    Object[] args = new Object[properties.length];
                    for (int i = 0; i < properties.length; i++) {
                        args[i] = node.get(properties[i]).asText();
                    }
                    arguments.add(Arguments.of(args));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON test data for key: " + arrayName, e);
        }
        return arguments.stream();
    }
}
