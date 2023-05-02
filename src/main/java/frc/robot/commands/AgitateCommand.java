package frc.robot.commands;

import java.time.LocalDateTime;
import java.util.function.BooleanSupplier;
import java.util.function.DoubleSupplier;

import edu.wpi.first.util.sendable.Sendable;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotContainer;
import frc.robot.TeamTalonFX;
import frc.robot.subsystems.ShooterSubsystem;

public class AgitateCommand extends CommandBase {
    
    private TeamTalonFX agitator;

    public int agitatorDirection = 1;
    public LocalDateTime lastSwapTime;

    public boolean shouldEnd = false;
    
    public AgitateCommand(TeamTalonFX agtr) {
        agitator = agtr;

        agitator.resetEncoder();
        
        RobotContainer.tab.addDouble("Agitator Direction", ()->agitatorDirection);

        lastSwapTime = LocalDateTime.now();
        RobotContainer.tab.addString("Agitator Swap Time", 
            ()-> lastSwapTime.getHour() + ":" + lastSwapTime.getMinute() + ":" + lastSwapTime.getSecond());
    }

    public void execute() {
        if(LocalDateTime.now().compareTo(lastSwapTime.plusSeconds(Constants.AGITATOR_REVERSE_TIME)) == 1) {
            agitatorDirection *= -1;
            lastSwapTime = LocalDateTime.now();
        }

        agitator.set(Constants.AGITATOR_SPEED * agitatorDirection, "A button down.");
    }

    public boolean isFinished() {
        return shouldEnd;
    }

    public void end(boolean interrupted) {
        agitator.set(0, "stopped shooting");
    }
}
