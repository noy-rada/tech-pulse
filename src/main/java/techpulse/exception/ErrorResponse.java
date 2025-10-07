package techpulse.exception;

import lombok.Getter;

import java.time.LocalDateTime;

public record ErrorResponse(LocalDateTime timestamp, int status, String error, String message) {

}
