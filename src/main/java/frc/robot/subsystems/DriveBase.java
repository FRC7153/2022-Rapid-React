package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.DriveBaseConstants;
import frc.robot.Constants.HardwareConstants;

public class DriveBase extends SubsystemBase {
    // Hardware
    private CANSparkMax wheel_fl = new CANSparkMax(HardwareConstants.FL_DRIVE_CAN, MotorType.kBrushless);
    private CANSparkMax wheel_fr = new CANSparkMax(HardwareConstants.FR_DRIVE_CAN, MotorType.kBrushless);
    private CANSparkMax wheel_rl = new CANSparkMax(HardwareConstants.RL_DRIVE_CAN, MotorType.kBrushless);
    private CANSparkMax wheel_rr = new CANSparkMax(HardwareConstants.RR_DRIVE_CAN, MotorType.kBrushless);

    // Drive
    private MecanumDrive mecDrive;
    private double maxSpeed = DriveBaseConstants.SLOW_MAX_SPEED;

    // IMU
    private AHRS gyro = new AHRS(SPI.Port.kMXP);

    // Constructor
    public DriveBase() {
        // These are inverted
        wheel_fl.setInverted(false);
        wheel_fr.setInverted(true);
        wheel_rr.setInverted(true);
        wheel_rl.setInverted(false);

        // Init drive base
        mecDrive = new MecanumDrive(wheel_fl, wheel_rl, wheel_fr, wheel_rr);
    }

    // Drive method
    public void drive(double y, double x, double rot) {
        // TODO debug
        // Note the axes that are inverted below
        x *= -maxSpeed;
        y *= maxSpeed;
        rot *= -maxSpeed;
        
        //mecDrive.driveCartesian(x, y, rot, Rotation2d.fromDegrees(gyro.getPitch()));
        mecDrive.driveCartesian(y, x, rot);
    }

    // Set max speed
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }

    // Get yaw
    public double getYaw() {
        return gyro.getRoll() * 360.0;
    }
}
