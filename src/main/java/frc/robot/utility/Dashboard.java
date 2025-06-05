package frc.robot.utility;

import edu.wpi.first.net.WebServer;
import edu.wpi.first.wpilibj.Filesystem;

public class Dashboard {
  /**
   * Begins hosting Elastic's layout folder on port 5800.
   */
  public static final void hostElasticLayoutFolder() {
    WebServer.start(5800, Filesystem.getDeployDirectory().toPath().resolve("Elastic").toString());
    System.out.println("Hosting Elastic layout web server at port 5800");
  }
}
