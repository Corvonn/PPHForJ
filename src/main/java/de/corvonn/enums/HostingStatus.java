package de.corvonn.enums;

import java.util.HashMap;

/**
 * The HostingStatus describes the status of the given host.
 */
public enum HostingStatus {
    /**
     * Indicates that the host is ready for use.
     */
    ACTIVE("Active"),
    /**
     * Indicates that the host is not yet ready for use.
     */
    PENDING("Pending"),
    /**
     * The host was terminated by the customer after the end of the billing period and is no longer available.
     */
    CANCELLED("Cancelled"),
    /**
     * The host has been terminated by the customer with immediate effect and is no longer available.
     */
    TERMINATED("Terminated"),
    /**
     * The host was blocked due to scam.
     */
    FRAUD("Fraud"),
    /**
     * Indicates that the host has been locked and cannot be used at the moment.
     */
    SUSPENDED("Suspended");


    private final String name;

    HostingStatus(String name) {
        this.name = name;
    }

    /**
     * Returns the name used by the PPH API
     * @return the name
     */
    public String getName() {
        return name;
    }


    private static final HashMap<String, HostingStatus> statusHashMap = new HashMap<>();
    public static HostingStatus getByName(String name) {
        if(statusHashMap.isEmpty()) {
            for(HostingStatus t : HostingStatus.values()) {
                statusHashMap.put(t.getName(), t);
            }
        }

        return statusHashMap.get(name);
    }
}
