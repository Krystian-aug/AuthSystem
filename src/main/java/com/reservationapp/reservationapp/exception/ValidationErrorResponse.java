package com.reservationapp.reservationapp.exception;

import java.util.List;

public record ValidationErrorResponse(List<ValidationError> errors) {}
