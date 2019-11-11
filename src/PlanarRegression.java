
public class PlanarRegression {
	private final Vector Y;
	private final Vector[] XS;
	private final boolean USE_INTERCEPT;
	
	private final Matrix MAT;
	private final Vector VEC;
	
	private final int N;
	
	public PlanarRegression(boolean useIntercept, Vector y, Vector... xs) {
		Y = y;
		XS = xs;
		USE_INTERCEPT = useIntercept;
		
		int vars = xs.length;
		if (USE_INTERCEPT) {
			vars += 1;
		}
		
		MAT = new Matrix(new double[vars][vars]);
		VEC = new Vector(new double[vars]);
		
		N = xs[0].getDimension();
		
		
		for (int i = 0; i < XS.length; i++) {
			double vecNum = 0;
			
			for (int j = 0; j < XS.length; j++) {
				double matNum = 0;
				
				for (int n = 0; n < N; n++) {
					matNum += XS[i].get(n) * XS[j].get(n) / N;
				}
				
				MAT.set(i, j, matNum);
			}
			
			for (int n = 0; n < N; n++) {
				vecNum += XS[i].get(n) * Y.get(n) / N;
			}
			
			VEC.set(i, vecNum);
		}
	}
	
	public PlanarRegression(Vector y, Vector... xs) {
		this(false, y, xs);
	}
	
	public RegressionSolution getSolution() {
		Vector solution = MAT.inv(VEC);
		
		double yAvg = Y.getAverage();
		
		double var = 0;
		double res2 = 0;
		
		for (int n = 0; n < N; n++) {
			double pred = 0;
			
			for (int i = 0; i < XS.length; i++) {
				pred += XS[i].get(n) * solution.get(i);
			}
			
			var += Math.pow(Y.get(n) - yAvg, 2) / N;
			res2 += Math.pow(Y.get(n) - pred, 2) / N;
		}
		
		return new RegressionSolution(solution, var, res2);
	}
}
