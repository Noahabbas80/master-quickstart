package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Autonomous(name="Auton")
public class Auton extends LinearOpMode {
    DcMotor leftFront, leftBack, rightFront, rightBack, slide,pivot0,pivot2;
    Servo clawServo, wristServo;
    @Override
    public void runOpMode() throws InterruptedException {

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        slide = hardwareMap.get(DcMotorEx.class, "slide");

        pivot0 = hardwareMap.get(DcMotorEx.class, "pivot0");
        pivot2 = hardwareMap.get(DcMotorEx.class, "pivot2");

        clawServo = hardwareMap.servo.get("clawServo");
        wristServo = hardwareMap.servo.get("wristServo");

        clawServo.setPosition(0);
        wristServo.setPosition(.62);

        leftBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);

        pivot0.setPower(.15);
        pivot2.setPower(.15);

        while (((DcMotorEx) slide).getCurrent(CurrentUnit.AMPS) < 3.25) {
            slide.setPower(.5);
        }

        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivot0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivot2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slide.setTargetPosition(-5);
        pivot0.setTargetPosition(450);
        pivot2.setTargetPosition(-450);

        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pivot0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pivot2.setMode(DcMotor.RunMode.RUN_TO_POSITION);


        waitForStart();

        slide.setTargetPosition(-455);
        sleep(3000);

        move(-1,-1,-1,-1);
        sleep(10000);

        clawServo.setPosition(.35);
        sleep(2000);

        move(1,1,1,1);
        sleep(5000);

        move(0,0,0,0);
        slide.setTargetPosition(-20);
        sleep(2000);

        pivot0.setTargetPosition(5);
        pivot2.setTargetPosition(-5);

        sleep(3000); 

    }
    public void move(int fl,int fr,int bl,int br){
        leftFront.setPower(fl * .2);
        rightFront.setPower(fr * .2);
        leftBack.setPower(bl * .2);
        rightBack.setPower(br * .2);
    }
}