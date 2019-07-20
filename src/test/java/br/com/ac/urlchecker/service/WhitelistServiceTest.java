package br.com.ac.urlchecker.service;

import br.com.ac.urlchecker.dto.ValidationItem;
import br.com.ac.urlchecker.dto.ValidationResult;
import br.com.ac.urlchecker.entity.WhitelistItem;
import br.com.ac.urlchecker.repository.WhitelistRepository;
import br.com.ac.urlchecker.stub.UrlValidationStub;
import br.com.ac.urlchecker.stub.WhitelistItemStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class WhitelistServiceTest {


    @InjectMocks
    private WhitelistService whitelistService;

    @Mock
    private WhitelistRepository whitelistRepository;

    @Test
    void validateWithoutRegexToCheck() {
        when(whitelistRepository.findByClientOrClientIsNull(anyString(), any())).thenReturn(Page.empty());

        ValidationItem validationItem = UrlValidationStub.createOne();
        ValidationResult validateResult = whitelistService.validate(validationItem);

        assertAll(
                () -> assertFalse(validateResult.isMatch()),
                () -> assertNull(validateResult.getRegex())
        );
    }

    @Test
    void validateGlobalRegex() {
        checkValidRegex(WhitelistItemStub.createGlobalItem());
    }

    @Test
    void validateClientRegex() {
        checkValidRegex(WhitelistItemStub.createClientItem());
    }

    private void checkValidRegex(WhitelistItem whitelistItem) {
        when(whitelistRepository.findByClientOrClientIsNull(anyString(), any()))
                .thenReturn(new PageImpl<>(Collections.singletonList(whitelistItem)));

        ValidationItem validationItem = UrlValidationStub.createOne();
        ValidationResult validationResult = whitelistService.validate(validationItem);

        assertAll(
                () -> assertTrue(validationResult.isMatch()),
                () -> assertEquals(whitelistItem.getRegex(), validationResult.getRegex())
        );
    }
}