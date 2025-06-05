package frc.robot.utility;

import java.util.UUID;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.networktables.BooleanPublisher;
import edu.wpi.first.networktables.DoubleSubscriber;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.shuffleboard.SendableCameraWrapper;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants.LimelightConstants;
import frc.robot.utility.LimelightHelpers.PoseEstimate;

public class Limelight {
    // Cache
    private boolean seesTags = false; // If tags have been seen
    private double lastHeartBeat = -1.0; // Last heartbeat received
    private double lastHeartBeatTimeStamp = -1.0; // Timestamp of last heartbeat

    // Network Tables
    private final String name;
    private final DoubleSubscriber heartbeatSub;

    // Telemetry
    private final BooleanPublisher isAlive;

    /**
     * Instantiates a new LimeLight.
     * @param name The name of the limelight.
     */
    public Limelight(String name) {
        this.name = LimelightHelpers.sanitizeName(name);

        NetworkTable nt = NetworkTableInstance.getDefault().getTable(this.name);
        heartbeatSub = nt.getDoubleTopic("hb").subscribe(-1.0);

        // Enforce pipeline 0
        LimelightHelpers.setPipelineIndex(this.name, 0);

        // Init telemetry
        nt = NetworkTableInstance.getDefault().getTable("dashboard").getSubTable("limelights").getSubTable(this.name);
        isAlive = nt.getBooleanTopic("IsAlive").publish();

        SmartDashboard.putData(this.name, SendableCameraWrapper.wrap(this.name, getURL()));
    }

    /**
     * Sends the Yaw to the limelight for MT2 integration. Call this periodically, if using MT2.
     */
    /*public void refresh(double yaw, double yawRate, double pitch, double pitchRate, double roll, double rollRate) {
        LimelightHelpers.SetRobotOrientation(
            name, yaw, yawRate, pitch, pitchRate, roll, rollRate);
    }*/

    /**
     * @return Estimated robot pose2d, relative to blue alliance.
     */
    public Pose2d getPose() {
        PoseEstimate est = LimelightHelpers.getBotPoseEstimate_wpiBlue(name);
        seesTags = (est.tagCount != 0);
        return est.pose;
    }

    /**
     * @return Distance from robot's estimated position to the trash can
     */
    public double getDistanceToTrashCan() {
        return getPose().getTranslation().getDistance(LimelightConstants.kTRASH_CAN);
    }

    /**
     * @return Vertical angle (deg) to the best AprilTag target (tx)
     */
    public double getAngleToTrashCan() {
        return LimelightHelpers.getTX(name);        
    }

    /**
     * @return If the camera saw tags on the last call to {@code getPose()} or 
     * {@code getDistanceToCenter()}
     */
    public boolean seesTags() {
        return seesTags;
    }

    /**
     * Checks the limelight's heartbeat to see if it's still responding.
     * @return Whether the limelight is still responding
     */
    public boolean isAlive() {
        double newHeartBeat = heartbeatSub.get();

        if (newHeartBeat == -1.0) {
            // Limelight is not publishing a heartbeat!
            return false;
        } else if (newHeartBeat != lastHeartBeat) {
            // Limelight has pinged!
            lastHeartBeat = newHeartBeat;
            lastHeartBeatTimeStamp = Timer.getFPGATimestamp();
            return true;
        } else if (Timer.getFPGATimestamp() - lastHeartBeatTimeStamp <= LimelightConstants.kCACHE_TIMEOUT) {
            // Limelight has pinged recently!
            return true;
        } else {
            // Limelight has not pinged recently!
            return false;
        }
    }

    /**
     * Takes a snapshot with the limelight, and gives it a random UUID name.
     * This only works if the kTAKE_PICTURES configuration is set to true.
     */
    public void takeSnapshot() {
        if (!LimelightConstants.kTAKE_PICTURES) return;

        String snapshotName = UUID.randomUUID().toString();
        LimelightHelpers.takeSnapshot(name, snapshotName);
        System.out.printf("Limelight captured: '%s'\n", snapshotName);
    }

    /**
     * @return A URL for viewing this camera stream
     */
    public final String getURL() {
        return String.format("http://%s.local:5800/", name);
    }

    public void log() {
      isAlive.set(isAlive());
    }
}
