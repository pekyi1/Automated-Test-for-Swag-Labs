package utils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.params.provider.Arguments;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class JsonDataUtils {

    private static final String SWAG_LABS_DATA_FILE = "swag_labs_data.json";

    public static JsonNode getSwagLabsData() {
        return readJson(SWAG_LABS_DATA_FILE);
    }

    public static Stream<Arguments> provideParameterizedLogin() {
        return readArrayFromJson(SWAG_LABS_DATA_FILE, "parameterizedLogin", "username", "password", "expectedError");
    }

    public static Stream<Arguments> provideParameterizedCheckout() {
        return readArrayFromJson(SWAG_LABS_DATA_FILE, "parameterizedCheckout", "firstName", "lastName", "zipCode",
                "expectedError");
    }

    public static JsonNode readJson(String fileName) {
        ObjectMapper mapper = new ObjectMapper();
        try (InputStream is = JsonDataUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new RuntimeException("Could not find " + fileName + " in classpath");
            }
            return mapper.readTree(is);
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON from file: " + fileName, e);
        }
    }

    private static Stream<Arguments> readArrayFromJson(String fileName, String arrayName, String... properties) {
        ObjectMapper mapper = new ObjectMapper();
        List<Arguments> arguments = new ArrayList<>();
        try (InputStream is = JsonDataUtils.class.getClassLoader().getResourceAsStream(fileName)) {
            if (is == null) {
                throw new RuntimeException("Could not find " + fileName + " in classpath");
            }
            JsonNode rootNode = mapper.readTree(is);
            JsonNode arrayNode = rootNode.get(arrayName);
            if (arrayNode != null && arrayNode.isArray()) {
                for (JsonNode node : arrayNode) {
                    Object[] args = new Object[properties.length];
                    for (int i = 0; i < properties.length; i++) {
                        JsonNode propertyNode = node.get(properties[i]);
                        args[i] = (propertyNode != null) ? propertyNode.asText() : "";
                    }
                    arguments.add(Arguments.of(args));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Failed to read JSON test data for key: " + arrayName + " in file: " + fileName,
                    e);
        }
        return arguments.stream();
    }
}
