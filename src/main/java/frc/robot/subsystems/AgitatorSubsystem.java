package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TeamTalonFX;

public class AgitatorSubsystem extends SubsystemBase {
    
    public TeamTalonFX agitator;

    public AgitatorSubsystem() {
        agitator = new TeamTalonFX("Agitator", Ports.AGITATOR);
    }

    public void setMotorPower(double power, String reason) {
        agitator.set(power, reason);
    }
    
}
