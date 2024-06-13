package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.IntakeConstants;

/**
 * The subsystem for controlling the intake.
 */
public class Intake implements Subsystem {
    // Hardware
    private TalonSRX intakeWheel = new TalonSRX(HardwareConstants.kINTAKE_WHEEL_CAN);
    
    private DoubleSolenoid leftPiston = new DoubleSolenoid(
        HardwareConstants.kINTAKE_PH_CAN,    // Pneumatics hub CAN ID
        PneumaticsModuleType.REVPH,         // This is a REV Pneumatics Hub
        IntakeConstants.kLEFT_OUT_CHANNEL,    // The channel that makes it extend
        IntakeConstants.kLEFT_IN_CHANNEL    // The channel that makes it retract
    );

    private DoubleSolenoid rightPiston = new DoubleSolenoid(
        HardwareConstants.kINTAKE_PH_CAN, 
        PneumaticsModuleType.REVPH, 
        IntakeConstants.kRIGHT_OUT_CHANNEL, 
        IntakeConstants.kRIGHT_IN_CHANNEL
    );

    private Compressor compressor = new Compressor(
        HardwareConstants.kCLIMBER_PH_CAN, 
        PneumaticsModuleType.REVPH
    );

    /**
     * Instantiates and configures a new Intake subsystem. (including the pneumatics hub)
     */
    public Intake() {
        // Intake motor config
        intakeWheel.configContinuousCurrentLimit(
            IntakeConstants.kINTAKE_WHEEL_CURRENT_LIMIT, 15);

        // Compressor config
        compressor.enableHybrid(
            IntakeConstants.kMIN_PRESSURE, IntakeConstants.kMAX_PRESSURE);

        // To turn off compressor:
        //compressor.disable();

        // Disable on startup
        setIntakeState(false);

        register();
    }

    /** 
     * Sets the default functionality of this subsystem. (The intake stowed)
     */
    public void initDefaultCommand() {
        setDefaultCommand(new InstantCommand(() -> this.setIntakeState(false), this));
    }

    /**
     * Sets the intake's state.
     * @param intake If true, the intake is out. If false, the intake is stowed
     */
    public void setIntakeState(boolean intake) {
        if (!intake) {
            intakeWheel.set(TalonSRXControlMode.PercentOutput, 0.0);

            leftPiston.set(DoubleSolenoid.Value.kReverse);
            rightPiston.set(DoubleSolenoid.Value.kReverse);
        } else {
            intakeWheel.set(TalonSRXControlMode.PercentOutput, IntakeConstants.kINTAKE_WHEEL_SPEED);

            leftPiston.set(DoubleSolenoid.Value.kForward);
            rightPiston.set(DoubleSolenoid.Value.kForward);
        }
    }

    /**
     * @return The current pressure level, in PSI.
     */
    public double getPressure() {
        return compressor.getPressure();
    }

    /**
     * @return The digital pressure switch value. (true if full)
     */
    public boolean getPressureSwitch() {
        return !compressor.getPressureSwitchValue();
    }
}
