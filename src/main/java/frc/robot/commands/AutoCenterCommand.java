package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Shooter;

public class AutoCenterCommand extends CommandBase {
    // Subsystem
    private DriveBase drive;
    private Shooter shooter;

    // Constructor
    public AutoCenterCommand(DriveBase drive, Shooter shooter) {
        this.drive = drive;
        this.shooter = shooter;

        addRequirements(drive);
    }

    // Start
    @Override
    public void initialize() {
        drive.drive(0.0, 0.0, 0.0);
    }

    // Run
    @Override
    public void execute() {
        drive.drive(
            0.0, 0.0,
            0.025 * shooter.getLLXpos()
        );
    }

    // End
    @Override
    public void end(boolean terminated) {
        drive.drive(0.0, 0.0, 0.0);
    }
}
