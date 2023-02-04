package de.corvonn.client.invoices;

import java.time.OffsetDateTime;
import java.time.ZoneId;

/**
 * Represents a transaction from an invoice. When an invoice is paid by a transaction (for example, a server is not
 * paid by credits or credits are charged), the invoice contains this object.
 */
public class Transaction {
    private final int invoiceID;
    private final String gateway, dateHumanReadable, description;
    private final float amountIn, amountOut;
    private final OffsetDateTime date;

    public Transaction(int id, int invoiceID, String gateway, String dateHumanReadable, String description, float amountIn, float amountOut, OffsetDateTime date) {
        this.id = id;
        this.invoiceID = invoiceID;
        this.gateway = gateway;
        this.dateHumanReadable = dateHumanReadable;
        this.description = description;
        this.amountIn = amountIn;
        this.amountOut = amountOut;
        this.date = date;
    }


    private final int id;

    @SuppressWarnings("unused")
    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public int getInvoiceID() {
        return invoiceID;
    }

    @SuppressWarnings("unused")
    public String getGateway() {
        return gateway;
    }

    @SuppressWarnings("unused")
    public String getDateHumanReadable() {
        return dateHumanReadable;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public float getAmountIn() {
        return amountIn;
    }

    @SuppressWarnings("unused")
    public float getAmountOut() {
        return amountOut;
    }

    @SuppressWarnings("unused")
    public OffsetDateTime getDate() {
        return date;
    }

    @SuppressWarnings("unused")
    public OffsetDateTime getDateCET() {
        return getDate().atZoneSameInstant(ZoneId.of("Europe/Berlin")).toOffsetDateTime();
    }
}
