package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.arcrobotics.ftclib.controller.PIDFController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Objects;


@TeleOp(name="ABDULSTOPDOINGTHAT")


public class ABDULSTOPDOINGTHAT extends LinearOpMode {

    private PIDController controller;
    public static double p = 0.004, i = 0, d = 0.0004;
    public static double f=0.069;
    public static double target = 0;

    private final double ticksInDegrees = 1425.1/180;

    private DcMotorEx arm;

    @Override
    public void runOpMode() throws InterruptedException {
        controller = new PIDController(p,i,d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        arm = hardwareMap.get(DcMotorEx.class,"arm");
        waitForStart();
        while (opModeIsActive()) {
            controller.setPID(p,i,d);
            int armPos = arm.getCurrentPosition();
            double pid = controller.calculate(armPos,target);
            double ff = Math.cos(Math.toRadians(target/ticksInDegrees)) * f;

            double power = pid + ff;


            if(gamepad1.a){
                target = 600;
            }
            else{
                target = 1500;
            }
            arm.setPower(power);
            telemetry.addData("pos", armPos);
            telemetry.addData("pid", pid);
            telemetry.addData("ff", ff);
            telemetry.addData("power", power);
            telemetry.addData("target", target);
            telemetry.update();
        }
    }

}


