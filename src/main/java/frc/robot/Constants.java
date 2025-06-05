package frc.robot;

import com.revrobotics.spark.config.ClosedLoopConfig;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.math.geometry.Translation2d;

public class Constants {
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

        public static final double kMIN_PRESSURE = 40; // PSI level to enable compressor
        public static final double kMAX_PRESSURE = 120; // PSI level to disable compressor
    }

    public static final class ShooterConstants {
        public static final double kINDEXER_SPEED = 0.5;

        public static final double kSHOOT_LOW_SPEED = 1900; // default RPM for shooting without target

        public static final SparkBaseConfig SHOOTER_CONFIG = new SparkMaxConfig()
          .inverted(true)
          .apply(
            new ClosedLoopConfig()
              .pidf(0.00008, 6e-7, 0.00002, 0.0)
              .outputRange(-1.0, 1.0)
            );

        public static final double kINDEXER_TIMEOUT = 1.15; // seconds
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
        public static final boolean kTAKE_PICTURES = true;

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
