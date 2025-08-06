package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "SDItest")


public class SDItest extends LinearOpMode {

    DcMotor fr, bl, fl, br, sl, sr;
    Servo linkServo;
    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();

    @Override
    public void runOpMode() throws InterruptedException {

        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");
        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");
        sr = hardwareMap.dcMotor.get("sr");
        sl = hardwareMap.dcMotor.get("sl");

        linkServo = hardwareMap.servo.get("linkServo");

        sr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        sl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        sr.setTargetPosition(0);
        sl.setTargetPosition(0);

        sr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        sl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        sl.setDirection(DcMotor.Direction.REVERSE);

//        fr.setDirection(DcMotor.Direction.REVERSE);
//        br.setDirection(DcMotor.Direction.REVERSE);

        sr.setPower(.975);
        sl.setPower(.975);

        linkServo.setPosition(0.98);

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
        double rx = -gamepad1.right_stick_x;


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
        
    }

    public void telem() {
        telemetry.addData("elm pos", linkServo.getPosition());
        telemetry.update();
    }


}

