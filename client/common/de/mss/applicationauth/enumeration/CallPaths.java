package de.mss.applicationauth.enumeration;

import de.mss.net.rest.RestMethod;

public enum CallPaths {

   //@formatter:off
     CHECK_APPLICATION_ID               (RestMethod.GET        , "/{applicationId}")
   ;
   //@formatter:on

   private RestMethod method;
   private String     path;

   private CallPaths(RestMethod r, String p) {
      this.method = r;
      this.path = p;
   }


   public RestMethod getMethod() {
      return this.method;
   }


   public String getPath() {
      return this.path;
   }
}
