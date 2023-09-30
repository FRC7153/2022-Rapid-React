package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.RobotContainer;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Shooter;

public class ShootCommand extends ParallelCommandGroup {
    public ShootCommand(DriveBase drive, Shooter shooter) {
        addCommands(
            new AutoCenterCommand(drive, shooter),
            new SequentialCommandGroup(
                new WaitUntilCommand(shooter::isLookingAtTarget),
                // TODO set in real time:
                new InstantCommand(() -> shooter.setShootSpeed(shooter.getLLShootSpeed()), shooter),
                new WaitCommand(ShooterConstants.INDEXER_TIMEOUT),
                new InstantCommand(shooter::indexerOn, shooter)
            )
        );

        addRequirements(shooter);
    }

    // Turn shooter off
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
