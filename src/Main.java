import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;



public class Main {
	public static void readCurve(File file, boolean last) throws IOException {
		String motorName = file.getName().replace(".txt", "");
		
		Path path = Paths.get(file.getPath());
		int N = (int) Files.lines(path).count();
		
		Scanner scanner = new Scanner(file);
		
		double[] torque = new double[N];
		double[] omega = new double[N];
		double[] current = new double[N];
		double[] volts = new double[N];
		
		int i = 0;
		while (scanner.hasNextLine()) {
			omega[i] = scanner.nextDouble() * 2 * Math.PI / 60; // RPM to rad/s like a normal person
			torque[i] = scanner.nextDouble();
			current[i] = scanner.nextDouble();
			volts[i] = 12;
			
			scanner.nextLine(); // don't care about the rest
			i++;
		}
		
		scanner.close();
		
		RegressionSolution sol = (new PlanarRegression(new Vector(torque), new Vector(omega), new Vector(current))).getSolution();
		double b = -sol.getSolution().get(0);
		double Kt = sol.getSolution().get(1);
		
		
		sol = (new PlanarRegression(new Vector(volts), new Vector(omega), new Vector(current))).getSolution();
		double Kw = sol.getSolution().get(0);
		double R = sol.getSolution().get(1);
		
		System.out.print("\t" + motorName + " (" + b + ", " + R + ", " + Kt + ", " + Kw + ")");
		if (last) {
			System.out.println(";");
		} else {
			System.out.println(",");
		}
	}
	public static void main(String[] args) throws IOException {
		File folder = new File("./motorData");
		File[] files = folder.listFiles();

		for (int i = 0; i < files.length; i++) {
			readCurve(files[i], i == files.length - 1);
		}
	}
}
