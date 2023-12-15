// Class Package Name
package frc.robot;

// Importing Vendor Deps
import com.ctre.phoenix.motorcontrol.VictorSPXControlMode; // Required for configuring the Victor SPX Motor Controller modes which are used in the tank drive system.
import com.ctre.phoenix.motorcontrol.can.VictorSPX; // Required for using the Victor SPX Motor Controller in CAN mode.
import com.revrobotics.CANSparkMax; // Required for using the REV SparkMAX Motor Controller which controls the elevator and arm (if there was one attached).
import com.revrobotics.CANSparkMaxLowLevel.MotorType; // Required for setting the REV SparkMAX Motor Controller motor type between brushed and brushless.

// Importing Required Built In Libraries
import edu.wpi.first.cameraserver.CameraServer; // Used to initiate a link between the camera on the robot and the computer over wifi or ethernet.
import edu.wpi.first.wpilibj.GenericHID; // Used for adding Genaric HID devices like misc USB Devices on the robot and computer.
import edu.wpi.first.wpilibj.Joystick; // Used for adding any joysticks compatable with the FRC Software like the Logitech Xtreme 3D Pro. Also reqires GenericHID Library.
import edu.wpi.first.wpilibj.PS4Controller; // Used for adding any PS4 stle controller compatable with the FRC Software like the Logitech C310 or a PS4/5 Controller. Also reqires GenericHID Library. There is also a similar lib for XBOX controllers.
import edu.wpi.first.wpilibj.Servo; // Used for interfacing PWM Servos with the RoboRIO.
import edu.wpi.first.wpilibj.TimedRobot; // This is one of the most important libraries this is the libray that creates the class that starts/stops all the code written to the robot when it is enabled/disabled.
import edu.wpi.first.wpilibj.Timer; // This library is reqired during competions to control if the robot is enabled or disabled and the begining or end of a timer.
import edu.wpi.first.wpilibj2.command.button.POVButton; // This is an addon library for the Joystick library that allow the use of the pov hat on the top of the joystick.

public class Robot extends TimedRobot {

  // Assinging Controllers


  Joystick driverController = new Joystick(0); // Assigns the joystick to port 0 on the computer.
  PS4Controller driverController2 = new PS4Controller(1); // Assigns the PS4 style controller to port 1
  GenericHID functionController = new GenericHID(3); // Assigns Generic HID to port 3

  // The computer will find the usb device and assing the port automaticly.

  double autoStart = 0; // Autonomous start delay

  // Assing POV button directions 

  POVButton povUp = new POVButton(driverController2, 0);
  POVButton povDown = new POVButton(driverController2, 90);
  POVButton povLeft = new POVButton(driverController2, 180);
  POVButton povRight = new POVButton(driverController2, 270);

  // Assing Motors and Servos

  VictorSPX driveRightB = new VictorSPX(1); // Assigns Motor Controller to CAN ID 1 VSPX
  VictorSPX driveRightA = new VictorSPX(2); // Assigns Motor Controller to CAN ID 2 VSPX
  VictorSPX driveLeftB = new VictorSPX(3); // Assigns Motor Controller to CAN ID 3 VSPX
  VictorSPX driveLeftA = new VictorSPX(4); // Assigns Motor Controller to CAN ID 4 VSPX

  CANSparkMax slideA = new CANSparkMax(5, MotorType.kBrushless); // Assigns Motor Controller to CAN ID 5 SPMAX
  CANSparkMax slideB = new CANSparkMax(6, MotorType.kBrushless); // Assigns Motor Controller to CAN ID 6 SPMAX

  CANSparkMax elevatorA = new CANSparkMax(7, MotorType.kBrushless); // Assigns Motor Controller to CAN ID 7 SPMAX
  CANSparkMax elevatorB = new CANSparkMax(8, MotorType.kBrushless); // Assigns Motor Controller to CAN ID 8 SPMAX

  CANSparkMax clawMotor = new CANSparkMax(9, MotorType.kBrushless); // Assigns Motor Controller to CAN ID 9 SPMAX

  Servo boxServo = new Servo(0); // Assigns Server 1 to PWM Channel 0

  // Initalization Fuctions

  // Runs when Robot is turned on
  @Override
  public void robotInit() {

    CameraServer.startAutomaticCapture(); // Starts Camera Feed

  }

  // Runs when autonomous is enabled
  @Override
  public void autonomousInit() {

    autoStart = Timer.getFPGATimestamp(); // Autonomous timing for during competion.

  }

  @Override
  public void autonomousPeriodic() {
     
    double autoTimeElapsed = Timer.getFPGATimestamp() - autoStart; // Delays autonomous start

    // Autonomous Starting Code

      Double autoCode = 1.0; // More autonomous timing stuff
 
    if(autoCode == 1) {
      if(autoTimeElapsed<1.0) { // Checks if still within autonomous time and set run time to 1 second.

        // If autonomous true.
        driveLeftA.set(VictorSPXControlMode.PercentOutput, 0.5); // Set driveLeftA to 0.5/50% power
        driveLeftB.set(VictorSPXControlMode.PercentOutput, 0.5); // Set driveLeftB to 0.5/50% power
        driveRightA.set(VictorSPXControlMode.PercentOutput, -0.5); // Set driveRightA to 0.5/50% power
        driveRightB.set(VictorSPXControlMode.PercentOutput, -0.5); // Set driveRightB to 0.5/50% power
        // This causes the robot to drive forward for 1 second moving very rougly 1-2 feet.

      } else {

        // If autonomous false.
        driveLeftA.set(VictorSPXControlMode.PercentOutput, 0); // Set driveLeftA to 0/0% power
        driveLeftB.set(VictorSPXControlMode.PercentOutput, 0); // Set driveLeftB to 0/0% power
        driveRightA.set(VictorSPXControlMode.PercentOutput, 0); // Set driveRightA to 0/0% power
        driveRightB.set(VictorSPXControlMode.PercentOutput, 0); // Set driveRightB to 0/0% power
        // This just disables the motors when auton ends.
      }
    }
  }

  // Runs when teleop is enabled
  @Override
  public void teleopInit() { // Can run code on teleop started.
  }

  @Override
  public void teleopPeriodic() {

    // Driver Controller Assignments //

    // Sets up PS4 Controller Bindings
    boolean crossButton = driverController2.getCrossButton();
    boolean squareButton = driverController2.getSquareButton();
    //boolean circleButton = driverController2.getCircleButton(); // UNUSED
    boolean triangleButton = driverController2.getTriangleButton();

    // Naming Axis on Joystick
    double slowturn = driverController.getRawAxis(2);
    double forward = driverController.getRawAxis(1);
    double turn = -driverController.getRawAxis(0);

    // Setting Drive Motors Up

    driveLeftA.set(VictorSPXControlMode.PercentOutput, -(forward+(turn*0.65+(slowturn*-0.55))));
    driveLeftB.set(VictorSPXControlMode.PercentOutput, -(forward+(turn*0.65+(slowturn*-0.55))));
    driveRightA.set(VictorSPXControlMode.PercentOutput, forward-(turn*0.65-(slowturn*0.55)));
    driveRightB.set(VictorSPXControlMode.PercentOutput, forward-(turn*0.65-(slowturn*0.55)));

    // Setting Elevator Motors Up

    if (povUp.getAsBoolean()) { // On POV Pressed Move Elevator Up.
      elevatorA.set(0.2);
      elevatorB.set(-0.2);
    } else if (povDown.getAsBoolean()) { // On POV Pressed Move Elevator Down.
      elevatorA.set(-0.1);
      elevatorB.set(0.1);
    } else { // Disable motors if no POV pressed.
      elevatorA.set(0);
      elevatorB.set(0);
    }

    // Setting Slide Motors Up
  
   if (triangleButton) { // On Triangle Pressed Move Slide Out
      slideA.set(0.2);
      slideB.set(-0.2);
    } else if (squareButton) { // On Square Pressed Move Slide In
      slideA.set(-0.2);
      slideB.set(0.2);
    } else { // Disable motors if no button pressed
      slideA.set(0);
      slideB.set(0);
    }

    // Setting Box Servos Up

    if (crossButton) { // On Cross Held Open Box Flap
      boxServo.set(1);
    } else { // If no button pressed close flap
      boxServo.set(0.5);
    }
    
  }

  // Runs on robot disabled
  @Override
  public void disabledInit() {
    driveLeftA.set(VictorSPXControlMode.Velocity, 0);
    driveLeftB.set(VictorSPXControlMode.Velocity, 0);
    driveRightA.set(VictorSPXControlMode.Velocity, 0);
    driveRightB.set(VictorSPXControlMode.Velocity, 0);

    elevatorA.disable();
    elevatorB.disable();

    slideA.disable();
    slideB.disable();

    boxServo.setDisabled();
  }
  
}
