package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Shooter;
import frc.robot.utility.Dashboard;

/**
 * Shoots at the speed from Shuffleboard. For tuning regressions.
 */
public class ManualShootCommand extends SequentialCommandGroup {
    public ManualShootCommand(DriveBase drive, Shooter shooter, Dashboard db) {
        addCommands(
            new SequentialCommandGroup(
                new WaitCommand(ShooterConstants.INDEXER_TIMEOUT),
                new InstantCommand(shooter::indexerOn)
            ),
            new InstantCommand(() -> shooter.setShootSpeed(
                db.getManualSpeed()
            )).repeatedly()
        );

        addRequirements(shooter);
    }

    // Turn shooter off
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
