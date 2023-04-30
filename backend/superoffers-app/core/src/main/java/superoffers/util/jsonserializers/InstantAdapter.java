package superoffers.util.jsonserializers;

import com.google.gson.*;

import java.lang.reflect.Type;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

public class InstantAdapter implements JsonSerializer<Instant>, JsonDeserializer<Instant> {

    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ISO_INSTANT;

    @Override
    public Instant deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return DATE_TIME_FORMATTER.parse(jsonElement.getAsString(), Instant::from);
    }

    @Override
    public JsonElement serialize(Instant instant, Type type, JsonSerializationContext jsonSerializationContext) {
        return new JsonPrimitive(DATE_TIME_FORMATTER.format(instant));
    }

}
