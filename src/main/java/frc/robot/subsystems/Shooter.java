package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonFXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.TrajectoryConstants;
import frc.robot.utility.Limelight;

public class Shooter extends SubsystemBase {
    // Motors
    private CANSparkMax shooter1 = new CANSparkMax(HardwareConstants.SHOOTER_1_CAN, MotorType.kBrushless);
    private CANSparkMax shooter2 = new CANSparkMax(HardwareConstants.SHOOTER_2_CAN, MotorType.kBrushless);
    private SparkMaxPIDController shootPID = shooter1.getPIDController();

    public TalonFX indexerCan = new TalonFX(HardwareConstants.INDEXER_CAN);

    // Limelight
    private Limelight limelight = new Limelight();
    
    // Constructor
    public Shooter() {
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
        limelight.refresh();
    }

    // Shooter
    public void setShootSpeed(double speed){
        if (Math.abs(speed) < 0.05) {
            shooter1.stopMotor();
            shooter2.stopMotor();
        } else {
            shootPID.setReference(speed, ControlType.kVelocity, 0);
        }
        
    }

    // Indexer
    public void indexerOff(){
        indexerCan.set(TalonFXControlMode.PercentOutput, 0.0);
    }

    public void indexerOn(){
        indexerCan.set(TalonFXControlMode.PercentOutput, ShooterConstants.INDEXER_SPEED);
    }
    
    // Get shoot speed from limelight
    public double getLLShootSpeed() {
        return TrajectoryConstants.TARGET_REGRESSION(limelight.getTA());
    }

    public double getLLXpos() {
        return limelight.getTX();
    }

    public boolean isLookingAtTarget() {
        return Math.abs(getLLXpos()) < 2.0;
    }
}
