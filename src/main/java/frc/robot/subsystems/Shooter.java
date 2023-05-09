package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;
import com.revrobotics.CANSparkMax.ControlType;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Shooter extends SubsystemBase {
    // Indexer
    private TalonFX indexer = new TalonFX(10);

    // Shooter
    private CANSparkMax leftShooter = new CANSparkMax(7, MotorType.kBrushless);
    private CANSparkMax rightShooter = new CANSparkMax(8, MotorType.kBrushless);

    private SparkMaxPIDController shootPID;

    // Init
    public Shooter() {
        // Follower
        rightShooter.follow(leftShooter, true);

        // PID Controller
        shootPID = leftShooter.getPIDController();

        shootPID.setP(0.00008);
        shootPID.setI(6e-7);
        shootPID.setD(0.00002);
        shootPID.setIZone(0);
        shootPID.setFF(0.0);
        shootPID.setOutputRange(-1.0, 1.0);

        // Off
        setShootSpeed(0.0);
    }

    // Set Shoot Speed
    public void setShootSpeed(double speed) {
        shootPID.setReference(speed, ControlType.kVelocity);
    }

    // Run Indexer
    public void setIndexer(boolean state) {
        if (state) {
            indexer.set(ControlMode.PercentOutput, 0.85);
        } else {
            indexer.set(ControlMode.PercentOutput, 0.0);
        }
    }
}
