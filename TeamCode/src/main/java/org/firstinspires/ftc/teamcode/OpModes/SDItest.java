package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "SDItest")


public class SDItest extends LinearOpMode {

    DcMotor frMotor, blMotor, flMotor, brMotor;
    Servo servo;
    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();

    @Override
    public void runOpMode() throws InterruptedException {

        flMotor = hardwareMap.dcMotor.get("flMotor");
        blMotor = hardwareMap.dcMotor.get("blMotor");
        frMotor = hardwareMap.dcMotor.get("frMotor");
        brMotor = hardwareMap.dcMotor.get("brMotor");

        servo = hardwareMap.servo.get("servo");

//        elm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

//        elm.setTargetPosition(0);

//        elm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

//        erm.setDirection(DcMotor.Direction.REVERSE);

//        frMotor.setDirection(DcMotor.Direction.REVERSE);
//        brMotor.setDirection(DcMotor.Direction.REVERSE);

//        elm.setPower(.975);

        clawServo.setPosition(0.98);

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

        flMotor.setPower((frontLeftPower));
        blMotor.setPower((backLeftPower));
        frMotor.setPower((frontRightPower));
        brMotor.setPower((backRightPower));

    }

    public void p2Controls(Gamepad currentGamepad2, Gamepad previousGamepad2) {
        
    }

    public void telem() {
        telemetry.addData("elm pos", servo.getPosition());
        telemetry.update();
    }


}

