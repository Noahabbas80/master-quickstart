package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "main ")

public class main extends LinearOpMode {
    DcMotor frMotor, blMotor, flMotor, brMotor;
    Servo autoServo;
    public enum RobotState {
        INTAKE,
        OUTTAKE,
    };

    RobotState robotState = RobotState.INTAKE;
    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();




    @Override
    public void runOpMode() throws InterruptedException {
        flMotor = hardwareMap.dcMotor.get("flMotor");
        blMotor = hardwareMap.dcMotor.get("blMotor");
        frMotor = hardwareMap.dcMotor.get("frMotor");
        brMotor = hardwareMap.dcMotor.get("brMotor");







        autoServo = hardwareMap.servo.get("autoServo");

        autoServo.setPosition(.1);




        brMotor.setDirection(DcMotor.Direction.REVERSE);
        flMotor.setDirection(DcMotor.Direction.REVERSE);



        waitForStart();




        while (opModeIsActive()) {




            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);


            p1Controls();


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
        autoServo.setPosition(gamepad1.left_bumper ? .9 : .2);
        autoServo.setPosition(gamepad1.left_stick_x/2 +.5);
    }






    public void telem() {


        telemetry.addData("current state", robotState);
        telemetry.addData("spatula", gamepad1.left_bumper);
        telemetry.addData("spatula pos", gamepad1.left_stick_x);
        telemetry.update();
    }




}
