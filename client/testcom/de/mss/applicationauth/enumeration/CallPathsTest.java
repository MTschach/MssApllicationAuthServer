package de.mss.applicationauth.enumeration;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import de.mss.net.rest.RestMethod;

public class CallPathsTest {

   @Test
   public void test() {
      assertEquals(RestMethod.GET, CallPaths.CHECK_APPLICATION_ID.getMethod());
      assertEquals("/{applicationId}", CallPaths.CHECK_APPLICATION_ID.getPath());
   }
}
