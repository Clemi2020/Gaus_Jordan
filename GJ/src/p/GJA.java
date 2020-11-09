package p;

import java.util.ArrayList;

/**
 * Die Klasse GJA dient zur Lösung von linearen Gleichungssystemen (LGS).
 * 
 * Dazu stellt die Klasse Funktionen zur Durchführung des Gauß-Algorithmus und
 * des Gauß-Jordan-Algorithmus sowie weitere Hilfsfunktionen bereit.
 * 
 * Alle Funktionen verwenden erweiterte Koeffizientenmatrizen (EKM).
 * 
 * @author Tamim Rahmani, Clemens Richter, Mohammad Danandeh
 * @version 1.0
 * @since 2020.10.18
 */

public class GJA {
	public String trace = "";

	/**
	 * Fügt der Trace Tabelle Text hinzu.
	 * 
	 * @param z Zeile für Trace Tabelle
	 */

	private void addTrace(String z) {
		this.trace += z;
	}

	/**
	 * Fügt der Trace Tabelle eine Zeile hinzu.
	 * 
	 * @param z Zeile für Trace Tabelle
	 */

	private void addTraceNl(String z) {
		this.trace += (z + "\n");
	}

	/**
	 * Fügt der Trace Tabelle eine Textdarstellung einer EKM zu.
	 * 
	 * @param m EKM
	 */

	private void addTraceEKM(double[][] m) {
		for (int i = 0; i < m.length; i++) {
			addTrace((i + 1) + ". (");
			for (int k = 0; k < (m[0].length - 1); k++) {
				addTrace(" " + m[i][k]);
			}
			addTrace(" |" + m[i][(m[0].length - 1)] + ")");
			addTrace("\n");
		}
	}

	/**
	 * Fügt der Trace Tabelle ein Trennzeichen zu.
	 * 
	 * @param m EKM
	 */

	private void addTraceL() {
		addTraceNl("");
	}

	/**
	 * Gibt den Index des ersten Koeffizienten != 0 Gibt -1 bei Nullzeile.
	 * 
	 * @param m Zeile einer EKM
	 * @param r Leserichtung. true -> l-r, false -> r-l
	 * @return Index
	 */

	public int getOrd(double[] mz, boolean r) {
		int b = (mz.length - 1);
		if (r) {
			for (int i = 0; i < b; i++) {
				if (mz[i] != 0) {
					return i;
				}
			}
		} else {
			for (int k = (b - 1); k >= 0; k--) {
				if (mz[k] != 0) {
					return k;
				}
			}
		}
		return -1;
	}

	/**
	 * Normiert eine Zeile einer EKM.
	 *
	 * @param mz Zeile einer EKM
	 * @param r  Richtung. true -> l-r, false -> r-l
	 * @return normierte Zeile einer EKM
	 */

	public double[] normZeile(double[] mz, boolean r) {
		double nfak = 1 / mz[getOrd(mz, r)]; // Faktor
		for (int i = 0; i < mz.length; i++) {
			mz[i] *= nfak;
		}
		return mz;
	}

	/**
	 * Normiert alle Zeilen einer EKM.
	 *
	 * @param m EKM
	 * @param r Richtung. true -> l-r, false -> r-l
	 * @return normierte EKM
	 */

	public double[][] normMatrix(double[][] m, boolean r) {
		for (int z = 0; z < m.length; z++) {
			m[z] = normZeile(m[z], r);
		}

		// Trace Start
		addTraceL();
		addTraceNl("Normiert");
		addTraceEKM(m);
		addTraceL();
		// Trace Ende

		return m;
	}

	/**
	 * Checkt ob zwei EKM Zeilen gleich sind bis zu gegebenen Idx
	 * 
	 * @param m EKM
	 * @return Ergebnis
	 */

	public boolean checkGleich(double[] z1, double[] z2, int idx) {
		if ((z1.length != z2.length) || (idx > (z1.length - 1))) {
			return false;
		} else {
			for (int i = 0; i <= idx; i++) {
				if (z1[i] != z2[i]) {
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * Checkt ob ein Widerspruch im LGS vorliegt. LGS muss vorher normiert und
	 * sortiert sein! Nutze sonst checkWid()!
	 * 
	 * 
	 * @param m EKM
	 * @return Ergebnis
	 */

	private boolean checkWidRaw(double[][] m) {
		int h = m.length;
		int b = m[0].length - 1;

		for (int i = 0; i < h; i++) { // i = Zeilen-Idx
			for (int k = (i + 1); k < h; k++) { // k = Zeilen-Idx nach i
				if (checkGleich(m[i], m[k], (b - 1))) {
					if (m[i][b] != m[k][b]) {
						// Trace Start
						addTraceL();
						addTraceNl("Widerspruch erkannt!");
						addTraceNl("Zwischen Zeile " + (i + 1) + " und Zeile " + (k + 1) + "!");
						addTraceEKM(m);
						addTraceL();
						// Trace Ende
						return true;
					}
				} else {
					break;
				}
			}
		}
		return false;
	}

	/**
	 * Checkt ob ein Widerspruch im LGS vorliegt.
	 * 
	 * @param m EKM
	 * @return Ergebnis
	 */

	public boolean checkWid(double[][] m) {

		m = normMatrix(m, true);
		m = sortMatrix(m);

		return checkWidRaw(m);
	}

	/**
	 * Entfernt Doppelte EKM Zeilen
	 * 
	 * @param m EKM
	 * @return Ergebnis
	 */

	public double[][] entfDoppel(double[][] m) {
		m = normMatrix(m, true);
		m = sortMatrix(m);

		ArrayList<double[]> mList = new ArrayList<double[]>();
		int h = m.length;
		int b = m[0].length - 1;
		boolean add;

		for (int i = 0; i < h; i++) {
			add = true;
			for (int k = 0; k < mList.size(); k++) {
				if (checkGleich(m[i], mList.get(k), b)) {
					add = false;
					break;
				}
			}
			if (add) {
				mList.add(m[i]);
			}
		}

		double[][] res = new double[mList.size()][m[0].length];
		for (int i = 0; i < mList.size(); i++) {
			res[i] = mList.get(i);
		}

		// Trace Start
		addTraceL();
		addTraceNl("Doppelte Zeilen entfernt");
		addTraceEKM(m);
		addTraceL();
		// Trace Ende

		return res;
	}

	/**
	 * Entfernt Nullzeilen
	 * 
	 * @param m EKM
	 * @return Ergebnis
	 */

	public double[][] entfNZ(double[][] m) {
		ArrayList<double[]> mList = new ArrayList<double[]>();
		int h = m.length;
		int ekmb = m[0].length;
		double[] n = new double[ekmb];

		for (int i = 0; i < h; i++) {
			if (!checkGleich(m[i], n, ekmb - 1)) {
				mList.add(m[i]);
			}
		}

		double[][] res = new double[mList.size()][ekmb];
		for (int i = 0; i < mList.size(); i++) {
			res[i] = mList.get(i);
		}

		// Trace Start
		if (res.length < m.length) {
			addTraceL();
			addTraceNl("Nullzeilen entfernt");
			addTraceEKM(res);
			addTraceL();
		}
		// Trace Ende

		return res;
	}

	/**
	 * Kombiniert Zwei Zeilen einer EKM.
	 * 
	 * @param z1 Zeile der EKM
	 * @param z2 Zeile der EKM
	 * @return Kombinierte Zeile
	 */

	public double[] kombZeilen(double[] z1, double[] z2) {
		if (z1.length == z2.length) {
			for (int i = 0; i < z1.length; i++) {
				z2[i] = z2[i] - z1[i];
			}
		}
		return z2;
	}

	/**
	 * Kombiniert Zeile mit allen Nachfolger- oder Vorgängerzeilen
	 * 
	 * @param m   EKM
	 * @param idx Index der Zeile
	 * @param r   Richtung. true -> o-u, false -> u-o
	 * @return Kombinierte EKM
	 */

	public double[][] kombMatrix(double[][] m, int idx, boolean r) {
		addTraceL(); // Trace
		addTraceNl("Zeilen Kombiniert"); // Trace
		if (r) {
			for (int i = idx + 1; i < m.length; i++) {
				m[i] = kombZeilen(m[idx], m[i]);
				addTraceNl((i + 1) + ". Zeile = " + (i + 1) + ". Zeile - " + (idx + 1) + ". Zeile"); // Trace
			}
		} else {
			for (int i = idx - 1; i >= 0; i--) {
				m[i] = kombZeilen(m[idx], m[i]);
				addTraceNl((i + 1) + ". Zeile = " + (i + 1) + ". Zeile - " + (idx + 1) + ". Zeile"); // Trace
			}
		}
		addTraceEKM(m); // Trace
		addTraceL(); // Trace
		return m;
	}

	/**
	 * Sortiert Zeilen einer EKM. Hohe Ordnung -> Niedrige Ordnung
	 *
	 * @param m EKM
	 * @return sortierte EKM
	 * @see https://www.javatpoint.com/bubble-sort-in-java
	 */

	public double[][] sortMatrix(double[][] m) {
		boolean t = false; // Trace
		int n = m.length;
		double[] temp;
		for (int i = 0; i < n; i++) {
			for (int j = 1; j < (n - i); j++) {
				if (getOrd(m[j - 1], true) > getOrd(m[j], true)) {
					temp = m[j - 1];
					m[j - 1] = m[j];
					m[j] = temp;
					t = true;
				}

			}
		}
		// Trace Start
		if (t) {
			addTraceL();
			addTraceNl("Zeilen Sortiert");
			addTraceEKM(m);
			addTraceL();
		}
		// Trace Ende
		return m;
	}

	/**
	 * Checkt ob eine dreieckige EKM vorliegt.
	 * 
	 * 1 k2 k1 | d 0 1 k1 | d 0 0 1 | d -> Dreieckige EKM
	 * 
	 * @param m EKM
	 * @return Ergebnis Ja/Nein
	 * @see https://www.mathebibel.de/gauss-algorithmus
	 */

	public boolean checkTri(double[][] m) {
		int h = m.length; // Höhe KM
		int b = m[0].length - 1; // Breite KM
		if (h == b) {
			for (int i = 0; i < h; i++) {
				for (int k = 0; k < i; k++) {
					if (m[i][k] != 0) {
						return false;
					}
				}
				if (m[i][i] != 1) {
					return false;
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Checkt ob eine diagonale EKM vorliegt.
	 * 
	 * 1 0 0 | d 0 1 0 | d 0 0 1 | d -> Diagonale EKM
	 * 
	 * @param m EKM
	 * @return Ergebnis Ja/Nein
	 * @see https://www.mathebibel.de/gauss-algorithmus
	 */

	public boolean checkDiag(double[][] m) {
		int h = m.length; // Höhe KM
		int b = m[0].length - 1; // Breite KM
		if (checkTri(m)) { // Prüfe ob Dreiecksform vorliegt
			for (int i = 0; i < h; i++) {
				for (int k = (i + 1); k < b; k++) {
					if (m[i][k] != 0) {
						return false;
					}
				}
			}
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Checkt eine Unterbestimme EKM vorliegt
	 * 
	 * @param m EKM
	 * @return Ergebnis Ja/Nein
	 */

	public boolean checkUntEKM(double[][] m) {
		if (m.length < (m[0].length - 1)) {
			// Trace Start
			addTraceL();
			addTraceNl("EKM ist unterbestimmt!");
			addTraceEKM(m);
			addTraceNl("Algorithmus Ende");
			addTraceL();
			// Trace Ende
			return true;
		}
		return false;
	}

	/**
	 * Checkt eine Überbestimme EKM vorliegt
	 * 
	 * @param m EKM
	 * @return Ergebnis Ja/Nein
	 */

	public boolean checkUebEKM(double[][] m) {
		if (m.length > (m[0].length - 1)) {
			return true;
		}
		return false;
	}

	/**
	 * Führt den Gauß-Algorithmus zum Lösen von LGS durch.
	 * 
	 * @param m EKM
	 * @return EKM in Dreiecksform
	 * @see https://www.mathebibel.de/gauss-algorithmus
	 */

	public double[][] gaussAlg(double[][] m) {
		int idx = 0;

		// Trace Start
		addTraceL();
		addTraceNl("Gauss-Algorithmus Start");
		addTraceEKM(m);
		addTraceL();
		// Trace Ende

		if (checkUebEKM(m)) { // Fall Überbestimmt
			m = entfDoppel(m);
		}

		while (!checkTri(m)) {
			m = entfNZ(m); // Entferne Nullzeilen
			if (checkUntEKM(m)) { // Fall Unterbestimmt
				return null;
			}
			m = normMatrix(m, true); // Normiere
			m = sortMatrix(m); // Sortiere

			if (checkWidRaw(m)) { // Fall Widerspruch
				return null;
			}
			m = kombMatrix(m, idx, true);
			idx++;
		}
		// Trace Start
		addTraceL();
		addTraceNl("Gauss-Algorithmus Ende");
		addTraceEKM(m);
		addTraceL();
		// Trace Ende
		return m;
	}

	/**
	 * Führt den Gauß-Jordan-Algorithmus zum Lösen von LGS durch.
	 * 
	 * @param m EKM
	 * @return EKM in Diagonalform
	 * @see https://www.mathebibel.de/gauss-algorithmus
	 */

	public double[][] gaussjordanAlg(double[][] m) {
		m = gaussAlg(m); // Führe Gauss-Alg durch
		if (m != null) {
			// Trace Start
			addTraceL();
			addTraceNl("Jordan-Algorithmus Start");
			addTraceEKM(m);
			addTraceL();
			// Trace Ende
			int idx = m[0].length - 2;
			while (!checkDiag(m)) {
				m = normMatrix(m, false);
				m = kombMatrix(m, idx, false);
				idx--;
			}
			// Trace Start
			addTraceL();
			addTraceNl("Jordan-Algorithmus Ende");
			addTraceEKM(m);
			addTraceL();
			// Trace Ende
		}
		return m;
	}
}
