package org.firstinspires.ftc.teamcode.OpModes;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp(name = "myTime")


public class myTime extends LinearOpMode {
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

    DcMotor frMotor, blMotor, flMotor, brMotor, erm, elm, arm;
    Servo clawServo, wristServo, spinServo, armServo;
    Servo[] servoList;
    public int selected = 0;
    public Gamepad currentGamepad2 = new Gamepad();
    public Gamepad previousGamepad2 = new Gamepad();
    public double speedControl = 1;
    public boolean clawOpen = false;
    public boolean spinUp = false;
    public int armTarget = 150;
    public int[] slideTargets = {2275,560,1000,3250,1000,550};
    public double[] slidePowers = {1,.7,.7,1,1,1};
    public boolean screwOverGabe = false;

    @Override
    public void runOpMode() throws InterruptedException {
//        controller = new PIDController(p,i,d);

        flMotor = hardwareMap.dcMotor.get("flMotor");
        blMotor = hardwareMap.dcMotor.get("blMotor");
        frMotor = hardwareMap.dcMotor.get("frMotor");
        brMotor = hardwareMap.dcMotor.get("brMotor");


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

        elm.setDirection(DcMotor.Direction.REVERSE);

        frMotor.setDirection(DcMotor.Direction.REVERSE);
        brMotor.setDirection(DcMotor.Direction.REVERSE);

        elm.setPower(.9);
        erm.setPower(.9);
        arm.setPower(0.2);

        clawServo.setPosition(0.98);
        wristServo.setPosition(0.49);
        spinServo.setPosition(.975);
        armServo.setPosition(.1);

        armTarget = 150;
        waitForStart();


        while (opModeIsActive()) {


            previousGamepad2.copy(currentGamepad2);
            currentGamepad2.copy(gamepad2);

            if(!screwOverGabe){
                p1Controls();
                arm.setTargetPosition(armTarget - 25);
            }
            else{
                arm.setPower(0);
            }
            if(currentGamepad2.right_bumper && !previousGamepad2.right_bumper){
                selected++;
            }
            else if(currentGamepad2.left_bumper && !previousGamepad2.left_bumper){
                selected--;
            }
            elm.setTargetPosition(slideTargets[selected % 7]);
            erm.setTargetPosition(slideTargets[selected % 7]);

            elm.setPower(slidePowers[selected % 7]);
            erm.setPower(slidePowers[selected % 7]);
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

        double y = -gamepad1.left_stick_y;
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
                armServo.setPosition(0.62 - ((SOA+.07) * (-gamepad2.right_stick_x + 1)));
                spinServo.setPosition(spinUp ? .99 : 0.3);

                armTarget = 150;
                erm.setTargetPosition(0);
                elm.setTargetPosition(0);

                if(spinUp && currentGamepad2.left_bumper && previousGamepad2.left_bumper){
                    spinUp = false;
                    robotState = RobotState.DROPSAMPLE;
                } else if (currentGamepad2.left_stick_button &&  currentGamepad2.right_stick_button) {
                    robotState = RobotState.ASCENTSTART;
                }
                break;
            case DROPSAMPLE:
                clawServo.setPosition(clawOpen ? .65 : .98);
                wristServo.setPosition(0.49);
                armServo.setPosition(0.8 - (.1 * (-gamepad2.right_stick_x + 1)));
                spinServo.setPosition(spinUp ? 0.3 : .975);

                elm.setTargetPosition((int)(2150* 0.7172413793103448));
                erm.setTargetPosition((int)(2150* 0.7172413793103448));
                armTarget = 1040;
                if(currentGamepad2.right_bumper && !previousGamepad2.right_bumper){
                    clawOpen = !clawOpen;
//                    sleep(1000); //might need to fix
                }
                if(currentGamepad2.left_bumper && !previousGamepad2.left_bumper){
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
                armServo.setPosition(.8);
                spinServo.setPosition(.975);

                elm.setTargetPosition((int)(2750* 0.7172413793103448));
                erm.setTargetPosition((int)(2750* 0.7172413793103448));
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
                armServo.setPosition(0.7);
                spinServo.setPosition(.975); //possibly comment servo positions out (since they're the same as last time)

                elm.setTargetPosition((int)(1200* 0.7172413793103448));
                erm.setTargetPosition((int)(1200* 0.7172413793103448));
                arm.setPower(0.2);
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
        telemetry.addData("elm pos", elm.getTargetPosition());
        telemetry.addData("elm cur pos", elm.getCurrentPosition());
        telemetry.addData("erm pos", erm.getTargetPosition());
        telemetry.addData("erm cur pos", erm.getCurrentPosition());

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

