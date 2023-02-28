package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.DrivetrainSubsystem;

public class SpeedMode extends CommandBase {
    DrivetrainSubsystem drivetrain;
    Double speedMod;

    public SpeedMode(DrivetrainSubsystem drivetrain) {

    }

    public void initialize() {
        speedMod = (double) 2;
        drivetrain.setSpeedMod(speedMod);
    }

    public void end() {
        speedMod = (double) 1;
        drivetrain.setSpeedMod(speedMod);
    }
}
