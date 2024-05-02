package frc.robot.commands;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.robot.subsystems.SpindexerSubsystem;

public class SpindexerCommand extends InstantCommand
{

    SpindexerSubsystem spindexer;
    double power;
    
    public SpindexerCommand(SpindexerSubsystem spindexer, double power) {
        this.spindexer = spindexer;
        this.power = power;

        addRequirements(spindexer);
    }

    @Override
    public void initialize() {
        spindexer.setMotorPower(power);
    }

}
