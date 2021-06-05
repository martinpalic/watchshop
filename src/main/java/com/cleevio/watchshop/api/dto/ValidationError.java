package com.cleevio.watchshop.api.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@RequiredArgsConstructor
public class ValidationError {
    private final String errorCause;
    private final List<String> errorMessages;
}
