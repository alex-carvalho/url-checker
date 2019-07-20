package br.com.ac.urlchecker.service;

import br.com.ac.urlchecker.dto.ValidationItem;
import br.com.ac.urlchecker.dto.ValidationResponse;
import br.com.ac.urlchecker.dto.ValidationResult;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

@Service
@Validated
public class ValidationService {


    private final WhitelistService whitelistService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public ValidationService(WhitelistService whitelistService, ApplicationEventPublisher applicationEventPublisher) {
        this.whitelistService = whitelistService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void validate(@Valid ValidationItem validationItem) {
        ValidationResult validationResult = whitelistService.validate(validationItem);

        ValidationResponse validationResponse = new ValidationResponse(validationResult.isMatch(), validationResult.getRegex(), validationItem.getCorrelationId());

        applicationEventPublisher.publishEvent(validationResponse);
    }
}
