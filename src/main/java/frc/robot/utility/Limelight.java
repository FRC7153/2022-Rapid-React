package frc.robot.utility;

import edu.wpi.first.math.geometry.Pose2d;
import frc.robot.Constants.LimelightConstants;
import frc.robot.utility.LimelightHelpers.PoseEstimate;

public class Limelight {
    // Cache
    private boolean seesTags = false;

    /**
     * Sends the Yaw to the limelight for MT2 integration. Call this periodically.
     * @param yaw Yaw, degrees, CCW+
     */
    public void refresh(double yaw, double yawRate, double pitch, double pitchRate, double roll, double rollRate) {
        LimelightHelpers.SetRobotOrientation(
            LimelightConstants.NT_NAME, yaw, yawRate, pitch, pitchRate, roll, rollRate);
    }

    /**
     * @return Estimated robot pose2d, relative to blue alliance.
     */
    public Pose2d getPose() {
        PoseEstimate est = LimelightHelpers.getBotPoseEstimate_wpiBlue_MegaTag2(LimelightConstants.NT_NAME);
        seesTags = (est.tagCount != 0);
        return est.pose;
    }

    /**
     * @return Distance from robot's estimated position to center of field (trash can).
     */
    public double getDistanceToCenter() {
        return getPose().getTranslation().getNorm();
    }

    /**
     * @return If the camera saw tags on the last call to {@code getPose()}
     */
    public boolean seesTags() {
        return seesTags;
    }
}
