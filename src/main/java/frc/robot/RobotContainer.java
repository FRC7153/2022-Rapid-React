// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.DriveBaseConstants;
import frc.robot.commands.AutoCenterCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;
import frc.robot.utility.Dashboard;
import frc.robot.utility.PDH;

public class RobotContainer {
  // Subsystems
  private DriveBase drive = new DriveBase();
  private Intake intake = new Intake();
  private Shooter shooter = new Shooter();
  //private Climber climber = new Climber();

  // Misc hardware
  private PDH pdh = new PDH();

  // Controllers and dashboard
  private CommandXboxController driveController = new CommandXboxController(0);
  private Dashboard dashboard = new Dashboard(drive, intake, shooter, pdh);

  // Constructor
  public RobotContainer() {
    // Set default commands
    intake.initDefaultCommand();
    shooter.initDefaultCommand();

    // Set controls
    configureBindings();
  }

  // Create joystick bindings
  private void configureBindings() {
    // Drive Bindings
    drive.setDefaultCommand(new TeleopDriveCommand(
      drive,
      dashboard,
      () -> - driveController.getLeftY(),
      () -> -driveController.getLeftX(),
      () -> driveController.getRightX()
    ));

    // Aim bindings
    driveController.leftBumper().whileTrue(new AutoCenterCommand(drive, shooter));

    // Intake Bindings
    driveController.leftTrigger().whileFalse(new InstantCommand(() -> intake.setIntakeState(false), intake).repeatedly());
    driveController.leftTrigger().whileTrue(new InstantCommand(() -> intake.setIntakeState(true), intake).repeatedly());

    // Climber Bindings (removed)
    /*driveController.y().toggleOnTrue(new InstantCommand(() -> climber.setClimberState(true), climber));
    driveController.y().toggleOnFalse(new InstantCommand(() -> climber.setClimberState(false), climber));*/

    // Shoot Bindings
    driveController.rightTrigger().whileTrue(new ShootCommand(shooter, dashboard));

    // Sprint Bindings
    driveController.leftStick().onTrue(new InstantCommand(() -> drive.setMaxSpeed(DriveBaseConstants.kFAST_MAX_SPEED)));
    driveController.leftStick().onFalse(new InstantCommand(() -> drive.setMaxSpeed(DriveBaseConstants.kSLOW_MAX_SPEED)));

    // Limelight snapshot bindings
    driveController.a()
      .onTrue(new InstantCommand(shooter.limelight::takeSnapshot));
  }

  // Auto command
  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }

  /**
   * Refreshes the dashboard's values. Call this periodically.
   */
  public void refreshDashboard() {
    dashboard.periodic();
  }
}

