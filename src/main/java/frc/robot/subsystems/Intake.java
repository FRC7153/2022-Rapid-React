package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.PneumaticsModuleType;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class Intake extends SubsystemBase {
    // Solenoids
    private DoubleSolenoid leftSolenoid = new DoubleSolenoid(12, PneumaticsModuleType.REVPH, 7, 6);
    private DoubleSolenoid rightSolenoid =  new DoubleSolenoid(12, PneumaticsModuleType.REVPH, 5, 4);

    // Motors
    private TalonSRX intakeWheel = new TalonSRX(9);

    // Set State
    public void setIntakeState(boolean state) {
        DoubleSolenoid.Value val = (state) ? DoubleSolenoid.Value.kForward : DoubleSolenoid.Value.kReverse;

        leftSolenoid.set(val);
        rightSolenoid.set(val);

        intakeWheel.set(ControlMode.PercentOutput, (state) ? 0.5 : 0.0);
    }
}
