// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import java.util.function.DoubleSupplier;

import edu.wpi.first.wpilibj2.command.CommandBase;

import frc.robot.subsystems.DrivetrainSubsystem;

/** An example command that uses an example subsystem. */
public class TankDriveCommand extends CommandBase {
  DrivetrainSubsystem driveTrain;
  DoubleSupplier rightStick;
  DoubleSupplier leftStick;

  final double deadZoneAmount = 0.05;
  final double SPEED_DIVISOR = 4;

  public TankDriveCommand(DrivetrainSubsystem driveTrain, DoubleSupplier leftStick, DoubleSupplier rightStick) {
    this.driveTrain = driveTrain;
    this.rightStick = rightStick;
    this.leftStick = leftStick;
    addRequirements(driveTrain);
  }

  public void execute()
  {
    driveTrain.setMotorPowers(deadZone(leftStick.getAsDouble())/SPEED_DIVISOR, deadZone(rightStick.getAsDouble())/SPEED_DIVISOR, "Joysticks said to");
  }

  double deadZone (double input) 
  {
    if (Math.abs(input) <= deadZoneAmount) return 0;
    return input;
  }
}
