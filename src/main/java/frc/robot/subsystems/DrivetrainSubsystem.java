// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.ITeamTalon;
import frc.robot.TeamTalonFX;
import frc.robot.Ports;
import frc.robot.Robot;

import java.util.Objects;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;



public class DrivetrainSubsystem extends SubsystemBase 
{
  ITeamTalon rightDriveFalconMain;
  ITeamTalon leftDriveFalconMain;
  ITeamTalon rightDriveFalconSub;
  ITeamTalon leftDriveFalconSub;

  static final double maxPowerChange = 0.01;

  public DrivetrainSubsystem() {
    rightDriveFalconMain =
        new TeamTalonFX("Subsystems.DriveTrain.RightMain", Ports.RightDriveFalconMainCAN);
    leftDriveFalconMain =
        new TeamTalonFX("Subsystems.DriveTrain.LeftMain", Ports.LeftDriveFalconMainCAN);
    rightDriveFalconSub =
        new TeamTalonFX("Subsystems.DriveTrain.RightSub", Ports.RightDriveFalconSubCAN);
    leftDriveFalconSub =
        new TeamTalonFX("Subsystems.DriveTrain.LeftSub", Ports.LeftDriveFalconSubCAN);
    setupDrivetrain();
  }

  private void setupDrivetrain() {
    // This configures the falcons to use their internal encoders
    TalonFXConfiguration configs = new TalonFXConfiguration();
    rightDriveFalconMain.configBaseAllSettings(configs);
    leftDriveFalconMain.configBaseAllSettings(configs);

    leftDriveFalconSub.follow(leftDriveFalconMain);
    rightDriveFalconSub.follow(rightDriveFalconMain);

    rightDriveFalconMain.setInverted(false);
    rightDriveFalconSub.setInverted(false);
    
    leftDriveFalconMain.setInverted(true);
    leftDriveFalconSub.setInverted(true);

    leftDriveFalconMain.setNeutralMode(NeutralMode.Brake);
    rightDriveFalconMain.setNeutralMode(NeutralMode.Brake);
  }

  private double getCappedPower(double desired) {
    return Math.max(Math.min(1, desired), -1);
  }

  public void setMotorPowers(double leftPowerDesired, double rightPowerDesired, String reason) 
  {
    if (Robot.isTestMode)
    {
      leftPowerDesired = getCappedPower(leftPowerDesired);
      rightPowerDesired = getCappedPower(rightPowerDesired);
      double currentRightPower = rightDriveFalconMain.get();
      double currentLeftPower = leftDriveFalconMain.get();
      double newPowerRight;
      double newPowerLeft;

      if ((rightPowerDesired < currentRightPower) && (currentRightPower < 0))
      {
        newPowerRight = Math.max(rightPowerDesired, currentRightPower - maxPowerChange);
      } 
      else if ((rightPowerDesired > currentRightPower) && (currentRightPower > 0))
      {
        newPowerRight = Math.min(rightPowerDesired, currentRightPower + maxPowerChange);
      } 
      else 
      {
        newPowerRight = rightPowerDesired;
      }

      if ((leftPowerDesired < currentLeftPower) && (currentRightPower < 0))
      {
        newPowerLeft = Math.max(leftPowerDesired, currentLeftPower - maxPowerChange);
      } 
      else if ((leftPowerDesired > currentLeftPower) && (currentRightPower > 0))
      {
        newPowerLeft = Math.min(leftPowerDesired, currentLeftPower + maxPowerChange);
      } 
      else 
      {
        newPowerLeft = leftPowerDesired;
      }

      rightDriveFalconMain.set(newPowerRight, reason);
      leftDriveFalconMain.set(newPowerLeft, reason);
    }
    else if (!Robot.isTestMode)
    {
      rightDriveFalconMain.set(0, "Not in test mode!");
      leftDriveFalconMain.set(0, "Not in test mode!");
    }
  }
}
