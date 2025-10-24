package miranda.gabriel.tenus.infrastructure.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TenusExceptions {
    
    public static class UserNotFoundException extends ResponseStatusException {
        public UserNotFoundException(String userId) {
            super(HttpStatus.NOT_FOUND, "User not found with ID: " + userId);
        }
        
        public UserNotFoundException(String field, String value) {
            super(HttpStatus.NOT_FOUND, "User not found with " + field + ": " + value);
        }
    }
    
    public static class UserAlreadyExistsException extends ResponseStatusException {
        public UserAlreadyExistsException(String message) {
            super(HttpStatus.CONFLICT, message);
        }
    }
    
    public static class BoardNotFoundException extends ResponseStatusException {
        public BoardNotFoundException(Long boardId) {
            super(HttpStatus.NOT_FOUND, "Activity board not found with ID: " + boardId);
        }
    }
    
    public static class BusinessRuleViolationException extends ResponseStatusException {
        public BusinessRuleViolationException(String message) {
            super(HttpStatus.BAD_REQUEST, message);
        }
    }

    public static class UnauthorizedOperationException extends ResponseStatusException {
        public UnauthorizedOperationException(String operation) {
            super(HttpStatus.FORBIDDEN, "Not authorized to perform operation: " + operation);
        }
    }
    
    public static class InvalidTokenException extends ResponseStatusException {
        public InvalidTokenException(String message) {
            super(HttpStatus.UNAUTHORIZED, message);
        }
    }
    
    public static class InvalidCredentialsException extends ResponseStatusException {
        public InvalidCredentialsException(String message) {
            super(HttpStatus.UNAUTHORIZED, message);
        }
        
        public InvalidCredentialsException() {
            super(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }
    }
}