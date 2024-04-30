package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;
import frc.robot.Ports;
import frc.robot.TeamTalonFX;

public class SpindexerSubsystem extends SubsystemBase {
    
    public TeamTalonFX agitator;

    public SpindexerSubsystem() {
        agitator = new TeamTalonFX("Agitator", Ports.AGITATOR);
    }

    public void setMotorPower(double power) {
        agitator.set(power * Constants.SPINDEXER_SPEED);
    }
    
}
