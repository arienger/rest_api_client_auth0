package no.eg.nexstephub.client.domain;

import java.time.LocalDateTime;

import no.eg.nexstephub.client.util.DateTimeUtil;
import org.apache.commons.lang3.Validate;

public class BearerAuthenticationToken {

    private final Client client;
    private String scope;
    private String accessToken;
    private long expiresInSeconds;
    private String tokenType;
    private LocalDateTime expireDateTime;

    public BearerAuthenticationToken(Client client) {
        Validate.notNull(client, "client: null");
        this.client = client;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getScope() {
        return scope;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public long getExpiresInSeconds() {
        return expiresInSeconds;
    }

    public void setExpiresInSeconds(long expiresInSeconds) {
        this.expiresInSeconds = expiresInSeconds;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public String getTokenType() {
        return tokenType;
    }

    private LocalDateTime getExpireDateTime() {
        if (expireDateTime == null)
            expireDateTime = calculateExpireDateTime();
        return expireDateTime;
    }

    private LocalDateTime calculateExpireDateTime() {
        if (expiresInSeconds <= 0) return LocalDateTime.now();

        return LocalDateTime.now().plusSeconds(expiresInSeconds);
    }

    public String getExpireDateTimeAsString() {
        if (getExpireDateTime() == null) return null;
        return getExpireDateTime().format(DateTimeUtil.dateTimeFormatter_yyyy_MM_dd_HH_mm_ss);
    }

    public boolean isExpired() {
        if (getExpireDateTime() == null) return true;
        return (getExpireDateTime().isBefore(LocalDateTime.now()));
    }

    public String toJson() {
        return new StringBuilder("{\"access_token\":\"").append(getAccessToken())
                .append("\",\"scope\":\"").append(scope).append("\",\"expires_in\":")
                .append(expiresInSeconds).append(",\"token_type\":\"").append(tokenType).append("\",\"expires_at\":\"")
                .append(getExpireDateTimeAsString()).append("\",\"isExpired\":").append(isExpired()).append("}").toString();
    }

    @Override
    public String toString() {
        return "BearerAuthenticationToken{" +
                "client=" + client +
                ", scope='" + scope + '\'' +
                ", accessToken='" + accessToken + '\'' +
                ", expiresInSeconds=" + expiresInSeconds +
                ", tokenType='" + tokenType + '\'' +
                ", expireDateTime=" + expireDateTime +
                '}';
    }

    public String getAuthorizationToken() {
        return getTokenType() + " " + getAccessToken();
    }

    public Client getClient() {
        return this.client;
    }

    public String getFileGroup() {
        if (getClient() != null) return getClient().getNexstepFileGroup();
        return null;
    }
}

