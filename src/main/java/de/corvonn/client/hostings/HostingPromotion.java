package de.corvonn.client.hostings;

import com.google.gson.JsonObject;
import de.corvonn.utils.ReadFromJson;

/**
 * Represents a promotion for hostings
 */
public class HostingPromotion {
    private final String code, label, type;
    private int recurringFor;
    private final int value;


    public HostingPromotion(JsonObject promotion) {
        code = ReadFromJson.readString(promotion, "code");
        label = ReadFromJson.readString(promotion, "label");
        type = ReadFromJson.readString(promotion, "type");

        String recurringFor = ReadFromJson.readString(promotion, "recurring_for");
        try {
            boolean b = Boolean.parseBoolean(recurringFor);
            this.recurringFor = b ? 0 : -1;
        }catch (Exception b) {
            try {
                this.recurringFor = Integer.parseInt(recurringFor);
            }catch (Exception i) {
                throw new RuntimeException("An unknown API error occurred while processing recurringFor.");
            }
        }

        value = ReadFromJson.readInt(promotion, "value");
    }

    /**
     * Returns the code of the promotion
     * @return the code
     */
    @SuppressWarnings("unused")
    public String getCode() {
        return code;
    }

    /**
     * Returns the label of the promotion
     * @return the label
     */
    @SuppressWarnings("unused")
    public String getLabel() {
        return label;
    }

    /**
     * Returns the type of the amount (e.g. percentage).
     * @return the type
     */
    @SuppressWarnings("unused")
    public String getType() {
        return type;
    }

    /**
     * Returns how often the promotion is valid. 0 if the promotion has no ending.
     * @return how often the promotion is valid
     */
    @SuppressWarnings("unused")
    public int getRecurringFor() {
        return recurringFor;
    }

    /**
     * Returns whether the promotion is recurring.
     * @return whether the promotion is recurring
     */
    @SuppressWarnings("unused")
    public boolean isRecurring() {
        return recurringFor != -1;
    }

    /**
     * Returns whether the promotion is infinitive recurring.
     * @return whether the promotion is infinitive recurring
     */
    @SuppressWarnings("unused")
    public boolean isInfiniteRecurring() {
        return recurringFor == 0;
    }

    /**
     * Returns the value how much the promotion reduces the price of the hosting.
     * @return the value
     */
    @SuppressWarnings("unused")
    public int getValue() {
        return value;
    }

    /**
     * Returns all parameters of the Object.
     * @return all parameters
     */
    @Override
    public String toString() {
        return "HostingPromotion{" +
                "code='" + code + '\'' +
                ", label='" + label + '\'' +
                ", type='" + type + '\'' +
                ", recurringFor=" + recurringFor +
                ", value=" + value +
                '}';
    }
}
