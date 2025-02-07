package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="firstRoadrunner")
public class firstRoadrunner extends LinearOpMode {
    @Override
    
    public void runOpMode() throws InterruptedException {
        int botwidth = 14;
        int unmodifiedOffset = 18 - botwidth;
        int armIdleTarget = 1870;
        int armRaisedTarget = 1040;
        int off = unmodifiedOffset/2;
        double sto = 0.5;
        double bto = 1.25;
        double backwallforsample = 15.246;
        int  initArmTarget = 1870;
        double backwallforsampleactual = 56.754;
        DcMotor frMotor, blMotor, flMotor, brMotor, erm, elm, arm;
        Servo clawServo, wristServo, spinServo, armServo, flagServo;

        MecanumDrive drive = new MecanumDrive(hardwareMap,new Pose2d(-32.5175 , -65.071, Math.toRadians(180)));

        arm = hardwareMap.dcMotor.get("arm");
        erm = hardwareMap.dcMotor.get("erm");
        elm = hardwareMap.dcMotor.get("elm");

        clawServo = hardwareMap.servo.get("clawServo");
        wristServo = hardwareMap.servo.get("wristServo");
        spinServo = hardwareMap.servo.get("spinServo");
        armServo = hardwareMap.servo.get("armServo");
        flagServo = hardwareMap.servo.get("flagServo");

        elm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        erm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        elm.setTargetPosition(0);
        erm.setTargetPosition(0);
        arm.setTargetPosition(initArmTarget);

        elm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        erm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elm.setPower(.9);
        erm.setPower(.9);
        arm.setPower(0.2);
        spinServo.setPosition(0.235);
        clawServo.setPosition(0.98);
        wristServo.setPosition(0.49);
        armServo.setPosition(.55);

        class dropSampleMode implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(0.235);
                clawServo.setPosition(0.98);
                wristServo.setPosition(0.49);
                armServo.setPosition(.55);

                elm.setTargetPosition(2150);
                erm.setTargetPosition(2150);
                arm.setTargetPosition(armRaisedTarget);
                return (erm.getCurrentPosition() < 2140);
            }
        }

        TrajectoryActionBuilder startShift = drive.actionBuilder(new Pose2d(44, -64.5, -Math.PI/2))
                .strafeTo(new Vector2d(44, -54.5))
                .waitSeconds(sto);

        waitForStart();

        Action dropSampleMode = new dropSampleMode();
        Actions.runBlocking(
                new SequentialAction(
                        startShift,
                        dropSampleMode,
                        startShift,
                        ));
    }



//      drive.actionBuilder(new Pose2d(-9, -60.5 - off, Math.toRadians(90)))
//            .lineToX(32)
//                        .splineTo(new Vector2d(60, -60), Math.toRadians(0)).lineToLinearHeading(new Pose2d(-48, -48 , Math.toRadians(45)))
//            .build());
}
