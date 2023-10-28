package frc.robot;

public class Constants {
    public static final class DriveBaseConstants {
        public static final double SLOW_MAX_SPEED = 0.3;
        public static final double FAST_MAX_SPEED = 0.5;
    }

    public static final class IntakeConstants {
        public static final double INTAKE_WHEEL_SPEED = 0.5;

        public static final int LEFT_OUT_CHANNEL = 7;
        public static final int RIGHT_OUT_CHANNEL = 5;
        public static final int LEFT_IN_CHANNEL = 6;
        public static final int RIGHT_IN_CHANNEL = 4;
    }

    public static final class ShooterConstants {
        public static final double INDEXER_SPEED = 0.85;

        public static final double SHOOT_P = 0.00008;
        public static final double SHOOT_I = 6e-7;
        public static final double SHOOT_D = 0.00002;

        public static final double INDEXER_TIMEOUT = 0.9;
    }

    public static final class TrajectoryConstants {
        public static final double TARGET_REGRESSION(double y) {
           //y = 261.8423x2 - 1715.7136x + 3942.7859
           return 261.8423 * y * y - 1715.7136 * y + 3942.7859;
        }
    }

    public static final class ClimberConstants {
        public static final int LEFT_UP_CHANNEL = 3;
        public static final int LEFT_DOWN_CHANNEL = 2;
        public static final int RIGHT_UP_CHANNEL = 0; // ?
        public static final int RIGHT_DOWN_CHANNEL = 5;
    }

    public static final class LimelightConstants {
        public static final double CACHE_TIMEOUT = 1.5; // seconds
        public static final String NT_NAME = "limelight";
    }

    public static final class HardwareConstants {
        // Drive base
        public static final int FL_DRIVE_CAN = 3;
        public static final int FR_DRIVE_CAN = 4;
        public static final int RL_DRIVE_CAN = 5;
        public static final int RR_DRIVE_CAN = 6;

        // Intake
        public static final int INTAKE_PH_CAN = 12;
        public static final int INTAKE_WHEEL_CAN = 9;

        // Shooter
        public static final int INDEXER_CAN = 10;
        public static final int SHOOTER_1_CAN = 7;
        public static final int SHOOTER_2_CAN = 8;

        // Climber
        public static final int CLIMBER_PH_CAN = 2;
    }
}
