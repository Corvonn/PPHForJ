package de.corvonn.enums;

import java.util.HashMap;

/**
 * The DomainStatus describes the status of the given domain. For example, the status can be used to read out whether
 * the domain is ready for use.
 */
public enum DomainStatus {
    /**
     * Indicates that the domain is not yet registered and therefore not ready for use.
     */
    PENDING("Pending"),
    /**
     * Indicates that the domain still needs to be transferred from another hoster.
     */
    PENDING_TRANSFER("Pending Transfer"),
    /**
     * Indicates that the domain is registered and ready for use.
     */
    ACTIVE("Active"),
    /**
     * Indicates that the domain has expired and must be renewed in order to continue using it.
     */
    EXPIRED("Expired"),
    /**
     * The domain was transferred to another hoster.
     */
    TRANSFERRED_AWAY("Transferred Away"),
    /**
     * The domain was canceled and cannot be used (anymore).
     */
    CANCELLED("Cancelled"),
    /**
     * The domain was blocked due to scam.
     */
    FRAUD("Fraud");


    private final String name;

    DomainStatus(String name) {
        this.name = name;
    }

    /**
     * Returns the name used by the PPH API
     * @return the name
     */
    public String getName() {
        return name;
    }


    private static final HashMap<String, DomainStatus> statusHashMap = new HashMap<>();
    public static DomainStatus getByName(String name) {
        if(statusHashMap.isEmpty()) {
            for(DomainStatus t : DomainStatus.values()) {
                statusHashMap.put(t.getName(), t);
            }
        }

        return statusHashMap.get(name);
    }
}
