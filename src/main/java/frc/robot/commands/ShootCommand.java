package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Shooter;

public class ShootCommand extends SequentialCommandGroup {
    public ShootCommand(Shooter shooter) {
        // TODO, shoot subsys will need supplier for speed
        addCommands(
            shooter::setShootSpeed()
        );

        addRequirements(shooter);
    }
}
