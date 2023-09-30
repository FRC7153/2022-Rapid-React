package frc.robot.utility;

import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.Constants.LimelightConstants;

public class Limelight {
    // Network objects
    private NetworkTable limelight = NetworkTableInstance.getDefault().getTable(LimelightConstants.NT_NAME);
    private DoubleSubscriber llTx = limelight.getDoubleTopic("tx").subscribe(0.0);
    private DoubleSubscriber llTa = limelight.getDoubleTopic("ta").subscribe(0.0);
    private DoubleSubscriber llTv = limelight.getDoubleTopic("tv").subscribe(0.0);

    // Cache
    private double[] cache = {0.0, 0.0};
    private double cacheTimestamp = 0.0;

    // Refresh (call periodically)
    public void refresh() {
        if (llTv.get() == 1.0) {
            // Refresh the cache
            cache[0] = llTx.get();
            cache[1] = llTa.get();

            cacheTimestamp = Timer.getFPGATimestamp();
        }
    }

    // Get stale
    public boolean isStale() {
        return (Timer.getFPGATimestamp() - cacheTimestamp > LimelightConstants.CACHE_TIMEOUT);
    }

    // Get values (check if stale)
    public double getTX() {
        return isStale() ? 15.0 : cache[0]; // defaults to 15.0
    }

    public double getTA() {
        return isStale() ? 0.0 : cache[1];
    }
}
