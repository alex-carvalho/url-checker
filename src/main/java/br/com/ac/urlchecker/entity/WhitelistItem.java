package br.com.ac.urlchecker.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Aléx Carvalho
 */
@Table(name = "whitelist")
@Entity
public class WhitelistItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String client;
    @NotNull
    private String regex;

    public WhitelistItem() {
    }

    public WhitelistItem(String client, @NotNull String regex) {
        this.client = client;
        this.regex = regex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getRegex() {
        return regex;
    }

    public void setRegex(String regex) {
        this.regex = regex;
    }

    public boolean match(String url) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(url);

        return matcher.find();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WhitelistItem whitelistItem = (WhitelistItem) o;
        return Objects.equals(id, whitelistItem.id) &&
                Objects.equals(client, whitelistItem.client) &&
                Objects.equals(regex, whitelistItem.regex);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, client, regex);
    }

    @Override
    public String toString() {
        return "WhitelistItem{" +
                "id=" + id +
                ", client='" + client + '\'' +
                ", regex='" + regex + '\'' +
                '}';
    }
}
