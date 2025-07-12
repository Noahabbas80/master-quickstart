package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "coney ")


public class coney extends LinearOpMode {
    public enum RobotState {
        GRABSAMPLE,
        DROPSAMPLE,
        ASCENTSTART,
        ASCENTEND,
        TAKESPECIMEN,
        HANGSPECIMEN,
    };


    RobotState robotState = RobotState.GRABSAMPLE;

    DcMotor frMotor, blMotor, flMotor, brMotor;
    Servo autoServo, baseServo, intakeServo;
    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();


    @Override
    public void runOpMode() throws InterruptedException {
//        controller = new PIDController(p,i,d);

        flMotor = hardwareMap.dcMotor.get("flMotor");
        blMotor = hardwareMap.dcMotor.get("blMotor");
        frMotor = hardwareMap.dcMotor.get("frMotor");
        brMotor = hardwareMap.dcMotor.get("brMotor");
//        brMotor = hardwareMap.dcMotor.get("outMotor");


        autoServo = hardwareMap.servo.get("autoServo");
        intakeServo = hardwareMap.servo.get("intakeServo");
        baseServo = hardwareMap.servo.get("baseServo");

//        frMotor.setDirection(DcMotor.Direction.REVERSE);
//        brMotor.setDirection(DcMotor.Direction.REVERSE);
//        blMotor.setDirection(DcMotor.Direction.REVERSE);
//        flMotor.setDirection(DcMotor.Direction.REVERSE);

        brMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        waitForStart();


        while (opModeIsActive()) {
            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);
                p1Controls();
                autoServo.setPosition(gamepad2.left_bumper ? 0.01 : 0.6);
            intakeServo.setPosition(gamepad2.right_stick_y/2 + .5);
            baseServo.setPosition(gamepad2.left_stick_x/2 + .5);
//            outMotor.setPower(gamepad2.a ? 1 : 0);
            telem();

        }
    }


    public void p1Controls() {

        double y = gamepad1.left_stick_y;
        double x = -gamepad1.left_stick_x * 1.1;
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


    public void telem() {

        telemetry.addData("auto pos", autoServo.getPosition());
        telemetry.addData("intake pos", intakeServo.getPosition());
        telemetry.update();
    }


}

