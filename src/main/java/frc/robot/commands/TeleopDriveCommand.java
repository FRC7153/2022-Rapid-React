package frc.robot.commands;

import java.util.function.Supplier;

<<<<<<< HEAD
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveBase;

public class TeleopDriveCommand extends Command {
=======
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DriveBase;

public class TeleopDriveCommand extends CommandBase {
>>>>>>> 5f3592d8bba8d90e05278e4f71ab2cc2e31acb2c
    private DriveBase drive;
    private Supplier<Double> ySupplier;
    private Supplier<Double> xSupplier;
    private Supplier<Double> rotSupplier;

    public TeleopDriveCommand(
        DriveBase drive, 
        Supplier<Double> ySupplier, 
        Supplier<Double> xSupplier,
        Supplier<Double> rotSupplier
    ) {
        this.drive = drive;
        this.ySupplier = ySupplier;
        this.xSupplier = xSupplier;
        this.rotSupplier = rotSupplier;

        addRequirements(drive);
    }

    @Override
    public void initialize() {
        drive.drive(0.0, 0.0, 0.0);
    }

    @Override
    public void execute() {
        drive.drive(
            ySupplier.get(),
            xSupplier.get(),
            rotSupplier.get()
        );
    }

    @Override
    public void end(boolean terminated) {
        drive.drive(0.0, 0.0, 0.0);
    }
}
