package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.PrintCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;

public class LowShootCommand extends ParallelCommandGroup {
    
    // Constructor
    public LowShootCommand(Shooter shooter) {
        addCommands(
            new PrintCommand("No target, shooting LOW"),
            new InstantCommand(() -> shooter.setShootSpeed(2250)),
            new SequentialCommandGroup(
                new WaitCommand(ShooterConstants.INDEXER_TIMEOUT),
                new InstantCommand(shooter::indexerOn)
            )
        );

        addRequirements(shooter);
    }

    // Interruption
    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
