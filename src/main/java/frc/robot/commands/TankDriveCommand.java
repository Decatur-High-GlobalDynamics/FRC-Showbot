// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import java.util.function.DoubleSupplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants;
import frc.robot.subsystems.DrivetrainSubsystem;

/** An example command that uses an example subsystem. */
public class TankDriveCommand extends CommandBase {
  DrivetrainSubsystem driveTrain;
  DoubleSupplier rightStick;
  DoubleSupplier leftStick;

  public TankDriveCommand(DrivetrainSubsystem driveTrain, DoubleSupplier leftStick, DoubleSupplier rightStick) {
    this.driveTrain = driveTrain;
    this.rightStick = rightStick;
    this.leftStick = leftStick;
    addRequirements(driveTrain);
  }

  double cubePower(double input) {
    return Math.pow(input, 3);
  }

  double deadZone (double input) 
  {
    if (Math.abs(input) <= Constants.DEADZONE_AMOUNT) return 0;
    return input;
  }

  public void execute()
  {
    driveTrain.setMotorPowers(cubePower(deadZone(leftStick.getAsDouble())), cubePower(deadZone(rightStick.getAsDouble())), "Joysticks said so");
  }

}
