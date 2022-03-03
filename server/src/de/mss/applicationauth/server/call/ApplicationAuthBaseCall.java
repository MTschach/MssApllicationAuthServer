package de.mss.applicationauth.server.call;

import java.util.function.Supplier;

import org.apache.logging.log4j.Logger;

import de.mss.configtools.ConfigFile;
import de.mss.dbutils.MssConnection;
import de.mss.dbutils.MssDbServer;
import de.mss.net.call.BaseCall;
import de.mss.net.webservice.WebServiceRequest;
import de.mss.net.webservice.WebServiceResponse;
import de.mss.net.webservice.WebServiceServer;
import de.mss.utils.exception.Error;
import de.mss.utils.exception.MssException;

public abstract class ApplicationAuthBaseCall<T extends WebServiceRequest, R extends WebServiceResponse> extends BaseCall<T, R> {

   public static final String   DBNAME       = "mss_application_auth";

   private static MssConnection dbConnection = null;

   protected static MssConnection getConnection(ConfigFile conf, Logger logger) throws MssException {
      if (dbConnection == null) {
         final MssDbServer server = new MssDbServer(
               conf.getValue(DBNAME + ".type", ""),
               conf.getValue(DBNAME + ".url", "jdbc:mysql://localhost:3306/mss_application_auth"),
               conf.getValue(DBNAME + ".user", ""),
               conf.getValue(DBNAME + ".passwd", ""),
               null);
         server.setDbDriver(conf.getValue(DBNAME + ".driver", ""));
         dbConnection = new MssConnection(logger, server);
      }
      return dbConnection;
   }

   protected ConfigFile cfg;

   public ApplicationAuthBaseCall(ConfigFile conf, Supplier<R> response, Error error) {
      super(response, error);
      this.cfg = conf;
   }


   protected Logger getLogger() {
      return WebServiceServer.getLogger();
   }
}
