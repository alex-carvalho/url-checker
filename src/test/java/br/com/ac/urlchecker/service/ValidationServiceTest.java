package br.com.ac.urlchecker.service;

import br.com.ac.urlchecker.dto.ValidationResponse;
import br.com.ac.urlchecker.dto.ValidationResult;
import br.com.ac.urlchecker.stub.UrlValidationStub;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ValidationServiceTest {

    @InjectMocks
    private ValidationService validationService;

    @Mock
    private WhitelistService whitelistService;
    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Test
    void validate() {
        when(whitelistService.validate(any())).thenReturn(new ValidationResult());

        validationService.validate(UrlValidationStub.createOne());

        verify(whitelistService, times(1)).validate(any());
        verify(applicationEventPublisher, times(1)).publishEvent(any(ValidationResponse.class));
    }
}