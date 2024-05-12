package frc.robot.utility;

import edu.wpi.first.wpilibj.PowerDistribution;
import edu.wpi.first.wpilibj.PowerDistribution.ModuleType;
import frc.robot.Constants.HardwareConstants;

/**
 * Class for communicating with the Power Distribution Hub.
 * This is not a subsystem.
 */
public class PDH {
  // Hardware
  private PowerDistribution pdh = 
    new PowerDistribution(HardwareConstants.kPDH_CAN, ModuleType.kRev);

  /**
   * @return The total current draw (amps)
   */
  public double getTotalCurrent() {
    return pdh.getTotalCurrent();
  }
}
