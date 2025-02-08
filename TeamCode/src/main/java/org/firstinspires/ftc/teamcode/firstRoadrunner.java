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
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="firstRoadrunner")
public class firstRoadrunner extends LinearOpMode {
    @Override
    
    public void runOpMode() throws InterruptedException {
        int botwidth = 14;
        int unmodifiedOffset = 18 - botwidth;
        int armIdleTarget = 150;
        int armRaisedTarget = 1040;
        int off = unmodifiedOffset/2;
        double sto = .12;
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
        arm.setPower(0.2);

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

                elm.setTargetPosition(2300);
                erm.setTargetPosition(2300);
                arm.setTargetPosition(armRaisedTarget);
                return (erm.getCurrentPosition() < 1000);
            }


        }

        class grabSampleMode implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(0.3);
                clawServo.setPosition(0.65);
                wristServo.setPosition(0.49);
                armServo.setPosition(0.1);

                elm.setTargetPosition(0);
                erm.setTargetPosition(0);
                arm.setTargetPosition(150);
                return (erm.getCurrentPosition() > 1000);
            }

        }

        class grabSampleMode2 implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                spinServo.setPosition(0.975);
                clawServo.setPosition(0.65);
                wristServo.setPosition(0.49);
                armServo.setPosition(0.1);

                elm.setTargetPosition(0);
                erm.setTargetPosition(0);
                arm.setTargetPosition(150);
                return (erm.getCurrentPosition() > 1000);
            }

        }



        class lowerArm implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                    if(timer == null){
                        timer = new ElapsedTime();
                    }
                    armServo.setPosition(0.573);
                    return timer.seconds() < 1.2;
                }
            }

        class lowerArm2 implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if(timer == null){
                    timer = new ElapsedTime();
                }
                armServo.setPosition(0.572);
                return timer.seconds() < 1.2;
            }
        }

        class pickSample implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if(timer == null){
                    timer = new ElapsedTime();
                }
                clawServo.setPosition(0.99);
                return timer.seconds() < 1.2;
            }
        }
        class pickSample2 implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if(timer == null){
                    timer = new ElapsedTime();
                }
                clawServo.setPosition(0.99);
                return timer.seconds() < 1.2;
            }
        }




        class armBucketShift implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if((erm.getCurrentPosition() < 2290)){
                    return true;
                }
                else{
                    if(timer == null){
                        timer = new ElapsedTime();
                    }
                    armServo.setPosition(0.7);
                    if (timer.seconds() < .9){
                        armServo.setPosition(.59);
                        return true;
                    }
                    else{
                        if(timer.seconds() < 1.75){
                            clawServo.setPosition(0.65);
                        }
                        return timer.seconds() < 3;
                    }
                }
            }


        }
        class armBucketShift2 implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if((erm.getCurrentPosition() < 2290)){
                    return true;
                }
                else{
                    if(timer == null){
                        timer = new ElapsedTime();
                    }
                    armServo.setPosition(0.7);
                    if (timer.seconds() < .9){
                        armServo.setPosition(.59);
                        return true;
                    }
                    else{
                        if(timer.seconds() < 1.75){
                            clawServo.setPosition(0.65);
                        }
                        return timer.seconds() < 3;
                    }
                }
            }


        }
        class armBucketShift3 implements Action {
            ElapsedTime timer;
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                if((erm.getCurrentPosition() < 2290)){
                    return true;
                }
                else{
                    if(timer == null){
                        timer = new ElapsedTime();
                    }
                    armServo.setPosition(0.7);
                    if (timer.seconds() < .9){
                        armServo.setPosition(.59);
                        return true;
                    }
                    else{
                        if(timer.seconds() < 1.75){
                            clawServo.setPosition(0.65);
                        }
                        return timer.seconds() < 3;
                    }
                }
            }


        }

//
//        TrajectoryActionBuilder goToBucket1 = drive.actionBuilder(new Pose2d(-32.5175 , -65.071, Math.toRadians(180)))
//                .setTangent(Math.toRadians(90))
//                .splineTo(new Vector2d(-57, -57), Math.toRadians(135))
//                .waitSeconds(sto);

        TrajectoryActionBuilder goToBucket1 = drive.actionBuilder(new Pose2d(-32.5175 , -65.071, Math.toRadians(180)))
                .strafeTo(new Vector2d(-32.5175, -54.5))
                .waitSeconds(sto)
                .splineTo(new Vector2d(-57, -57), Math.toRadians(225))
                .waitSeconds(sto);

        TrajectoryActionBuilder GrabSample2 = drive.actionBuilder(new Pose2d(-57, -57, Math.toRadians(225)))
                .setTangent(Math.toRadians(260))
                .splineToLinearHeading(new Pose2d(-53.5, -46.25, Math.toRadians(260)), Math.toRadians(260))
                .waitSeconds(sto);

        TrajectoryActionBuilder goToBucket2 = drive.actionBuilder(new Pose2d(-53.5, -46.25, Math.toRadians(260)))
                .setTangent(Math.toRadians(260))
                .splineTo(new Vector2d(-56.5, -56.5), Math.toRadians(225))
                .waitSeconds(sto);

        TrajectoryActionBuilder GrabSample3 = drive.actionBuilder(new Pose2d(-56.5 , -56.5, Math.toRadians(225)))
                .setTangent(Math.toRadians(260))
                .splineToLinearHeading(new Pose2d(-63.5, -46.25, Math.toRadians(260)), Math.toRadians(260))
                .waitSeconds(sto);

        TrajectoryActionBuilder goToBucket3 = drive.actionBuilder(new Pose2d(-63.5, -46.25, Math.toRadians(260)))
                .strafeTo(new Vector2d(-50, -50))
                .setTangent(Math.toRadians(180))
                .splineTo(new Vector2d(-57, -57), Math.toRadians(155))
                .waitSeconds(sto);
        

        TrajectoryActionBuilder end = drive.actionBuilder(new Pose2d(-57, -57, Math.toRadians(155)))
                .setTangent(Math.toRadians(180))
                .strafeTo(new Vector2d(-45, -25))
                .waitSeconds(sto);

        waitForStart();




        Action dropSampleMode = new dropSampleMode();
        Action armBucketShift = new armBucketShift();
        Action armBucketShift2 = new armBucketShift2();
        Action grabSampleMode = new grabSampleMode();
        Action lowerArm = new lowerArm();
        Action pickSample = new pickSample();
        Action armBucketShift3 = new armBucketShift3();
        Action pickSample2 = new pickSample2();
        Action lowerArm2 = new lowerArm2();
        Action grabSampleMode2 = new grabSampleMode2();
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
                        armBucketShift2,
                        grabSampleMode,
                        GrabSample3.build(),
                        lowerArm2,
                        pickSample2,
                        dropSampleMode,
                        goToBucket3.build(),
                        armBucketShift3,
                        grabSampleMode2,
                        end.build()
                        ));
    }



//      drive.actionBuilder(new Pose2d(-9, -60.5 - off, Math.toRadians(90)))
//            .lineToX(32)
//                        .splineTo(new Vector2d(60, -60), Math.toRadians(0)).lineToLinearHeading(new Pose2d(-48, -48 , Math.toRadians(45)))
//            .build());
}
