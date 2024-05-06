package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.BaseMotorController;
import com.ctre.phoenix.motorcontrol.can.BaseTalon;
import com.ctre.phoenix.motorcontrol.ControlMode;

import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj.motorcontrol.Talon;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.utility.Limelight;

public class Shooter extends SubsystemBase {
    // Motors
    private CANSparkMax shooter1 = new CANSparkMax(HardwareConstants.SHOOTER_1_CAN, MotorType.kBrushless);
    private CANSparkMax shooter2 = new CANSparkMax(HardwareConstants.SHOOTER_2_CAN, MotorType.kBrushless);
    private SparkPIDController shootPID = shooter1.getPIDController();
    private RelativeEncoder shooterEnc = shooter1.getEncoder();

    public Talon indexerCan = new Talon(HardwareConstants.INDEXER_CAN);

    // Limelight
    public Limelight limelight = new Limelight();
    private DriveBase base;

    // Speed
    private double currentSpeed = 0.0;
    
    /**
     * Init
     * @param drive (for gyro + limelight)
     */
    public Shooter(DriveBase drive) {
        this.base = drive;

        // Config PID
        shootPID.setP(ShooterConstants.SHOOT_P, 0);
        shootPID.setI(ShooterConstants.SHOOT_I, 0);
        shootPID.setD(ShooterConstants.SHOOT_D, 0);
        shootPID.setFF(0.0, 0);
        shootPID.setOutputRange(-1.0, 1.0, 0);

        shooter1.setInverted(true);
        shooter2.follow(shooter1, true);

        // Disable on startup
        setShootSpeed(0.0);
        indexerOff();
    }
    
    // Periodic
    @Override
    public void periodic() {
        // Our robot is stable, we can assume pitch and roll is always 0.0
        limelight.refresh(
            // Yaw
            base.getYaw(),
            base.getYawRate(),
            // Pitch
            0.0,
            0.0,
            // Roll
            0.0,
            0.0
        );
    }

    // Shooter
    public void setShootSpeed(double speed){
        if (Math.abs(speed) < 0.05) {
            shooter1.stopMotor();
            shooter2.stopMotor();
            currentSpeed = 0.0;
        } else {
            shootPID.setReference(speed, ControlType.kVelocity, 0);
            currentSpeed = speed;
        }
        
    }

    // Indexer
    public void indexerOff(){
        indexerCan.set(0.0);
    }

    public void indexerOn(){
        indexerCan.set( ShooterConstants.INDEXER_SPEED);
    }

    // Telemetry
    public double getSetpointPercentage() {
        if (currentSpeed == 0.0) { return 1.0; }
        return shooterEnc.getVelocity() / currentSpeed;
    }
}
