package superoffers.util.jsonserializers;

import java.time.Instant;
import java.time.format.DateTimeParseException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class InstantAdapterTest {

    @Test
    public void it_should_serialize_and_deserialize_an_instant_date_given_a_valid_date_string_format() {
        // arrange
        Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
        Instant instant = Instant.parse("2022-02-25T12:34:56Z");

        // act
        String json = gson.toJson(instant);

        // assert
        Assertions.assertEquals("\"2022-02-25T12:34:56Z\"", json);

        // act
        Instant deserializedInstant = gson.fromJson(json, Instant.class);

        // assert
        Assertions.assertEquals(instant, deserializedInstant);
    }

    @Test
    public void it_should_throws_exception_given_an_invalid_date_string_format() {

        Exception exception = Assert.assertThrows(DateTimeParseException.class, () -> {

            // arrange
            Gson gson = new GsonBuilder().registerTypeAdapter(Instant.class, new InstantAdapter()).create();
            String invalidJson = "\"invalid-format\"";

            // act
            Instant deserializedInstant = gson.fromJson(invalidJson, Instant.class);

        });

        // assert
        Assert.assertTrue(exception instanceof DateTimeParseException);
    }

}
