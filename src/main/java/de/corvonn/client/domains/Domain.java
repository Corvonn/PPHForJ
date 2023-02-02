package de.corvonn.client.domains;

import com.google.gson.JsonObject;
import de.corvonn.enums.DomainStatus;
import de.corvonn.utils.ReadFromJson;

import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Represents a domain
 */
public class Domain {
    private final int id;
    private final float firstAmount, recurringAmount;
    private final String domain, domainIDN, nextDueHumanReadable;
    private final DomainStatus status;
    private final OffsetDateTime registerDate, nextDueDate;

    public Domain(JsonObject o) {
        id = ReadFromJson.readInt(o, "id");
        domain = ReadFromJson.readString(o, "domain");
        domainIDN = ReadFromJson.readString(o, "domain_idn");
        firstAmount = ReadFromJson.readFloat(o, "firstamount");
        recurringAmount = ReadFromJson.readFloat(o, "recurringamount");
        status = DomainStatus.getByName(ReadFromJson.readString(o, "status"));
        registerDate = ReadFromJson.readOffsetDateTime(o, "register_date");
        nextDueDate = ReadFromJson.readOffsetDateTime(o, "next_due_date");
        nextDueHumanReadable = ReadFromJson.readString(o, "next_due_human");
    }

    /**
     * Returns the id of the domain
     * @return the domain id
     */
    @SuppressWarnings("unused")

    public int getId() {
        return id;
    }

    /**
     * Returns the price paid when the domain was purchased.
     * @return the price from the first invoice
     */
    @SuppressWarnings("unused")

    public float getFirstAmount() {
        return firstAmount;
    }

    /**
     * Returns the price due on the next payment.
     * @return the price for the next invoice
     * @see Domain#getNextDueDate()
     */
    @SuppressWarnings("unused")

    public float getRecurringAmount() {
        return recurringAmount;
    }

    /**
     * Returns the full registered domain (name)
     * @return the domain
     */
    @SuppressWarnings("unused")

    public String getDomain() {
        return domain;
    }

    /**
     * Returns the registered domain as UTF-8 compatible name. Umlauts or emojis in a domain are transformed accordingly.
     * @return the UTF-8 compatible name
     */
    @SuppressWarnings("unused")

    public String getDomainIDN() {
        return domainIDN;
    }

    /**
     * Returns the {@link DomainStatus} of the domain.
     * @return {@link DomainStatus}
     */
    @SuppressWarnings("unused")

    public DomainStatus getStatus() {
        return status;
    }

    /**
     * Returns the next date when the domain expires and needs to be renewed.
     * @return the next expiration date
     */
    @SuppressWarnings("unused")

    public OffsetDateTime getNextDueDate() {
        return nextDueDate;
    }

    /**
     * Returns the next date in CET when the domain expires and needs to be renewed.
     * @return the next expiration date
     */
    @SuppressWarnings("unused")

    public OffsetDateTime getNextDueDateCET() {
        return nextDueDate.atZoneSameInstant(ZoneId.of("Europe/Berlin")).toOffsetDateTime();
    }

    /**
     * Returns the next date in human readable form when the domain expires and needs to be renewed.
     * @return the next expiration date
     */
    @SuppressWarnings("unused")

    public String getNextDueHumanReadable() {
        return nextDueHumanReadable;
    }

    /**
     * Returns the date when the domain was registered and thus was ready for use for the first time.
     * @return the registration date
     */
    @SuppressWarnings("unused")

    public OffsetDateTime getRegisterDate() {
        return registerDate;
    }

    /**
     * Returns the date in CET when the domain was registered and thus was ready for use for the first time.
     * @return the registration date
     */
    @SuppressWarnings("unused")

    public OffsetDateTime getRegisterDateCET() {
        return registerDate.atZoneSameInstant(ZoneId.of("Europe/Berlin")).toOffsetDateTime();
    }
}
