package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.HardwareConstants;

/**
 * Subsystem for the robot's two climbing pistons.
 * 
 * <p><b>This subsystem has been physically removed from the robot in the offseason,
 * and this code is no longer used.</b>
 */
public class Climber implements Subsystem {
    // Create DoubleSolenoid objects
    private DoubleSolenoid leftClimber = new DoubleSolenoid(
        HardwareConstants.kCLIMBER_PH_CAN,   // Pneumatic hub CAN id
        PneumaticsModuleType.REVPH,         // This is a REV Pneumatics hub
        ClimberConstants.kLEFT_UP_CHANNEL,   // The channel that makes it go up
        ClimberConstants.kLEFT_DOWN_CHANNEL  // The channel that makes it go down
    );

    private DoubleSolenoid rightClimber = new DoubleSolenoid(
        HardwareConstants.kCLIMBER_PH_CAN,
        PneumaticsModuleType.REVPH,
        ClimberConstants.kRIGHT_UP_CHANNEL,
        ClimberConstants.kRIGHT_DOWN_CHANNEL
    );

    /**
     * Instantiate and config a new Climber subsystem
     */
    public Climber() {
        // Disable on startup
        setClimberState(false);

        register();
    }

    /**
     * Sets the climber's position
     * @param up true = hooks up, false = hooks down
     */
    public void setClimberState(boolean up) {
        if (!up) {
            leftClimber.set(Value.kForward);
            rightClimber.set(Value.kForward);
        } else {
            leftClimber.set(Value.kReverse);
            rightClimber.set(Value.kReverse);
        }
    }
}
