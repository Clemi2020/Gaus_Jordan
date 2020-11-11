package p;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		double[][] test1 = {{2, 3, 5}, {0, 5, 3}, {7, 3, 4}};
		double[][] test2 = {{2, 3, 5}, {0, 0, 3}, {7, 3, 4}, {0, 2, 4}};
		double[][] test3 = {{2, 3, 5}, {0, 0, 3}, {7, 3, 4}, {0, 2, 4}};
		double[][] test4 = {{1, 3, 4, 5}, {0, 1, 4, 3}, {0, 0, 1, 5}};
		
		double[][] test6 = {{1, 3, 4, 5}, {5, 1, 4, 3}, {7, 0, 1, 5}};
		
		double[][] test10 = {{1, 1, 0, 5}, {1, 1, 0, 4}, {1, 1, 1, 5}};
		double[][] test11 = {{1, 1, 0, 5}, {1, 1, 0, 5}, {1, 1, 0, 7}};
		double[][] test12 = {{0, 0, 0, 0}, {1, 1, 0, 5}, {1, 1, 0, 7}};
		double[][] test13 = {{-2, -4, -6, 4}, {3, -1, 2, 1}, {4, 0, 3, 3}};
		double[][] test14 = {{1, 1, -1, 4}, {4, -2, -2, 3}, {-5, 4, 2, 0}};
		double[][] test15 = {{3, 2, -1}, {4, 1, -2}, {6, 4, 3}};
		double[][] res;
		
		double[][] test5 = {{-4, -5, 0, 3}, {-3, 7, -8, 4}, {5, -7, -6, 5}};
		double[][] test7 = {{1, 1, 0, 5}, {0, 1, 0, 3}, {0, 0, 1, 5}};
		double[][] test8 = {{1, 0, 5, 5}, {0, 1, 5, 3}, {0, 0, 1, 5}};
		double[][] test9 = {{9, -4, -2, 3}, {9, -4, -2, 3}, {9, -4, -2, 3}};
		
		GJA a = new GJA();
		a.gaussjordanAlg(test6);
		System.out.print(a.trace);
		
	}
	static public void print(double[][] m) {
		if(m == null) {
			System.out.println("INVALID RESULT");
		}else {
			for(int z = 0; z < m.length; z++) {
				String line = ""; 
				for(int s = 0; s < m[0].length; s++) {
					line += (" " + m[z][s]);
				}
				System.out.println(line);
			}
		}
	}
}
