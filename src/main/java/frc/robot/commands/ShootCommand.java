package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;
import frc.robot.utility.BallSpeed;

public class ShootCommand extends SequentialCommandGroup {
    public ShootCommand(Shooter shooter) {
        addCommands(
            new InstantCommand(() -> shooter.setShootSpeed(BallSpeed.getSuggestedSpeed()), shooter),
            new WaitCommand(ShooterConstants.INDEXER_TIMEOUT),
            new InstantCommand(shooter::indexerOn, shooter)
        );

        addRequirements(shooter);
    }

    // Turn shooter off
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
