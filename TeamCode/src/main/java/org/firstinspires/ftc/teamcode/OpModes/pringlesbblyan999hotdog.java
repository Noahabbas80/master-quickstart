package org.firstinspires.ftc.teamcode.OpModes;

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
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="pringlesbblyan999hotdog")
public class pringlesbblyan999hotdog extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
         int selected = 0;
         int armRaisedTarget = 1040;
         Gamepad currentGamepad2 = new Gamepad();
         Gamepad previousGamepad2 = new Gamepad();

        double[] initValues = {0,-7,0.49};
        DcMotor frMotor, blMotor, flMotor, brMotor,  erm, elm, arm;
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
        arm.setTargetPosition(150 - 25);

        elm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        erm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elm.setPower(.975);
        erm.setPower(.975);
        arm.setPower(0.3);

        clawServo.setPosition(0.98);
        wristServo.setPosition(0.49);
        spinServo.setPosition(.975);
        armServo.setPosition(.1);

        erm.setDirection(DcMotor.Direction.REVERSE);

        class dropSampleMode implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(.975);
                clawServo.setPosition(0.98);
                wristServo.setPosition(0.49);
                armServo.setPosition(.85);
//0.7172413793103448
                elm.setTargetPosition((int)(1950 * 0.7172413793103448));
                erm.setTargetPosition((int)(1950 * 0.7172413793103448));
                arm.setTargetPosition(armRaisedTarget);
                return (erm.getCurrentPosition() < 250);
            }


        }
        class sdropSampleMode implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(.975);
                clawServo.setPosition(0.98);
                wristServo.setPosition(0.49);
                armServo.setPosition(.85);
//0.7172413793103448
                elm.setTargetPosition((int)(1950 * 0.7172413793103448));
                erm.setTargetPosition((int)(1950 * 0.7172413793103448));
                arm.setTargetPosition(armRaisedTarget);
                return (erm.getCurrentPosition() < 300);
            }


        }


        class grabSampleMode implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(.3);
                clawServo.setPosition(0.65);
                wristServo.setPosition(0.49);
                armServo.setPosition(0.6);

                elm.setTargetPosition(0);
                erm.setTargetPosition(0);
                arm.setTargetPosition(150);
//                return (erm.getCurrentPosition() > 2200);
                return false;
            }

        }

        class grabSampleMode2 implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(.3);
                clawServo.setPosition(0.65);
                wristServo.setPosition(0.3);
                armServo.setPosition(0.6);

                elm.setTargetPosition(0);
                erm.setTargetPosition(0);
                arm.setTargetPosition(150);
//                return (erm.getCurrentPosition() > 2200);
                return false;
            }

        }

        class grabSampleModed implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(.99);
                clawServo.setPosition(0.65);
                wristServo.setPosition(0.3);
                armServo.setPosition(0.67);

                elm.setTargetPosition(0);
                erm.setTargetPosition(0);
                arm.setTargetPosition(10);
//                return (erm.getCurrentPosition() > 2200);
                return false;
            }

        }

        class grabSampleMode3 implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(.3);
                clawServo.setPosition(0.65);
                wristServo.setPosition(0.49);
                armServo.setPosition(0.6);

                elm.setTargetPosition(0);
                erm.setTargetPosition(0);
                arm.setTargetPosition(150);
                class grabSampleMode implements Action {
                    @Override
                    public boolean run(@NonNull TelemetryPacket packet) {
                        spinServo.setPosition(.3);
                        clawServo.setPosition(0.65);
                        wristServo.setPosition(0.49);
                        armServo.setPosition(0.8);

                        elm.setTargetPosition(0);
                        erm.setTargetPosition(0);
                        arm.setTargetPosition(150);
//                return (erm.getCurrentPosition() > 2200);
                        return false;
                    }

                }
//                return (erm.getCurrentPosition() > 2200);
                return false;
            }

        }

        class lowerArm implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                    armServo.setPosition(0.7);
                    sleep(500);
                    clawServo.setPosition(0.99);
                    sleep(400);
                    return false;
                }
            }

        class lowerArm2 implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                wristServo.setPosition(0.675);
                armServo.setPosition(0.7);
                sleep(500);
                clawServo.setPosition(0.99);
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

        class alignWrist implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                wristServo.setPosition(initValues[2]);
                sleep(200);
                return false;
            }
        }

        class raiseArm implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {

                armServo.setPosition(0.35);
                sleep(250);
                return false;
            }
        }





        class armBucketShift implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                armServo.setPosition(.7);
                sleep(400);
                clawServo.setPosition(0.65);
                sleep(250);
                armServo.setPosition(.85);
                spinServo.setPosition(.3);
                sleep(500);
                return false;
            }


        }



        while(!isStarted()){

            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);
            telemetry.addData("Selected:", selected);
            telemetry.addData("Sample X Position:", initValues[0]);
            telemetry.addData("Sample Y Position:", initValues[1]);
            telemetry.addData("Wrist Position:", initValues[2]);

            if(currentGamepad2.a && !previousGamepad2.a && selected < 2){
                selected++;
            } else if(currentGamepad2.x && !previousGamepad2.x && selected > 0){
                selected--;
            } else if(currentGamepad2.dpad_left && !previousGamepad2.dpad_left){
                initValues[selected] -= (((selected == 2) ? 0.05 : 0.25) * (gamepad2.left_bumper ? 4 : 1));
            } else if(currentGamepad2.dpad_right && !previousGamepad2.dpad_right){
                initValues[selected] += ((selected == 2) ? 0.05 : 0.25)  * (gamepad2.left_bumper ? 4 : 1);
            }
            telemetry.update();
        }

        waitForStart();


        TrajectoryActionBuilder goToBucket1 = drive.actionBuilder(new Pose2d(-32.5175 , -65.071, Math.toRadians(180)))
                .strafeTo(new Vector2d(-50,-64));

        TrajectoryActionBuilder goToObservationZone = drive.actionBuilder(new Pose2d(-50, -64, Math.toRadians(225)))
                .strafeTo(new Vector2d(12,-64));

        TrajectoryActionBuilder goToBucketObv = drive.actionBuilder(new Pose2d(12, -64, Math.toRadians(255)))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(180));

        TrajectoryActionBuilder GrabSample2 = drive.actionBuilder(new Pose2d(-58, -58, Math.toRadians(225)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-52, -46.5, Math.toRadians(260)), Math.toRadians(90));

        TrajectoryActionBuilder goToBucket2 = drive.actionBuilder(new Pose2d(-52, -46.5, Math.toRadians(255)))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(270));

        TrajectoryActionBuilder GrabSample3 = drive.actionBuilder(new Pose2d(-58 , -58, Math.toRadians(225)))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-62, -47.5, Math.toRadians(260)), Math.toRadians(90));
//
            TrajectoryActionBuilder goToBucket3 = drive.actionBuilder(new Pose2d(-62, -46.5, Math.toRadians(260)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(270));
//
        TrajectoryActionBuilder GrabSample4 = drive.actionBuilder(new Pose2d(-58, -58, Math.toRadians(225)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-59, -42.25, Math.toRadians(305)), Math.toRadians(90));

        TrajectoryActionBuilder goToBucket4 = drive.actionBuilder(new Pose2d(-59, -42.25, Math.toRadians(305)))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-58 , -58, Math.toRadians(225)), Math.toRadians(270));

        TrajectoryActionBuilder GrabSample5 = drive.actionBuilder(new Pose2d(-58, -58, Math.toRadians(225)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-45 , -15, Math.toRadians(180)), Math.toRadians(0))
                .strafeTo(new Vector2d(initValues[0] - 13.5,initValues[1]));

        TrajectoryActionBuilder backAway = drive.actionBuilder(new Pose2d(initValues[0], initValues[1], Math.toRadians(180)))
                .strafeTo(new Vector2d(-45,-15));

        TrajectoryActionBuilder goToBucket5 = drive.actionBuilder(new Pose2d(-45,-15, Math.toRadians(180)))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-58,-58,Math.toRadians(225)),Math.toRadians(225));

        TrajectoryActionBuilder end = drive.actionBuilder(new Pose2d(-58, -58, Math.toRadians(225)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-50, -15, Math.toRadians(180)), Math.toRadians(0));




        Action sdropSampleMode = new sdropSampleMode();
        Action dropSampleMode = new dropSampleMode();
        Action armBucketShift = new armBucketShift();
        Action grabSampleMode = new grabSampleMode();
        Action grabSampleMode2 = new grabSampleMode2();
        Action grabSampleMode3 = new grabSampleMode3();
        Action grabSampleModed = new grabSampleModed();
        Action lowerArm = new lowerArm();
        Action lowerArm2 = new lowerArm2();
        Action alignWrist = new alignWrist();
        Action raiseArm = new raiseArm();
        Actions.runBlocking(
                new SequentialAction(
                        sdropSampleMode,
                        goToBucket1.build(),
                        armBucketShift,
                        grabSampleMode,
                        goToObservationZone.build(),
                        lowerArm2,
                        dropSampleMode,
                        goToBucketObv.build(),
                        armBucketShift,
                        grabSampleMode,
                        GrabSample2.build(),
                        lowerArm,
                        dropSampleMode,
                        goToBucket2.build(),
                        armBucketShift,
                        GrabSample3.build(),
                        lowerArm,
                        dropSampleMode,
                        goToBucket3.build(),
                        armBucketShift,
                        grabSampleMode2,
                        GrabSample4.build(),
                        lowerArm,
                        dropSampleMode,
                        goToBucket4.build(),
                        armBucketShift,
                        grabSampleModed,
                        end.build()
                        ));
    }
}
