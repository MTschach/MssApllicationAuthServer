package de.mss.applicationauth.server.call;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.junit.jupiter.Testcontainers;

import de.mss.applicationauth.client.param.CheckApplicationIdRequest;
import de.mss.applicationauth.client.param.CheckApplicationIdResponse;
import de.mss.utils.exception.Error;

@Testcontainers
public class CheckApplictionIdTest extends ApplicationAuthBaseTest {

   private CheckApplicationIdRequest request;

   private void checkResponse(CheckApplicationIdResponse resp, Error errorCode) {
      assertNotNull(resp);
      assertEquals(Integer.valueOf(errorCode.getErrorCode()), resp.getErrorCode());
      if (errorCode.getErrorCode() != 0) {
         assertNotNull(resp.getErrorText());
         return;
      }
      assertNull(resp.getErrorText());
   }


   private CheckApplicationIdRequest getRequest() {
      final CheckApplicationIdRequest ret = new CheckApplicationIdRequest();
      ret.setApplicationId(DEFAULT_APPLICATION_ID);
      return ret;
   }


   @Override
   @BeforeEach
   public void setUp() throws Exception {
      super.setUp();

      this.request = getRequest();
   }


   @Test
   public void testOk() {
      final String loggingId = getLoggingId();
      this.request.setLoggingId(loggingId);

      System.out.println(loggingId);

      replay();

      final CheckApplicationIdResponse resp = new CheckApplicationIdCall(ApplicationAuthBaseTest.cfgMock).action(loggingId, this.request);

      checkResponse(resp, de.mss.utils.exception.ErrorCodes.NO_ERROR);
   }


   @Test
   public void testUnknownApplicationIdNook() {
      final String loggingId = getLoggingId();
      this.request.setLoggingId(loggingId);
      this.request.setApplicationId("invlaid");

      System.out.println(loggingId);

      replay();

      final CheckApplicationIdResponse resp = new CheckApplicationIdCall(ApplicationAuthBaseTest.cfgMock).action(loggingId, this.request);

      checkResponse(resp, de.mss.applicationauth.exception.ErrorCodes.ERROR_APPLICATION_ID_UNKNOWN);
   }
}
