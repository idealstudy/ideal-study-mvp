package com.idealstudy.mvp.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.function.Supplier;

@Slf4j
public class TryCatchControllerTemplate {

    public static <T> ResponseEntity<T> execute(Supplier<T> supplier) {

        T result = null;
        try {
            result = supplier.get();
            return ResponseEntity.ok(result);
        } catch (SecurityException e) {
            log.error(e + " : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatusCode.valueOf(403));
        } catch (RuntimeException e) {
            log.error(e + " : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatusCode.valueOf(500));
        } catch (Exception e) {
            log.error(e + " : " + e.getMessage());
            return new ResponseEntity<>(result, HttpStatusCode.valueOf(404));
        }
    }
}
