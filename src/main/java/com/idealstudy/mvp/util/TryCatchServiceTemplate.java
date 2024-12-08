package com.idealstudy.mvp.util;

import com.idealstudy.mvp.enums.DBErrorMsg;
import com.idealstudy.mvp.enums.SecurityErrorMsg;
import lombok.extern.slf4j.Slf4j;

import java.util.function.Supplier;

@Slf4j
public class TryCatchServiceTemplate {

    public static <T> T execute(Supplier<T> supplier, Runnable runnable, DBErrorMsg errorMsg) {

        try {
            if(runnable != null)
                runnable.run();

            return supplier.get();
        } catch (SecurityException e) {
            log.error(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
            throw new SecurityException(SecurityErrorMsg.PRIVATE_EXCEPTION.toString());
        } catch (RuntimeException e) {
            log.error(errorMsg.toString());
            throw new RuntimeException(errorMsg.toString());
        }
    }
}
