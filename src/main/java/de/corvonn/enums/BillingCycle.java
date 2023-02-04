package de.corvonn.enums;

import java.util.HashMap;

/**
 * An enum that specifies the intervals at which a rented resource must be paid for.
 */
public enum BillingCycle {
    /**
     * The rented resource must be paid monthly.
     */
    MONTHLY("Monthly"),
    /**
     * The rented resource must be paid every three months.
     */
    QUARTERLY("Quarterly"),
    /**
     * The rented resource must be paid semi-annually.
     */
    SEMI_ANNUALLY("Semi-Annually"),
    /**
     * The rented resource must be paid annually.
     */
    ANNUALLY("Annually");

    private final String cycleName;

    BillingCycle(String type) {
        this.cycleName = type;
    }

    /**
     * Returns the name used by the prepaid hoster API.
     * @return the name
     */
    public String getCycleName() {
        return cycleName;
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
