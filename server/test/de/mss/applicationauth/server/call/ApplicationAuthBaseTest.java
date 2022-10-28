package de.mss.applicationauth.server.call;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.easymock.EasyMock;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import de.mss.applicationauth.enumeration.CallPaths;
import de.mss.applicationauth.server.ApplicationAuthServerWebService;
import de.mss.configtools.ConfigFile;
import de.mss.db.migration.MssDbMigration;

@Testcontainers
public abstract class ApplicationAuthBaseTest {

   protected static final String      DEFAULT_APPLICATION_ID = "sha1shi2eijesh6pekeigapeeCheik5a";

   @Container
   protected static MySQLContainer<?> database               = new MySQLContainer<>(DockerImageName.parse("mysql:8.0"))
         .withUsername("junit")
         .withPassword("junitpass")
         .withDatabaseName("mss_application_auth");

   private static Map<String, String> cfgValues              = new HashMap<>();

   protected static ConfigFile        cfgMock;

   static {
      database.start();

      cfgValues.put(ApplicationAuthBaseCall.DBNAME + ".driver", database.getDriverClassName());
      cfgValues.put(ApplicationAuthBaseCall.DBNAME + ".url", database.getJdbcUrl());
      cfgValues.put(ApplicationAuthBaseCall.DBNAME + ".user", database.getUsername());
      cfgValues.put(ApplicationAuthBaseCall.DBNAME + ".passwd", database.getPassword());
      cfgValues.put(ApplicationAuthBaseCall.DBNAME + ".type", "mysql");
   }

   @BeforeAll
   public static void setUpClass() throws Exception {
      cfgMock = EasyMock.createNiceMock(ConfigFile.class);
      for (final Entry<String, String> c : cfgValues.entrySet()) {
         EasyMock.expect(cfgMock.getValue(EasyMock.eq(c.getKey()), EasyMock.anyString())).andReturn(c.getValue()).anyTimes();
      }

      EasyMock.replay(cfgMock);

      final MssDbMigration dbMig = new MssDbMigration(
            cfgMock,
            "mss_application_auth",
            de.mss.applicationauth.exception.ErrorCodes.ERROR_APPLICATION_ID_UNKNOWN);
      dbMig.migrateDb(new File("db").isDirectory());
   }


   protected void checkRestCall(ApplicationAuthServerWebService<?, ?> restCall, CallPaths callPath) {
      assertEquals(callPath.getMethod(), restCall.getMethod());
      assertEquals(callPath.getPath(), restCall.getPath());
   }


   protected String getLoggingId() {
      final Throwable th = new Throwable();
      return th.getStackTrace()[1].getMethodName() + "_" + System.currentTimeMillis();
   }


   protected void replay() {
      // nothing to do here
   }


   @BeforeEach
   public void setUp() throws Exception {
      // nothing to do here
   }


   @AfterEach
   public void tearDown() throws Exception {
      verify();
   }


   protected void verify() {
      // nothing to do here
   }


}
