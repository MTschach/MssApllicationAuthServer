package de.mss.applicationauth.client;

import de.mss.applicationauth.client.param.CheckApplicationIdRequest;
import de.mss.applicationauth.client.param.CheckApplicationIdResponse;
import de.mss.applicationauth.enumeration.CallPaths;
import de.mss.configtools.ConfigFile;
import de.mss.net.client.ClientBase;
import de.mss.net.webservice.WebServiceJsonCaller;
import de.mss.utils.exception.MssException;

public class MssApplicationAuthClient extends ClientBase {

   private static MssApplicationAuthClient instance;

   private static final String             PATH_PREFIX = "/applicationauth";
   private static final int                MAX_RETRIES = 3;

   public static MssApplicationAuthClient getInstance(ConfigFile cfg) throws MssException {
      if (instance == null) {
         instance = new MssApplicationAuthClient(cfg);
      }

      return instance;
   }


   public static MssApplicationAuthClient reloadInstance(ConfigFile cfg) throws MssException {
      instance = null;
      return getInstance(cfg);
   }


   private MssApplicationAuthClient(ConfigFile cfg) throws MssException {
      super(cfg, "mssapplicationauth");
   }


   public CheckApplicationIdResponse checkApplicationId(String loggingId, CheckApplicationIdRequest request)
         throws MssException {
      return new WebServiceJsonCaller<CheckApplicationIdRequest, CheckApplicationIdResponse>()
            .call(
                  loggingId,
                  this.servers,
                  PATH_PREFIX + CallPaths.CHECK_APPLICATION_ID.getPath(),
                  CallPaths.CHECK_APPLICATION_ID.getMethod(),
                  request,
                  new CheckApplicationIdResponse(),
                  MAX_RETRIES);
   }

}
