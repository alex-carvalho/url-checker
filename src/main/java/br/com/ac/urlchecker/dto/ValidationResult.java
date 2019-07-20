package br.com.ac.urlchecker.dto;

public class ValidationResult {

    private boolean match;
    private String regex;

    public ValidationResult() {
    }

    public ValidationResult(boolean match, String regex) {
        this.match = match;
        this.regex = regex;
    }

    public boolean isMatch() {
        return match;
    }

    public String getRegex() {
        return regex;
    }
}
