package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.ftccommon.SoundPlayer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.util.ElapsedTime;
@TeleOp(name = "ea")


public class ea extends LinearOpMode {

    DcMotor motor1;
    DcMotor motor2;
    public Gamepad currentGamepad1 = new Gamepad();
    public Gamepad previousGamepad1 = new Gamepad();
    public ElapsedTime runTime = new ElapsedTime();
      public boolean ended = false;
    @Override
    public void runOpMode() throws InterruptedException {
        int marillID = hardwareMap.appContext.getResources().getIdentifier("marill", "raw", hardwareMap.appContext.getPackageName());
        int nessID = hardwareMap.appContext.getResources().getIdentifier("ness", "raw", hardwareMap.appContext.getPackageName());
        int ff1ID = hardwareMap.appContext.getResources().getIdentifier("ff1", "raw", hardwareMap.appContext.getPackageName());
        int gtaID = hardwareMap.appContext.getResources().getIdentifier("gta", "raw", hardwareMap.appContext.getPackageName());

        motor1 = hardwareMap.dcMotor.get("motor1");
        motor2 = hardwareMap.dcMotor.get("motor2");
        waitForStart();
        runTime.reset();


        while (opModeIsActive()) {

            telemetry.addData("gamepad", gamepad1.x);
            telemetry.update();
            previousGamepad1.copy(currentGamepad1);
            currentGamepad1.copy(gamepad1);

            if(gamepad1.triangle){
                motor1.setPower(1);
                motor2.setPower(1);
            }
            else if(gamepad1.square){
                motor1.setPower(0);
                motor2.setPower(0);
            }
            else{
                motor1.setPower(-1);
                motor2.setPower(-1);
            }

            if(currentGamepad1.dpad_up && !previousGamepad1.dpad_up){
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, nessID);
            }

            if (runTime.seconds() > 123 && !ended)
            {
                ended = true;
                SoundPlayer.getInstance().startPlaying(hardwareMap.appContext, nessID);
            }

        }
    }


}

