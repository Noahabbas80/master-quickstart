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
        Pose2d startPose = new Pose2d(-32.5175 , -65.071, Math.toRadians(180));
        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, t rack width

                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive -> drive.trajectorySequenceBuilder
                                (startPose)
//                        .strafeTo(new Vector2d(-32.5175, -65.5))
                        .waitSeconds(sto)
                        .splineTo(new Vector2d(-58, -58), Math.toRadians(225))
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(-53, -47, Math.toRadians(260)), Math.toRadians(90))
//                        .waitSeconds(0.3)
                        .setTangent(Math.toRadians(180))
                        .splineToLinearHeading(new Pose2d(-58, -58, Math.toRadians(225)), Math.toRadians(270))
                        .setTangent(Math.toRadians(180))
                        .splineToLinearHeading(new Pose2d(-63.5, -47.5, Math.toRadians(260)), Math.toRadians(90))
                        .setTangent(Math.toRadians(0))
                        .splineToLinearHeading(new Pose2d(-63.5, -47.5, Math.toRadians(225)), Math.toRadians(270))
                        .build());



        meepMeep.setBackground(MeepMeep.Background.FIELD_INTOTHEDEEP_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}