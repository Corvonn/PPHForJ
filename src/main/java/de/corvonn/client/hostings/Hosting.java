package de.corvonn.client.hostings;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.corvonn.enums.BillingCycle;
import de.corvonn.enums.HostingStatus;
import de.corvonn.utils.ReadFromJson;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Represents a hosting.
 */
public class Hosting {

    private final int id, packageID, orderID, nextDueInDays, hasUnpaidInvoice;
    private final String productName, productGroup, label, module, ip, domain, nextDueHumanReadable;
    private final boolean active, suspended, hasInvoice, cancellation;
    private final OffsetDateTime createdAt, nextDueDate;
    private final float price, priceOnOrder;
    private final HostingStatus status;
    private final HostingPromotion promotion;
    private final List<HostingOption> options = new ArrayList<>();
    private final BillingCycle billingCycle;

    public Hosting(JsonObject o) {
        id = ReadFromJson.readInt(o, "id");
        productName = ReadFromJson.readString(o, "product_name");
        productGroup = ReadFromJson.readString(o, "product_group");
        label = ReadFromJson.readString(o, "label");
        module = ReadFromJson.readString(o, "module");
        packageID = ReadFromJson.readInt(o, "package_id");
        orderID = ReadFromJson.readInt(o, "order_id");
        ip = ReadFromJson.readString(o, "ipaddress");
        active = ReadFromJson.readBool(o, "active");
        suspended = ReadFromJson.readBool(o, "suspended");
        domain = ReadFromJson.readString(o, "domain");
        status = HostingStatus.getByName(ReadFromJson.readString(o, "status"));
        billingCycle = BillingCycle.getByName(ReadFromJson.readString(o, "billingcycle"));
        createdAt = OffsetDateTime.parse(ReadFromJson.readString(o, "created_at"));
        nextDueDate = OffsetDateTime.parse(ReadFromJson.readString(o, "next_due_date"));
        nextDueInDays = ReadFromJson.readInt(o, "next_due_in");
        nextDueHumanReadable = ReadFromJson.readString(o, "next_due_human");
        price = ReadFromJson.readFloat(o, "amount");
        priceOnOrder = ReadFromJson.readFloat(o, "firstpaymentamount");
        hasInvoice = ReadFromJson.readBool(o, "has_invoices");
        hasUnpaidInvoice = ReadFromJson.readInt(o, "has_unpaid_invoice");
        cancellation = ReadFromJson.readBool(o, "cancellation");

        if(o.has("promotion"))
            promotion = new HostingPromotion(o.get("promotion").getAsJsonObject());
        else
            promotion = null;

        JsonElement jsonElement = o.get("options");
        if(jsonElement != null)
            jsonElement.getAsJsonArray().forEach(element -> options.add(new HostingOption(element.getAsJsonObject())));
    }

    /**
     * Returns the id of the hosting.
     * @return the hosting id
     */
    @SuppressWarnings("unused")
    public int getId() {
        return id;
    }

    /**
     * Returns the id of the package.
     * @return the package id
     */
    @SuppressWarnings("unused")
    public int getPackageID() {
        return packageID;
    }

    /**
     * Returns the id of the order from the hosting
     * @return the order id
     */
    @SuppressWarnings("unused")
    public int getOrderID() {
        return orderID;
    }

    /**
     * Returns the next date when the hosting expires and needs to be renewed.
     * @return the next expiration date
     */
    @SuppressWarnings("unused")
    public int getNextDueInDays() {
        return nextDueInDays;
    }

    /**
     * Returns the name of the hosting.
     * @return the hosting name
     */
    @SuppressWarnings("unused")
    public String getProductName() {
        return productName;
    }

    /**
     * Returns the product group of the hosting.
     * @return the product group
     */
    @SuppressWarnings("unused")
    public String getProductGroup() {
        return productGroup;
    }

    /**
     * Returns the name of the hosting that was assigned by the customer. The string is empty if no name was assigned.
     * @return the label
     */
    @SuppressWarnings("unused")
    public String getLabel() {
        return label;
    }

    /**
     * Returns the module of the hosting. For example, the module can be virtualizor or dedicated_hetzner.
     * @return the hosting module
     */
    @SuppressWarnings("unused")
    public String getModule() {
        return module;
    }

    /**
     * Returns the ip of the hosting
     * @return the hosting ip
     */
    @SuppressWarnings("unused")
    public String getIp() {
        return ip;
    }

    /**
     * Returns the domain assigned by Prepaid-Hoster.de
     * @return the domain
     */
    @SuppressWarnings("unused")
    public String getDomain() {
        return domain;
    }

    /**
     * Returns the {@link HostingStatus}
     * @return {@link HostingStatus}
     */
    @SuppressWarnings("unused")
    public HostingStatus getStatus() {
        return status;
    }

    /**
     * Specifies the intervals at which hosting must be paid for in order not to expire.
     * @return the {@link BillingCycle}
     */
    @SuppressWarnings("unused")
    public BillingCycle getBillingCycle() {
        return billingCycle;
    }

    /**
     * Returns the next date when the hosting expires and needs to be renewed in human readable form.
     * @return the next expiration date
     */
    @SuppressWarnings("unused")
    public String getNextDueHumanReadable() {
        return nextDueHumanReadable;
    }

    /**
     * Returns whether the hosting is currently active
     * @return true, when the hosting is currently active
     */
    @SuppressWarnings("unused")
    public boolean isActive() {
        return active;
    }

    /**
     * Returns whether the hosting is suspended and cant be used.
     * @return true if the hosting is suspended
     */
    @SuppressWarnings("unused")
    public boolean isSuspended() {
        return suspended;
    }

    /**
     * Returns whether the hosting has open invoices.
     * @return true, if there are open invoices.
     */
    @SuppressWarnings("unused")
    public boolean hasInvoice() {
        return hasInvoice;
    }

    /**
     * Returns the number of unpaid invoices for this hosting.
     * @return unpaid invoices
     */
    @SuppressWarnings("unused")
    public int unpaidInvoice() {
        return hasUnpaidInvoice;
    }

    /**
     * Returns whether the hosting is cancelled.
     * @return true, if the hosting is cancelled
     */
    @SuppressWarnings("unused")
    public boolean isCancelled() {
        return cancellation;
    }

    /**
     * Returns whether the hosting has an (active) discount.
     * @return true, if the hosting has a promotion
     * @see Hosting#getPromotion()
     */
    @SuppressWarnings("unused")
    public boolean hasPromotion() {
        return promotion != null;
    }

    /**
     * Returns the date on that the hosting was created.
     * @return the creation date
     */
    @SuppressWarnings("unused")
    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    /**
     * Returns the next date until which the hosting must be extended, unless it is to expire.
     * @return the next due date
     */
    @SuppressWarnings("unused")
    public OffsetDateTime getNextDueDate() {
        return nextDueDate;
    }

    /**
     * Returns the current price of the hosting.
     * @return the current price
     */
    @SuppressWarnings("unused")
    public float getPrice() {
        return price;
    }

    /**
     * Returns the price of hosting at the time of creation.
     * @return the price on order
     */
    @SuppressWarnings("unused")
    public float getPriceOnOrder() {
        return priceOnOrder;
    }

    /**
     * Returns an {@link Optional} with the promotion, if available.
     * @return the promotion
     * @see Hosting#hasPromotion()
     */
    @SuppressWarnings("unused")
    public Optional<HostingPromotion> getPromotion() {
        return Optional.ofNullable(promotion);
    }

    /**
     * Returns a list of all options of the hosting. Options specify exactly how the server is configured and can e.g.
     * specify the number of RAM or the number of CPU cores.
     * @return hosting options
     */
    @SuppressWarnings("unused")
    public List<HostingOption> getOptions() {
        return options;
    }

    /**
     * Returns all parameters of the hosting.
     * @return all parameters
     */
    @Override
    public String toString() {
        return "Hosting{" +
                "id=" + id +
                ", packageID=" + packageID +
                ", orderID=" + orderID +
                ", nextDueInDays=" + nextDueInDays +
                ", productName='" + productName + '\'' +
                ", productGroup='" + productGroup + '\'' +
                ", label='" + label + '\'' +
                ", module='" + module + '\'' +
                ", ip='" + ip + '\'' +
                ", domain='" + domain + '\'' +
                ", billingCycle='" + billingCycle + '\'' +
                ", nextDueHumanReadable='" + nextDueHumanReadable + '\'' +
                ", active=" + active +
                ", suspended=" + suspended +
                ", hasInvoice=" + hasInvoice +
                ", hasUnpaidInvoice=" + hasUnpaidInvoice +
                ", cancellation=" + cancellation +
                ", createdAt=" + createdAt +
                ", nextDueDate=" + nextDueDate +
                ", price=" + price +
                ", priceOnOrder=" + priceOnOrder +
                ", status=" + status +
                ", promotion=" + promotion +
                ", options=" + options +
                '}';
    }
}
