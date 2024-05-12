package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Shooter;
import frc.robot.utility.Dashboard;

public class ManualShootCommand extends ParallelCommandGroup {
    /**
     * Shoots at the speed from Shuffleboard. For tuning regressions.
     * 
     * <p>{@code MANUAL_SHOOTING} in BuildConstants MUST be true for this to function.
     */
    public ManualShootCommand(DriveBase drive, Shooter shooter, Dashboard dashboard) {
        addCommands(
            // Set the indexer after a delay
            new SequentialCommandGroup(
                new WaitCommand(ShooterConstants.kINDEXER_TIMEOUT),
                new InstantCommand(() -> shooter.setIndexerState(true))
            ),
            // Set the shoot velocity
            new InstantCommand(() -> {
                shooter.setShootVelocity(dashboard.getManualShootVelocity());
            }).repeatedly()
        );

        addRequirements(shooter);
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
