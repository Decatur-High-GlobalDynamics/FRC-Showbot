package frc.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TeamSparkMAX;

public class ShooterSubsystem extends SubsystemBase {

    public TeamSparkMAX leftMotor, rightMotor;

    private static double voltage = 12;

    public ShooterSubsystem() {
        leftMotor = new TeamSparkMAX("Left Shooter Motor", Ports.SHOOTER_MOTOR_LEFT);
        rightMotor = new TeamSparkMAX("Right Shooter Motor", Ports.SHOOTER_MOTOR_RIGHT);

        rightMotor.follow(leftMotor, true);

        leftMotor.enableVoltageCompensation(voltage);
        rightMotor.enableVoltageCompensation(voltage);
    }

    public static ShooterSubsystem Create() {
        return new ShooterSubsystem();
    }

    public void set(float power, String reason) {
        leftMotor.set(power, reason);
    }
    
}
