package org.saga.employee.service.exception;

import java.time.LocalDateTime;

public record ErrorDetails(LocalDateTime timestamp, String message, String path, String errorCode) {

}