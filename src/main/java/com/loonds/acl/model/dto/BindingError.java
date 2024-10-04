package com.loonds.acl.model.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.validation.FieldError;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class BindingError {
    private String resource;
    private String field;
    private String code;
    private String value;
    private String message;

    public static BindingError of(FieldError fieldError) {
        var bindingError = new BindingError();
        bindingError.setResource(fieldError.getObjectName());
        bindingError.setField(fieldError.getField());
        bindingError.setCode(fieldError.getCode());
        bindingError.setValue(String.valueOf(fieldError.getRejectedValue()));
        bindingError.setMessage(fieldError.getDefaultMessage());
        return bindingError;
    }
}
