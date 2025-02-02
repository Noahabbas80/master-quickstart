package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="firstRoadrunner")
public class firstRoadrunner extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap,new Pose2d(0,0,0));
        Servo armServo = hardwareMap.servo.get("armServo");

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0,0,0))
                        .lineToX(64)
                        .lineToX(0)
                        .build());
    }
}
