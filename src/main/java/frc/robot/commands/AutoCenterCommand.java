package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Shooter;

public class AutoCenterCommand extends Command {
    // Subsystem
    private final DriveBase drive;
    private final Shooter shooter;

    // Constructor
    public AutoCenterCommand(DriveBase drive, Shooter shooter) {
        this.drive = drive;
        this.shooter = shooter;

        addRequirements(drive);
    }

    // Start
    @Override
    public void initialize() {
        drive.driveOpenLoop(0.0, 0.0, 0.0, false);
    }

    // Run
    @Override
    public void execute() {
        drive.driveOpenLoop(
            0.0, 
            0.0, 
            0.025 * shooter.getLimelight().getAngleToTrashCan(), 
            false
        );
    }

    // End
    @Override
    public void end(boolean terminated) {
        drive.driveOpenLoop(0.0, 0.0, 0.0, false);
    }
}
