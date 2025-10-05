package com.PastebinMiniDemo.PastebinMiniDemo.PastService.ExceptionHandler;

import com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions.NoContentException;
import com.PastebinMiniDemo.PastebinMiniDemo.PastService.MyExceptions.UrlNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.net.MalformedURLException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body("❌ Invalid request: " + ex.getMessage());
    }

    @ExceptionHandler(UrlNotFoundException.class)
    public ResponseEntity<String> handleUrlNotFound(UrlNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("❌ URL not found: " + ex.getMessage());
    }

    @ExceptionHandler(NoContentException.class)
    public ResponseEntity<String> handleNoContent(NoContentException ex) {
        return ResponseEntity.status(HttpStatus.NO_CONTENT)
                .body("⚠️ No content: " + ex.getMessage());
    }

    @ExceptionHandler(MalformedURLException.class)
    public ResponseEntity<String> handleMalformedUrl(MalformedURLException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("❌ Invalid file URL: " + ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntime(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("🔥 Server error: " + ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGeneral(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("⚠️ Unexpected error: " + ex.getMessage());
    }
}

