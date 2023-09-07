package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.ClimberConstants;
import frc.robot.Constants.HardwareConstants;

public class Climber extends SubsystemBase {
    private DoubleSolenoid leftClimber = new DoubleSolenoid(
        HardwareConstants.CLIMBER_PH_CAN,   // Pneumatic hub CAN id
        PneumaticsModuleType.REVPH,         // This is a REV Pneumatics hub
        ClimberConstants.LEFT_UP_CHANNEL,   // The channel that makes it go up
        ClimberConstants.LEFT_DOWN_CHANNEL  // The channel that makes it go down
    );
    // TODO: Create right climber solenoid (similar to above)

    // The compressor
    private Compressor compressor = new Compressor(
        HardwareConstants.CLIMBER_PH_CAN, 
        PneumaticsModuleType.REVPH
    );

    // Set climber
    public void setClimberState(boolean up) {
        // TODO: in the blank lines, add code to control the right solenoid
        if (up) {
            leftClimber.set(Value.kForward);

        } else {
            leftClimber.set(Value.kReverse);

        }
    }

    // For logging, this will return if the air tanks are full
    public double getPressure() {
        return compressor.getPressure();
    }

    public boolean pressureFull() {
        return !compressor.getPressureSwitchValue();
    }
}
