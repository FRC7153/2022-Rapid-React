package frc.robot.utility;

import java.util.function.Supplier;

import edu.wpi.first.util.sendable.SendableBuilder;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj.motorcontrol.MotorController;

/** Elastic does not support the Mecanum Drive Sendable. This class publishes a list of 
 * SwerveDriveStates to NT so that Elastic can display the wheel speeds individually.
 */
public class MecanumDriveSendable extends MecanumDrive {
  private static final String[] motorNames = {"Front Left", "Front Right", "Rear Left", "Rear Right"};

  private final MotorController[] motors = new MotorController[4]; // fl, fr, rl, rr order
  private final Supplier<Double> robotRadiansSupplier;

  /**
   * MecanumDrive Sendable that can be put on Elastic
   * @param robotRadiansSupplier A supplier that gives the yaw angle of the robot, in radians
   * @param motors... A list of motors in fl, fr, rl, rr order.
   */
  public MecanumDriveSendable(Supplier<Double> robotRadiansSupplier, MotorController fl, MotorController rl, MotorController fr, MotorController rr) {
    super(fl, rl, fr, rr);

    this.motors[0] = fl;
    this.motors[1] = fr;
    this.motors[2] = rl;
    this.motors[3] = rr;

    this.robotRadiansSupplier = robotRadiansSupplier;
  }

  @Override
  public void initSendable(SendableBuilder builder) {
    builder.setSmartDashboardType("SwerveDrive");

    // Add each motor
    for (int m = 0; m < 4; m++) {
      final int index = m;

      builder.addDoubleProperty(motorNames[m] + " Angle", () -> 0.0, null);
      builder.addDoubleProperty(motorNames[m] + " Velocity", () -> motors[index].get(), null);
    }

    // Add angle
    builder.addDoubleProperty("Robot Angle", robotRadiansSupplier::get, null);
  }
}
