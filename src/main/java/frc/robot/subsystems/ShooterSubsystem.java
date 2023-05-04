package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.PidParameters;
import frc.robot.Ports;
import frc.robot.Robot;
import frc.robot.TeamSparkMAX;

public class ShooterSubsystem extends SubsystemBase {

    public TeamSparkMAX leftMotor, rightMotor;

    private static double voltage = 12;

    public DoubleSupplier speedMod = () -> 0.5;

    private PidParameters pidParameters;
    private static double kP = .0005,
        kI = 0,
        kD = 0,
        kF = .00018,
        kIZone = 0,
        kPeakOutput = 1,
        maxVel = 20000,
        maxAcc = 1500;
    private static int errorTolerance = 10;

    private static int maximumRPM = 5600;

    public ShooterSubsystem() {
        leftMotor = new TeamSparkMAX("Left Shooter Motor", Ports.SHOOTER_MOTOR_LEFT);
        rightMotor = new TeamSparkMAX("Right Shooter Motor", Ports.SHOOTER_MOTOR_RIGHT);

        rightMotor.follow(leftMotor, true);
        leftMotor.setInverted(false); //Right is disconnected

        leftMotor.enableVoltageCompensation(voltage);
        rightMotor.enableVoltageCompensation(voltage);

        pidParameters = new PidParameters(kP, kI, kD, kF, kIZone, kPeakOutput, maxVel, maxAcc, errorTolerance);
    }

    public static ShooterSubsystem Create() {
        return new ShooterSubsystem();
    }

    public void set(float power, String reason) {
        //leftMotor.setSmartMotionVelocity(powerToRPM(power), reason);
        leftMotor.set(power * speedMod.getAsDouble(), reason);
        //leftMotor.configureWithPidParameters(pidParameters, 0);
        //rightMotor.configureWithPidParameters(pidParameters, 0);
    }

    public double powerToRPM(double power) {
        return Math.max(-1, Math.min(1, power)) * maximumRPM;
    }

    public void periodic() {
        leftMotor.periodic();
        rightMotor.periodic();
        pidParameters.periodic("Subsystems.Shooter.Left", leftMotor, 0);
        pidParameters.periodic("Subsystems.Shooter.Right", rightMotor, 0);
    }
    
}
