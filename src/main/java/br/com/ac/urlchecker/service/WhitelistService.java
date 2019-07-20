package br.com.ac.urlchecker.service;

import br.com.ac.urlchecker.dto.ValidationItem;
import br.com.ac.urlchecker.dto.ValidationResult;
import br.com.ac.urlchecker.entity.WhitelistItem;
import br.com.ac.urlchecker.repository.WhitelistRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

/**
 * @author Aléx Carvalho
 */
@Service
@Validated
public class WhitelistService {

    private final Logger logger = LoggerFactory.getLogger(WhitelistService.class);

    private final WhitelistRepository repository;

    public WhitelistService(WhitelistRepository repository) {
        this.repository = repository;
    }

    public void insert(@Validated WhitelistItem whitelistItem) {
        repository.save(whitelistItem);
        logger.info("Saved: {}", whitelistItem);
    }

    ValidationResult validate(ValidationItem validationItem) {
        Page<WhitelistItem> pageData = repository.findByClientOrClientIsNull(validationItem.getClient(), PageRequest.of(0, 50));

        List<WhitelistItem> finalList = pageData.getContent();
        while (!pageData.isLast()) {
            pageData = repository.findByClientOrClientIsNull(validationItem.getClient(), pageData.nextPageable());
            List<WhitelistItem> listData = pageData.getContent();
            finalList.addAll(listData);
        }

        Optional<WhitelistItem> optional = finalList.stream()
                .filter(it -> it.match(validationItem.getUrl()))
                .findFirst();

        boolean match = optional.isPresent();
        String regex = optional.map(WhitelistItem::getRegex).orElse(null);

        return new ValidationResult(match, regex);
    }
}
