package de.corvonn.client.hostings;

import com.google.gson.JsonObject;
import de.corvonn.utils.ReadFromJson;

/**
 * Represents hosting options. Options specify exactly how the server is configured and can e.g.
 * specify the number of RAM or the number of CPU cores. Check out the
 * <a href="https://fsn-01.api.pph.sh/new-docs/client-hosting.html#hosting-details">docs</a> of Prepaid-Hoster.de for
 * more information.
 */
public class HostingOption {
    protected final int configID, defaultID, optionID;
    protected final String configLabel, optionLabel;

    public HostingOption(JsonObject option) {
        JsonObject config = option.get("config").getAsJsonObject();
        option = option.get("option").getAsJsonObject();

        configID = ReadFromJson.readInt(config, "id");
        defaultID = ReadFromJson.readInt(config, "default");
        optionID = ReadFromJson.readInt(option, "id");
        configLabel = ReadFromJson.readString(config, "label");
        optionLabel = ReadFromJson.readString(option, "label");
    }

    @SuppressWarnings("unused")
    public int getConfigID() {
        return configID;
    }

    @SuppressWarnings("unused")
    public int getDefaultID() {
        return defaultID;
    }

    @SuppressWarnings("unused")
    public int getOptionID() {
        return optionID;
    }

    @SuppressWarnings("unused")
    public String getConfigLabel() {
        return configLabel;
    }

    @SuppressWarnings("unused")
    public String getOptionLabel() {
        return optionLabel;
    }

    /**
     * Returns all parameters of the Object.
     * @return all parameters
     */
    @Override
    public String toString() {
        return "HostingOption{" +
                "configID=" + configID +
                ", defaultID=" + defaultID +
                ", optionID=" + optionID +
                ", configLabel='" + configLabel + '\'' +
                ", optionLabel='" + optionLabel + '\'' +
                '}';
    }
}
