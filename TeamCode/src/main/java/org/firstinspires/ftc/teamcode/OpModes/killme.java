package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@TeleOp(name = "killme")


public class killme extends LinearOpMode {

    DcMotor leftFront, leftBack, rightFront, rightBack, slide,pivot0,pivot2;
    Servo clawServo, wristServo;
    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();
    public int sv = 0;

    public enum RobotState {
        GRABSAMPLE,
        DROPSAMPLE,
    };

    RobotState state = RobotState.DROPSAMPLE;
    public boolean clawOpen = false;
    public boolean slideOut = false;
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
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        pivot0.setPower(.5);
        pivot2.setPower(.55);
        while(((DcMotorEx) slide).getCurrent(CurrentUnit.AMPS) < 3.25) {
            slide.setPower(.5);
            telem();
        }

        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivot0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivot2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slide.setTargetPosition(-5);
        pivot0.setTargetPosition(400);
        pivot2.setTargetPosition(-400);
        clawServo.setPosition(.35);

        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pivot0.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        pivot2.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telem();

        waitForStart();

        while (opModeIsActive()) {

            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);

            p1Controls();
            p2Controls(currentGamepad2, previousGamepad2);

            telem();

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

        leftFront.setPower((frontLeftPower));
        leftBack.setPower((backLeftPower));
        rightFront.setPower((frontRightPower));
        rightBack.setPower((backRightPower));

    }

    public void p2Controls(Gamepad currentGamepad, Gamepad previousGamepad) {

        if(state == RobotState.GRABSAMPLE){

            slide.setTargetPosition((int)(slideOut ? -250 : -5));
            pivot0.setTargetPosition((int)(740 + gamepad2.left_stick_x * 125));
            pivot2.setTargetPosition((int)(-740 - gamepad2.left_stick_x * 125));
            if(gamepad2.dpad_up){
                state = RobotState.DROPSAMPLE;
            }


        }
        else if(state == RobotState.DROPSAMPLE){
            slide.setTargetPosition((int)(slideOut ? -250 : -5));
            pivot0.setTargetPosition((int)(350 + gamepad2.left_stick_x * -60));
            pivot2.setTargetPosition((int)(-350 - gamepad2.left_stick_x * -60));
            if(gamepad2.dpad_down){
                state = RobotState.GRABSAMPLE;
            }
        }

        wristServo.setPosition( gamepad2.right_stick_x *.375 + .62);
        if(currentGamepad.right_bumper && !previousGamepad.right_bumper){
                    clawOpen = !clawOpen;
                }
        if(currentGamepad.left_bumper && !previousGamepad.left_bumper){
            slideOut = !slideOut;
        }
        clawServo.setPosition(clawOpen ? .35 : .65);
    }

    public void telem() {
        telemetry.addData("slide Current:",((DcMotorEx) slide).getCurrent(CurrentUnit.AMPS));
        telemetry.addData("encoder",slide.getCurrentPosition());
        telemetry.addData("Wrist ",wristServo.getPosition());

        telemetry.addData("Claw ",clawServo.getPosition());
        telemetry.addData("piv0 target",pivot0.getTargetPosition());
        telemetry.addData("piv2 target",pivot2.getTargetPosition());

        telemetry.addData("piv0 cur",pivot0.getCurrentPosition());

        telemetry.addData("piv2 cur",pivot2.getCurrentPosition());

        telemetry.addData("slide target",slide.getTargetPosition());

        telemetry.addData("slide cur",slide.getCurrentPosition());
        telemetry.addData("state",state);
        telemetry.update();
    }


}

