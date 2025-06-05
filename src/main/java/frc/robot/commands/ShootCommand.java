package frc.robot.commands;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants.ShooterConstants;
import frc.robot.subsystems.Shooter;
import frc.robot.utility.Limelight;
import frc.robot.utility.Regressions;

public class ShootCommand extends Command {
    private final Shooter shooter;
    private final Limelight limelight;

    private double limelightDistanceAvg; // Average distance during each shot
    private boolean hasSeenTagsYet; // Whether the limelight has seen tags since command initialized
    private final Timer indexerTimer = new Timer();
    private int strategy; // Current shoot strategy (from Shuffleboard)

    /**
     * Instantiates and configs a new limelight shooting command.
     * Shoots, using the limelight and a regression.
     * This should be done when the robot is stationary!
     * If no limelight targets are seen, defaults to a slow speed.
     * @param shooter The shooter subsystem
     */
    public ShootCommand(Shooter shooter) {
        this.shooter = shooter;
        this.limelight = shooter.getLimelight();

        addRequirements(shooter);
    }

    /**
     * Calculates the shooter velocity, based on the current strategy from Shuffleboard and the 
     * current limelight input (if needed).
     * @param strategy The strategy from Shuffleboard (see Dashboard.java).
     * @param distance The distance from the Limelight.
     * @return Shoot velocity (RPM).
     */
    private double calculateShootStrategy(double distance) {
        switch (strategy) {
            case -1: return ShooterConstants.kSHOOT_LOW_SPEED;
            case 0: return shooter.getManualShootVelocity();
            case 3: return Regressions.TARGET_REGRESSION_V3(distance);
            case 4: return Regressions.TARGET_REGRESSION_V4(distance);
            case 5: return Regressions.TARGET_REGRESSION_V5(distance);
            default: {
                // This will run if none of the above cases are matched
                DriverStation.reportWarning(
                    String.format("Unknown shoot strategy: %d", strategy), 
                    false
                );
                return 0.0;
            }
        }
    }

    @Override
    public void initialize() {
        // Get initial limelight reading
        limelightDistanceAvg = limelight.getDistanceToTrashCan();
        limelightDistanceAvg = MathUtil.clamp(limelightDistanceAvg, 0.0, 4.0); // safety

        if (!limelight.seesTags() || !limelight.isAlive()) {
            // No targets
            DriverStation.reportWarning("Shooting without any limelight targets!", false);
            hasSeenTagsYet = false;
            limelightDistanceAvg = 0.0;
        } else {
            // Good target
            hasSeenTagsYet = true;
        }

        // Start indexer timer
        indexerTimer.restart();
        shooter.setIndexerState(false);

        // Get shoot strategy
        strategy = shooter.getShootStrategy();
    }

    @Override
    public void execute() {
        // Get new measurement
        double dist = limelight.getDistanceToTrashCan();
        dist = MathUtil.clamp(dist, 0.0, 4.0); // safety

        if (limelight.seesTags() && limelight.isAlive()) {
            // This is a good measurement
            if (!hasSeenTagsYet) {
                // This is the first measurement
                hasSeenTagsYet = true;
                limelightDistanceAvg = dist;
            } else {
                // Not the first measurement, factor it in
                limelightDistanceAvg = ((limelightDistanceAvg + dist) / 2.0);
            }
        }

        // Apply speed
        // In Dashboard.java, strategies <= 0 do not require a limelight input
        if (hasSeenTagsYet || strategy <= 0 ) {
            double setpoint = calculateShootStrategy(limelightDistanceAvg);
            shooter.setShootVelocity(setpoint);
        } else {
            // This is the default if the strategy requires a limelight input but no targets
            // have been found yet.
            shooter.setShootVelocity(ShooterConstants.kSHOOT_LOW_SPEED);
        }
        
        // Run indexer
        if (indexerTimer.hasElapsed(ShooterConstants.kINDEXER_TIMEOUT)) {
            shooter.setIndexerState(true);
        }
    }

    @Override
    public void end(boolean terminated) {
        shooter.setShootVelocity(0.0);
        shooter.setIndexerState(false);
    }

    @Override
    public boolean isFinished() {
        // Never end
        return false;
    }

    @Override
    public InterruptionBehavior getInterruptionBehavior() {
        return InterruptionBehavior.kCancelSelf;
    }
}
