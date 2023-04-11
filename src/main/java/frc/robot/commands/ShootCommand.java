package frc.robot.commands;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Robot;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends CommandBase {
    
    private JoystickButton secondaryTrigger;
    private ShooterSubsystem shooter;
    
    public ShootCommand(JoystickButton secondTrggr, ShooterSubsystem shtr) {
        secondaryTrigger = secondTrggr;
        shooter = shtr;
    }

    public void execute() {
        if(Robot.isTestMode || secondaryTrigger.getAsBoolean()) shooter.set(1, "Right trigger on secondary joystick said so");
        else shooter.set(0, "not meant to shoot");
        SmartDashboard.putBoolean("Secondary Trigger", secondaryTrigger.getAsBoolean());
    }

    public void end(boolean interrupted) {
        shooter.set(0, "stopped shooting");
    }
}
