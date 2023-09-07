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

public class Shooter extends SubsystemBase {
    // Remember to set the following two motors as brushless
    private CANSparkMax shooter1 = new CANSparkMax(HardwareConstants.SHOOTER_1_CAN, MotorType.kBrushless);
    private CANSparkMax shooter2 = new CANSparkMax(HardwareConstants.SHOOTER_2_CAN, MotorType.kBrushless);
    private SparkMaxPIDController shootPID = shooter1.getPIDController();

    public TalonFX indexerCan = new TalonFX(HardwareConstants.INDEXER_CAN);
    
    // Constructor
    public Shooter() {
        shootPID.setP(ShooterConstants.SHOOT_P, 0);
        shootPID.setI(ShooterConstants.SHOOT_I, 0);
        shootPID.setD(ShooterConstants.SHOOT_D, 0);
        shootPID.setFF(0.0, 0);
        shootPID.setOutputRange(-1.0, 1.0, 0);

        shooter2.follow(shooter1, true);

        setShootSpeed(0.0);
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
        //INDEXER_SPEED.set(0.0);
        indexerCan.set(TalonFXControlMode.PercentOutput, 0.0);
    }

    public void indexerOn(){
        //INDEXER_SPEED.set(0.85);
        indexerCan.set(TalonFXControlMode.PercentOutput, ShooterConstants.INDEXER_SPEED);
    }
}
