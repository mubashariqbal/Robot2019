package frc.robot.auto.functions;

import frc.robot.auto.setup.RobotFunction;
import frc.robot.subsystems.Intake;

/**
 * Outtakes the cargo from the intake
 */
public class CollectCargo extends RobotFunction {

    /**
     * Initializes all needed variables
     */
    public CollectCargo() {
    }

    @Override
    public void init() {
    }

    /**
     * Sets the cargo collector to the desired set speed
     */
    @Override
    public void run() {
        Intake.collectCargo();
    }

    @Override
    public void stop() {
        Intake.stopCargoRollers();
    }

    /**
     * @return is the collector set?
     */
    @Override
    public boolean isFinished() {
        return Intake.isCargoPresent();
    }
}