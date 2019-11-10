
public class CurvePoint {
	private final double OMEGA;
	private final double TORQUE;
	private final double CURRENT;
	
	public CurvePoint(double omega, double torque, double current) {
		OMEGA = omega;
		TORQUE = torque;
		CURRENT = current;
	}
	
	public double getOmega() { return OMEGA; }
	public double getTorque() { return TORQUE; }
	public double getCurrent() { return CURRENT; }
}
