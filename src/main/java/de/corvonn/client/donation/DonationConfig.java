package de.corvonn.client.donation;

import com.google.gson.JsonObject;
import de.corvonn.utils.ReadFromJson;

import java.util.UUID;

/**
 * DonationConfig contains information about the settings (partly assigned by PPH) for donations.
 */
public class DonationConfig {

    private final UUID uuid;
    private final String url, displayName;

    public DonationConfig(JsonObject data) {
        uuid = ReadFromJson.readUUID(data, "uuid");
        url = ReadFromJson.readString(data, "url");
        displayName = ReadFromJson.readString(data, "display_name");
    }

    /**
     * Returns the donation UUID of the user
     * @return the donation UUID
     */
    @SuppressWarnings("unused")
    public UUID getUuid() {
        return uuid;
    }

    /**
     * Returns the link through which other people can donate.
     * @return the donation url
     */
    @SuppressWarnings("unused")
    public String getUrl() {
        return url;
    }

    /**
     * Returns the name that will be displayed for people who want to donate. Null if the username is displayed.
     * @return the display name
     */
    @SuppressWarnings("unused")
    public String getDisplayName() {
        return displayName;
    }

    /**
     * Returns all parameters of the Object.
     * @return all parameters
     */
    @Override
    @SuppressWarnings("unused")
    public String toString() {
        return "DonationConfig{" +
                "uuid=" + uuid +
                ", url='" + url + '\'' +
                ", displayName='" + displayName + '\'' +
                '}';
    }
}
