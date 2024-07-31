package frc.robot.utility;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.networktables.GenericPublisher;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

import java.util.Map;

public class Dashboard {
  // Subsystems
  private DriveBase driveSys;
  private Intake intakeSys;
  private Shooter shooterSys;
  private PDH pdh;

  // Drive tab
  private GenericPublisher analogPressureOut, gyroOut, digitalPressureOut, limelightAliveOut,
    shooterErrOut, currentOut, targetDistanceOut;

  private GenericEntry fieldOrientedEntry, manualShootVeloEntry;
  private SendableChooser<Integer> shootStrategyEntry = new SendableChooser<>();

  /**
   * Configs the driver-facing Shuffleboard dashboard.
   * @param drive The drive subsystem.
   * @param intake The intake subsystem.
   * @param shooter The shooter subsystem.
   */
  public Dashboard(DriveBase drive, Intake intake, Shooter shooter, PDH pdh) {
    this.driveSys = drive;
    this.intakeSys = intake;
    this.shooterSys = shooter;
    this.pdh = pdh;

    // Init tabs
    ShuffleboardTab driveTab = Shuffleboard.getTab("Drive");

    // Analog pressure output
    analogPressureOut = driveTab.add("Pressure (PSI)", -1.0)
      .withSize(2, 1)
      .withPosition(0, 0)
      .withWidget(BuiltInWidgets.kNumberBar)
      .withProperties(Map.of("Min", 0, "Max", 125))
      .getEntry();
    
    // Gyro output (yaw, pitch, roll)
    gyroOut = driveTab.add("Gyro (Y, P, R)", "?, ?, ?")
      .withSize(1, 1)
      .withPosition(0, 1)
      .getEntry();

    // Digital pressure output
    digitalPressureOut = driveTab.add("Pressure Full", false)
      .withSize(1, 1)
      .withPosition(1, 1)
      .withWidget(BuiltInWidgets.kBooleanBox)
      .getEntry();

    // Limelight alive output
    limelightAliveOut = driveTab.add("Limelight Alive", false)
      .withSize(1, 1)
      .withPosition(6, 2)
      .withWidget(BuiltInWidgets.kBooleanBox)
      .getEntry();

    // Camera stream output
    driveTab.addCamera("Limelight", "Limelight", shooterSys.limelight.getURL())
      .withSize(3, 3)
      .withPosition(2, 0)
      .withWidget(BuiltInWidgets.kCameraStream)
      .withProperties(Map.of("Show controls", false));

    // Mecanum state output
    driveTab.add("Mecanum Drivebase", driveSys.getMecanumSendable())
      .withSize(3, 2)
      .withPosition(5, 0)
      .withWidget(BuiltInWidgets.kMecanumDrive);

    // Shooter error percentage
    shooterErrOut = driveTab.add("Shoot Setpoint %", -1.0)
      .withSize(1, 1)
      .withPosition(5, 2)
      .withWidget(BuiltInWidgets.kNumberBar)
      .withProperties(Map.of("Min", 0.0, "Max", 1.1))
      .getEntry();

    // Field oriented switch input
    fieldOrientedEntry = driveTab.add("Field Oriented", false)
      .withSize(1, 1)
      .withPosition(0, 2)
      .withWidget(BuiltInWidgets.kToggleSwitch)
      .getEntry();

    // Current output
    currentOut = driveTab.add("Current (amps)", -1.0)
      .withSize(1, 1)
      .withPosition(7, 2)
      .withWidget(BuiltInWidgets.kNumberBar)
      .withProperties(Map.of("Min", 0.0, "Max", 240.0))
      .getEntry();

    // Distance to target output
    targetDistanceOut = driveTab.add("Target Distance (m)", -1.0)
      .withSize(1, 1)
      .withPosition(8, 2)
      .getEntry();

    // Manual shoot velo input
    manualShootVeloEntry = driveTab.add("Manual Shoot Velocity (RPM)", 0.0)
      .getEntry(); 

    // Shoot strategy dropdown
    driveTab.add("Strategy", shootStrategyEntry)
      .withSize(1, 1)
      .withPosition(1, 2);

    shootStrategyEntry.onChange((Integer newStrategy) -> {
      System.out.printf("Shoot strategy has changed: %d\n", newStrategy);
    });

    shootStrategyEntry.addOption("Low Constant", -1); // Constant low velocity
    shootStrategyEntry.addOption("Manual", 0); // Manual input from Shuffleboard
    shootStrategyEntry.addOption("Reg v3", 3); // Regression V3
    shootStrategyEntry.addOption("Reg v4", 4); // Regression V4
    shootStrategyEntry.setDefaultOption("Reg v5", 5);
  }

  /**
   * Call this periodically to update all outputs.
   */
  public void periodic() {
    analogPressureOut.setDouble(intakeSys.getPressure());
    gyroOut.setString(driveSys.prettyPrintGyro());
    digitalPressureOut.setBoolean(intakeSys.getPressureSwitch());
    limelightAliveOut.setBoolean(shooterSys.limelight.isAlive());
    shooterErrOut.setDouble(shooterSys.getShootVelocityErrorPercentage());
    currentOut.setDouble(pdh.getTotalCurrent());
    targetDistanceOut.setDouble(shooterSys.limelight.getDistanceToTrashCan());
  }

  /**
   * Gets the value of the field oriented switch, supplied by the user
   * @return true if field oriented.
   */
  public boolean getFieldOrientedSwitch() {
    return fieldOrientedEntry.getBoolean(false);
  }

  /**
   * Gets the manual shoot velocity supplied by the user.
   * @return RPM
   */
  public double getManualShootVelocity() {
    return manualShootVeloEntry.getDouble(0.0);
  }

  /**
   * Gets the currently selected shoot strategy.
   * @return The unique integer associated with this option.
   */
  public Integer getShootStrategy() {
    return shootStrategyEntry.getSelected();
  }
}
