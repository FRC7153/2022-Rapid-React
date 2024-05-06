package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.TrajectoryConstants;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Shooter;

public class LLShootCommand extends SequentialCommandGroup {
    public LLShootCommand(DriveBase drive, Shooter shooter) {
        addCommands(
            new SequentialCommandGroup(
                new WaitCommand(ShooterConstants.INDEXER_TIMEOUT),
                new InstantCommand(shooter::indexerOn)
            ),
            new InstantCommand(() -> shooter.setShootSpeed(
                TrajectoryConstants.TARGET_REGRESSION(shooter.limelight.getDistanceToCenter())
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
