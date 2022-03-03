package de.mss.applicationauth.server.call;

import de.mss.applicationauth.client.param.CheckApplicationIdRequest;
import de.mss.applicationauth.client.param.CheckApplicationIdResponse;
import de.mss.applicationauth.server.db.ReadSysUserFromApplicationIdStatement;
import de.mss.configtools.ConfigFile;
import de.mss.utils.exception.MssException;

public class CheckApplicationIdCall extends ApplicationAuthBaseCall<CheckApplicationIdRequest, CheckApplicationIdResponse> {

   public CheckApplicationIdCall(ConfigFile conf) {
      super(conf, CheckApplicationIdResponse::new, de.mss.applicationauth.exception.ErrorCodes.ERROR_CHECK_APPLICATION_ID_FAILED);
   }


   @Override
   protected CheckApplicationIdResponse doAction(String loggingId, CheckApplicationIdRequest req) throws MssException {
      return new ReadSysUserFromApplicationIdStatement().doExecute(loggingId, req.getApplicationId(), getConnection(this.cfg, getLogger()));
   }

}
