package no.eg.nexstephub.client.domain;

import java.util.Objects;

public class Client {
    private static final long serialVersionUID = 2943330299296958488L;
    private String urlForAuthorizationCode;

    public enum ClientTypeEnum {GOOGLE,AUTH0}
    private ClientTypeEnum clientType;
    private String clientId;
    private String clientSecret;
    private boolean exportEnabled;
    private boolean importEnabled;
    private String audience;
    private String grantType;
    private String urlForGetToken;
    private String urlForExport;
    private String urlForImport;
    private boolean bypassZipping;
    private String applicationId;
    private Integer nexstepCompany;
    private String nexstepFileGroup;
    private String domain;
    private String customField1;
    private String customField2;

    public Client(ClientTypeEnum type) {
        this.clientType = type;
    }

    public ClientTypeEnum getClientType() {
        return clientType;
    }

    public void setClientType(ClientTypeEnum clientType) {
        this.clientType = clientType;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    public boolean isExportEnabled() {
        return exportEnabled;
    }

    public void setExportEnabled(boolean exportEnabled) {
        this.exportEnabled = exportEnabled;
    }

    public boolean isImportEnabled() {
        return importEnabled;
    }

    public void setImportEnabled(boolean importEnabled) {
        this.importEnabled = importEnabled;
    }

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getGrantType() {
        return grantType;
    }

    public void setGrantType(String grantType) {
        this.grantType = grantType;
    }

    public String getUrlForGetToken() {
        return urlForGetToken;
    }

    public void setUrlForGetToken(String urlForGetToken) {
        this.urlForGetToken = urlForGetToken;
    }

    public String getUrlForExport() {
        return urlForExport;
    }

    public void setUrlForExport(String urlForExport) {
        this.urlForExport = urlForExport;
    }

    public String getUrlForImport() {
        return urlForImport;
    }

    public void setUrlForImport(String urlForImport) {
        this.urlForImport = urlForImport;
    }

    public boolean isBypassZipping() {
        return bypassZipping;
    }

    public void setBypassZipping(boolean bypassZipping) {
        this.bypassZipping = bypassZipping;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public Integer getNexstepCompany() {
        return nexstepCompany;
    }

    public void setNexstepCompany(Integer nexstepCompany) {
        this.nexstepCompany = nexstepCompany;
    }

    public void setNexstepFileGroup(String nexstepFileGroup) {
        this.nexstepFileGroup = nexstepFileGroup;
    }

    public String getNexstepFileGroup() {
        return nexstepFileGroup;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public String getDomain() {
        return domain;
    }

    public void setCustomField1(String customField1) {
        this.customField1 = customField1;
    }

    public String getCustomField1() {
        return customField1;
    }

    public String getCustomField2() {
        return customField2;
    }

    public String getUrlForAuthorizationCode() {
        return urlForAuthorizationCode;
    }

    public void setCustomField2(String customField2) {
        this.customField2 = customField2;
    }

    public void setUrlForAuthorizationCode(String urlForAuthorizationCode) {
        this.urlForAuthorizationCode = urlForAuthorizationCode;
    }

    @Override
    public String toString() {
        return "Client{" +
                "urlForAuthorizationCode='" + urlForAuthorizationCode + '\'' +
                ", clientType=" + clientType +
                ", clientId='" + clientId + '\'' +
                ", clientSecret='" + clientSecret + '\'' +
                ", exportEnabled=" + exportEnabled +
                ", importEnabled=" + importEnabled +
                ", audience='" + audience + '\'' +
                ", grantType='" + grantType + '\'' +
                ", urlForGetToken='" + urlForGetToken + '\'' +
                ", urlForExport='" + urlForExport + '\'' +
                ", urlForImport='" + urlForImport + '\'' +
                ", bypassZipping=" + bypassZipping +
                ", applicationId='" + applicationId + '\'' +
                ", nexstepCompany=" + nexstepCompany +
                ", nexstepFileGroup='" + nexstepFileGroup + '\'' +
                ", domain='" + domain + '\'' +
                ", customField1='" + customField1 + '\'' +
                ", customField2='" + customField2 + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Client client = (Client) o;
        return exportEnabled == client.exportEnabled && importEnabled == client.importEnabled && bypassZipping == client.bypassZipping && Objects.equals(urlForAuthorizationCode, client.urlForAuthorizationCode) && clientType == client.clientType && Objects.equals(clientId, client.clientId) && Objects.equals(clientSecret, client.clientSecret) && Objects.equals(audience, client.audience) && Objects.equals(grantType, client.grantType) && Objects.equals(urlForGetToken, client.urlForGetToken) && Objects.equals(urlForExport, client.urlForExport) && Objects.equals(urlForImport, client.urlForImport) && Objects.equals(applicationId, client.applicationId) && Objects.equals(nexstepCompany, client.nexstepCompany) && Objects.equals(nexstepFileGroup, client.nexstepFileGroup) && Objects.equals(domain, client.domain) && Objects.equals(customField1, client.customField1) && Objects.equals(customField2, client.customField2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(urlForAuthorizationCode, clientType, clientId, clientSecret, exportEnabled, importEnabled, audience, grantType, urlForGetToken, urlForExport, urlForImport, bypassZipping, applicationId, nexstepCompany, nexstepFileGroup, domain, customField1, customField2);
    }
}
