package org.firstinspires.ftc.teamcode.OpModes;
import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="ABDULSTOPDOINGTHAT")


public class ABDULSTOPDOINGTHAT extends LinearOpMode {

    private PIDController controller;
    public static double p = 0.0015, i = 0.12, d = 0.0002;
    public static double f=0.055;
    public static double target = 0;

    private final double ticksInDegrees = 1425.1/180;

    private DcMotorEx arm;
    public Servo armServo;

    @Override
    public void runOpMode() throws InterruptedException {
        controller = new PIDController(p,i,d);
        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
        arm = hardwareMap.get(DcMotorEx.class,"arm");
        armServo = hardwareMap.servo.get("armServo");
//        armServo.setPosition(0.77 - (.1 * (gamepad2.right_stick_x + 1)));

        arm.setDirection(DcMotor.Direction.REVERSE);
        waitForStart();
        while (opModeIsActive()) {
            controller.setPID(p,i,d);
            int armPos = arm.getCurrentPosition();
            double pid = controller.calculate(armPos,target);
            double ff = Math.cos(Math.toRadians(target/ticksInDegrees)) * f;

            double power = pid + ff;


//            armServo.setPosition(.61 + .05 * (-gamepad1.right_stick_x + 1));
            armServo.setPosition(0.85);
            if(gamepad1.a){
                target = 175;
            }
            else{
                target = 1040;
         }

                   arm.setPower(power);
            telemetry.addData("pos", armPos);
            telemetry.addData("pid", pid);
            telemetry.addData("ff", ff);
            telemetry.addData("power", power);
            telemetry.addData("target", target);

            telemetry.addData("arms", armServo.getPosition());
//            telemetry.addData("SERVotarget", servo.getPosition());
            telemetry.update();
        }
    }

}


