package frc.robot.subsystems;

import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.networktables.DoubleEntry;
import edu.wpi.first.networktables.DoublePublisher;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
    private final StatusSignal<AngularVelocity> indexerVelocity = indexerMotor.getVelocity();

    // Limelight
    private final Limelight limelight = new Limelight(LimelightConstants.kNT_NAME);

    // Velocity target
    private double velocitySetpoint = 0.0;

    // Telemetry
    private final DoublePublisher shooterErrOut, targetDistanceOut, indexerVeloOut;
    private final DoubleEntry manualShootVeloEntry;
    private final SendableChooser<Integer> shootStrategyChooser;
    
    /**
     * Instantiate and config the shooter subsystem.
     */
    public Shooter() {
        // Config shooter PID
        shooter1.configure(ShooterConstants.SHOOTER_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        
        shooter2.configure(ShooterConstants.SHOOTER_CONFIG, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
        shooter2.configure(new SparkMaxConfig().follow(shooter1, true), ResetMode.kNoResetSafeParameters, PersistMode.kPersistParameters);

        // Disable on startup
        setShootVelocity(0.0);
        setIndexerState(false);

        // Init telemetry
        NetworkTable nt = NetworkTableInstance.getDefault().getTable("dashboard").getSubTable("shooter");

        shooterErrOut = nt.getDoubleTopic("ShooterError").publish();
        targetDistanceOut = nt.getDoubleTopic("TargetDistance").publish();
        indexerVeloOut = nt.getDoubleTopic("IndexerVelo").publish();

        manualShootVeloEntry = nt.getDoubleTopic("ManualShootVelo").getEntry(0.0);
        manualShootVeloEntry.set(0.0);

        // Init shoot strategy chooser
        shootStrategyChooser = new SendableChooser<>();

        shootStrategyChooser.onChange((Integer newStrategy) -> {
            System.out.printf("Shoot strategy has changed: %d\n", newStrategy);
        });

        shootStrategyChooser.addOption("Low Constant", -1); // Constant low velocity
        shootStrategyChooser.addOption("Manual", 0); // Manual input from Shuffleboard
        shootStrategyChooser.addOption("Reg v3", 3); // Regression V3
        shootStrategyChooser.addOption("Reg v4", 4); // Regression V4
        shootStrategyChooser.setDefaultOption("Reg v5", 5);

        SmartDashboard.putData("ShootStrategyChooser", shootStrategyChooser);

        // Init subsystem
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

    /**
     * Gets the manual shoot velocity from the dashboard input, supplied by the user.
     * @return RPM
     */
    public double getManualShootVelocity() {
        return manualShootVeloEntry.get();
    }

    /**
     * Gets the currently selected shoot strategy.
     * @return The unique integer associated with the selected option.
     */
    public int getShootStrategy() {
        return shootStrategyChooser.getSelected();
    }

    public void log() {
        indexerVelocity.refresh();

        shooterErrOut.set(getShootVelocityErrorPercentage());
        targetDistanceOut.set(limelight.getDistanceToTrashCan());
        indexerVeloOut.set(indexerVelocity.getValueAsDouble());

        limelight.log();
    }

    /**
     * @return The limelight
     */
    public Limelight getLimelight() {
        return limelight;
    }
}
