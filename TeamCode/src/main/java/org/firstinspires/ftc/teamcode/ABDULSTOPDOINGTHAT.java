package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
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

import javax.net.ssl.SSLEngineResult;

@Config
@TeleOp(name="ABDULSTOPDOINGTHAT")


public class ABDULSTOPDOINGTHAT extends LinearOpMode {

    private PIDController controller;
    public static double p = 0.004, i = 0, d = 0.0004;
    public static double f=0.069;
    public static double target = 0;

    private final double ticksInDegrees = 1425.1/180;

    private DcMotorEx arm;
    public Servo servo;

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
                target = 150;
            }
            else{
                target = 1040;
            }

//            if(gamepad1.a){
//                gamepad1.rumble(0.25,0.25,200);
//                gamepad1.setLedColor(0.9,0.2,0.2,Gamepad.LED_DURATION_CONTINUOUS);
//                servo.setPosition(0.95);
//            }
//            else{
//                servo.setPosition(0.05 );
//                gamepad1.setLedColor(0.2,0.9,0.2,Gamepad.LED_DURATION_CONTINUOUS);
//            }
            arm.setPower(power);
            telemetry.addData("pos", armPos);
            telemetry.addData("pid", pid);
            telemetry.addData("ff", ff);
            telemetry.addData("power", power);
            telemetry.addData("target", target);
//            telemetry.addData("SERVotarget", servo.getPosition());
            telemetry.update();
        }
    }

}


