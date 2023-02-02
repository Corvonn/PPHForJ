package de.corvonn;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.corvonn.client.invoices.Invoice;
import de.corvonn.client.invoices.InvoiceDonationItem;
import de.corvonn.client.invoices.InvoiceItem;
import de.corvonn.client.invoices.Transaction;
import de.corvonn.enums.InvoiceType;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Main Object that is needed to generate requests to prepaid-hoster
 */
public class PPHForJ {

    private static final String API_WEB_LINK = "https://api.pph.sh/",
            INVOICES_PATH = "client/invoices";

    private final String token;

    /**
     * Creates the SDK-objekt
     * @param token the token that you can generate at <a href=https://www.vionity.de/clientarea/api-tokens>the website
     *              from prepaid-hoster.de</a>
     */
    @SuppressWarnings("unused")
    public PPHForJ(String token) {
        this.token = token;
    }

    /**
     * Requests the invoices from the given account.
     * @return all invoices
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method may take several seconds since the api from PPH takes a long time for this request
     */
    @SuppressWarnings("unused")
    public Collection<Invoice> getInvoices() throws IOException {
        return getInvoices(InvoiceType.ANY, 15);
    }
    /**
     * Requests the invoices from the given account.
     * @param type witch invoice type should be requested. Use {@link InvoiceType#ANY} to request all invoices.
     * @return all invoices
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method may take several seconds since the api from PPH takes a long time for this request
     */
    @SuppressWarnings("unused")
    public Collection<Invoice> getInvoices(InvoiceType type) throws IOException {
        return getInvoices(type, 15);
    }
    /**
     * Requests the invoices from the given account.
     * @param amount the amount of invoices that should be requested
     * @return all invoices
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method may take several seconds since the api from PPH takes a long time for this request
     */
    @SuppressWarnings("unused")
    public Collection<Invoice> getInvoices(int amount) throws IOException {
        return getInvoices(InvoiceType.ANY, amount);
    }

    /**
     * Requests the invoices from the given account.
     * @param type witch invoice type should be requested. Use {@link InvoiceType#ANY} to request all invoices.
     * @param amount the amount of invoices that should be requested
     * @return all invoices
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method may take several seconds since the api from PPH takes a long time for this request
     */
    public Collection<Invoice> getInvoices(InvoiceType type, int amount) throws IOException {
        String getParams = "";
        if(type != InvoiceType.ANY) getParams += "?type=" + type.getType();
        if(amount != 15) getParams += "?per_page=" + amount;
        HttpsURLConnection con = getConnection(INVOICES_PATH + getParams);
        BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        con.disconnect();

        JsonObject object = JsonParser.parseString(content.toString()).getAsJsonObject();

        Collection<Invoice> invoices = new ArrayList<>();

        object.get("data").getAsJsonArray().forEach(jsonElement -> {
            JsonObject o = jsonElement.getAsJsonObject();
            Transaction transaction = null;
            if(o.has("transaction")) {
                JsonObject tO = o.get("transaction").getAsJsonObject();
                transaction = new Transaction(tO.get("id").getAsInt(), tO.get("invoiceid").getAsInt(),
                        tO.get("gateway").getAsString(), tO.get("diff_for_humans").getAsString(),
                        tO.get("description").getAsString(), tO.get("amountin").getAsFloat(),
                        tO.get("amountout").getAsFloat(), OffsetDateTime.parse(tO.get("date").getAsString()));
            }

            List<InvoiceItem> invoiceItems = new ArrayList<>();
            if(o.has("items")) {
                JsonArray array = o.get("items").getAsJsonArray();
                array.forEach(element -> {
                    JsonObject item = element.getAsJsonObject();
                    if(item.get("donation").getAsBoolean()) {
                        JsonElement donatorName = item.get("donator_name");
                        JsonElement donatorText = item.get("donator_text");
                        invoiceItems.add(new InvoiceDonationItem(item.get("id").getAsInt(),
                                item.get("invoiceid").getAsInt(), InvoiceType.getByType(item.get("type").getAsString()),
                                item.get("short_description").getAsString(), item.get("description").getAsString(),
                                item.get("amount").getAsFloat(), item.get("donation").getAsBoolean(),
                                donatorName.isJsonNull() ? null : donatorName.getAsString(),
                                donatorText.isJsonNull() ? null : donatorText.getAsString()));
                    }else{
                        invoiceItems.add(new InvoiceItem(item.get("id").getAsInt(),
                                item.get("invoiceid").getAsInt(), InvoiceType.getByType(item.get("type").getAsString()),
                                item.get("short_description").getAsString(), item.get("description").getAsString(),
                                item.get("amount").getAsFloat(), item.get("donation").getAsBoolean()));
                    }
                });
            }

            invoices.add(new Invoice(o.get("id").getAsInt(), OffsetDateTime.parse(o.get("datepaid").getAsString()),
                    o.get("subtotal").getAsFloat(), o.get("credit").getAsFloat(), o.get("tax").getAsFloat(),
                    o.get("taxrate").getAsFloat(), o.get("total").getAsFloat(), o.get("status").getAsString(),
                    o.get("status_label").getAsString(), o.get("donation_invoice").getAsBoolean(),
                    transaction, invoiceItems));
        });

        return invoices;
    }


    /**
     * Receives the connection for the requests.
     * @param path the path to the request. Eg client/invoices?type=donation
     * @return {@link HttpsURLConnection}
     */
    private HttpsURLConnection getConnection(String path) {
        try {
            path = API_WEB_LINK + path;
            URL url = new URL(path);
            HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
            con.setRequestProperty("Authorization", "Bearer " + token);
            con.setRequestProperty("Content-Type", "application/json");
            return con;
        }catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }
}
