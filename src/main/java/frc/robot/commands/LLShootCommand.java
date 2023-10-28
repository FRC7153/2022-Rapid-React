package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import edu.wpi.first.wpilibj2.command.WaitUntilCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.TrajectoryConstants;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Shooter;
import frc.robot.utility.Dashboard;

public class LLShootCommand extends ParallelCommandGroup {
    public LLShootCommand(DriveBase drive, Shooter shooter, Dashboard db) {
        addCommands(
            new AutoCenterCommand(drive, shooter),
            new SequentialCommandGroup(
                new WaitUntilCommand(shooter.limelight::isCentered),
                new ParallelCommandGroup(
                    new SequentialCommandGroup(
                        new WaitCommand(ShooterConstants.INDEXER_TIMEOUT),
                        new InstantCommand(shooter::indexerOn)
                    ),
                    new InstantCommand(() -> shooter.setShootSpeed(
                        TrajectoryConstants.TARGET_REGRESSION(shooter.limelight.getTA())
                    )).repeatedly()
                )
                //new InstantCommand(() -> shooter.setShootSpeed(db.getManualSpeed()), shooter),
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
