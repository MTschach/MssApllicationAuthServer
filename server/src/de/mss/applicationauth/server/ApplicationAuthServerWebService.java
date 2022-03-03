package de.mss.applicationauth.server;

import java.util.function.Supplier;

import de.mss.net.webservice.WebService;
import de.mss.net.webservice.WebServiceRequest;
import de.mss.net.webservice.WebServiceResponse;

public abstract class ApplicationAuthServerWebService<R extends WebServiceRequest, T extends WebServiceResponse> extends WebService<R, T> {


   private static final long serialVersionUID = 59609874587243786L;


   public ApplicationAuthServerWebService(Supplier<R> reqts, Supplier<T> rts) {
      super(reqts, rts);
   }
}
