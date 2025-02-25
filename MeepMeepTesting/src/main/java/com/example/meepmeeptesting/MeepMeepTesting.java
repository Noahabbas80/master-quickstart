//package com.example.meepmeeptesting;
//
//import com.acmerobotics.roadrunner.geometry.Pose2d;
//import com.acmerobotics.roadrunner.geometry.Vector2d;
//
//import org.rowlandhall.meepmeep.MeepMeep;
//import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
//import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;
//
//public class MeepMeepTesting {
//    public static void main(String[] args) {
//        MeepMeep meepMeep = new MeepMeep(800);
//
//        int botwidth = 14;
//        int unmodifiedOffset = 18 - botwidth;
//        int off = unmodifiedOffset/2;
//        double wristWidth = 3.442;
//        double sto = 0.01;
//        double subSampleX = -25;
//        double subSampleY = -10;
//        Pose2d startPose = new Pose2d(-32.5175 , -65.071, Math.toRadians(180));
//        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
//                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, t rack width
//
//                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
//                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder
//                                (startPose)
//                        .lineTo(new Vector2d(-55, -65.071))
//                        .waitSeconds(1.25)
//                        .lineTo(new Vector2d(17, -65.071))
//                        .setTangent(Math.toRadians(180))
//                        .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(180))
//                        .setTangent(Math.toRadians(0))
//                        .splineToLinearHeading(new Pose2d(-53, -47, Math.toRadians(260)), Math.toRadians(90))
////                        .waitSeconds(1.25)
//                        .setTangent(Math.toRadians(180))
//                        .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(270))
////                        .waitSeconds(1.25)
//                        .setTangent(Math.toRadians(180))
//                        .splineToLinearHeading(new Pose2d(-63.5, -47.5, Math.toRadians(260)), Math.toRadians(90))
////                        .waitSeconds(1.25)
//                        .setTangent(Math.toRadians(0))
//                        .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(270))
////                        .waitSeconds(1.25)
//                        .setTangent(Math.toRadians(90))
//                        .splineToLinearHeading(new Pose2d(-58.62 , -41, Math.toRadians(315)), Math.toRadians(90))
////                        .waitSeconds(1.25)
//                        .setTangent(Math.toRadians(270))
//                        .splineToLinearHeading(new Pose2d(-58 , -58, Math.toRadians(225)), Math.toRadians(270))
////                        .waitSeconds(1.25)
//                        .setTangent(Math.toRadians(0))
//                        .splineToLinearHeading(new Pose2d(-37.5 , -15, Math.toRadians(180)), Math.toRadians(90))
//                        .lineTo(new Vector2d(subSampleX,subSampleY))
////                        .waitSeconds(1.25)
//                        .lineTo(new Vector2d(-37.5,-15))
//                        .setTangent(Math.toRadians(180))
//                        .splineToLinearHeading(new Pose2d(-58 , -58, Math.toRadians(225)), Math.toRadians(270))
////                        .waitSeconds(1.25)
//                        .setTangent(Math.toRadians(0))
//                        .splineToLinearHeading(new Pose2d(-40, -12, Math.toRadians(0)), Math.toRadians(90))
////                        .lineTo(new Vector2d(-42.5,-12))
//                        .build());
//
//
//
//        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
//                .setDarkMode(true)
//                .setBackgroundAlpha(0.95f)
//                .addEntity(myBot)
//                .start();
//    }
//}

package com.example.meepmeeptesting;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;

import org.rowlandhall.meepmeep.MeepMeep;
import org.rowlandhall.meepmeep.roadrunner.DefaultBotBuilder;
import org.rowlandhall.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        int botwidth = 14;
        int unmodifiedOffset = 18 - botwidth;
        int off = unmodifiedOffset/2;
        double wristWidth = 3.442;
        double sto = 0.01;
        double subSampleX = -25;
        double subSampleY = -10;
        Pose2d startPose = new Pose2d(-32.5175 , -65.071, Math.toRadians(180));
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, t rack width

                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder
                                (startPose)
//                        .strafeTo(new Vector2d(-32.5175,-60))
                        .setTangent(90)
                        .splineToLinearHeading(new Pose2d(-58,-58,Math.toRadians(225)), Math.toRadians(225))
//                        .splineTo(new Vector2d(-58, -58), Math.toRadians(225))
//                        .waitSeconds(1.25)
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(-53, -47, Math.toRadians(260)), Math.toRadians(90))
//                        .waitSeconds(1.25)
                        .setTangent(Math.toRadians(180))
                        .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(270))
//                        .waitSeconds(1.25)
                        .setTangent(Math.toRadians(180))
                        .splineToLinearHeading(new Pose2d(-63, -47, Math.toRadians(260)), Math.toRadians(90))
//                        .waitSeconds(1.25)
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(270))
//                        .waitSeconds(1.25)
                        .setTangent(Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(-56.5 , -40, Math.toRadians(305)), Math.toRadians(90))
//                        .waitSeconds(1.25)
                        .setTangent(Math.toRadians(270))
                        .splineToLinearHeading(new Pose2d(-58 , -58, Math.toRadians(225)), Math.toRadians(270))
//                        .waitSeconds(1.25)
                        .setTangent(Math.toRadians(90))
                        .splineToLinearHeading(new Pose2d(-40 , -15, Math.toRadians(180)), Math.toRadians(0))
                        .strafeTo(new Vector2d(subSampleX,subSampleY))
//                        .waitSeconds(1.25)
                        .strafeTo(new Vector2d(-40,-15))
                        .setTangent(Math.toRadians(180))
                        .splineTo(new Vector2d(-58,-58),Math.toRadians(225))
//                        .splineToLinearHeading(new Pose2d(-58 , -58, Math.toRadians(225)), Math.toRadians(270))
//                        .waitSeconds(1.25)
                        .setTangent(Math.toRadians(0))
//                        .strafeTo(new Vector2d(-40,-12))
                        .lineToLinearHeading(new Pose2d(-49,-34,Math.toRadians(0)))
                        .lineToLinearHeading(new Pose2d(-40,-10,Math.toRadians(0)))
//                        .splineToLinearHeading(new Pose2d(-40, -12, Math.toRadians(0)), Math.toRadians(90))
//                        .lineTo(new Vector2d(-42.5,-12))
                        .build());



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}