package frc.robot.subsystems;
import java.util.Map;
import java.util.function.DoubleSupplier;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Ports;
import frc.robot.TeamSparkMAX;

public class ShooterSubsystem extends SubsystemBase {

    public TeamSparkMAX leftMotor, rightMotor;

    private static double voltage = 12;

    public static ShuffleboardTab tab = Shuffleboard.getTab("Main");

    private GenericEntry speedEntry;

    public ShooterSubsystem() {
        leftMotor = new TeamSparkMAX("Left Shooter Motor", Ports.SHOOTER_MOTOR_LEFT);
        rightMotor = new TeamSparkMAX("Right Shooter Motor", Ports.SHOOTER_MOTOR_RIGHT);

        rightMotor.follow(leftMotor, true);
        leftMotor.setInverted(false); 

        leftMotor.enableVoltageCompensation(voltage);
        rightMotor.enableVoltageCompensation(voltage);

        speedEntry = tab.add("Shooter Speed", 1)
            .withWidget(BuiltInWidgets.kNumberSlider)
            .withProperties(Map.of("min", 0, "max", 1))
            .getEntry();
    }

    public void setMotorPower(float power, String reason) {
        leftMotor.set(power * speedEntry.getDouble(1), reason);
    }
    
}
