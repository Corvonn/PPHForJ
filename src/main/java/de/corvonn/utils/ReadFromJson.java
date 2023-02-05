package de.corvonn.utils;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;

/**
 * <p>
 *     Class that allows easy reading from a JsonObject. Methods with readXXXOptional are safe and return an Optional.
 *     Methods named only readXXX can throw {@link NullPointerException} or {@link com.google.gson.JsonNull},
 *     but return the requested data type directly without an optional.
 * <p>
 *     The jsonObject from which the variable is to be read is always required as a transfer value,
 *     and the memberName is also required, which specifies the name under which the object can be read.
 * </p>
 */
public class ReadFromJson {
    /**
     * See class description
     */
    @SuppressWarnings("unused")
    public static OptionalInt readIntOptional(JsonObject jsonObject, String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if(element == null) return OptionalInt.empty();
        if(element.isJsonNull()) return OptionalInt.empty();
        return OptionalInt.of(element.getAsInt());
    }
    /**
     * See class description
     */
    public static int readInt(JsonObject jsonObject, String memberName) {
        return jsonObject.get(memberName).getAsInt();
    }

    /**
     * See class description
     */
    @SuppressWarnings("unused")

    public static Optional<Float> readFloatOptional(JsonObject jsonObject, String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if(element == null) return Optional.empty();
        if(element.isJsonNull()) return Optional.empty();
        return Optional.of(element.getAsFloat());
    }
    /**
     * See class description
     */
    public static float readFloat(JsonObject jsonObject, String memberName) {
        return jsonObject.get(memberName).getAsFloat();
    }

    /**
     * See class description
     */
    public static Optional<String> readStringOptional(JsonObject jsonObject, String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if(element == null) return Optional.empty();
        if(element.isJsonNull()) return Optional.empty();
        return Optional.of(element.getAsString());
    }
    /**
     * See class description
     */
    public static String readString(JsonObject jsonObject, String memberName) {
        return readStringOptional(jsonObject, memberName).orElse(null);
    }

    /**
     * See class description
     */
    public static Optional<OffsetDateTime> readOffsetDateTimeOptional(JsonObject jsonObject, String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if(element == null) return Optional.empty();
        if(element.isJsonNull()) return Optional.empty();
        return Optional.of(OffsetDateTime.parse(element.getAsString()));
    }
    /**
     * See class description
     */
    public static OffsetDateTime readOffsetDateTime(JsonObject jsonObject, String memberName) {
        return readOffsetDateTimeOptional(jsonObject, memberName).orElse(null);
    }

    /**
     * See class description
     */
    @SuppressWarnings("unused")
    public static Optional<Boolean> readBoolOptional(JsonObject jsonObject, String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if(element == null) return Optional.empty();
        if(element.isJsonNull()) return Optional.empty();
        return Optional.of(element.getAsBoolean());
    }
    /**
     * See class description
     */
    public static boolean readBool(JsonObject jsonObject, String memberName) {
        return jsonObject.get(memberName).getAsBoolean();
    }

    /**
     * See class description
     */
    public static Optional<UUID> readUUIDOptional(JsonObject jsonObject, String memberName) {
        JsonElement element = jsonObject.get(memberName);
        if(element == null) return Optional.empty();
        if(element.isJsonNull()) return Optional.empty();
        return Optional.of(UUID.fromString(element.getAsString()));
    }

    /**
     * See class description
     */
    public static UUID readUUID(JsonObject jsonObject, String memberName) {
        return readUUIDOptional(jsonObject, memberName).orElse(null);
    }
}
