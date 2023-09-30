package frc.robot.utility;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveBase;

public class Dashboard {
    // Drive Tab
    private GenericEntry compPressure;
    private GenericEntry gyro;

    // Subsystems
    private DriveBase drive;
    private Climber climber;

    public Dashboard(DriveBase drive, Climber climber) {
        // Unpack subsystems
        this.drive = drive;
        this.climber = climber;

        // Drive tab
        ShuffleboardTab driveTab = Shuffleboard.getTab("Drive");

        // Camera
        driveTab.addCamera("Limelight Stream", "Limelight Stream", "http://10.71.53.11:5800/")
            .withWidget(BuiltInWidgets.kCameraStream)
            .withPosition(1, 0)
            .withSize(3, 3)
            .withProperties(Map.of("SHOW CONTROLS", false));

        // Gyro
        gyro = driveTab.add("Gyro (Yaw)", 0.0)
            .withPosition(0, 1)
            .getEntry();

        // Pressure
        compPressure = driveTab.add("Pressure (PSI)", 0.0)
            .withPosition(0, 0)
            .getEntry();
    }

    // Periodic
    public void periodic() {
        gyro.setDouble(drive.getYaw());
        compPressure.setDouble(climber.getPressure());
    }
}
