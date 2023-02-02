package de.corvonn.client.invoices;

import de.corvonn.enums.InvoiceType;

/**
 * Represents an Item from an invoice that is a donation. Extends {@link InvoiceItem}.
 */
public class InvoiceDonationItem extends InvoiceItem{
    private final String donatorName, donatorText;

    public InvoiceDonationItem(int id, int invoiceID, InvoiceType type, String shortDescription, String description, float amount, boolean donation, String donatorName, String donatorText) {
        super(id, invoiceID, type, shortDescription, description, amount, donation);
        this.donatorName = donatorName;
        this.donatorText = donatorText;
    }

    @SuppressWarnings("unused")
    public String getDonatorName() {
        return donatorName;
    }

    @SuppressWarnings("unused")
    public String getDonatorText() {
        return donatorText;
    }
}
