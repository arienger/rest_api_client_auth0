package no.eg.nexstephub.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;

/**
 * ByggOffice format
 * ByggOffice kundemodul
 * Overføring av regnskapstranser via skyggetabell
 * https://www.nois.no/produkter/prosjektstyring/isy-byggoffice/
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ByggOfficeTransactionDto {
    /**
     * ImportId
     */
    private String importId;
    /**
     * Klientnr
     */
    private String clientNo;
    /**
     * Prosjekt
     */
    private String project;
    /**
     * Bilagsnr
     */
    private Number voucherNo;
    /**
     * BilagsnrS
     */
    private String voucherNoAsString;
    /**
     * BilagsnrSekvnr
     */
    private Number voucherNoSequence;
    /**
     * BilagsnrSplitnr
     */
    private Number voucherNoSplitNo;
    /**
     * CRMID
     */
    private Number crmId;

    /**
     * LevNr
     */
    private Number vendorNo;
    /**
     * LevNavn
     */
    private String vendorName;
    /**
     * Prodkode
     */
    private String productionCode;
    /**
     * KostArtnr
     */
    private String generalLedgerAccount;
    /**
     * Dato
     */
    private Instant date;
    /**
     * Periode
     */
    private Number period;
    /**
     * Mengde
     */
    private Number quantity;
    /**
     * Belop
     */
    private Number amount;
    /**
     * Kommentar
     */
    private String comment;
    /**
     * Mottaksnr
     */
    private String reference;
    /**
     * ArtNr
     */
    private String articleNo;
    /**
     * ArtType
     */
    private Number articleType;
    /**
     * Kilde
     */
    private Number source;
    /**
     * TypeBelop
     */
    private Number amountType;
    /**
     * TypePPK
     */
    private Number ppkType;
    /**
     * Status
     */
    private Number status;
    /**
     * Statusdato
     */
    private Instant statusDate;
    /**
     * StatusTekst
     */
    private String statusText;
    /**
     * webid
     */
    private String webId;
    /**
     * Reservert1
     */
    private String reserved1;
    /**
     * OrgNr
     */
    private String orgNo;


    public ByggOfficeTransactionDto() {

    }

    public String getImportId() {
        return this.importId;
    }

    public void setImportId(final String importId) {
        this.importId = importId;
    }

    public String getClientNo() {
        return this.clientNo;
    }

    public void setClientNo(final String clientNo) {
        this.clientNo = clientNo;
    }

    public String getProject() {
        return this.project;
    }

    public void setProject(final String project) {
        this.project = project;
    }

    public Number getVoucherNo() {
        return this.voucherNo;
    }

    public void setVoucherNo(final Number voucherNo) {
        this.voucherNo = voucherNo;
    }

    public String getVoucherNoAsString() {
        return this.voucherNoAsString;
    }

    public void setVoucherNoAsString(final String voucherNoAsString) {
        this.voucherNoAsString = voucherNoAsString;
    }

    public Number getVoucherNoSequence() {
        return this.voucherNoSequence;
    }

    public void setVoucherNoSequence(final Number voucherNoSequence) {
        this.voucherNoSequence = voucherNoSequence;
    }

    public Number getVoucherNoSplitNo() {
        return this.voucherNoSplitNo;
    }

    public void setVoucherNoSplitNo(final Number voucherNoSplitNo) {
        this.voucherNoSplitNo = voucherNoSplitNo;
    }

    public Number getCrmId() {
        return this.crmId;
    }

    public void setCrmId(final Number crmId) {
        this.crmId = crmId;
    }

    public Number getVendorNo() {
        return this.vendorNo;
    }

    public void setVendorNo(final Number vendorNo) {
        this.vendorNo = vendorNo;
    }

    public String getVendorName() {
        return this.vendorName;
    }

    public void setVendorName(final String vendorName) {
        this.vendorName = vendorName;
    }

    public String getProductionCode() {
        return this.productionCode;
    }

    public void setProductionCode(final String productionCode) {
        this.productionCode = productionCode;
    }

    public String getGeneralLedgerAccount() {
        return this.generalLedgerAccount;
    }

    public void setGeneralLedgerAccount(final String generalLedgerAccount) {
        this.generalLedgerAccount = generalLedgerAccount;
    }

    public Instant getDate() {
        return this.date;
    }

    public void setDate(final Instant date) {
        this.date = date;
    }

    public Number getPeriod() {
        return this.period;
    }

    public void setPeriod(final Number period) {
        this.period = period;
    }

    public Number getQuantity() {
        return this.quantity;
    }

    public void setQuantity(final Number quantity) {
        this.quantity = quantity;
    }

    public Number getAmount() {
        return this.amount;
    }

    public void setAmount(final Number amount) {
        this.amount = amount;
    }

    public String getComment() {
        return this.comment;
    }

    public void setComment(final String comment) {
        this.comment = comment;
    }

    public String getReference() {
        return this.reference;
    }

    public void setReference(final String reference) {
        this.reference = reference;
    }

    public String getArticleNo() {
        return this.articleNo;
    }

    public void setArticleNo(final String articleNo) {
        this.articleNo = articleNo;
    }

    public Number getArticleType() {
        return this.articleType;
    }

    public void setArticleType(final Number articleType) {
        this.articleType = articleType;
    }

    public Number getSource() {
        return this.source;
    }

    public void setSource(final Number source) {
        this.source = source;
    }

    public Number getAmountType() {
        return this.amountType;
    }

    public void setAmountType(final Number amountType) {
        this.amountType = amountType;
    }

    public Number getPpkType() {
        return this.ppkType;
    }

    public void setPpkType(final Number ppkType) {
        this.ppkType = ppkType;
    }

    public Number getStatus() {
        return this.status;
    }

    public void setStatus(final Number status) {
        this.status = status;
    }

    public Instant getStatusDate() {
        return this.statusDate;
    }

    public void setStatusDate(final Instant statusDate) {
        this.statusDate = statusDate;
    }

    public String getStatusText() {
        return this.statusText;
    }

    public void setStatusText(final String statusText) {
        this.statusText = statusText;
    }

    public String getWebId() {
        return this.webId;
    }

    public void setWebId(final String webId) {
        this.webId = webId;
    }

    public String getReserved1() {
        return this.reserved1;
    }

    public void setReserved1(final String reserved1) {
        this.reserved1 = reserved1;
    }

    public String getOrgNo() {
        return this.orgNo;
    }

    public void setOrgNo(final String orgNo) {
        this.orgNo = orgNo;
    }

    public String toString() {
        return "Bilag " + getVoucherNoAsString() + ", Konto " + getGeneralLedgerAccount() + ", Kommentar " + getComment();
    }
}

