package org.firstinspires.ftc.teamcode.src.main.java.org.firstinspires.ftc.teamcode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.Range;

@TeleOp()
public class C20_BetterMecanumDriveOpMode extends OpMode {
    C20_FourWheelDrive drive = new C20_FourWheelDrive();
    double intakePower;
    @Override
    public void init() {
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y / 2;
        double right = gamepad1.left_stick_x;
        double rotate = -gamepad1.right_stick_x / 4;

        drive.drive(forward, right,rotate);

        intakePower = gamepad1.right_trigger - gamepad1.left_trigger;
        drive.intakeServo.setPower(intakePower);
        }
    }

class C20_FourWheelDrive {
    private DcMotor frontLeftMotor;
    private DcMotor frontRightMotor;
    private DcMotor backLeftMotor;
    private DcMotor backRightMotor;
    CRServo intakeServo;

    public void init(HardwareMap hardwareMap) {
        frontLeftMotor = hardwareMap.dcMotor.get("front_left_motor");
        frontRightMotor = hardwareMap.dcMotor.get("front_right_motor");
        backLeftMotor = hardwareMap.dcMotor.get("back_left_motor");
        backRightMotor = hardwareMap.dcMotor.get("back_right_motor");
        intakeServo = hardwareMap.crservo.get("intakeServo");

        backLeftMotor.setDirection(DcMotor.Direction.FORWARD);
        frontLeftMotor.setDirection(DcMotor.Direction.REVERSE);

        frontLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        frontRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backLeftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        backRightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    private void setPowers(double frontLeftPower, double frontRightPower, double backLeftPower, double backRightPower) {
        double maxSpeed = 1.0;
        maxSpeed = Math.max(maxSpeed, Math.abs(frontLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(frontRightPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backLeftPower));
        maxSpeed = Math.max(maxSpeed, Math.abs(backRightPower));

        frontLeftPower /= maxSpeed;
        frontRightPower /= maxSpeed;
        backLeftPower /= maxSpeed;
        backRightPower /= maxSpeed;

        frontLeftMotor.setPower(frontLeftPower);
        frontRightMotor.setPower(frontRightPower);
        backLeftMotor.setPower( backLeftPower);
        backRightMotor.setPower(backRightPower);
    }

    public void drive(double forward, double right, double rotate) {
        double frontLeftPower = Range.clip(forward + right + rotate, -1.0, 1.0) ;
        double frontRightPower = Range.clip(forward - right - rotate, -1.0, 1.0) ;
        double backLeftPower = Range.clip(forward - right + rotate, -1.0, 1.0) ;
        double backRightPower = Range.clip(forward + right - rotate, -1.0, 1.0) ;


        setPowers(frontLeftPower, frontRightPower, backLeftPower, backRightPower);
    }
}
