package frc.robot.subsystems;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.drive.MecanumDrive;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveBase extends SubsystemBase {
    // Wheels
    private CANSparkMax frontLeft = new CANSparkMax(3, MotorType.kBrushless);
    private CANSparkMax frontRight = new CANSparkMax(4, MotorType.kBrushless);
    private CANSparkMax rearLeft = new CANSparkMax(5, MotorType.kBrushless);
    private CANSparkMax rearRight = new CANSparkMax(6, MotorType.kBrushless);

    // Base + Peripherals
    private MecanumDrive base = new MecanumDrive(frontLeft, rearLeft, frontRight, rearRight);
    private AHRS gyro = new AHRS(SPI.Port.kMXP);

    // Init
    public DriveBase() {
        // Invert
        rearRight.setInverted(true);
        frontRight.setInverted(true);

        // Current Limit
        frontLeft.setSmartCurrentLimit(40);
        frontRight.setSmartCurrentLimit(40);
        rearLeft.setSmartCurrentLimit(40);
        rearRight.setSmartCurrentLimit(40);
    }

    // Drive
    public void driveFieldOriented(double y, double x, double rot) { base.driveCartesian(x, y, rot, Rotation2d.fromDegrees(gyro.getYaw())); }
    public void driveRobotOriented(double y, double x, double rot) { base.driveCartesian(x, y, rot); }
}
