//package org.firstinspires.ftc.teamcode;
//
//
//import com.acmerobotics.dashboard.FtcDashboard;
//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
//import com.arcrobotics.ftclib.controller.PIDController;
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//import com.qualcomm.robotcore.hardware.DcMotor;
//import com.qualcomm.robotcore.hardware.Gamepad;
//import com.qualcomm.robotcore.hardware.Servo;
//
//@TeleOp(name = "expTeleop")
//
//
//public class expTeleop extends LinearOpMode {
//
//    private PIDController controller;
//    public static double p = 0.0032, i = 0, d = 0.0004;
//    public static double f=0.069;
//    public static double target = 0;
//
//    private final double ticksInDegrees = 1425.1/180;
//
//    public double SOA = 0;
//    public enum RobotState {
//        GRABSAMPLE,
//        DROPSAMPLE,
//        ASCENTSTART,
//        ASCENTEND,
//        TAKESPECIMEN,
//        HANGSPECIMEN,
//    };
//    public enum GrabState {
//        BACK,
//        FRONT,
//        CENTER,
//    };
//
//    RobotState robotState = RobotState.GRABSAMPLE;
//    GrabState grabState = GrabState.CENTER;
//
//    DcMotor frMotor, blMotor, flMotor, brMotor, erm, elm, arm;
//    Servo clawServo, wristServo, spinServo, armServo, flagServo;
//    Servo[] servoList;
//    public Gamepad currentGamepad2 = new Gamepad();
//    public Gamepad previousGamepad2 = new Gamepad();
//    public double speedControl = 1;
//    public boolean clawOpen = false;
//    public boolean grabSpecimen = false;
//    public boolean spinUp = false;
//    public boolean WelcometotheSpaceJam = false;
//    public int armTarget = 150;
//    public boolean screwOverGabe = false;
//
//    @Override
//    public void runOpMode() throws InterruptedException {
//        controller = new PIDController(p,i,d);
//        telemetry = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
//
//        flMotor = hardwareMap.dcMotor.get("flMotor");
//        blMotor = hardwareMap.dcMotor.get("blMotor");
//        frMotor = hardwareMap.dcMotor.get("frMotor");
//        brMotor = hardwareMap.dcMotor.get("brMotor");
//
//
//        arm = hardwareMap.dcMotor.get("arm");
//        erm = hardwareMap.dcMotor.get("erm");
//        elm = hardwareMap.dcMotor.get("elm");
//
//        clawServo = hardwareMap.servo.get("clawServo");
//        wristServo = hardwareMap.servo.get("wristServo");
//        spinServo = hardwareMap.servo.get("spinServo");
//        armServo = hardwareMap.servo.get("armServo");
//        armServo = hardwareMap.servo.get("flagServo");
//
//        elm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        erm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
//
//        elm.setTargetPosition(0);
//        erm.setTargetPosition(0);
//
//        elm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        erm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//
//        elm.setDirection(DcMotor.Direction.REVERSE);
//
//        frMotor.setDirection(DcMotor.Direction.REVERSE);
//        brMotor.setDirection(DcMotor.Direction.REVERSE);
//
//        elm.setPower(.9);
//        erm.setPower(.9);
//        arm.setPower(0.2);
//
//        clawServo.setPosition(0.98);
//        wristServo.setPosition(0.38);
//        spinServo.setPosition(.9);
//        armServo.setPosition(1);
//
//        armTarget = 150;
//        waitForStart();
//
//
//        while (opModeIsActive()) {
//
//            controller.setPID(p,i,d);
//            int armPos = arm.getCurrentPosition();
//            double pid = controller.calculate(armPos,armTarget);
//            double ff = Math.cos(Math.toRadians(target/ticksInDegrees)) * f;
//
//            double power = pid + ff;
//
//            previousGamepad2.copy(currentGamepad2);
//            currentGamepad2.copy(gamepad2);
//
//            if(!screwOverGabe){
//                p1Controls();
//                arm.setPower(power);
//            }
//            p2Controls(currentGamepad2, previousGamepad2);
//            telem();
//
//        }
//    }
//
//    public double setArmPower(){
//        double kpAtHome = 500;
//        double unscaledPower = (armTarget - arm.getCurrentPosition())/kpAtHome;
//        unscaledPower = (unscaledPower > 1 ? 1 : unscaledPower);
//        return unscaledPower*.3 + (0.2 * Math.signum(unscaledPower));
//    }
//
//    public void p1Controls() {
////
//        if (gamepad1.dpad_down) {
//            speedControl = 0.25;
//        } else if (gamepad1.dpad_left || gamepad1.dpad_right) {
//            speedControl = 0.5;
//        } else if (gamepad1.dpad_up) {
//            speedControl = 1;
//        }
//
//        double y = -gamepad1.left_stick_y;
//        double x = gamepad1.left_stick_x * 1.1;
//        double rx = -gamepad1.right_stick_x;
//
//
//        double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
//        double frontLeftPower = (y + x + rx) / denominator;
//        double backLeftPower = (y - x + rx) / denominator;
//        double frontRightPower = (y - x - rx) / denominator;
//        double backRightPower = (y + x - rx) / denominator;
//
//        flMotor.setPower((frontLeftPower) * speedControl);
//        blMotor.setPower((backLeftPower) * speedControl);
//        frMotor.setPower((frontRightPower) * speedControl);
//        brMotor.setPower((backRightPower) * speedControl);
//
//    }
//
//    public void p2Controls(Gamepad currentGamepad2, Gamepad previousGamepad2) {
//        switch (robotState) {
//            case GRABSAMPLE:
//                if(currentGamepad2.right_bumper && !previousGamepad2.right_bumper){
//                    clawOpen = !clawOpen;
//                }
//                if(currentGamepad2.dpad_up && !previousGamepad2.dpad_up){
//                    spinUp = !spinUp;
//                }
//
//                if(currentGamepad2.dpad_left && !previousGamepad2.dpad_left){
//                    SOA -= .01;
//                }
//                else if(currentGamepad2.dpad_right && !previousGamepad2.dpad_right){
//                    SOA += .01;
//                }
//                else if(currentGamepad2.dpad_down && !previousGamepad2.dpad_down){
//                    SOA = 0;
//                }
//
//                if(currentGamepad2.y && !previousGamepad2.y && (currentGamepad2.left_trigger + currentGamepad2.right_trigger) > 1.5){
//                    grabSpecimen = !grabSpecimen;
//                }
//                clawServo.setPosition(clawOpen ? .65 : .98);
//                wristServo.setPosition(0.38 - .375 * gamepad2.left_stick_x); // write code to disable wrist while moving spinServo
//                armServo.setPosition(0.85 - ((SOA+.07) * (gamepad2.right_stick_x + 1)));
//                spinServo.setPosition(spinUp ? .9 : 0.235);
//
//                armTarget = 150;
//                erm.setTargetPosition(0);
//                elm.setTargetPosition(0);
//
//                if(spinUp && currentGamepad2.left_bumper && previousGamepad2.left_bumper){
//                    if(!grabSpecimen){
//                        spinUp = false;
//                        robotState = RobotState.DROPSAMPLE;
//                    }
//                    else{
//                        robotState = RobotState.TAKESPECIMEN;
//                    }
//
//                } else if (currentGamepad2.left_stick_button &&  currentGamepad2.right_stick_button) {
//                    robotState = RobotState.ASCENTSTART;
//                }
//                break;
//            case DROPSAMPLE:
//                clawServo.setPosition(clawOpen ? .65 : .98);
//                wristServo.setPosition(0.38);
//                armServo.setPosition(0.7 - (.1 * (gamepad2.right_stick_x + 1)));
//                spinServo.setPosition(spinUp ? 0.235 : .9);
//
//                elm.setTargetPosition(2150);
//                erm.setTargetPosition(2150);
//                armTarget = 1040;
//                if(currentGamepad2.right_bumper && !previousGamepad2.right_bumper){
//                    clawOpen = !clawOpen;
//                }
//                if(currentGamepad2.left_bumper && !previousGamepad2.left_bumper){
//                    robotState = RobotState.GRABSAMPLE;
//                    spinUp = false;
//                    clawOpen = true;
//                }
//                else if(currentGamepad2.left_stick_button && currentGamepad2.right_stick_button){
//                    robotState = RobotState.ASCENTSTART;
//                }
//
//                break;
//            case TAKESPECIMEN:
//                clawServo.setPosition(clawOpen ? .65 : .98);
//                wristServo.setPosition(0.38);
////                armServo.setPosition(ArmWiggle - (.04 * (gamepad2.right_stick_x + 1)));
//                spinServo.setPosition(0.235);
//
//                elm.setTargetPosition(0);
//                erm.setTargetPosition(0);
////                armTarget = TakespecimenArmPos;
//
//                if(currentGamepad2.right_bumper && !previousGamepad2.right_bumper){
//                    clawOpen = !clawOpen;
//                }
//                if(currentGamepad2.dpad_down && !previousGamepad2.dpad_down){
//                    robotState = RobotState.GRABSAMPLE;
//                    spinUp = false;
//                    clawOpen = true;
//                }
//                else if(currentGamepad2.left_bumper && !previousGamepad2.left_bumper){
//                    robotState = RobotState.HANGSPECIMEN;
//                    clawOpen = false;
////                    spinUp = selectSomething
//                }
//                else if(currentGamepad2.left_stick_button && currentGamepad2.right_stick_button){
//                    robotState = RobotState.ASCENTSTART;
//                }
//                break;
//            case HANGSPECIMEN:
//                clawServo.setPosition(clawOpen ? .65 : .98);
//                wristServo.setPosition(0.38);
//                armServo.setPosition(HangArm);
//                spinServo.setPosition(0.235);
//
//                elm.setTargetPosition(WelcometotheSpaceJam ? 1500 : 1000);
//                erm.setTargetPosition(WelcometotheSpaceJam ? 1500 : 1000);
////                armTarget = HangSpecimenTargetPos;
//                if(currentGamepad2.right_bumper && !previousGamepad2.right_bumper){
//                    clawOpen = !clawOpen;
//                }
//                if(currentGamepad2.dpad_down && !previousGamepad2.dpad_down){
//                    robotState = RobotState.GRABSAMPLE;
//                    spinUp = false;
//                    clawOpen = true;
//                }
//                else if(currentGamepad2.left_bumper && !previousGamepad2.left_bumper){
//                    robotState = RobotState.TAKESPECIMEN;
//                    clawOpen = true;
//                    WelcometotheSpaceJam = false;
////                    spinUp = selectSomething
//                }
//                break;
//            case ASCENTSTART:
//                clawServo.setPosition(.98);
//                wristServo.setPosition(0.38);
//                armServo.setPosition(.55);
//                spinServo.setPosition(.9);
//
////                elm.setTargetPosition(2500);
////                erm.setTargetPosition(2500);
////                armTarget = 1170;
//
//                elm.setTargetPosition(2650);
//                erm.setTargetPosition(2650);
//                armTarget = 900;
//                if(currentGamepad2.right_bumper) {
//                    robotState = RobotState.ASCENTEND;
//                } else if (currentGamepad2.x && currentGamepad2.y && currentGamepad2.a && currentGamepad2.b) {
//                    robotState = RobotState.GRABSAMPLE;
//                }
//                break;
//            case ASCENTEND:
//                clawServo.setPosition(.98);
//                wristServo.setPosition(0.38);
//                armServo.setPosition(.55);
//                spinServo.setPosition(.9); //possibly comment servo positions out (since they're the same as last time)
//
//                elm.setTargetPosition(1200);
//                erm.setTargetPosition(1200);
////                arm.setPower(0);
//                screwOverGabe = true; //yippee
//                flMotor.setPower(0);
//                frMotor.setPower(0);
//                brMotor.setPower(0);
//                blMotor.setPower(0);
//                break;
//
//        }
//    }
//
//    public void testServo(Servo testingServo){
////        spinServo.setPosition(.205);
//        if(gamepad2.a && !previousGamepad2.a){
//            testingServo.setPosition(testingServo.getPosition() + .025);
//        } else if (gamepad2.x && !previousGamepad2.x) {
//            testingServo.setPosition(testingServo   .getPosition() - .025);
//        }
//    }
//
//    public void telem() {
//        telemetry.addData("elm pos", elm.getTargetPosition());
//        telemetry.addData("elm cur pos", elm.getCurrentPosition());
//        telemetry.addData("erm pos", erm.getTargetPosition());
//        telemetry.addData("erm cur pos", erm.getCurrentPosition());
//
//        telemetry.addData("claw pos", clawServo.getPosition());
//        telemetry.addData("wrist pos", wristServo.getPosition());
//        telemetry.addData("arm pos", armServo.getPosition());
//        telemetry.addData("spin pos", spinServo.getPosition());
//
//        telemetry.addData("arm cur pos", arm.getCurrentPosition());
//
//
//        telemetry.addData("arm target", armTarget);
//        telemetry.addData("current state", robotState);
//        telemetry.addData("point mode", grabSpecimen ? "Specimen" : "Sample");
//        telemetry.update();
//    }
//
//
//}
//
