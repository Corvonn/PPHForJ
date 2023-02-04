package de.corvonn.enums;


import java.util.HashMap;

/**
 * The InvoiceType describes the types that an invoiceItem could be.
 */
public enum InvoiceType {
    /**
     * Nur Spenden werden gelistet
     */
    DONATION("donation"),
    /**
     * Upgrade und Downgrades werden gelistet
     */
    UPGRADE("Upgrade"),
    /**
     * Alle Aufladungen (auch Spenden) werden gelistet
     */
    ADD_FUNDS("AddFunds"),
    /**
     * Verlängerungen und Neukauf von Servern werden gelistet
     */
    HOSTING("Hosting"),
    /**
     * Alle Rechnungen mit einem RabattCode werden gelistet
     */
    PROMO_HOSTING("PromoHosting"),
    /**
     * Alle Rechnungen werden gelistet
     */
    ANY(null);

    private final String type;

    InvoiceType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    private static final HashMap<String, InvoiceType> typeHashMap = new HashMap<>();
    public static InvoiceType getByType(String type) {
        if(typeHashMap.isEmpty()) {
            for(InvoiceType t : InvoiceType.values()) {
                typeHashMap.put(t.getType(), t);
            }
        }

        return typeHashMap.get(type);
    }
}
