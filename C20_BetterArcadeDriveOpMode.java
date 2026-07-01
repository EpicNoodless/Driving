package org.firstinspires.ftc.teamcode.src.main.java.org.firstinspires.ftc.teamcode;;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp
public class C20_BetterArcadeDriveOpMode extends OpMode{
    TwoWheelDriveRobot drive = new TwoWheelDriveRobot();
    @Override
    public void init() {
        drive.init(hardwareMap);
    }

    @Override
    public void loop() {
        double forward = -gamepad1.left_stick_y;
        double right = gamepad1.left_stick_x;

        if (gamepad1.a) {
            drive.setPowers(forward + right, forward - right);
        }
        else drive.setPowers(forward / 2 + right / 2, forward / 2 - right / 2);
    }
}

class TwoWheelDriveRobot {
    private DcMotor leftMotor;
    private DcMotor rightMotor;

    public void init(HardwareMap hardwareMap) {
        leftMotor = hardwareMap.get(DcMotor.class, "left_motor");
        rightMotor = hardwareMap.get(DcMotor.class, "right_motor");

        leftMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        rightMotor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        leftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
    }
    public void setPowers(double leftPower, double rightPower){
        double largest = 1.0;
        largest = Math.max(largest, Math.abs(leftPower));
        largest = Math.max(largest, Math.abs(rightPower));

        leftMotor.setPower(leftPower / largest);
        rightMotor.setPower(rightPower / largest);
    }
}


