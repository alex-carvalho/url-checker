package br.com.ac.urlchecker.dto;

import java.util.Objects;

/**
 * @author Aléx Carvalho
 */
public class ValidationResponse {

    private boolean match;
    private String regex;
    private Integer correlationId;

    public ValidationResponse() {
    }

    public ValidationResponse(boolean match, String regex, Integer correlationId) {
        this.match = match;
        this.regex = regex;
        this.correlationId = correlationId;
    }

    public boolean isMatch() {
        return match;
    }

    public void setMatch(boolean match) {
        this.match = match;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
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
        ValidationResponse that = (ValidationResponse) o;
        return match == that.match &&
                Objects.equals(regex, that.regex) &&
                Objects.equals(correlationId, that.correlationId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(match, regex, correlationId);
    }

    @Override
    public String toString() {
        return "ValidationResponse{" +
                "match=" + match +
                ", regex='" + regex + '\'' +
                ", correlationId=" + correlationId +
                '}';
    }
}
