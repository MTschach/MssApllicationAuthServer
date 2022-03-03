package de.mss.applicationauth.server.rest;

import de.mss.applicationauth.client.param.CheckApplicationIdRequest;
import de.mss.applicationauth.client.param.CheckApplicationIdResponse;
import de.mss.applicationauth.server.ApplicationAuthServerWebService;
import de.mss.applicationauth.server.call.CheckApplicationIdCall;
import de.mss.net.rest.RestMethod;
import de.mss.utils.exception.MssException;

public class CheckApplicationId extends ApplicationAuthServerWebService<CheckApplicationIdRequest, CheckApplicationIdResponse> {

   private static final long serialVersionUID = 6665662369324043274L;


   public CheckApplicationId() {
      super(CheckApplicationIdRequest::new, CheckApplicationIdResponse::new);
   }


   @Override
   public RestMethod getMethod() {
      return RestMethod.POST;
   }


   @Override
   public String getPath() {
      return "/{applicationId}";
   }


   @Override
   public CheckApplicationIdResponse handleRequest(String loggingId, CheckApplicationIdRequest request) throws MssException {
      return new CheckApplicationIdCall(this.cfg).action(loggingId, request);
   }
}
