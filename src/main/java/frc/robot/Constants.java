package frc.robot;

public class Constants {
    public static final class DriveBaseConstants {
        public static final double SLOW_MAX_SPEED = 0.4;
        public static final double FAST_MAX_SPEED = 0.9;
    }

    public static final class IntakeConstants {
        public static final double INTAKE_WHEEL_SPEED = 0.5;

        public static final int LEFT_OUT_CHANNEL = 4;
        public static final int RIGHT_OUT_CHANNEL = 6;
        public static final int LEFT_IN_CHANNEL = 5;
        public static final int RIGHT_IN_CHANNEL = 7;
    }

    public static final class ShooterConstants {
        public static final double INDEXER_SPEED = 0.85;

        public static final double SHOOT_P = 0.00008;
        public static final double SHOOT_I = 6e-7;
        public static final double SHOOT_D = 0.00002;
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
    }
}
