package org.firstinspires.ftc.teamcode.OpModes;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
@TeleOp(name = "pidless2")


public class pidless2 extends LinearOpMode {

    DcMotor leftFront, leftBack, rightFront, rightBack, slide,pivot0;
    Servo clawServo, wristServo;
    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();

    double myVariable = 200; // the thing you're adjusting
    ElapsedTime timer = new ElapsedTime();
    public enum RobotState {
        GRABSAMPLE,
        DROPSAMPLE,
    };

    RobotState state = RobotState.DROPSAMPLE;
    public boolean clawOpen = false;
    public boolean slideOut = false;

    private PIDController controller;

    public static double p = 0.02, i = 0.07, d = 0.0001;
    public static double f = 0.2;


public int target = 100;

    private final double ticks_in_degree = 1425 / 180;

    @Override
    public void runOpMode() throws InterruptedException {
        timer.reset();

        leftFront = hardwareMap.get(DcMotorEx.class, "leftFront");
        leftBack = hardwareMap.get(DcMotorEx.class, "leftBack");
        rightBack = hardwareMap.get(DcMotorEx.class, "rightBack");
        rightFront = hardwareMap.get(DcMotorEx.class, "rightFront");

        slide = hardwareMap.get(DcMotorEx.class, "slide");

        pivot0 = hardwareMap.get(DcMotorEx.class, "pivot0");

        clawServo = hardwareMap.servo.get("clawServo");
        wristServo = hardwareMap.servo.get("wristServo");

        clawServo.setPosition(0);
        leftBack.setDirection(DcMotor.Direction.REVERSE);
        leftFront.setDirection(DcMotor.Direction.REVERSE);
        pivot0.setPower(.5);
        while(((DcMotorEx) slide).getCurrent(CurrentUnit.AMPS) < 3.25) {
            slide.setPower(.5);
            telem();
        }

        slide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        pivot0.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        slide.setTargetPosition(-5);
        clawServo.setPosition(.35);

        slide.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        telem();

        controller = new PIDController(p, i, d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());



        pivot0.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        waitForStart();

        while (opModeIsActive()) {
            double dt = timer.seconds(); // time since last loop
            timer.reset();
//            target = ((int)(450 + gamepad2.left_stick_x * -250));
            controller.setPID(p, i, d);
            target += gamepad2.left_stick_x * -300 * dt;

            telemetry.addData("myVariable", myVariable);
            int armPos = pivot0.getCurrentPosition();
            double pid = controller.calculate(armPos, (int)target);
            double ff = Math.cos(Math.toRadians(target / ticks_in_degree)) * f;

            double power = pid + ff;

            pivot0.setTargetPosition((int)target);

            telemetry.addData("pos ", armPos);
            telemetry.addData("target ", target);


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

            slide.setTargetPosition((int)(slideOut ? -240 : -5));

            if(gamepad2.dpad_up){
                state = RobotState.DROPSAMPLE;
            }


        }
        else if(state == RobotState.DROPSAMPLE){
            slide.setTargetPosition((int)(slideOut ? -240 : -5));

            if(gamepad2.dpad_down){
                state = RobotState.GRABSAMPLE;
            }
        }

        wristServo.setPosition( gamepad2.left_trigger *.375 + gamepad2.right_trigger * -.375 + .62);
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

        telemetry.addData("piv0 cur",pivot0.getCurrentPosition());


        telemetry.addData("slide target",slide.getTargetPosition());
        telemetry.addData("target",target);
        telemetry.addData("slide cur",slide.getCurrentPosition());
        telemetry.addData("state",state);
        telemetry.update();
    }


}

