package net.ramso.doc.svg.utils;

/**
 * @author user To change this generated comment edit the template variable
 *         "typecomment": Window>Preferences>Java>Templates. To enable and
 *         disable the creation of type comments go to
 *         Window>Preferences>Java>Code Generation.
 */
public class CalculaPosiciones {
	private int		size	= 0;
	private int		space	= 0;
	private int		border	= 10;
	private int		y;
	private int		filas;
	private int[]	objX;
	private int[]	objY;
	private int[]	fila;
	private int[]	columna;

	/**
	 * Constructor for CalculaPosiciones.
	 */
	public CalculaPosiciones() {
		setSize(100);
		setSpace(50);
	}

	/**
	 * Returns the border.
	 * 
	 * @return int
	 */
	public int getBorder() {
		return border;
	}

	/**
	 * Returns the size.
	 * 
	 * @return int
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Returns the space.
	 * 
	 * @return int
	 */
	public int getSpace() {
		return space;
	}

	/**
	 * Returns the y.
	 * 
	 * @return int
	 */
	private int getY() {
		return y;
	}

	/**
	 * Sets the border.
	 * 
	 * @param border
	 *            The border to set
	 */
	public void setBorder(int border) {
		this.border = border;
	}

	/**
	 * Sets the size.
	 * 
	 * @param size
	 *            The size to set
	 */
	public void setSize(int size) {
		this.size = size;
		setY((size / 5) * 3);
	}

	/**
	 * Sets the space.
	 * 
	 * @param space
	 *            The space to set
	 */
	public void setSpace(int space) {
		this.space = space;
	}

	/**
	 * Sets the y.
	 * 
	 * @param y
	 *            The y to set
	 */
	private void setY(int y) {
		this.y = y;
	}

	/**
	 * Method calSizeDoc.
	 * 
	 * @param i
	 * @return int[]
	 */
	public int[] calSizeDoc(int numObjs) {
		int[] pos = new int[2];
		setFilas((numObjs - 1) / 3);
		if (((numObjs - 1) % 3) > 0) {
			setFilas(getFilas() + 1);
		}
		setFilas(getFilas() + 1);
		pos[0] = (getBorder() * 2) + (getSpace() * 2) + (getSize() * 3);
		pos[1] = (getBorder() * 2) + (getSpace() * (getFilas() - 1))
				+ (getY() * getFilas());
		calPosObjs(numObjs);
		return pos;
	}

	/**
	 * Method getPosObj.
	 * 
	 * @param i
	 * @return int[]
	 */
	public int[] getPosObj(int i) {
		int[] pos = new int[4];
		pos[0] = getObjX()[i];
		pos[1] = getObjY()[i];
		pos[2] = getSize();
		pos[3] = getY();
		return pos;
	}

	private void calPosObjs(int numObjs) {
		objX = new int[numObjs];
		objY = new int[numObjs];
		fila = new int[numObjs];
		columna = new int[numObjs];
		int pX = 0;
		int pY = 0;
		pX = getBorder() + getSize() + getSpace();
		pY = getBorder();
		setObjX(pX, 0);
		setObjY(pY, 0);
		setColumna(1, 0);
		setFila(0, 0);
		int ind = 1;
		for (int i = 0; i < getFilas(); i++) {
			if (numObjs == ind) {
				break;
			}
			else {
				pY = pY + getY() + getSpace();
				for (int j = 0; j < 3; j++) {
					if (numObjs == ind) {
						break;
					}
					else {
						if (j == 0) {
							pX = getBorder();
						}
						else {
							pX = pX + getSize() + getSpace();
						}
						setObjX(pX, ind);
						setObjY(pY, ind);
						setColumna(j, ind);
						setFila((i + 1), ind);
						ind++;
					}
				}
			}
		}
	}

	/**
	 * Returns the objX.
	 * 
	 * @return int[]
	 */
	private int[] getObjX() {
		return objX;
	}

	/**
	 * Returns the objY.
	 * 
	 * @return int[]
	 */
	private int[] getObjY() {
		return objY;
	}

	/**
	 * Sets the objX.
	 * 
	 * @param objX
	 *            The objX to set
	 */
	private void setObjX(int val, int pos) {
		this.objX[pos] = val;
	}

	/**
	 * Sets the objY.
	 * 
	 * @param objY
	 *            The objY to set
	 */
	private void setObjY(int val, int pos) {
		this.objY[pos] = val;
	}

	/**
	 * Returns the filas.
	 * 
	 * @return int
	 */
	public int getFilas() {
		return filas;
	}

	/**
	 * Sets the filas.
	 * 
	 * @param filas
	 *            The filas to set
	 */
	public void setFilas(int filas) {
		this.filas = filas;
	}

	/**
	 * Method getPosCon.
	 */
	public int[] getPosCon(int ind) {
		int[] posCon = new int[9];
		if (getColumna(ind) < getColumna(0)) {
			posCon[0] = getPosX(0);
			posCon[1] = getPosY(0) + (getY() / 2);
			if ((getFila(ind) - 1) == 0) {
				posCon[6] = getPosX(ind) + (getSize() / 2);
				posCon[7] = getPosY(ind);
				posCon[2] = posCon[6];
				posCon[4] = posCon[6];
				posCon[3] = posCon[1];
				posCon[5] = posCon[1];
				posCon[8] = 120;
			}
			else {
				posCon[6] = getPosX(ind) + getSize();
				posCon[7] = getPosY(ind) + (getY() / 2);
				posCon[2] = posCon[0] - (getSpace() / 2);
				posCon[4] = posCon[0] - (getSpace() / 2);
				posCon[3] = posCon[1];
				posCon[5] = posCon[7];
				posCon[8] = 180;
			}
		}
		else if (getColumna(ind) > getColumna(0)) {
			posCon[0] = getPosX(0) + getSize();
			posCon[1] = getPosY(0) + (getY() / 2);
			if ((getFila(ind) - 1) == 0) {
				posCon[6] = getPosX(ind) + (getSize() / 2);
				posCon[7] = getPosY(ind);
				posCon[2] = posCon[6];
				posCon[4] = posCon[6];
				posCon[3] = posCon[1];
				posCon[5] = posCon[1];
				posCon[8] = 120;
			}
			else {
				posCon[6] = getPosX(ind);
				posCon[7] = getPosY(ind) + (getY() / 2);
				posCon[2] = posCon[0] + (getSpace() / 2);
				posCon[4] = posCon[0] + (getSpace() / 2);
				posCon[3] = posCon[1];
				posCon[5] = posCon[7];
				posCon[8] = 90;
			}
		}
		else {
			if ((getFila(ind) - 1) == 0) {
				posCon[0] = getPosX(0) + (getSize() / 2);
				posCon[1] = getPosY(0) + getY();
				posCon[6] = getPosX(ind) + (getSize() / 2);
				posCon[7] = getPosY(ind);
				posCon[2] = posCon[6];
				posCon[4] = posCon[6];
				posCon[3] = posCon[7];
				posCon[5] = posCon[7];
				posCon[8] = 120;
			}
			else {
				posCon[0] = getPosX(0) + getSize();
				posCon[1] = getPosY(0) + (getY() / 2);
				posCon[6] = getPosX(ind) + getSize();
				posCon[7] = getPosY(ind) + (getY() / 2);
				posCon[2] = posCon[0] + (getSpace() / 2);
				posCon[4] = posCon[0] + (getSpace() / 2);
				posCon[3] = posCon[1];
				posCon[5] = posCon[7];
				posCon[8] = 180;
			}
		}
		return posCon;
	}

	/**
	 * Method getPosY.
	 * 
	 * @param i
	 * @return int
	 */
	private int getPosY(int i) {
		return objY[i];
	}

	/**
	 * Method getPosX.
	 * 
	 * @param i
	 * @return int
	 */
	private int getPosX(int i) {
		return objX[i];
	}

	/**
	 * Returns the columna.
	 * 
	 * @return int[]
	 */
	private int[] getColumna() {
		return columna;
	}

	private int getColumna(int i) {
		return columna[i];
	}

	/**
	 * Returns the fila.
	 * 
	 * @return int[]
	 */
	private int[] getFila() {
		return fila;
	}

	private int getFila(int i) {
		return fila[i];
	}

	/**
	 * Sets the columna.
	 * 
	 * @param columna
	 *            The columna to set
	 */
	private void setColumna(int val, int i) {
		this.columna[i] = val;
	}

	/**
	 * Sets the fila.
	 * 
	 * @param fila
	 *            The fila to set
	 */
	private void setFila(int val, int i) {
		this.fila[i] = val;
	}
}
