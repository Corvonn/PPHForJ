package de.corvonn.client.invoices;

import de.corvonn.enums.InvoiceType;

/**
 * Defines an item from an invoice. <br>
 * An invoice can contain different products. Each individual product is an item.
 * But also for example discounts count as an item of the invoice.
 */
public class InvoiceItem {
    private final int id, invoiceID;
    private final InvoiceType type;
    private final String shortDescription, description;
    private final float amount;
    private final boolean donation;

    public InvoiceItem(int id, int invoiceID, InvoiceType type, String shortDescription, String description, float amount, boolean donation) {
        this.id = id;
        this.invoiceID = invoiceID;
        this.type = type;
        this.shortDescription = shortDescription;
        this.description = description;
        this.amount = amount;
        this.donation = donation;
    }

    @SuppressWarnings("unused")
    public int getId() {
        return id;
    }

    @SuppressWarnings("unused")
    public int getInvoiceID() {
        return invoiceID;
    }
    @SuppressWarnings("unused")
    public InvoiceType getType() {
        return type;
    }

    @SuppressWarnings("unused")
    public String getShortDescription() {
        return shortDescription;
    }

    @SuppressWarnings("unused")
    public String getDescription() {
        return description;
    }

    @SuppressWarnings("unused")
    public float getAmount() {
        return amount;
    }

    @SuppressWarnings("unused")
    public boolean isDonation() {
        return donation;
    }
}
