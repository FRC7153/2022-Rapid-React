package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

public class TeleopDriveCommand extends CommandBase {
    // Subsystem
    private DriveBase base;

    // Suppliers
    private DoubleSupplier xSupply, ySupply, rSupply;

    // Init
    public TeleopDriveCommand(DriveBase drive, DoubleSupplier xSupplier, DoubleSupplier ySupplier, DoubleSupplier rotSupplier) {
        base = drive;

        xSupply = xSupplier;
        ySupply = ySupplier;
        rSupply = rotSupplier;
    }

    // Run
    @Override
    public void execute() {
        base.driveRobotOriented(ySupply.getAsDouble(), xSupply.getAsDouble(), rSupply.getAsDouble());
    }

    // End
    @Override
    public void end(boolean terminated) {
        base.driveRobotOriented(0.0, 0.0, 0.0);
    }

    // Don't Finish
    @Override
    public boolean isFinished() { return false; }
}
