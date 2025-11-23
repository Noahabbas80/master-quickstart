package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.ftccommon.SoundPlayer;

@TeleOp(name="Sound Test", group="Examples")
public class SoundTest extends LinearOpMode {

    @Override
    public void runOpMode() throws InterruptedException {

        int soundID = hardwareMap.appContext.getResources()
                .getIdentifier("ness", "raw", hardwareMap.appContext.getPackageName());

        telemetry.addLine("Ready to play sound");
        telemetry.update();

        waitForStart();

        SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, soundID);

        telemetry.addLine("Played sound!");
        telemetry.update();
    }
}
