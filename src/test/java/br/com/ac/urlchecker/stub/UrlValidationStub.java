package br.com.ac.urlchecker.stub;

import br.com.ac.urlchecker.dto.ValidationItem;

public class UrlValidationStub {

    public static ValidationItem createOne() {
        return new ValidationItem("client", "https://google.com", 1);
    }
}
