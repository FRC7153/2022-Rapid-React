package frc.robot.commands;

import java.util.function.Supplier;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.DriveBase;

public class TeleopDriveCommand extends Command {
    private final DriveBase drive;
    private final Supplier<Double> ySupplier;
    private final Supplier<Double> xSupplier;
    private final Supplier<Double> rotSupplier;

    /**
     * Instantiates and configs a new Teleop Drive command, for driving the robot in teleop mode.
     * This drives open-loop. Field or robot oriented is determined by the dashboard.
     * @param drive The drive subsystem.
     * @param ySupplier Forward/backward supplier.
     * @param xSupplier Left/right supplier.
     * @param rotSupplier Theta supplier;
     */
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
        drive.driveOpenLoop(0.0, 0.0, 0.0, false);
    }

    @Override
    public void execute() {
        drive.driveOpenLoop(
            ySupplier.get(),
            xSupplier.get(),
            rotSupplier.get(),
            drive.getFieldOrientedInput()
        );
    }

    @Override
    public void end(boolean terminated) {
        initialize();
    }
}
