package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "Mercury")


public class Mercury extends LinearOpMode {

    DcMotor leftFront, leftBack, rightFront, rightBack, elm,erm;
    Servo clawServo, pivotServo, flipServo, linkServo;
    public Gamepad currentGamepad1 = new Gamepad();
    public Gamepad previousGamepad1 = new Gamepad();

    public enum RobotState {
        GRABSAMPLE,
        DROPSAMPLE,
    };

    RobotState state = RobotState.GRABSAMPLE;
    public boolean flipped = false;
    public boolean clawOpen = false;
    public boolean pivotUp = false;

    public double link = 0.5;

    @Override
    public void runOpMode() throws InterruptedException {

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        elm = hardwareMap.get(DcMotorEx.class, "elm");
        erm = hardwareMap.get(DcMotorEx.class, "erm");



        clawServo = hardwareMap.servo.get("clawServo");
        pivotServo = hardwareMap.servo.get("pivotServo");
        flipServo = hardwareMap.servo.get("flipServo");
        linkServo = hardwareMap.servo.get("linkServo");

        clawServo.setPosition(0);
//        leftBack.setDirection(DcMotor.Direction.REVERSE);
//        leftFront.setDirection(DcMotor.Direction.REVERSE);
//        pivot0.setPower(.35);
//        pivot2.setPower(.35);
//        while(((DcMotorEx) slide).getCurrent(CurrentUnit.AMPS) < 3.25) {
//            slide.setPower(.5);
//            telem();
//        }
        elm.setPower(.5);
        erm.setPower(.5);

        elm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        erm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        elm.setTargetPosition(0);
        erm.setTargetPosition(0);


        elm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        erm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telem();

        waitForStart();

        while (opModeIsActive()) {

            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            p1Controls();
            p2Controls(currentGamepad1, previousGamepad1);

            telem();

        }
    }

    public void p1Controls() {

        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
        double rx = gamepad1.right_stick_x;

        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        leftFront.setPower((frontLeftPower));
        leftBack.setPower((backLeftPower));
        rightFront.setPower((frontRightPower));
        rightBack.setPower((backRightPower));

    }

    public void p2Controls(Gamepad currentGamepad, Gamepad previousGamepad) {

        if(state == RobotState.GRABSAMPLE){
            flipped = false;
            linkServo.setPosition(gamepad2.left_trigger * .5 + .25);
           elm.setTargetPosition(0);
           erm.setTargetPosition(0);
           clawServo.setPosition(clawOpen ? 0 : 1);
           pivotServo.setPosition(pivotUp ? 0 : 1);
           flipServo.setPosition(0);
            if(currentGamepad.right_bumper && !previousGamepad.right_bumper){
                clawOpen = !clawOpen;
            }
            if(currentGamepad.left_bumper && !previousGamepad.left_bumper){
                pivotUp = !pivotUp;
            }
            if(currentGamepad.circle && !previousGamepad.circle){
                state = RobotState.DROPSAMPLE;
            }
        }
        else if(state == RobotState.DROPSAMPLE){
            clawOpen = false;
            pivotUp = false;
            elm.setTargetPosition(2000);
            erm.setTargetPosition(2000);
            clawServo.setPosition(clawOpen ? 0 : 1);
            pivotServo.setPosition(pivotUp ? 0 : 1);
            flipServo.setPosition(flipped ? 0 : 1);
            if(currentGamepad.right_bumper && !previousGamepad.right_bumper){
                flipped = !flipped;
            }
            if(currentGamepad1.circle && !previousGamepad1.circle){
                state = RobotState.GRABSAMPLE;
            }
        }

    }

    public void telem() {

        telemetry.update();
    }


}

