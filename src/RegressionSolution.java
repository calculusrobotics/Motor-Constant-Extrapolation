
public class RegressionSolution {
	private final Vector SOLUTION;
	private final double R2;
	private final double VAR_Y;
	private final double VAR_RES;
	
	public RegressionSolution(Vector solution, double varY, double varRes) {
		SOLUTION = solution;
		R2 = 1 - varRes/varY;
		VAR_Y = varY;
		VAR_RES = varRes;
	}
	
	public Vector getSolution() {
		return SOLUTION;
	}
	
	public double getR2() {
		return R2;
	}
	
	public double getOutputVariance() {
		return VAR_Y;
	}
	
	public double getResidualVariance() {
		return VAR_RES;
	}
}
