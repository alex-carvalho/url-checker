package br.com.ac.urlchecker.dto;

import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * @author Aléx Carvalho
 */
public class ValidationItem {

    @NotNull
    private String client;
    @NotNull
    private String url;
    @NotNull
    private Integer correlationId;

    public ValidationItem() {
    }

    public ValidationItem(@NotNull String client, @NotNull String url, @NotNull Integer correlationId) {
        this.client = client;
        this.url = url;
        this.correlationId = correlationId;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Integer getCorrelationId() {
        return correlationId;
    }

    public void setCorrelationId(Integer correlationId) {
        this.correlationId = correlationId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ValidationItem that = (ValidationItem) o;
        return Objects.equals(client, that.client) &&
                Objects.equals(url, that.url) &&
                Objects.equals(correlationId, that.correlationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(client, url, correlationId);
    }

    @Override
    public String toString() {
        return "ValidationItem{" +
                "client='" + client + '\'' +
                ", url='" + url + '\'' +
                ", correlationId='" + correlationId + '\'' +
                '}';
    }
}
