package frc.robot.subsystems;

import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.LimelightConstants;
import frc.robot.Constants.ShooterConstants;
import frc.robot.utility.Limelight;

/**
 * Subsystem for controlling the shooter and indexer.
 */
public final class Shooter implements Subsystem {
    // Motors
    private final SparkMax shooter1 = new SparkMax(HardwareConstants.kSHOOTER_1_CAN, MotorType.kBrushless);
    private final SparkMax shooter2 = new SparkMax(HardwareConstants.kSHOOTER_2_CAN, MotorType.kBrushless);
    private final SparkClosedLoopController shootPID = shooter1.getClosedLoopController();
    private final RelativeEncoder shooterEnc = shooter1.getEncoder();

    private final TalonFX indexerMotor = new TalonFX(HardwareConstants.kINDEXER_CAN);

    // Output to NT for debugging
    private final DoublePublisher indexerVeloOut;

    // Limelight
    public Limelight limelight = new Limelight(LimelightConstants.kNT_NAME);

    // Velocity target
    private double velocitySetpoint = 0.0;
    
    /**
     * Instantiate and config the shooter subsystem.
     */
    public Shooter() {
        // Config shooter PID
        shooter1.configure(ShooterConstants.SHOOTER_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        shooter2.configure(ShooterConstants.SHOOTER_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        shooter2.configure(new SparkMaxConfig().follow(shooter1, true), ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

        // Get NT logging output
        NetworkTable nt = NetworkTableInstance.getDefault().getTable("Debug");
        indexerVeloOut = nt.getDoubleTopic("Indexer Velo").publish();

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
            shootPID.setReference(speed, ControlType.kVelocity);
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

    @Override
    public void periodic() {
        // Output the indexer velocity (for debugging)
        indexerVeloOut.set(indexerMotor.getVelocity().getValueAsDouble());
    }
}
