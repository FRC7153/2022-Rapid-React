package frc.robot;

import edu.wpi.first.math.geometry.Translation2d;

public class Constants {
    public static final class BuildConstants {
        public static final boolean kMANUAL_SHOOTING = false; // Shooter velocity input on Shuffleboard
    }

    public static final class DriveBaseConstants {
        public static final double kSLOW_MAX_SPEED = 0.3;
        public static final double kFAST_MAX_SPEED = 0.55;
    }

    public static final class IntakeConstants {
        public static final double kINTAKE_WHEEL_SPEED = 0.5; // % duty cycle
        public static final int kINTAKE_WHEEL_CURRENT_LIMIT = 25; // amps

        public static final int kLEFT_OUT_CHANNEL = 3;
        public static final int kRIGHT_OUT_CHANNEL = 1;
        public static final int kLEFT_IN_CHANNEL = 2;
        public static final int kRIGHT_IN_CHANNEL = 0;

        public static final double kMIN_PRESSURE = 20; // PSI level to enable compressor
        public static final double kMAX_PRESSURE = 60; // PSI level to disable compressor
    }

    public static final class ShooterConstants {
        public static final double kINDEXER_SPEED = 0.5;

        public static final double kSHOOT_LOW_SPEED = 2250; // default RPM for shooting

        public static final double kSHOOT_P = 0.00008;
        public static final double kSHOOT_I = 6e-7;
        public static final double kSHOOT_D = 0.00002;

        public static final double kINDEXER_TIMEOUT = 0.9; // seconds
    }

    public static final class TrajectoryConstants {
        /**
         * Shooter velocity regression for retro-reflective tape around the green trash can.
         * 
         * <p>{@code y = 261.8423x^2 - 1715.7136x + 3942.7859}
         * @param y The y angle of the retro-reflective tape from the limelight (degrees)
         * @return The shoot velocity (rpm)
         */
        public static final double TARGET_REGRESSION_V1(double y) {
           return 261.8423 * y * y - 1715.7136 * y + 3942.7859;
        }

        /**
         * Shooter velocity regression for apriltags around the green trash can, with Limelight
         * 3G.
         * 
         * <p>{@code y = -277.698x^2 + 2500.87x - 2158.17}
         * @param d The distance to the trash can (meters)
         * @return The shoot velocity (rpm)
         */
        public static final double TARGET_REGRESSION_V2(double d) {
            return -277.698 * d * d + 2500.87 * d - 2158.17;
        }

        /**
         * Shooter velocity regression for apriltags around the green trash can, with Limelight
         * 2+.
         * 
         * <p>{@code y = 2.48344x^2 + 911.863x + 815.438}
         * @param d The distance to the trash can (meters)
         * @return The shoot velocity (rpm)
         */
        public static final double TARGET_REGRESSION_V3(double d) {
            return 2.48344 * d * d + 911.863 * d + 815.438;
        }
    }

    public static final class ClimberConstants {
        public static final int kLEFT_UP_CHANNEL = 3;
        public static final int kLEFT_DOWN_CHANNEL = 2;
        public static final int kRIGHT_UP_CHANNEL = 0;
        public static final int kRIGHT_DOWN_CHANNEL = 5;
    }

    public static final class LimelightConstants {
        public static final double kCACHE_TIMEOUT = 0.75; // seconds
        public static final String kNT_NAME = "limelight-aetos"; // Host name

        /**
         * Trash can's position on the april tag map file.
         */
        public static final Translation2d kTRASH_CAN = new Translation2d(16.48/2.0, 8.10/2.0);
    }

    public static final class HardwareConstants {
        public static final int kPDH_CAN = 1;

        // Drive base
        public static final int kFL_DRIVE_CAN = 3;
        public static final int kFR_DRIVE_CAN = 4;
        public static final int kRL_DRIVE_CAN = 5;
        public static final int kRR_DRIVE_CAN = 6;

        // Intake
        public static final int kINTAKE_PH_CAN = 2; // Was 12
        public static final int kINTAKE_WHEEL_CAN = 9;

        // Shooter
        public static final int kINDEXER_CAN = 10;
        public static final int kSHOOTER_1_CAN = 7;
        public static final int kSHOOTER_2_CAN = 8;

        // Climber
        public static final int kCLIMBER_PH_CAN = 2; // No longer in use
    }
}
