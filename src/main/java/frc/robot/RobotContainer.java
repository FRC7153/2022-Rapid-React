// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.subsystems.Climber;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
  // Subsystems
  private DriveBase drive = new DriveBase();
  private Intake intake = new Intake();
  private Shooter shooter = new Shooter();
  private Climber climber = new Climber();

  // Controllers
  private XboxController driveControl = new XboxController(0);

  // Buttons
  private Trigger intakeBttn = new Trigger(() -> { return driveControl.getLeftTriggerAxis() >= 0.5; });

  public RobotContainer() {
    configureBindings();
  }

  private void configureBindings() {
    // TODO may cause teleop/auto problems

    // Drive Bindings
    drive.setDefaultCommand(new TeleopDriveCommand(
      drive,
      driveControl::getLeftY,
      driveControl::getLeftX,
      driveControl::getRightX
    ));

    // Intake Bindings
    intakeBttn.onFalse(new InstantCommand(intake::intakeUp, intake));
    intakeBttn.onTrue(new InstantCommand(intake::intakeUp, intake));

    // Shoot Bindings
    
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}

