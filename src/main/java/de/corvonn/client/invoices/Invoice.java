package de.corvonn.client.invoices;


import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

/**
 * Object that represents an invoice.
 */
public class Invoice {
    private final int id;
    private final OffsetDateTime datePaid;
    private final float nettoTotal, paidWithCredit, tax, taxRate, bruttoTotal;
    private final String status, statusLabel;
    private final boolean isDonation;
    private final Transaction transaction;
    private final List<InvoiceItem> invoiceItems;


    public Invoice(int id, OffsetDateTime datePaid, float nettoTotal, float paidWithCredit, float tax, float taxRate,
                   float bruttoTotal, String status, String statusLabel, boolean isDonation, Transaction transaction,
                   List<InvoiceItem> invoiceItems) {
        this.id = id;
        this.datePaid = datePaid;
        this.nettoTotal = nettoTotal;
        this.paidWithCredit = paidWithCredit;
        this.tax = tax;
        this.taxRate = taxRate;
        this.bruttoTotal = bruttoTotal;
        this.status = status;
        this.statusLabel = statusLabel;
        this.isDonation = isDonation;
        this.transaction = transaction;
        this.invoiceItems = invoiceItems;
    }

    /**
     * Returns the id of the invoice.
     * @return the invoice id
     */
    @SuppressWarnings("unused")
    public int getId() {
        return id;
    }

    /**
     * Returns the date on that the invoice was paid.
     * @return the pay date in UTC
     */
    @SuppressWarnings("unused")
    public OffsetDateTime getDatePaid() {
        return datePaid;
    }

    /**
     * Returns the date on that the invoice was paid.
     * @return the pay date in CET
     */
    @SuppressWarnings("unused")
    public OffsetDateTime getDatePaidCET() {
        return getDatePaid().atZoneSameInstant(ZoneId.of("Europe/Berlin")).toOffsetDateTime();
    }

    /**
     * Returns the net amount paid.
     * @return the net amount paid
     */
    @SuppressWarnings("unused")
    public float getNettoTotal() {
        return nettoTotal;
    }

    /**
     * Returns the amount that was paid with credits.
     * @return the credit amount
     */
    @SuppressWarnings("unused")
    public float getPaidWithCredit() {
        return paidWithCredit;
    }

    /**
     * Returns whether taxes was paid for this invoice.
     * @return whether taxes was paid
     */
    @SuppressWarnings("unused")
    public float getTax() {
        return tax;
    }

    /**
     * Returns the tax rate for this invoice. Note that the rate can be greater than 0, even if no taxes was paid.
     * @return the tax rate
     * @see Invoice#getTax()
     */
    @SuppressWarnings("unused")
    public float getTaxRate() {
        return taxRate;
    }

    /**
     * Returns the gross amount paid
     * @return the gross amount
     */
    @SuppressWarnings("unused")
    public float getBruttoTotal() {
        return bruttoTotal;
    }

    /**
     * Returns the status of the invoice. See the PPH Docs for more information.
     * @return the invoice status
     */
    @SuppressWarnings("unused")
    public String getStatus() {
        return status;
    }

    /**
     * Returns the status label of the invoice
     * @return the invoice status label
     */
    @SuppressWarnings("unused")
    public String getStatusLabel() {
        return statusLabel;
    }

    /**
     * Returns the {@link Transaction} object if the invoice is a transaction
     * @return the transaction or null, if the invoice is no transaction
     */
    @SuppressWarnings("unused")
    public Transaction getTransaction() {
        return transaction;
    }

    /**
     * Returns a list of items of the invoice.
     * @return the items
     */
    @SuppressWarnings("unused")
    public List<InvoiceItem> getInvoiceItems() {
        return new ArrayList<>(invoiceItems);
    }

    /**
     * Returns whether the invoice is a donation.
     * @return whether the invoice is a donation
     */
    @SuppressWarnings("unused")
    public boolean isDonation() {
        return isDonation;
    }

    /**
     * Returns whether the invoice is a transaction.
     * @return whether the invoice is a transaction
     */
    @SuppressWarnings("unused")
    public boolean isTransaction() {
        return transaction != null;
    }
}
