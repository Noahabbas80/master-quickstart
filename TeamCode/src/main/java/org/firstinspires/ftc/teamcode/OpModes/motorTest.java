package org.firstinspires.ftc.teamcode.OpModes;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@TeleOp(name="motorTest")
public class motorTest extends LinearOpMode {
    DcMotor fr, bl, fl, br;
    Servo sweepServo;

    @Override
    public void runOpMode() throws InterruptedException {


        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");

        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");
        sweepServo = hardwareMap.servo.get("sweepServo");



        waitForStart();

        while (opModeIsActive()) {
            telem();
            p1Controls();
            p2Controls();


        }
    }
    public void p1Controls() {


        double y = -gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * 1.1;
        double rx = gamepad1.right_stick_x;


        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        fl.setPower((frontLeftPower));
        bl.setPower((backLeftPower));
        fr.setPower((frontRightPower));
        br.setPower((backRightPower));
    }

    public void p2Controls() {
        sweepServo.setPosition(gamepad2.right_stick_y*0.35  + .55); //adding .75 not .5 because i dont want it to go lower than .25
    };

    public void telem() {
        telemetry.addData("servo", sweepServo.getPosition());
        telemetry.addData("left stick x", gamepad1.left_stick_x);
        telemetry.addData("left stick y", gamepad1.left_stick_y);
        telemetry.addData("right stick x", gamepad1.right_stick_x);
        telemetry.update();
    }
}
