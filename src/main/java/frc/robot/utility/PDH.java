package frc.robot.utility;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import frc.robot.Constants.HardwareConstants;

/**
 * Class for communicating with the Power Distribution Hub.
 * This is not a subsystem.
 */
public class PDH {
  // Hardware
  private final PowerDistribution pdh = 
    new PowerDistribution(HardwareConstants.kPDH_CAN, ModuleType.kRev);

  // Telemetry
  private final DoublePublisher currentPub;

  /** Initializes the PDH */
  public PDH() {
    // Init telemetry
    NetworkTable nt = NetworkTableInstance.getDefault().getTable("dashboard").getSubTable("pdh");

    currentPub = nt.getDoubleTopic("current").publish();
  }

  /**
   * @return The total current draw (amps)
   */
  public double getTotalCurrent() {
    return pdh.getTotalCurrent();
  }

  public void log() {
    currentPub.set(getTotalCurrent());
  }
}
