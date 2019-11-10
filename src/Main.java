import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) throws FileNotFoundException {
		ArrayList<CurvePoint> points = new ArrayList<CurvePoint>();
		
		File file = new File("./data.txt");
		Scanner scanner = new Scanner(file);
		
		while (scanner.hasNextLine()) {
			double speed = scanner.nextDouble() * 2 * Math.PI / 60; // RPM to rad/s like a normal person
			double torque = scanner.nextDouble();
			double current = scanner.nextDouble();
			
			points.add(new CurvePoint(speed, torque, current));
			
			scanner.nextLine(); // don't care about the rest
		}
		
		scanner.close();
		
		
		
		CurvePoint stall = points.get(0);
		double K1 = stall.getTorque() / stall.getCurrent();
		System.out.println("K = " + K1 + " (stall point)");
		// Ki - torque = bw
		double w2 = 0;
		double i2 = 0;
		double iw = 0;
		double ti = 0;
		double tw = 0;
		double w = 0;
		double i = 0;
		double t = 0;
		double Ki_T_w = 0; 
		for (int j = 0; j < points.size(); j++) {
			CurvePoint point = points.get(j);
			
			i2 += Math.pow(point.getCurrent(), 2);
			iw += point.getCurrent() * point.getOmega();
			w2 += Math.pow(point.getOmega(), 2);
			tw += point.getTorque() * point.getOmega();
			ti += point.getTorque() * point.getCurrent();
			w += point.getOmega();
			i += point.getCurrent();
			t += point.getTorque();
			
			Ki_T_w += ((K1 * point.getCurrent()) - point.getTorque()) * point.getOmega();
		}
		double b1 = Ki_T_w / w2;
		System.out.println("B = " + b1 + " (linear regression given K at stall)");
		System.out.println();
		
		double[] Kb = EquationSolver.solve(
			new double[][] {
				new double[] {i2, iw},
				new double[] {iw, w2},
			}, new double[] {ti, tw}
		);
		double K2 = Kb[0];
		double b2 = -Kb[1];
		System.out.println("K = " + K2 + " (multilinear regression without friction)");
		System.out.println("b = " + b2 + " (multilinear regression without friction)");
		System.out.println();
		
		i2 -= Math.pow(stall.getCurrent(), 2);
		ti -= stall.getTorque() * stall.getCurrent();
		i -= stall.getCurrent();
		t -= stall.getTorque();
		
		double[] KbF = EquationSolver.solve(
			new double[][] {
				new double[] {i2, iw, i},
				new double[] {iw, w2, w},
				new double[] {i, w, points.size() - 1} // ignore first point = static friction
			}, new double[] {ti, tw, t}
		);
		double K3 = KbF[0];
		double b3 = -KbF[1];
		double F3 = -KbF[2];
		System.out.println("K = " + K3 + " (multilinear regression with kinetic friction)");
		System.out.println("b = " + b3 + " (multilinear regression with kinetic friction)");
		System.out.println("T_F = " + F3 + " (multilinear regression with kinetic friction)");
		// notice T_F is even in the wrong direction. Clearly this isn't a good model, especially
		// given the high amount of torque from friction. Motor curves are done with low friction
		System.out.println();
		
		double[] KR = EquationSolver.solve(
			new double[][] {
				new double[] {w2, iw},
				new double[] {iw, i2},
			}, new double[] {12 * w, 12 * i}
		);
		double Kw1 = KR[0];
		double R1 = KR[1];
		System.out.println("K = " + Kw1 + " multilinear regression with resistance");
		System.out.println("R = " + R1 + " multilinear regression with resistance");
	}
}
