// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import frc.robot.commands.TeleopDriveCommand;
import frc.robot.subsystems.DriveBase;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Shooter;

public class RobotContainer {
  // Subsystems
  private DriveBase drive = new DriveBase();
  private Intake intake = new Intake();
  private Shooter shooter = new Shooter();

  // Controller
  private XboxController driveController = new XboxController(0);

  // Init
  public RobotContainer() {
    configureBindings();
  }

  // Configure Controls
  private void configureBindings() {
    // Base Drive
    drive.setDefaultCommand(
      new TeleopDriveCommand(
        drive, 
        driveController::getLeftX, 
        driveController::getLeftY, 
        driveController::getRightY
      )
    );
  }
}
