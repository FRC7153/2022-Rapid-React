package frc.robot.utility;

import java.util.Map;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class Dashboard {
    // Drive Tab
    private GenericEntry compPressure;
    private GenericEntry targetDistance;
    private GenericEntry gyro;
    private GenericEntry shootSpeed;
    private GenericEntry shootManual;
    private GenericEntry seesTags;

    // Subsystems
    private DriveBase drive;
    private Shooter shooter;
    private Intake intake;
    //private Climber climber;

    public Dashboard(DriveBase drive, Shooter shooter, Intake intake) {
        // Unpack subsystems
        this.drive = drive;
        this.shooter = shooter;
        this.intake = intake;
        //this.climber = climber;

        // Drive tab
        ShuffleboardTab driveTab = Shuffleboard.getTab("Drive");

        // Camera
        driveTab.addCamera("Limelight Stream", "Limelight Stream", "http://limelight.local:5800/")
            .withWidget(BuiltInWidgets.kCameraStream)
            .withPosition(1, 0)
            .withSize(3, 3)
            .withProperties(Map.of("SHOW CONTROLS", false));

        // Gyro
        gyro = driveTab.add("Gyro (Y, P, R)", "?, ?, ?")
            .withPosition(0, 1)
            .getEntry();

        // Target distance
        targetDistance = driveTab.add("Target Distance (m)", -1.0)
            .getEntry();

        // Sees Tags?
        seesTags = driveTab.add("Sees tags?", false)
            .getEntry();

        // Pressure
        compPressure = driveTab.add("Pressure (PSI)", 0.0)
            .withPosition(0, 0)
            .getEntry();

        // Speed
        shootSpeed = driveTab.add("Shoot Setpoint %", -1.0)
            .withPosition(0, 2)
            .getEntry();

        // Manual Shoot Speed
        shootManual = driveTab.add("Manual Shoot Speed", 0.0)
            .withPosition(7, 0)
            .getEntry();
    }

    // Periodic
    public void periodic() {
        gyro.setString(String.format("%f, %f, %f", drive.getYaw(), drive.getPitch(), drive.getRoll()));
        targetDistance.setDouble(shooter.limelight.getDistanceToCenter());
        seesTags.setBoolean(shooter.limelight.seesTags());
        compPressure.setDouble(intake.getPressure());
        shootSpeed.setDouble(shooter.getSetpointPercentage() * 100.0);
    }

    // Get value
    public double getManualSpeed() {
        return shootManual.getDouble(0.0);
    }
}
