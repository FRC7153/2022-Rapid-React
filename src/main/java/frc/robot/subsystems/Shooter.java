package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.TalonFX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.SparkMaxPIDController;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants.HardwareConstants;
import frc.robot.Constants.ShooterConstants;

public class Shooter extends SubsystemBase {
    /*
     * The shooter is made of two parts:
     * - The shooter, controlled by two CanSparkMax's that are connected
     * - The indexer, which feeds balls to the shooter, and is controlled by a
     *      single Falcon500 (TalonFX!)
     * 
     * All the ShooterConstants and HardwareConstants are already set up.
     * For this file, you need to:
     * - Create two CanSparkMax's (use HardwareConstants for ID's)
     * - Create one TalonFX
     * - Create a method to set the speed of the CanSparkMax (leave this blank 
     *      for now)
     * - Create a method that turns on/off the TalonFX (use the speed from 
     *      ShooterConstants)
     * 
     * You will need to do configuration for the shooter motor. Remember, they
     * are connected physically, so they need to spin together. See:
     * https://codedocs.revrobotics.com/java/com/revrobotics/cansparkmax#follow(com.revrobotics.CANSparkMax,boolean) 
     * 
     * Don't worry about the PID values yet, we'll go over this later.
     * Feel free to look at the other two subsystems as a reference for setting
     * up motors and methods. Remember what needs to be public or private!
     * 
     * For a labeled diagram of the shooter, see: 
     * https://bit.ly/frc7153_2022livewire
     */
}
