// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import edu.wpi.first.wpilibj2.command.button.Trigger;
import frc.robot.Constants.DriveBaseConstants;
import frc.robot.commands.ShootCommand;
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
  private Trigger shootBttn = new Trigger(() -> { return driveControl.getRightTriggerAxis() >= 0.5; });
  private JoystickButton climbBttn = new JoystickButton(driveControl, XboxController.Button.kY.value);
  private JoystickButton sprintBttn = new JoystickButton(driveControl, XboxController.Button.kLeftStick.value);

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

    // Climber Bindings
    climbBttn.toggleOnTrue(new InstantCommand(() -> climber.setClimberState(true), climber));
    climbBttn.toggleOnFalse(new InstantCommand(() -> climber.setClimberState(false), climber));

    // Shoot Bindings
    shootBttn.onTrue(new ShootCommand(shooter));
    shootBttn.onFalse(new ParallelCommandGroup(
      new InstantCommand(shooter::indexerOff),
      new InstantCommand(() -> shooter.setShootSpeed(0.0), shooter)
    ));

    // Sprint Bindings
    sprintBttn.onTrue(new InstantCommand(() -> drive.setMaxSpeed(DriveBaseConstants.FAST_MAX_SPEED)));
    sprintBttn.onFalse(new InstantCommand(() -> drive.setMaxSpeed(DriveBaseConstants.SLOW_MAX_SPEED)));
  }

  public Command getAutonomousCommand() {
    return Commands.print("No autonomous command configured");
  }
}

