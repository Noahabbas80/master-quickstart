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
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="summerAUTON")
public class summerAUTON extends LinearOpMode {
    DcMotor fr, bl, fl, br;
    Servo sweepServo;

    public static class Offsets {
        double x = 1;
        double y = -0.5;
    }

    public static Offsets OFFSETS = new Offsets();
    @Override
    public void runOpMode() throws InterruptedException {

        MecanumDrive drive = new MecanumDrive(hardwareMap,new Pose2d(-4.7 , 62.5, Math.toRadians(180)));


        fl = hardwareMap.dcMotor.get("fl");
        bl = hardwareMap.dcMotor.get("bl");

        fr = hardwareMap.dcMotor.get("fr");
        br = hardwareMap.dcMotor.get("br");
        sweepServo = hardwareMap.servo.get("sweepServo");

        sweepServo.setPosition(.2);

        class lowerArm implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                sweepServo.setPosition(.9);
                sleep(600);
                return false;
            }
        }

        class raiseArm implements Action {
            @Override
            public boolean run(@NonNull TelemetryPacket packet) {
                sweepServo.setPosition(.2);
                return false;
            }
        }
        waitForStart();

//        TrajectoryActionBuilder goToSample1 = drive.actionBuilder(new Pose2d(0 , 62.5, Math.toRadians(180)))
//                .setTangent(180)
//                .splineToLinearHeading(new Pose2d(-40 + OFFSETS.x,25 + OFFSETS.y,Math.toRadians(250)), Math.toRadians(220));
//
//        TrajectoryActionBuilder sweep1 = drive.actionBuilder(new Pose2d(-40 + OFFSETS.x, 25 + OFFSETS.y, Math.toRadians(250)))
//                .setTangent(90)
//                .splineToLinearHeading(new Pose2d(-35 + OFFSETS.x, 62.5 + OFFSETS.y, Math.toRadians(180)), Math.toRadians(90));
//
//       TrajectoryActionBuilder goToSample2 = drive.actionBuilder(new Pose2d(-35 + OFFSETS.x, 62.5 + OFFSETS.y, Math.toRadians(180)))
//               .setTangent(270)
//               .splineToLinearHeading(new Pose2d(-50 + OFFSETS.x, 25 + OFFSETS.y, Math.toRadians(250)), Math.toRadians(180));
//
//       TrajectoryActionBuilder sweep2 = drive.actionBuilder(new Pose2d(-50 + OFFSETS.x, 25 + OFFSETS.y, Math.toRadians(250)))
//               .setTangent(90)
//               .splineToLinearHeading(new Pose2d(-50 + OFFSETS.x, 62.5 + OFFSETS.y, Math.toRadians(180)), Math.toRadians(90));
//        TrajectoryActionBuilder goToSample3 = drive.actionBuilder(new Pose2d(-50 + OFFSETS.x, 62.5 + OFFSETS.y, Math.toRadians(180)))
//                .setTangent(250)
//                .splineToLinearHeading(new Pose2d(-54 + OFFSETS.x, 20 + OFFSETS.y, Math.toRadians(250)), Math.toRadians(180));
//        TrajectoryActionBuilder sweep3 = drive.actionBuilder(new Pose2d(-54 + OFFSETS.x, 20 + OFFSETS.y, Math.toRadians(250)))
//                .setTangent(90)
//                .splineToLinearHeading(new Pose2d(-54 + OFFSETS.x, 62.5 + OFFSETS.y, Math.toRadians(180)), Math.toRadians(90));
//        TrajectoryActionBuilder test = drive.actionBuilder(new Pose2d(-4.7,  66, 180))
//                .setTangent(180)
//                .splineToLinearHeading(new Pose2d(-40 + OFFSETS.x,35 + OFFSETS.y,Math.toRadians(250)), Math.toRadians(220));

        //FASTER ROUTE (weirder tho)
        TrajectoryActionBuilder goToSample1 = drive.actionBuilder(new Pose2d(0 , 62.5, Math.toRadians(180)))
                .setTangent(180)
                .splineToLinearHeading(new Pose2d(-40 + OFFSETS.x,25 + OFFSETS.y,Math.toRadians(250)), Math.toRadians(220));

        TrajectoryActionBuilder sweep1 = drive.actionBuilder(new Pose2d(-40 + OFFSETS.x, 25 + OFFSETS.y, Math.toRadians(250)))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-35 + OFFSETS.x, 62.5 + OFFSETS.y, Math.toRadians(180)), Math.toRadians(90));

        TrajectoryActionBuilder goToSample2 = drive.actionBuilder(new Pose2d(-35 + OFFSETS.x, 62.5 + OFFSETS.y, Math.toRadians(180)))
                .setTangent(250)
                .splineToLinearHeading(new Pose2d(-50 + OFFSETS.x, 25 + OFFSETS.y, Math.toRadians(250)), Math.toRadians(250));

        TrajectoryActionBuilder sweep2 = drive.actionBuilder(new Pose2d(-50 + OFFSETS.x, 25 + OFFSETS.y, Math.toRadians(250)))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-50 + OFFSETS.x, 62.5 + OFFSETS.y, Math.toRadians(180)), Math.toRadians(90));
        TrajectoryActionBuilder goToSample3 = drive.actionBuilder(new Pose2d(-50 + OFFSETS.x, 62.5 + OFFSETS.y, Math.toRadians(180)))
                .setTangent(250)
                .splineToLinearHeading(new Pose2d(-54 + OFFSETS.x, 20 + OFFSETS.y, Math.toRadians(250)), Math.toRadians(180));
        TrajectoryActionBuilder sweep3 = drive.actionBuilder(new Pose2d(-54 + OFFSETS.x, 20 + OFFSETS.y, Math.toRadians(250)))
                .setTangent(90)
                .splineToLinearHeading(new Pose2d(-54 + OFFSETS.x, 62.5 + OFFSETS.y, Math.toRadians(180)), Math.toRadians(90));
//        TrajectoryActionBuilder test = drive.actionBuilder(new Pose2d(-4.7,  66, 180))
//                .setTangent(180)
//                .splineToLinearHeading(new Pose2d(-40 + OFFSETS.x,35 + OFFSETS.y,Math.toRadians(250)), Math.toRadians(220));

        Action lowerArm = new lowerArm();
        Action raiseArm = new raiseArm();
        Actions.runBlocking(
                new SequentialAction(
                        goToSample1.build(),
                        lowerArm,
                        sweep1.build(),
                        raiseArm,
                        goToSample2.build(),
                        lowerArm,
                        sweep2.build(),
                        raiseArm,
                        goToSample3.build(),
                        lowerArm,
                        sweep3.build(),
                        raiseArm
//                        test.build()
                ));
        while (opModeIsActive()) {
            telem();
        }
    }

    public void telem() {
        telemetry.addData("servo", sweepServo.getPosition());
        telemetry.update();
    }
}
