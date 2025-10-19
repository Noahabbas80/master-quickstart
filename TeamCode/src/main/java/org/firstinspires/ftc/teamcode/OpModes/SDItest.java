package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "SDItest")


public class SDItest extends LinearOpMode {

    DcMotor fr, bl, fl, br, sl, sr;
    Servo linkServo, headServo, frontClawServo, backClawServo, armServo,workServo;
    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();
    public boolean headUp = false;
    public boolean slideUp = true;


    @Override
    public void runOpMode() throws InterruptedException {

        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");
//        sr = hardwareMap.dcMotor.get("sr");
//        sl = hardwareMap.dcMotor.get("sl");

//        linkServo = hardwareMap.servo.get("linkServo");
//        workServo = hardwareMap.servo.get("workServo");
//        headServo = hardwareMap.servo.get("headServo");
//        frontClawServo = hardwareMap.servo.get("frontClawServo");
//        backClawServo = hardwareMap.servo.get("backClawServo");
//        armServo = hardwareMap.servo.get("armServo");
//
//        sr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        sl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        sr.setTargetPosition(0);
//        sl.setTargetPosition(0);
//        frontClawServo.setPosition(1);
//        backClawServo.setPosition(1);
//
//
//        sr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        sl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        sl.setDirection(DcMotor.Direction.REVERSE);

        fr.setDirection(DcMotor.Direction.REVERSE);
        br.setDirection(DcMotor.Direction.REVERSE);
        fl.setDirection(DcMotor.Direction.REVERSE);
        bl.setDirection(DcMotor.Direction.REVERSE);

//        sr.setPower(.95);
//        sl.setPower(.95);
//
//        linkServo.setPosition(0.98);

        waitForStart();

        while (opModeIsActive()) {

            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad1);

            p1Controls();
            //p2Controls(currentGamepad2, previousGamepad2);

            telem();

        }
    }

    public void p1Controls() {

        double y = -gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
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

    public void p2Controls(Gamepad currentGamepad2, Gamepad previousGamepad2) {

//        linkServo.setPosition(gamepad2.right_trigger * -.3 + .63);
//        workServo.setPosition(headUp ? .6 : .29 );
//
//
//        if(currentGamepad2.left_bumper && !previousGamepad2.left_bumper){
//            headUp = !headUp;
//        }
//        if(currentGamepad2.right_bumper && !previousGamepad2.right_bumper){
//            slideUp = !slideUp;
//        }
//        if(slideUp){
//            sl.setTargetPosition(2100);
//            sr.setTargetPosition(2100);
//        }
//        else{
//            sl.setTargetPosition(15);
//            sr.setTargetPosition(15);
//        }
    }


    public void telem() {
//        telemetry.addData("elm pos", armServo.getPosition());
//        telemetry.addData("back claw", linkServo.getPosition());
//        telemetry.addData("head claw", headServo.getPosition());
        telemetry.addData("fr", fr.getCurrentPosition());
        telemetry.addData("fl", fl.getCurrentPosition());
        telemetry.addData("br", br.getCurrentPosition());
        telemetry.addData("bl", bl.getCurrentPosition());

//        telemetry.addData("work claw", workServo.getPosition());
//
//        telemetry.addData("SR pos", sr.getCurrentPosition());
//        telemetry.addData("Sl pos", sl.getCurrentPosition());
        telemetry.update();
    }


}

