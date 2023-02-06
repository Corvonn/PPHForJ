package de.corvonn;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import de.corvonn.client.domains.Domain;
import de.corvonn.client.donation.DonationConfig;
import de.corvonn.client.hostings.CancelledHosting;
import de.corvonn.client.hostings.Hosting;
import de.corvonn.client.invoices.Invoice;
import de.corvonn.client.invoices.InvoiceDonationItem;
import de.corvonn.client.invoices.InvoiceItem;
import de.corvonn.client.invoices.Transaction;
import de.corvonn.enums.InvoiceType;
import de.corvonn.utils.ReadFromJson;

import javax.net.ssl.HttpsURLConnection;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
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
            INVOICES_PATH = "client/invoices",
            DOMAINS_PATH = "client/domains",
            HOSTINGS_PATH = "client/hostings",
            DONATION_CONFIG_PATH = "client/donation/config";

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
                                donatorText.isJsonNull() ? null : donatorText.getAsString(),
                                ReadFromJson.readInt(item, "relid")));
                    }else{
                        invoiceItems.add(new InvoiceItem(item.get("id").getAsInt(),
                                item.get("invoiceid").getAsInt(), InvoiceType.getByType(item.get("type").getAsString()),
                                item.get("short_description").getAsString(), item.get("description").getAsString(),
                                item.get("amount").getAsFloat(), item.get("donation").getAsBoolean(),
                                ReadFromJson.readInt(item, "relid")));
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
     * Requests all domains from the given account.
     * @return a list of {@link Domain}
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method can take several seconds, since the API of PPH needs some time to process this request
     */
    @SuppressWarnings("unused")
    public List<Domain> getDomains() throws IOException {
        return getDomainsIntern(null);
    }

    /**
     * Requests one specific domain from the given account.
     * @param domainID the ID of the domain to be requested. Use null to request all domains.
     * @return a list of {@link Domain}
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method can take several seconds, since the API of PPH needs some time to process this request
     */
    @SuppressWarnings("unused")
    public Domain getDomain(int domainID) throws IOException {
        List<Domain> c = getDomainsIntern(domainID);
        if(c.size() == 1) return c.get(0);
        return null;
    }

    /**
     * Requests all or a specific domain(s) from the given account.
     * @param domainID the ID of the domain to be requested. Use null to request all domains.
     * @return a list of {@link Domain}
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method can take several seconds, since the API of PPH needs some time to process this request
     */
    @SuppressWarnings("unused")
    private List<Domain> getDomainsIntern(Integer domainID) throws IOException {
        String getParams = "";
        if(domainID != null) getParams = "/" + domainID;
        HttpsURLConnection con = getConnection(DOMAINS_PATH + getParams);
        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        }catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The given domainID " + domainID + " is not valid.");
        }
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        con.disconnect();

        List<Domain> domains = new ArrayList<>();
        if(getParams.equals("")) {
            JsonParser.parseString(content.toString()).getAsJsonObject().get("data").getAsJsonArray().forEach(element ->
                    domains.add(new Domain(element.getAsJsonObject())));
        }else{
            domains.add(new Domain(JsonParser.parseString(content.toString()).getAsJsonObject().get("data").getAsJsonObject()));
        }
        return domains;
    }


    /**
     * Requests a specific hosting from the given account.
     * @param hostID the id of the host that should be requested
     * @return the requested {@link Hosting}
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method can take several seconds, since the API of PPH needs some time to process this request
     */
    @SuppressWarnings("unused")
    public Hosting getHosting(int hostID) throws IOException{
        try {
            return getHosting(getHostingsJson("/" + hostID).get("data").getAsJsonObject());
        }catch (FileNotFoundException e) {
            throw new IllegalArgumentException("The given hostingID is not valid.");
        }
    }

    /**
     * Requests up to 25 active hostings from the given account.
     * @return a {@link List} of {@link Hosting}
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method can take several seconds, since the API of PPH needs some time to process this request
     */
    @SuppressWarnings("unused")
    public List<Hosting> getHostings() throws IOException {
        return getHostings(true, 25);
    }

    /**
     * Requests up to 25 hostings from the given account.
     * @param onlyActive whether only active hostings should be requested
     * @return a {@link List} of {@link Hosting}
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method can take several seconds, since the API of PPH needs some time to process this request
     */
    @SuppressWarnings("unused")
    public List<Hosting> getHostings(boolean onlyActive) throws IOException {
        return getHostings(onlyActive, 25);
    }

    /**
     * Requests all hostings from the given account.
     * @param onlyActive whether only active hostings should be requested
     * @param amount the amount of hostings that should be requested
     * @return a {@link List} of {@link Hosting}
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method can take several seconds, since the API of PPH needs some time to process this request
     */
    public List<Hosting> getHostings(boolean onlyActive, int amount) throws IOException {
        List<Hosting> hostings = new ArrayList<>();
        getHostingsJson((onlyActive ? "?active=true" : "") + "?per_page=" + amount).get("data").getAsJsonArray().forEach(element ->
                hostings.add(getHosting(element.getAsJsonObject())));
        return hostings;
    }

    /**
     * The method decides whether the host has been canceled or whether it is a normal host and returns the corresponding object.
     * @param data the data-JsonObject
     * @return Hosting
     */
    private Hosting getHosting(JsonObject data) {
        if(data.has("cancellation_data")) {
            return new CancelledHosting(data);
        }else{
            return new Hosting(data);
        }
    }

    @SuppressWarnings("unused")
    public DonationConfig getDonationConfig() throws IOException {
        HttpsURLConnection con = getConnection(DONATION_CONFIG_PATH);
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        con.disconnect();

        return new DonationConfig(JsonParser.parseString(content.toString()).getAsJsonObject().get("data").getAsJsonObject());
    }

    /**
     * Requests all hostings from the given account.
     * @param getParams parameters to be added to the request link
     * @return a {@link List} of {@link Hosting}
     * @throws IOException Signals that an I/O exception of some sort has occurred
     * @apiNote this method can take several seconds, since the API of PPH needs some time to process this request
     */
    private JsonObject getHostingsJson(String getParams) throws IOException{
        String s = HOSTINGS_PATH;
        if(getParams != null) s += getParams;
        HttpsURLConnection con = getConnection(s);
        BufferedReader reader;
        reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            content.append(line);
        }
        reader.close();
        con.disconnect();
        return JsonParser.parseString(content.toString()).getAsJsonObject();
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
