package br.com.ac.urlchecker.stub;

import br.com.ac.urlchecker.entity.WhitelistItem;

public class WhitelistItemStub {

    public static WhitelistItem createGlobalItem() {
        return new WhitelistItem(null, "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]");
    }

    public static WhitelistItem createClientItem() {
        return new WhitelistItem("Google", "google");
    }

}
