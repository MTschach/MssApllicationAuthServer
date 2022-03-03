package de.mss.applicationauth.server.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import de.mss.applicationauth.client.param.CheckApplicationIdResponse;
import de.mss.dbutils.BaseStatement;
import de.mss.dbutils.DbTools;
import de.mss.dbutils.MssConnection;
import de.mss.dbutils.MssPreparedStatement;
import de.mss.dbutils.enumeration.RecordState;
import de.mss.utils.Tools;
import de.mss.utils.exception.MssException;

public class ReadSysUserFromApplicationIdStatement extends BaseStatement<CheckApplicationIdResponse, String> {

   private static final long serialVersionUID = -791718294203624605L;

   public ReadSysUserFromApplicationIdStatement() {
      // nothing to do here
   }


   @Override
   public CheckApplicationIdResponse doExecute(String loggingId, String applicationId, MssConnection con) throws MssException {
      final CheckApplicationIdResponse ret = new CheckApplicationIdResponse();
      ret.setSysUserId(readSysUserId(loggingId, applicationId, con));
      ret.setMethodRights(readMethodRights(loggingId, ret.getSysUserId(), con));
      return ret;
   }


   private List<String> readMethodRights(String loggingId, Integer sysUserId, MssConnection con) throws MssException {
      final List<String> ret = new ArrayList<>();

      final String sql = "select * from METHOD_RIGHTS where ID_SYS_USER = ? and RECORD_STATE = ?";
      final Object[] values = new Object[] {sysUserId, RecordState.ACTIVE.getDbChar()};
      final int[] types = new int[] {java.sql.Types.INTEGER, java.sql.Types.VARCHAR};

      try (
           MssPreparedStatement pstmt = prepareStatement(loggingId, con, sql, values, types);
           ResultSet res = pstmt.executeQuery();
      ) {
         while (res.next()) {
            ret.add(DbTools.getString(res, "METHOD_NAME"));
         }
      }
      catch (final SQLException e) {
         throw new MssException(de.mss.applicationauth.exception.ErrorCodes.ERROR_APPLICATION_ID_UNKNOWN, e);
      }

      return ret;
   }


   private Integer readSysUserId(String loggingId, String applicationId, MssConnection con) throws MssException {
      final String sql = "select * from SYS_USER_APPLICATION_ID where APPLICATION_ID = ? and RECORD_STATE = ?";
      final Object[] values = new Object[] {Tools.getHash("SHA-256", applicationId, () -> {
         return new MssException(de.mss.applicationauth.exception.ErrorCodes.ERROR_COULD_NOT_HASH_VALUE, "could not hash the application id");
      }), RecordState.ACTIVE.getDbChar()};

      final int[] types = new int[] {java.sql.Types.VARCHAR, java.sql.Types.VARCHAR};

      try (
           MssPreparedStatement pstmt = prepareStatement(loggingId, con, sql, values, types);
           ResultSet res = pstmt.executeQuery();
      ) {
         if (res.next()) {
            return DbTools.getInteger(res, "ID_SYS_USER");
         }
      }
      catch (final SQLException e) {
         throw new MssException(de.mss.applicationauth.exception.ErrorCodes.ERROR_APPLICATION_ID_UNKNOWN, e);
      }

      throw new MssException(de.mss.applicationauth.exception.ErrorCodes.ERROR_APPLICATION_ID_UNKNOWN);
   }


}
