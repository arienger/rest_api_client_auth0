package no.eg.nexstephub.client.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class GeneralResponseDto<T> {
    private Integer httpStatusCode;
    private String message;

    private String occurred_at;
    private T data;
    public GeneralResponseDto() {
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }

    public void setHttpStatusCode(Integer httpStatusCode) {
        this.httpStatusCode = httpStatusCode;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getOccurred_at() {
        return occurred_at;
    }

    public void setOccurred_at(String occurred_at) {
        this.occurred_at = occurred_at;
    }
}
