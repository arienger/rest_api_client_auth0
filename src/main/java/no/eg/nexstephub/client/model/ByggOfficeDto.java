package no.eg.nexstephub.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ByggOfficeDto
{
    private String clientId;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ByggOfficeTransactionDto> transactions;

    public ByggOfficeDto() {
    }

    public String getClientId() {
        return this.clientId;
    }

    public void setClientId(final String clientId) {
        this.clientId = clientId;
    }

    public List<ByggOfficeTransactionDto> getTransactions() {
        return this.transactions;
    }

    public void setTransactions(final List<ByggOfficeTransactionDto> transactions) {
        this.transactions = transactions;
    }
}
