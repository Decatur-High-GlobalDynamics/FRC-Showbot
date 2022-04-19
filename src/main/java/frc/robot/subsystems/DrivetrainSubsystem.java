// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

import frc.robot.ITeamTalon;
import frc.robot.TeamTalonFX;
import frc.robot.Ports;

import java.util.Objects;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonFXConfiguration;



public class DrivetrainSubsystem extends SubsystemBase 
{
  ITeamTalon rightDriveFalconMain;
  ITeamTalon leftDriveFalconMain;
  ITeamTalon rightDriveFalconSub;
  ITeamTalon leftDriveFalconSub;

  static final double maxPowerChange = 0.1;

  public DrivetrainSubsystem() {
    throw new IllegalArgumentException(
        "not allowed! ctor must provide parameters for all dependencies");
  }

  public DrivetrainSubsystem(
      ITeamTalon rightDriveFalconMain,
      ITeamTalon leftDriveFalconMain,
      ITeamTalon rightDriveFalconSub,
      ITeamTalon leftDriveFalconSub) {
    this.rightDriveFalconMain =
        Objects.requireNonNull(rightDriveFalconMain, "rightDriveFalconMain must not be null");
    this.leftDriveFalconMain =
        Objects.requireNonNull(leftDriveFalconMain, "leftDriveFalconMain must not be null");
    this.rightDriveFalconSub =
        Objects.requireNonNull(rightDriveFalconSub, "rightDriveFalconSub must not be null");
    this.leftDriveFalconSub =
        Objects.requireNonNull(leftDriveFalconSub, "leftDriveFalconSub must not be null");
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

  public static DrivetrainSubsystem Create() {
    ITeamTalon rightDriveFalconMainCAN =
        new TeamTalonFX("Subsystems.DriveTrain.RightMain", Ports.RightDriveFalconMainCAN);
    ITeamTalon leftDriveFalconMainCAN =
        new TeamTalonFX("Subsystems.DriveTrain.LeftMain", Ports.LeftDriveFalconMainCAN);
    ITeamTalon rightDriveFalconSubCAN =
        new TeamTalonFX("Subsystems.DriveTrain.RightSub", Ports.RightDriveFalconSubCAN);
    ITeamTalon leftDriveFalconSub =
        new TeamTalonFX("Subsystems.DriveTrain.LeftSub", Ports.LeftDriveFalconSubCAN);
    return new DrivetrainSubsystem(
        rightDriveFalconMainCAN,
        leftDriveFalconMainCAN,
        rightDriveFalconSubCAN,
        leftDriveFalconSub);
  }

  private double getCappedPower(double desired) {
    return Math.max(Math.min(1, desired), -1);
  }

  public void setMotorPowers(double leftPowerDesired, double rightPowerDesired, String reason) 
  {
    leftPowerDesired = getCappedPower(leftPowerDesired);
    rightPowerDesired = getCappedPower(rightPowerDesired);
    double currentRightPower = rightDriveFalconMain.get();
    double currentLeftPower = leftDriveFalconMain.get();
    double newPowerRight;
    double newPowerLeft;

    if (rightPowerDesired > currentRightPower)
    {
      newPowerRight = Math.max(rightPowerDesired, currentRightPower - maxPowerChange);
    } 
    else if (rightPowerDesired > currentRightPower) 
    {
      newPowerRight = Math.min(rightPowerDesired, currentRightPower + maxPowerChange);
    } 
    else 
    {
      newPowerRight = rightPowerDesired;
    }

    if (leftPowerDesired > currentLeftPower)
    {
      newPowerLeft = Math.max(leftPowerDesired, currentLeftPower - maxPowerChange);
    } 
    else if (leftPowerDesired > currentLeftPower) 
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
}
