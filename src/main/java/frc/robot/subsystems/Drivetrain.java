package frc.robot.subsystems;

import com.ctre.phoenix.motion.MotionProfileStatus;
import com.ctre.phoenix.motion.SetValueMotionProfile;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Drivetrain {
    public final static TalonSRX frontRight, frontLeft;
    private final static VictorSPX backRight, backLeft;

    /**
     * Initializes and sets up all motors for the drivetrain
     */
    static {
        frontRight = new TalonSRX(1);
        frontLeft = new TalonSRX(2);

        backRight = new VictorSPX(3);
        backLeft = new VictorSPX(4);

        backRight.follow(frontRight);
        backLeft.follow(frontLeft);

        frontRight.setInverted(false);
        frontLeft.setInverted(false);

        backRight.setInverted(false);
        backLeft.setInverted(false);

        frontLeft.setSelectedSensorPosition(0);
        frontRight.setSelectedSensorPosition(0);

        frontLeft.setSensorPhase(true);
        frontRight.setSensorPhase(false);
    }

    /**
     * makes the drive train move in arcade drive
     */
    public static void drive(double speed) {
        drive(speed, 0.0, 0.0);
    }

    /**
     * makes the drive train move in arcade drive
     *
     * @param speed:     straight axis value
     * @param rightTurn: right axis value
     * @param leftTurn:  left axis value
     */
    public static void drive(double speed, double rightTurn, double leftTurn) {
        frontRight.set(ControlMode.PercentOutput, -(speed - rightTurn + leftTurn));
        frontLeft.set(ControlMode.PercentOutput, -speed + leftTurn - rightTurn);
    }

    /**
     * if {@code enabled} is true,
     * makes the drive train follow whatever motion profile it has stored in it
     * otherwise, the drivetrain keeps targeting whatever the its last point was
     */
    public static void motionProfile(boolean enabled) {
        if (enabled) {
            frontLeft.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
            frontRight.set(ControlMode.MotionProfile, SetValueMotionProfile.Enable.value);
        } else {
            frontLeft.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
            frontRight.set(ControlMode.MotionProfile, SetValueMotionProfile.Hold.value);
        }
    }

    /**
     * 2 in brake for normal, all for climbing
     *
     * @param isBraking if true all are in brake mode
     */
    public static void setBrakeMode(boolean isBraking) {
        if (isBraking) {
            Climber.stepNumL3 = 0;
            Climber.stepNumL2 = 0;
            frontLeft.setNeutralMode(NeutralMode.Brake);
            frontRight.setNeutralMode(NeutralMode.Brake);
            backLeft.setNeutralMode(NeutralMode.Brake);
            backRight.setNeutralMode(NeutralMode.Brake);
        } else {
            frontLeft.setNeutralMode(NeutralMode.Coast);
            frontRight.setNeutralMode(NeutralMode.Coast);
            backLeft.setNeutralMode(NeutralMode.Brake);
            backRight.setNeutralMode(NeutralMode.Brake);
        }
    }

    public static double getEncoderPosition() {
        return (frontLeft.getSelectedSensorPosition() + frontRight.getSelectedSensorPosition()) / 2.0;
    }

    public static double getEncoderVelocity() {
        return (frontLeft.getSelectedSensorVelocity() + frontRight.getSelectedSensorVelocity()) / 2.0;
    }

    public static MotionProfileStatus getLeftProfileStatus() {
        MotionProfileStatus status = new MotionProfileStatus();
        Drivetrain.frontLeft.getMotionProfileStatus(status);
        return status;
    }

    public static MotionProfileStatus getRightProfileStatus() {
        MotionProfileStatus status = new MotionProfileStatus();
        Drivetrain.frontRight.getMotionProfileStatus(status);
        return status;
    }

    public static void resetEncoders() {
        frontLeft.setSelectedSensorPosition(0);
        frontRight.setSelectedSensorPosition(0);
    }
}