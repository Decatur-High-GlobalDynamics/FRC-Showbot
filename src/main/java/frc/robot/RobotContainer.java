// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.util.Map;
import java.util.function.BooleanSupplier;

import edu.wpi.first.networktables.GenericEntry;
import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import frc.robot.commands.AgitateCommand;
import frc.robot.commands.ExampleCommand;
import frc.robot.commands.ReverseAgitateCommand;
import frc.robot.commands.ShootCommand;
import frc.robot.commands.ShootModeCommand;
import frc.robot.commands.TankDriveCommand;
import frc.robot.subsystems.DrivetrainSubsystem;
import frc.robot.subsystems.ExampleSubsystem;
import frc.robot.subsystems.ShooterSubsystem;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;

/**
 * This class is where the bulk of the robot should be declared. Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls). Instead, the structure of the robot (including
 * subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer {

  public static ShuffleboardTab tab = Shuffleboard.getTab("Main");

  // The robot's subsystems and commands are defined here...
  private final ExampleSubsystem m_exampleSubsystem = new ExampleSubsystem();

  private final ExampleCommand m_autoCommand = new ExampleCommand(m_exampleSubsystem);

  public static ShooterSubsystem shooter;

  public static DrivetrainSubsystem driveTrain;

  public static TeamTalonFX agitator;

  public static Joystick primaryJoystick, secondaryJoystick;
  public static JoystickButton primaryTrigger, triggerButton, primaryTriggerTwo;
  public static JoystickButton aButton;
  public static JoystickButton bButton;

  public static GenericEntry fastSpeedEntry, slowSpeedEntry, driveSpeedEntry;

  public static AgitateCommand agitateCommand;

  /** The container for the robot. Contains subsystems, OI devices, and commands. */
  public RobotContainer() {
    shooter = new ShooterSubsystem();
    driveTrain = new DrivetrainSubsystem();
    
    agitator = new TeamTalonFX("agitator", Ports.AGITATOR);
    agitateCommand = new AgitateCommand(RobotContainer.agitator);

    // Configure the button bindings
    configureButtonBindings();

    tab.addBoolean("Safe Mode", ()->!Robot.isTestMode);
    tab.addBoolean("Primary Trigger", ()->primaryTrigger.getAsBoolean());
    tab.addBoolean("Secondary Button", ()->triggerButton.getAsBoolean());
    tab.addDouble("Shooter Speed Modifier", ()->(shooter.speedMod.getAsDouble()));
    tab.addBoolean("Shooting", () -> primaryTrigger.getAsBoolean() && (Robot.isTestMode || triggerButton.getAsBoolean()));
    tab.addDouble("Agitating", () -> aButton.getAsBoolean() ? 1 : bButton.getAsBoolean() ? -1 : 0 );

    fastSpeedEntry = tab.add("Fast Speed", Constants.fastSpeed)
      .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -1, "max", 1))
      .getEntry();
    slowSpeedEntry = tab.add("Slow Speed", Constants.slowSpeed)
      .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", -1, "max", 1))
      .getEntry();
    driveSpeedEntry = tab.add("Drive Speed", Constants.driveSpeed)
      .withWidget(BuiltInWidgets.kNumberSlider).withProperties(Map.of("min", 0, "max", 1))
      .getEntry();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be created by
   * instantiating a {@link GenericHID} or one of its subclasses ({@link
   * edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then passing it to a {@link
   * edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */
  private void configureButtonBindings() {
    primaryJoystick = new Joystick(0);
    secondaryJoystick = new Joystick(1);

    primaryTrigger = new JoystickButton(primaryJoystick, LogitechControllerButtons.triggerRight);
    primaryTriggerTwo = new JoystickButton(primaryJoystick, LogitechControllerButtons.triggerLeft);
    triggerButton = new JoystickButton(secondaryJoystick, LogitechControllerButtons.x);
    aButton = new JoystickButton(primaryJoystick, LogitechControllerButtons.a);
    bButton = new JoystickButton(primaryJoystick, LogitechControllerButtons.b);

    primaryTrigger.whileTrue(new ShootCommand(triggerButton, shooter));

    primaryTriggerTwo.onTrue(new ShootModeCommand(()->fastSpeedEntry.getDouble(Constants.fastSpeed), shooter))
      .onFalse(new ShootModeCommand(()->slowSpeedEntry.getDouble(Constants.slowSpeed), shooter));

    driveTrain.setDefaultCommand(new TankDriveCommand(driveTrain, (() -> primaryJoystick.getY()), () -> primaryJoystick.getThrottle()));

    primaryTrigger.whileTrue(agitateCommand);
    // bButton.whileTrue(new ReverseAgitateCommand(agitator));
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    // An ExampleCommand will run in autonomous
    return m_autoCommand;
  }
}
