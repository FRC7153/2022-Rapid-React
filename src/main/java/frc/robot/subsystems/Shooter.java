package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.SparkPIDController;
import com.revrobotics.CANSparkBase.ControlType;
import com.revrobotics.CANSparkLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.LimelightConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.utility.Limelight;

/**
 * Subsystem for controlling the shooter and indexer.
 */
public class Shooter implements Subsystem {
    // Motors
    private CANSparkMax shooter1 = new CANSparkMax(HardwareConstants.kSHOOTER_1_CAN, MotorType.kBrushless);
    private CANSparkMax shooter2 = new CANSparkMax(HardwareConstants.kSHOOTER_2_CAN, MotorType.kBrushless);
    private SparkPIDController shootPID = shooter1.getPIDController();
    private RelativeEncoder shooterEnc = shooter1.getEncoder();

    public TalonFX indexerMotor = new TalonFX(HardwareConstants.kINDEXER_CAN);

    // Limelight
    public Limelight limelight = new Limelight(LimelightConstants.kNT_NAME);

    // Velocity target
    private double velocitySetpoint = 0.0;
    
    /**
     * Instantiate and config the shooter subsystem.
     */
    public Shooter() {
        // Config shooter PID
        shootPID.setP(ShooterConstants.kSHOOT_P, 0);
        shootPID.setI(ShooterConstants.kSHOOT_I, 0);
        shootPID.setD(ShooterConstants.kSHOOT_D, 0);
        shootPID.setFF(0.0, 0);
        shootPID.setOutputRange(-1.0, 1.0, 0);

        shooter1.setInverted(true);
        shooter2.follow(shooter1, true);

        // Disable on startup
        setShootVelocity(0.0);
        setIndexerState(false);

        register();
    }

    /**
     * Sets the default functionality of this subsystem (shooter and indexer disabled)
     */
    public void initDefaultCommand() {
        setDefaultCommand(
            new InstantCommand(() -> {
                setShootVelocity(0.0); setIndexerState(false);
            }, this));
    }

    /**
     * Sets the shooter's velocity.
     * @param speed Velocity, rpm
     */
    public void setShootVelocity(double speed){
        if (Math.abs(speed) < 0.05) {
            // Deadband for low speeds
            shooter1.stopMotor();
            shooter2.stopMotor();
            velocitySetpoint = 0.0;
        } else {
            shootPID.setReference(speed, ControlType.kVelocity, 0);
            velocitySetpoint = speed;
        }
        
    }

    /**
     * Sets the indexer's state.
     * @param running If true, the indexer is feeding. Otherwise, it is stopped
     */
    public void setIndexerState(boolean running) {
        if (running) {
            indexerMotor.set(ShooterConstants.kINDEXER_SPEED);
        } else {
            indexerMotor.disable();
        }
    }

    /**
     * @return The percentage of setpoint that the shooter is currently traveling at.
     */
    public double getShootVelocityErrorPercentage() {
        if (velocitySetpoint == 0.0) return 1.0;
        return (shooterEnc.getVelocity() / velocitySetpoint);
    }
}
