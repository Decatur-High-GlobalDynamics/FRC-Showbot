package frc.robot.commands;

import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Robot;
import frc.robot.TeamTalonFX;
import frc.robot.subsystems.ShooterSubsystem;

public class AgitateCommand extends CommandBase {
    
    private TeamTalonFX agitator;
    
    public AgitateCommand(TeamTalonFX agtr) {
        agitator = agtr;
    }

    public void execute() {
        agitator.set(.75, "A button down.");
        SmartDashboard.putBoolean("A Button", true);
    }

    public void end(boolean interrupted) {
        agitator.set(0, "stopped shooting");
        SmartDashboard.putBoolean("A Button", false);
    }
}
