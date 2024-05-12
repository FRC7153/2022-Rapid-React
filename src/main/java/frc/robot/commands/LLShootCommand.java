package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.WaitCommand;
import frc.robot.Constants.ShooterConstants;
import frc.robot.Constants.TrajectoryConstants;
import frc.robot.subsystems.Shooter;

public class LLShootCommand extends ParallelCommandGroup {
    /**
     * Instantiates and configs a new limelight shooting command.
     * Shoots, using the limelight and a regression.
     * If no limelight targets are seen, defaults to a slow speed.
     * @param shooter The shooter subsystem
     */
    public LLShootCommand(Shooter shooter) {
        addCommands(
            // Set the indexer after a delay
            new SequentialCommandGroup(
                new WaitCommand(ShooterConstants.kINDEXER_TIMEOUT),
                new InstantCommand(() -> shooter.setIndexerState(true))
            ),
            // Set the shooter velocity
            new InstantCommand(() -> {
                double dist = shooter.limelight.getDistanceToTrashCan();

                if (!shooter.limelight.seesTags() || !shooter.limelight.isAlive()) {
                    // No targets
                    DriverStation.reportWarning("Shooting without any limelight targets!", false);
                    shooter.setShootVelocity(ShooterConstants.kSHOOT_LOW_SPEED);
                } else {
                    dist = MathUtil.clamp(dist, 0.0, 4.5); // Sanity checks
                    shooter.setShootVelocity(TrajectoryConstants.TARGET_REGRESSION_V2(dist));
                }
            }).repeatedly()
        );

        addRequirements(shooter);
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
