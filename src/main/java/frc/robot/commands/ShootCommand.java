package frc.robot.commands;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.subsystems.ShooterSubsystem;

public class ShootCommand extends CommandBase {
    
    private JoystickButton secondaryTrigger;
    private ShooterSubsystem shooter;
    
    public ShootCommand(JoystickButton secondTrggr, ShooterSubsystem shtr) {
        secondaryTrigger = secondTrggr;
        shooter = shtr;
    }

    public void execute() {
        if(secondaryTrigger.get()) shooter.set(1, "Right trigger on secondary joystick said so");
    }

}
