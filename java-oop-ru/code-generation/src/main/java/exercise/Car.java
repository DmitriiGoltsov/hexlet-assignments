package exercise;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Map;

// BEGIN
@Getter
@Value
@AllArgsConstructor
// END
class Car {
    int id;
    String brand;
    String model;
    String color;
    User owner;

    // BEGIN
    public String serialize() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String json = mapper.writeValueAsString(this);
            return json;
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    public static Car unserialize(String str) {

        ObjectMapper mapper = new ObjectMapper();

        try {
            Map result = mapper.readValue(str, Map.class);
            int id = (int) result.get("id");
            String brand = (String) result.get("brand");
            String model = (String) result.get("model");
            String color = (String) result.get("color");
            User owner = (User) result.get("owner");
            Car car = new Car (id, brand, model, color, owner);
            return car;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }
    // END
}
