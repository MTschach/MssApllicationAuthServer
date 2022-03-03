package de.mss.applicationauth.exception;

import de.mss.utils.exception.Error;

public class ErrorCodes {

   private static final int  ERROR_CODE_BASE                   = 10000;

   public static final Error ERROR_APPLICATION_ID_UNKNOWN      = new Error(ERROR_CODE_BASE + 0, "the application id is unknown");
   public static final Error ERROR_CHECK_APPLICATION_ID_FAILED = new Error(ERROR_CODE_BASE + 1, "checking application id failed");
   public static final Error ERROR_COULD_NOT_HASH_VALUE        = new Error(ERROR_CODE_BASE + 2, "could not hash a value");

   private ErrorCodes() {}
}
