package de.corvonn.enums;

import java.util.HashMap;

/**
 * An enum that specifies the intervals at which a rented resource must be paid for.
 */
public enum BillingCycle {
    /**
     * The rented resource must be paid monthly.
     */
    MONTHLY("Monthly", 1),
    /**
     * The rented resource must be paid every three months.
     */
    QUARTERLY("Quarterly", 3),
    /**
     * The rented resource must be paid semi-annually.
     */
    SEMI_ANNUALLY("Semi-Annually", 6),
    /**
     * The rented resource must be paid annually.
     */
    ANNUALLY("Annually", 12);

    private final String cycleName;
    private final int billingCycle;

    BillingCycle(String type, int billingCycle) {
        this.cycleName = type;
        this.billingCycle = billingCycle;
    }

    /**
     * Returns the name used by the prepaid hoster API.
     * @return the name
     */
    public String getCycleName() {
        return cycleName;
    }

    /**
     * Specifies the number of months in a pay period. "The service must be paid every x months".
     * @return the months per billingCycle
     */
    @SuppressWarnings("unused")
    public int getMonthsPerBillingCycle() {
        return billingCycle;
    }

    private static final HashMap<String, BillingCycle> typeHashMap = new HashMap<>();
    public static BillingCycle getByName(String type) {
        if(typeHashMap.isEmpty()) {
            for(BillingCycle t : BillingCycle.values()) {
                typeHashMap.put(t.getCycleName(), t);
            }
        }

        return typeHashMap.get(type);
    }
}
