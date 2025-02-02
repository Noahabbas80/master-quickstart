package org.firstinspires.ftc.teamcode;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;

@Autonomous(name="firstRoadrunner")
public class firstRoadrunner extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap,new Pose2d(0,0,0));
        Servo armServo = hardwareMap.servo.get("armServo");

        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(0,0,0))
                        .lineToX(32)
                        .stopAndAdd(new ServoAction(armServo, .8))
                        .WaitSeconds(.3)
                        .lineToX(0)
                        .build());
    }

    public class ServoAction() implements Action{


        Servo servo;
        double pos;
        public ServoAction(Servo servo, double pos){
            this.servo = servo;
            this.pos = pos;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket telemetryPacket){
            servo.setPosition(pos);
            return false;
        }
    }
}
