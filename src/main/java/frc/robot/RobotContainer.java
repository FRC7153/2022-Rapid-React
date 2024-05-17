// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.Constants.BuildConstants;
import frc.robot.Constants.DriveBaseConstants;
import frc.robot.commands.AutoCenterCommand;
import frc.robot.commands.LLShootCommand;
import frc.robot.commands.ManualShootCommand;
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
  private CommandXboxController driveControl = new CommandXboxController(0);
  private Dashboard dashboard = new Dashboard(drive, intake, shooter, pdh);

  // Constructor
  public RobotContainer() {
    // Set default commands
    drive.initDefaultCommand();
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
      () -> - driveControl.getLeftY(),
      () -> -driveControl.getLeftX(),
      driveControl::getRightX
    ));

    // Aim bindings
    driveControl.leftBumper().whileTrue(new AutoCenterCommand(drive, shooter));

    // Intake Bindings
    driveControl.leftTrigger().whileFalse(new InstantCommand(() -> intake.setIntakeState(false), intake).repeatedly());
    driveControl.leftTrigger().whileTrue(new InstantCommand(() -> intake.setIntakeState(true), intake).repeatedly());

    // Climber Bindings (removed)
    /*driveControl.y().toggleOnTrue(new InstantCommand(() -> climber.setClimberState(true), climber));
    driveControl.y().toggleOnFalse(new InstantCommand(() -> climber.setClimberState(false), climber));*/

    // Shoot Bindings
    driveControl.rightTrigger().whileTrue(
        (BuildConstants.kMANUAL_SHOOTING) ? // Ternary conditional interpreted on initialization
        new ManualShootCommand(drive, shooter, dashboard) : // For regression tuning
        new LLShootCommand(shooter)// For regression shooting 
    );

    driveControl.rightTrigger().onFalse(new ParallelCommandGroup(
      new InstantCommand(() -> shooter.setIndexerState(false)),
      new InstantCommand(() -> shooter.setShootVelocity(0.0), shooter)
    ));

    // Sprint Bindings
    driveControl.leftStick().onTrue(new InstantCommand(() -> drive.setMaxSpeed(DriveBaseConstants.kFAST_MAX_SPEED)));
    driveControl.leftStick().onFalse(new InstantCommand(() -> drive.setMaxSpeed(DriveBaseConstants.kSLOW_MAX_SPEED)));
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

