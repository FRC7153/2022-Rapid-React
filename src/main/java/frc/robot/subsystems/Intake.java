package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.TalonSRXControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.IntakeConstants;

public class Intake extends SubsystemBase {
    // Hardware
    private TalonSRX intakeWheel = new TalonSRX(HardwareConstants.INTAKE_WHEEL_CAN);
    
    private DoubleSolenoid leftPiston = new DoubleSolenoid(
        HardwareConstants.INTAKE_PH_CAN, 
        PneumaticsModuleType.REVPH,
        IntakeConstants.LEFT_IN_CHANNEL,
        IntakeConstants.LEFT_OUT_CHANNEL
    );

    private DoubleSolenoid rightPiston = new DoubleSolenoid(
        HardwareConstants.INTAKE_PH_CAN, 
        PneumaticsModuleType.REVPH, 
        IntakeConstants.RIGHT_IN_CHANNEL, 
        IntakeConstants.RIGHT_OUT_CHANNEL
    );

    // The compressor
    private Compressor compressor = new Compressor(
        HardwareConstants.CLIMBER_PH_CAN, 
        PneumaticsModuleType.REVPH
    );

    // Constructor
    public Intake() {
        // Disable on startup
        intakeUp();
    }

    // Intake Down
    public void intakeDown() {
        intakeWheel.set(TalonSRXControlMode.PercentOutput, IntakeConstants.INTAKE_WHEEL_SPEED);

        leftPiston.set(DoubleSolenoid.Value.kForward);
        rightPiston.set(DoubleSolenoid.Value.kForward);
    } 

    // Intake Up
    public void intakeUp() {
        intakeWheel.set(TalonSRXControlMode.PercentOutput, 0.0);

        leftPiston.set(DoubleSolenoid.Value.kReverse);
        rightPiston.set(DoubleSolenoid.Value.kReverse);
    }

    public double getPressure() {
        return compressor.getPressure();
    }
}
