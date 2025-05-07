package org.firstinspires.ftc.teamcode.OpModes.OldOpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "bums")


public class bums extends LinearOpMode {
    public double SOA = 0;
    public enum RobotState {
        GRABSAMPLE,
        DROPSAMPLE,
        ASCENTSTART,
        ASCENTEND,
        TAKESPECIMEN,
        HANGSPECIMEN,
    };
    public enum GrabState {
        BACK,
        FRONT,
        CENTER,
    };

    RobotState robotState = RobotState.GRABSAMPLE;
    GrabState grabState = GrabState.CENTER;

    DcMotor frMotor, blMotor, flMotor, brMotor, arm;
    Servo clawServo, wristServo, spinServo, armServo;
    Servo[] servoList;
    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();
    public double o = .055;
    public double speedControl = 1;
    public boolean clawOpen = false;
    public boolean spinUp = true;
    public int armTarget = 150;
    public int specCycle = 0;
    public boolean screwOverGabe = false;

    @Override
    public void runOpMode() throws InterruptedException {
//        controller = new PIDController(p,i,d);

        flMotor = hardwareMap.dcMotor.get("flMotor");
        blMotor = hardwareMap.dcMotor.get("blMotor");
        frMotor = hardwareMap.dcMotor.get("frMotor");
        brMotor = hardwareMap.dcMotor.get("brMotor");


        arm = hardwareMap.dcMotor.get("arm");


        clawServo = hardwareMap.servo.get("clawServo");
        wristServo = hardwareMap.servo.get("wristServo");
        spinServo = hardwareMap.servo.get("spinServo");
        armServo = hardwareMap.servo.get("armServo");


        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        arm.setTargetPosition(150);

        arm.setMode(DcMotor.RunMode.RUN_TO_POSITION);



        frMotor.setDirection(DcMotor.Direction.REVERSE);
        brMotor.setDirection(DcMotor.Direction.REVERSE);


        arm.setPower(0.275);

        clawServo.setPosition(0.98);
        wristServo.setPosition(0.49);
        spinServo.setPosition(.975);
        armServo.setPosition(.4 + o);

        armTarget = 150;
        waitForStart();


        while (opModeIsActive()) {


            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);

            if(!screwOverGabe){
                p1Controls();
//                arm.setPower(setArmPower());
                arm.setTargetPosition(armTarget);
            }
            else{
                arm.setPower(0);
            }
            p2Controls(currentGamepad2, previousGamepad2);
//            testServo(armServo);
            telem();

        }
    }

    public double setArmPower(){
        double kpAtHome = 500;
        double unscaledPower = (armTarget - arm.getCurrentPosition())/kpAtHome;
        unscaledPower = (unscaledPower > 1 ? 1 : unscaledPower);
        return unscaledPower*.3 + (0.2 * Math.signum(unscaledPower));
    }

    public void p1Controls() {
//
        if (gamepad1.dpad_down) {
            speedControl = 0.25;
        } else if (gamepad1.dpad_left || gamepad1.dpad_right) {
            speedControl = 0.5;
        } else if (gamepad1.dpad_up) {
            speedControl = 1;
        }

        double y =-gamepad1.left_stick_y;
        double x = gamepad1.left_stick_x * 1.1;
        double rx = -gamepad1.right_stick_x;


        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
        double frontLeftPower = (y + x + rx) / denominator;
        double backLeftPower = (y - x + rx) / denominator;
        double frontRightPower = (y - x - rx) / denominator;
        double backRightPower = (y + x - rx) / denominator;

        flMotor.setPower((frontLeftPower) * speedControl);
        blMotor.setPower((backLeftPower) * speedControl);
        frMotor.setPower((frontRightPower) * speedControl);
        brMotor.setPower((backRightPower) * speedControl);

    }

    public void p2Controls(Gamepad currentGamepad2, Gamepad previousGamepad2) {
        switch (robotState) {
            case GRABSAMPLE:
                if(currentGamepad2.right_bumper && !previousGamepad2.right_bumper){
                    clawOpen = !clawOpen;
                }
                if(currentGamepad2.dpad_up && !previousGamepad2.dpad_up){
                    spinUp = !spinUp;
                }

                if(currentGamepad2.dpad_left && !previousGamepad2.dpad_left){
                    SOA -= .01;
                }
                else if(currentGamepad2.dpad_right && !previousGamepad2.dpad_right){
                    SOA += .01;
                }
                else if(currentGamepad2.dpad_down && !previousGamepad2.dpad_down){
                    SOA = 0;
                }
                clawServo.setPosition(clawOpen ? .65 : .98);
                wristServo.setPosition(0.49 - .375 * gamepad2.left_stick_x); // write code to disable wrist while moving spinServo
                armServo.setPosition(0.66 + o - ((SOA+.07) * (-gamepad2.right_stick_x + 1)));
                spinServo.setPosition(spinUp ? .99 : 0.3);

                armTarget = 150;


                if(spinUp && currentGamepad2.left_bumper && previousGamepad2.left_bumper){
                    spinUp = false;
                    robotState = RobotState.DROPSAMPLE;
                }

                break;
            case DROPSAMPLE:
                clawServo.setPosition(clawOpen ? .65 : .98);
                wristServo.setPosition(0.49);
                armServo.setPosition(o + 0.85 - (.1 * (-gamepad2.right_stick_x + 1)));
                spinServo.setPosition(spinUp ? 0.3 : .975);


                armTarget = 990;
                if(currentGamepad2.right_bumper && !previousGamepad2.right_bumper){
                    clawOpen = !clawOpen;
                }
                if(currentGamepad2.left_bumper && !previousGamepad2.left_bumper){
                    robotState = RobotState.GRABSAMPLE;
                    spinUp = false;
                    clawOpen = true;
                }


                break;
            case HANGSPECIMEN:
                clawServo.setPosition(clawOpen ? .65 : .98);
                wristServo.setPosition(0.49);
                armServo.setPosition(o + 0.3 - (.1 * (-gamepad2.right_stick_x + 1)));
                spinServo.setPosition(0.3);
//                0.7172413793103448)

                armTarget = 50;
                if(currentGamepad2.right_bumper && !previousGamepad2.right_bumper){
                    clawOpen = !clawOpen;
                }
                if(currentGamepad2.square && !previousGamepad2.square){
                    robotState = RobotState.GRABSAMPLE;
                    spinUp = false;
                    clawOpen = true;
                }
                else if(currentGamepad2.left_stick_button && currentGamepad2.right_stick_button){
                    robotState = RobotState.ASCENTSTART;
                }

                break;
            case ASCENTSTART:
                clawServo.setPosition(.98);
                wristServo.setPosition(0.49);
                armServo.setPosition(.95);
                spinServo.setPosition(.975);

                armTarget = 1300;
                if(currentGamepad2.right_bumper) {
                    robotState = RobotState.ASCENTEND;
                } else if (currentGamepad2.x && currentGamepad2.y && currentGamepad2.a && currentGamepad2.b) {
                    robotState = RobotState.GRABSAMPLE;
                }
                break;
            case ASCENTEND:
                clawServo.setPosition(.98);
                wristServo.setPosition(0.49);
                armServo.setPosition(0.95);
                spinServo.setPosition(.975); //possibly comment servo positions out (since they're the same as last time)


//                arm.setPower(0.2);
                screwOverGabe = true; //yippee
                flMotor.setPower(0);
                frMotor.setPower(0);
                brMotor.setPower(0);
                blMotor.setPower(0);
                break;
        }
    }

    public void testServo(Servo testingServo){
//        spinServo.setPosition(.205);
        if(gamepad2.a && !previousGamepad2.a){
            testingServo.setPosition(testingServo.getPosition() + .025);
        } else if (gamepad2.x && !previousGamepad2.x) {
            testingServo.setPosition(testingServo   .getPosition() - .025);
        }
    }

    public void setServo(double claw, double wrist, double arm, double spin){
        clawServo.setPosition(claw);
        wristServo.setPosition(wrist);
        armServo.setPosition(arm);
        spinServo.setPosition(spin);
    }

    public void telem() {


        telemetry.addData("claw pos", clawServo.getPosition());
        telemetry.addData("wrist pos", wristServo.getPosition());
        telemetry.addData("arm pos", armServo.getPosition());
        telemetry.addData("spin pos", spinServo.getPosition());

        telemetry.addData("arm cur pos", arm.getCurrentPosition());


        telemetry.addData("arm target", armTarget);
        telemetry.addData("current state", robotState);
        telemetry.update();
    }


}

