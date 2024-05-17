package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.DriveBaseConstants;
import frc.robot.Constants.HardwareConstants;

/**
 * The subsystem for driving the robot.
 */
public class DriveBase implements Subsystem {
    // Hardware
    private CANSparkMax wheel_fl = new CANSparkMax(HardwareConstants.kFL_DRIVE_CAN, MotorType.kBrushless);
    private CANSparkMax wheel_fr = new CANSparkMax(HardwareConstants.kFR_DRIVE_CAN, MotorType.kBrushless);
    private CANSparkMax wheel_rl = new CANSparkMax(HardwareConstants.kRL_DRIVE_CAN, MotorType.kBrushless);
    private CANSparkMax wheel_rr = new CANSparkMax(HardwareConstants.kRR_DRIVE_CAN, MotorType.kBrushless);

    // Drive
    private MecanumDrive mecDrive;
    private double maxSpeed = DriveBaseConstants.kSLOW_MAX_SPEED;

    // IMU
    private AHRS gyro = new AHRS(SPI.Port.kMXP);

    /**
     * Instantiate and config a new DriveBase subsystem.
     */
    public DriveBase() {
        // These wheels are inverted
        wheel_fl.setInverted(true);
        wheel_fr.setInverted(false);
        wheel_rr.setInverted(false);
        wheel_rl.setInverted(true);

        // Init drive base
        mecDrive = new MecanumDrive(wheel_fl, wheel_rl, wheel_fr, wheel_rr);

        register();
    }

    // Sets the default functionality of this subsystem
    public void initDefaultCommand() {
        setDefaultCommand(
            new InstantCommand(() -> driveOpenLoop(0.0, 0.0, 0.0, false), 
            this));
    }

    /**
     * Drives the robot using open loop control (all values are percentages of the motor's maximum
     * duty cycle, not actually 'real-world' units)
     * @param y forward/backward velocity. Forward is positive
     * @param x left/right velocity. Left is positive
     * @param rot theta velocity. CCW is positive.
     * @param fieldOriented If true, the robot will drive relative to the 'field'. Otherwise, it 
     * will drive relative to itself.
     */
    public void driveOpenLoop(double y, double x, double rot, boolean fieldOriented) {
        // Sanity check all the speeds
        x = MathUtil.clamp(x * -maxSpeed, -1.0, 1.0);
        y = MathUtil.clamp(y * maxSpeed, -1.0, 1.0);
        rot = MathUtil.clamp(rot * maxSpeed, -1.0, 1.0);
        
        if (fieldOriented) {
            mecDrive.driveCartesian(y, x, rot, Rotation2d.fromDegrees(getYaw()));
        } else {
            mecDrive.driveCartesian(y, x, rot);
        }

    }

    @Override
    public void periodic() {
        mecDrive.feedWatchdog();
    }

    /**
     * Sets the maximum robot speed, for both translation and rotation.
     * <p>This value is a percentage of the motor's maximum duty cycle, not an actual 'real-world'
     * measurement.
     * @param maxSpeed
     */
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = MathUtil.clamp(maxSpeed, 0.0, 1.0);
    }

    /**
     * @return The gyro's yaw, in degrees (CCW+)
     */
    public double getYaw() {
        return gyro.getYaw();
    }

    /**
     * @return The gyro's pitch, in degrees (CCW+)
     */
    public double getPitch() {
        return gyro.getPitch();
    }

    /**
     * @return The gyro's roll, in degrees (CCW+)
     */
    public double getRoll() {
        return gyro.getRoll();
    }

    /**
     * @return A pretty-printed gyro output in "yaw, pitch, roll" format.
     */
    public String prettyPrintGyro() {
        return String.format("%f, %f, %f", getYaw(), getPitch(), getRoll());
    }

    /**
     * @return A sendable mecanum drive object, for telemetry.
     */
    public Sendable getMecanumSendable() {
        return mecDrive;
    }
}
