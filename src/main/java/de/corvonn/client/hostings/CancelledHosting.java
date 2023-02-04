package de.corvonn.client.hostings;

import com.google.gson.JsonObject;
import de.corvonn.utils.ReadFromJson;

import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Represents a hosting that is already cancelled.
 */
public class CancelledHosting extends Hosting{
    private final int cancellationID;
    private final OffsetDateTime cancellationDate;
    private final String cancellationReason, cancellationType;

    public CancelledHosting(JsonObject data) {
        super(data);

        JsonObject o = data.get("cancellation_data").getAsJsonObject();
        cancellationID = ReadFromJson.readInt(o, "id");
        cancellationDate = ReadFromJson.readOffsetDateTime(o, "date");
        cancellationReason = ReadFromJson.readString(o, "reason");
        cancellationType = ReadFromJson.readString(o, "type");
    }

    /**
     * Get the id of the cancellation or termination.
     * @return the id
     */
    @SuppressWarnings("unused")
    public int getCancellationID() {
        return cancellationID;
    }

    /**
     * Returns the date of the cancellation in UTC.
     * @return the cancellation date
     */
    @SuppressWarnings("unused")
    public OffsetDateTime getCancellationDate() {
        return cancellationDate;
    }

    /**
     * Returns the date of the cancellation in CET.
     * @return the cancellation date in CET
     */
    @SuppressWarnings("unused")
    public OffsetDateTime getCancellationDateCET() {
        return cancellationDate.atZoneSameInstant(ZoneId.of("Europe/Berlin")).toOffsetDateTime();
    }

    /**
     * Returns the reason that was given by the customer why the host was cancelled.
     * @return the cancellation reason
     */
    @SuppressWarnings("unused")
    public String getCancellationReason() {
        return cancellationReason;
    }

    /**
     * Returns whether the cancellation was a termination or a cancellation.
     * Information about the difference is available in the {@link de.corvonn.enums.HostingStatus#CANCELLED} and
     * {@link de.corvonn.enums.HostingStatus#TERMINATED} enum.
     * @return whether the host was terminated or cancelled
     */
    @SuppressWarnings("unused")
    public String getCancellationType() {
        return cancellationType;
    }

    /**
     * Returns all parameters of the Object.
     * @return all parameters
     */
    @Override
    public String toString() {
        return "[CancelledHosting{" +
                "cancellationID=" + cancellationID +
                ", cancellationDate=" + cancellationDate +
                ", cancellationReason='" + cancellationReason + '\'' +
                ", cancellationType='" + cancellationType + '\'' +
                "}," + super.toString() + "]";
    }
}
