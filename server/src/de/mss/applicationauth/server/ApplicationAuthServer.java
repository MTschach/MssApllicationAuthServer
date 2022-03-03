package de.mss.applicationauth.server;

import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import de.mss.configtools.ConfigFile;
import de.mss.configtools.IniConfigFile;
import de.mss.net.webservice.WebService;
import de.mss.net.webservice.WebServiceRequest;
import de.mss.net.webservice.WebServiceResponse;
import de.mss.net.webservice.WebServiceServer;

public class ApplicationAuthServer extends WebServiceServer {

   private static final String OPTION_CFG_FILE = "config-file";

   private static CommandLine getCmdLineOptions(String[] args) throws ParseException {
      final Options cmdArgs = new Options();

      final Option confFile = new Option("f", OPTION_CFG_FILE, true, "configuration file");
      confFile.setRequired(false);
      cmdArgs.addOption(confFile);

      final Option port = new Option("p", "port", true, "local port");
      port.setRequired(false);
      cmdArgs.addOption(port);

      final Option ip = new Option("ip", "ip", true, "local server address");
      ip.setRequired(false);
      cmdArgs.addOption(ip);

      final Option debug = new Option("dd", "debug", false, "debug info");
      debug.setRequired(false);
      cmdArgs.addOption(debug);

      final Option verbose = new Option("vv", "verbose", false, "be verbose");
      verbose.setRequired(false);
      cmdArgs.addOption(verbose);

      final CommandLineParser parser = new DefaultParser();
      return parser.parse(cmdArgs, args);
   }


   private static ConfigFile getConfig(CommandLine cmd) {
      String cfgFile = "applicationauth.ini";
      if (cmd.hasOption(OPTION_CFG_FILE)) {
         cfgFile = cmd.getOptionValue(OPTION_CFG_FILE);
      }

      return new IniConfigFile(cfgFile);
   }


   private static String getLocalIp(CommandLine cmd) {
      if (cmd.hasOption("ip")) {
         return cmd.getOptionValue("ip");
      }

      return "localhost";
   }


   private static Integer getPort(CommandLine cmd, Logger logger) {
      if (cmd.hasOption("port")) {
         try {
            return Integer.parseInt(cmd.getOptionValue("port"));
         }
         catch (final NumberFormatException nfe) {
            logger.error("could not parse port", nfe);
         }
      }

      return Integer.valueOf(8080);
   }


   public static void main(String[] args) throws ParseException {
      final Logger logger = LogManager.getLogger("default");

      final CommandLine cmd = getCmdLineOptions(args);

      final ApplicationAuthServer as = new ApplicationAuthServer(getConfig(cmd), getPort(cmd, logger));
      WebServiceServer.setLogger(logger);
      as.run(getLocalIp(cmd));
   }


   public ApplicationAuthServer(ConfigFile c) {
      super(c);
   }


   public ApplicationAuthServer(ConfigFile c, Integer p) {
      super(c, p);
   }


   @Override
   protected Map<String, WebService<WebServiceRequest, WebServiceResponse>> getServiceList() {
      return loadWebServices(this.getClass().getClassLoader(), "de.mss.applicationauth.server.rest", "/applicationauth");
   }


   @Override
   protected void initApplication() {
      // nothing to do here
   }


   @Override
   protected void shutDown() {
      stopServer();
   }
}
