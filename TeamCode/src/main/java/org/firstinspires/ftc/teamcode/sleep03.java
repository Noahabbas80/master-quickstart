package org.firstinspires.ftc.teamcode;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="sleep03")
public class sleep03 extends LinearOpMode {
    @Override
    
    public void runOpMode() throws InterruptedException {
        int botwidth = 14;
        int unmodifiedOffset = 18 - botwidth;
        int armIdleTarget = 150;
        int armRaisedTarget = 1040;
        int off = unmodifiedOffset/2;
        double sto = .05;
        double bto = 1.25;

        double backwallforsample = 15.246;
        int initArmTarget = 220;
        double backwallforsampleactual = 56.754;
        DcMotor frMotor, blMotor, flMotor, brMotor, erm, elm, arm;
        Servo clawServo, wristServo, spinServo, armServo;

        MecanumDrive drive = new MecanumDrive(hardwareMap,new Pose2d(-32.5175 , -65.071, Math.toRadians(180)));

        arm = hardwareMap.dcMotor.get("arm");
        erm = hardwareMap.dcMotor.get("erm");
        elm = hardwareMap.dcMotor.get("elm");

        clawServo = hardwareMap.servo.get("clawServo");
        wristServo = hardwareMap.servo.get("wristServo");
        spinServo = hardwareMap.servo.get("spinServo");
        armServo = hardwareMap.servo.get("armServo");

        elm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        erm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        elm.setTargetPosition(0);
        erm.setTargetPosition(0);
        arm.setTargetPosition(150);

        elm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        erm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elm.setPower(.9);
        erm.setPower(.9);
        arm.setPower(0.3);

        clawServo.setPosition(0.98);
        wristServo.setPosition(0.49);
        spinServo.setPosition(.975);
        armServo.setPosition(.1);

        elm.setDirection(DcMotor.Direction.REVERSE);

        class dropSampleMode implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(0.975);
                clawServo.setPosition(0.98);
                wristServo.setPosition(0.49);
                armServo.setPosition(.7);

                elm.setTargetPosition(2000);
                erm.setTargetPosition(2000);
                arm.setTargetPosition(armRaisedTarget);
                return (erm.getCurrentPosition() < 250);
            }


        }

        class grabSampleMode implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(0.3);
                clawServo.setPosition(0.65);
                wristServo.setPosition(0.49);
                armServo.setPosition(0.5);

                elm.setTargetPosition(0);
                erm.setTargetPosition(0);
                arm.setTargetPosition(150);
//                return (erm.getCurrentPosition() > 2200);
                return false;
            }

        }



        class lowerArm implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                    armServo.setPosition(0.585);
                    sleep(400);
                    return false;
                }
            }

        class pickSample implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                clawServo.setPosition(0.99);
                sleep(250);
                return false;
            }
        }

        class armBucketShift implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                armServo.setPosition(.59);
                sleep(500);
                clawServo.setPosition(0.65);
                sleep(500);
                armServo.setPosition(.8);
                spinServo.setPosition(0.3);
                sleep(500);
                return false;
            }


        }



        TrajectoryActionBuilder goToBucket1 = drive.actionBuilder(new Pose2d(-32.5175 , -65.071, Math.toRadians(180)))
                .strafeTo(new Vector2d(-32.5175, -54.5))
                .waitSeconds(sto)
                .splineTo(new Vector2d(-58, -58), Math.toRadians(225))
                .waitSeconds(sto);

        TrajectoryActionBuilder GrabSample2 = drive.actionBuilder(new Pose2d(-58, -58.5, Math.toRadians(135)))
                .setTangent(Math.toRadians(255))
                .splineToLinearHeading(new Pose2d(-53, -47, Math.toRadians(260)), Math.toRadians(255))
                .waitSeconds(0.1);

        TrajectoryActionBuilder goToBucket2 = drive.actionBuilder(new Pose2d(-53, -47, Math.toRadians(255)))
                .setTangent(Math.toRadians(255))
                .splineTo(new Vector2d(-58, -58), Math.toRadians(225))
                .waitSeconds(sto);

        TrajectoryActionBuilder GrabSample3 = drive.actionBuilder(new Pose2d(-58 , -58, Math.toRadians(225)))
                .setTangent(Math.toRadians(255))
                .splineToLinearHeading(new Pose2d(-63.5, -47.5, Math.toRadians(260)), Math.toRadians(255))
                .waitSeconds(sto);

        TrajectoryActionBuilder goToBucket3 = drive.actionBuilder(new Pose2d(-63.5, -47.5, Math.toRadians(255)))
                .strafeTo(new Vector2d(-50, -50))
                .setTangent(Math.toRadians(180))
                .splineTo(new Vector2d(-58, -58), Math.toRadians(155))
                .waitSeconds(sto);

        TrajectoryActionBuilder end = drive.actionBuilder(new Pose2d(-58, -58, Math.toRadians(155)))
                .setTangent(Math. toRadians(180))
                .strafeTo(new Vector2d(-45, -25))
                .waitSeconds(sto);





        waitForStart();




        Action dropSampleMode = new dropSampleMode();
        Action armBucketShift = new armBucketShift();
        Action grabSampleMode = new grabSampleMode();
        Action lowerArm = new lowerArm();
        Action pickSample = new pickSample();

        Actions.runBlocking(
                new SequentialAction(
                        dropSampleMode,
                        goToBucket1.build(),
                        armBucketShift,
                        grabSampleMode,
                        GrabSample2.build(),
                        lowerArm,
                        pickSample,
                        dropSampleMode,
                        goToBucket2.build(),
                        armBucketShift,
                        grabSampleMode,
                        GrabSample3.build(),
                        lowerArm,
                        pickSample,
                        dropSampleMode,
                        goToBucket3.build(),
                        armBucketShift,
                        grabSampleMode,
                        end.build()
                        ));
    }



//      drive.actionBuilder(new Pose2d(-9, -60.5 - off, Math.toRadians(90)))
//            .lineToX(32)
//                        .splineTo(new Vector2d(60, -60), Math.toRadians(0)).lineToLinearHeading(new Pose2d(-48, -48 , Math.toRadians(45)))
//            .build());
}
