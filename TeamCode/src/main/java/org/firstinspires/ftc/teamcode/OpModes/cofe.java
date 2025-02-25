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

@Autonomous(name="")
public class cofe extends LinearOpMode {
    @Override

    public void runOpMode() throws InterruptedException {
         int selected = 0;
         int armRaisedTarget = 1040;
         Gamepad currentGamepad2 = new Gamepad();
         Gamepad previousGamepad2 = new Gamepad();

        double[] initValues = {-42,-6.0,0.5};
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
        arm.setTargetPosition(150 - 25);

        elm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        erm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        elm.setPower(.9);
        erm.setPower(.9);
        arm.setPower(0.3);

        clawServo.setPosition(0.98);
        wristServo.setPosition(0.49);
        spinServo.setPosition(.3);
        armServo.setPosition(.1);

        elm.setDirection(DcMotor.Direction.REVERSE);

        class dropSampleMode implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(.3);
                clawServo.setPosition(0.98);
                wristServo.setPosition(0.49);
                armServo.setPosition(.7);

                elm.setTargetPosition((int)(2000 * 0.7172413793103448));
                erm.setTargetPosition((int)(2000 * 0.7172413793103448));
                arm.setTargetPosition(armRaisedTarget - 25);
                return (erm.getCurrentPosition() < 250);
            }


        }

        class grabSampleMode implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(.975);
                clawServo.setPosition(0.65);
                wristServo.setPosition(0.49);
                armServo.setPosition(0.5);

                elm.setTargetPosition(0);
                erm.setTargetPosition(0);
                arm.setTargetPosition(150 -25 );
//                return (erm.getCurrentPosition() > 2200);
                return false;
            }

        }

        class grabSampleMode2 implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(.975);
                clawServo.setPosition(0.65);
                wristServo.setPosition(0.3);
                armServo.setPosition(0.51);

                elm.setTargetPosition(0);
                erm.setTargetPosition(0);
                arm.setTargetPosition(150 -25);
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
                    clawServo.setPosition(0.99);
                    sleep(250);
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
                armServo.setPosition(.59);
                sleep(400);
                clawServo.setPosition(0.65);
                sleep(250);
                armServo.setPosition(.8);
                spinServo.setPosition(.975);
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
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-58,-58,Math.toRadians(225)), Math.toRadians(225));

        TrajectoryActionBuilder GrabSample2 = drive.actionBuilder(new Pose2d(-58, -58, Math.toRadians(225)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-53, -47.5, Math.toRadians(260)), Math.toRadians(90));

        TrajectoryActionBuilder goToBucket2 = drive.actionBuilder(new Pose2d(-53, -47.5, Math.toRadians(255)))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(270));

        TrajectoryActionBuilder GrabSample3 = drive.actionBuilder(new Pose2d(-58 , -58, Math.toRadians(225)))
                .setTangent(Math.toRadians(180))
                .splineToLinearHeading(new Pose2d(-63, -47.5, Math.toRadians(260)), Math.toRadians(90));
//
            TrajectoryActionBuilder goToBucket3 = drive.actionBuilder(new Pose2d(-63., -47.5, Math.toRadians(260)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(270));
//
        TrajectoryActionBuilder GrabSample4 = drive.actionBuilder(new Pose2d(-58, -58, Math.toRadians(225)))
                .setTangent(Math.toRadians(90))
                .splineToLinearHeading(new Pose2d(-58.75, -42.25, Math.toRadians(305)), Math.toRadians(90));

        TrajectoryActionBuilder goToBucket4 = drive.actionBuilder(new Pose2d(-58.62, -42.25, Math.toRadians(305)))
                .setTangent(Math.toRadians(270))
                .splineToLinearHeading(new Pose2d(-58 , -58, Math.toRadians(225)), Math.toRadians(270));

        TrajectoryActionBuilder GrabSample5 = drive.actionBuilder(new Pose2d(-58, -58, Math.toRadians(225)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-45 , -15, Math.toRadians(180)), Math.toRadians(0))
                .strafeTo(new Vector2d(initValues[0],initValues[1]));

        TrajectoryActionBuilder backAway = drive.actionBuilder(new Pose2d(initValues[0], initValues[1], Math.toRadians(180)))
                .strafeTo(new Vector2d(-45,-15));

        TrajectoryActionBuilder goToBucket5 = drive.actionBuilder(new Pose2d(-45,-15, Math.toRadians(180)))
                .setTangent(Math.toRadians(180))
                .splineTo(new Vector2d(-58,-58),Math.toRadians(225));

        TrajectoryActionBuilder end = drive.actionBuilder(new Pose2d(-58, -58, Math.toRadians(155)))
                .setTangent(Math.toRadians(0))
                .splineToLinearHeading(new Pose2d(-40, -12, Math.toRadians(0)), Math.toRadians(90));





        Action dropSampleMode = new dropSampleMode();
        Action armBucketShift = new armBucketShift();
        Action grabSampleMode = new grabSampleMode();
        Action grabSampleMode2 = new grabSampleMode2();
        Action lowerArm = new lowerArm();
        Action alignWrist = new alignWrist();
        Action raiseArm = new raiseArm();
        Actions.runBlocking(
                new SequentialAction(
                        dropSampleMode,
                        goToBucket1.build(),
                        armBucketShift,
                        grabSampleMode,
                        GrabSample2.build(),
                        lowerArm,
                        dropSampleMode,
                        goToBucket2.build(),
                        armBucketShift,
                        grabSampleMode,
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
                        grabSampleMode,
                        GrabSample5.build(),
                        alignWrist,
                        lowerArm,
                        raiseArm,
                        backAway.build(),
                        dropSampleMode,
                        goToBucket5.build(),
                        armBucketShift,
                        grabSampleMode,
                        end.build()
                        ));
    }
}
