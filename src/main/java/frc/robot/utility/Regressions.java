package frc.robot.utility;

/** Shooter regressions */
public final class Regressions {

    /**
     * Shooter velocity regression for retro-reflective tape around the green
     * trash can.
     *
     * <p>
     * {@code y = 261.8423x^2 - 1715.7136x + 3942.7859}
     *
     * @param y The y angle of the retro-reflective tape from the limelight
     * (degrees)
     * @return The shoot velocity (rpm)
     */
    public static final double TARGET_REGRESSION_V1(double y) {
        return 261.8423 * y * y - 1715.7136 * y + 3942.7859;
    }

    /**
     * Shooter velocity regression for apriltags around the green trash can,
     * with Limelight 3G.
     *
     * <p>
     * {@code y = -277.698x^2 + 2500.87x - 2158.17}
     *
     * @param d The distance to the trash can (meters)
     * @return The shoot velocity (rpm)
     */
    public static final double TARGET_REGRESSION_V2(double d) {
        return -277.698 * d * d + 2500.87 * d - 2158.17;
    }

    /**
     * Shooter velocity regression for apriltags around the green trash can,
     * with Limelight 2+.
     *
     * <p>
     * {@code y = 2.48344x^2 + 911.863x + 815.438}
     *
     * @param d The distance to the trash can (meters)
     * @return The shoot velocity (rpm)
     */
    public static final double TARGET_REGRESSION_V3(double d) {
        return 2.48344 * d * d + 911.863 * d + 845.438;
    }

    /**
     * Shooter velocity regression for apriltags around the green trash can,
     * with Limelight 2+. This produces lower velocities than v3.
     *
     * <p>
     * {@code y = 947.335x^2 - 2113.09x + 3251.95}
     *
     * @param d The distance to the trash can (meters)
     * @return The shoot velocity (rpm)
     */
    public static final double TARGET_REGRESSION_V4(double d) {
        return 947.335 * d * d - 2113.09 * d + 3251.85;
    }

    /**
     * 7/31/24
     */
    public static final double TARGET_REGRESSION_V5(double d) {
        return 644.555 * d * d - 1622.77 * d + 3056.59 + 30.0;
    }
}
