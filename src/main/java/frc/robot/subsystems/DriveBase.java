package frc.robot.subsystems;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

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
    private MecanumDrive mecDrive = new MecanumDrive(wheel_fl, wheel_fr, wheel_rl, wheel_rr);
    private double maxSpeed = DriveBaseConstants.SLOW_MAX_SPEED;

    // Drive Method
    public void drive(double y, double x, double rot) {
        x *= maxSpeed;
        y *= maxSpeed;
        rot *= maxSpeed;
        
        mecDrive.driveCartesian(x, y, rot);
    }

    // Set max speed
    public void setMaxSpeed(double maxSpeed) {
        this.maxSpeed = maxSpeed;
    }
}
