package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootModeCommand extends CommandBase {
    
    ShooterSubsystem shooter;
    public double speedMod;

    public ShootModeCommand(double newSpeedMod, ShooterSubsystem shooter) {
        this.shooter = shooter;
        speedMod = newSpeedMod;

        addRequirements(shooter);
    }

    public void initialize() {
        shooter.speedMod = speedMod;
    }

    public boolean isFinished() {
        return true;
    }

}
