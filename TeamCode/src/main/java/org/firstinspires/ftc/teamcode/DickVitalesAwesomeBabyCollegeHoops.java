package org.firstinspires.ftc.teamcode;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="DickVitalesAwesomeBabyCollegeHoops")
public class DickVitalesAwesomeBabyCollegeHoops extends LinearOpMode {
    @Override
    
    public void runOpMode() throws InterruptedException {
        MecanumDrive drive = new MecanumDrive(hardwareMap,new Pose2d(0,0,0));

        int botwidth = 14;
        int unmodifiedOffset = 18 - botwidth;
        int off = unmodifiedOffset/2;


        waitForStart();

        Actions.runBlocking(
                drive.actionBuilder(new Pose2d(7, -60.5 - off, Math.toRadians(90)))
                        .splineTo(new Vector2d(60, -60), Math.toRadians(45))
                        .build());
    }

//    public class ServoAction() implements Action{
//
//
//        Servo servo;
//        double pos;
//        public ServoAction(Servo servo, double pos){
//            this.servo = servo;
//            this.pos = pos;
//        }
//        @Override
//        public boolean run(@NonNull TelemetryPacket telemetryPacket){
//            servo.setPosition(pos);
//            return false;
//        }
//    }
}
