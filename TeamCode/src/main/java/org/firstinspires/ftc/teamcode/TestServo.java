package org.firstinspires.ftc.teamcode;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="TestServo")


public class TestServo extends LinearOpMode {


    private DcMotorEx arm;
    Servo clawServo, wristServo, spinServo, armServo;
    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();

    @Override
    public void runOpMode() throws InterruptedException {
        arm = hardwareMap.get(DcMotorEx.class,"arm");
        armServo = hardwareMap.servo.get("armServo");
        armServo.setPosition(1);
        waitForStart();
        while (opModeIsActive()) {

            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);

            testServo(armServo);

            telemetry.addData("SERVotarget", armServo.getPosition());
            telemetry.update();
        }


    }
    public void testServo(Servo testingServo){
//        spinServo.setPosition(.205);
        if(gamepad2.a && !previousGamepad2.a){
            testingServo.setPosition(testingServo.getPosition() + .025);
        } else if (gamepad2.x && !previousGamepad2.x) {
            testingServo.setPosition(testingServo.getPosition() - .025);
        }
    }

}


