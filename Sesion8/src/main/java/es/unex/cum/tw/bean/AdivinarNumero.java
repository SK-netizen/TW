package es.unex.cum.tw.bean;

import java.util.Random;

public class AdivinarNumero {

	int solucion;
	boolean acierto;
	String ayuda;
	int intentos;

	public AdivinarNumero() {
		resetear();
	}
	
	public void resetear(){
		solucion= Math.abs(new Random().nextInt() % 100) + 1;
		acierto = false;
		intentos = 0;		
	}

	public void setProbar(String valor) {
		intentos++;
		int g;
		try {
			g = Integer.parseInt(valor);
		} catch (NumberFormatException e) {
			g = -1;
		}
		if (g == solucion) {
			acierto = true;
		} else if (g == -1) {
			ayuda = "Debe ser un número";
		} else if (g < solucion) {
			ayuda = "Es mayor";
		} else if (g > solucion) {
			ayuda = "Es menor";
		}
	}

	public boolean getAcierto() {
		return acierto;
	}

	public String getAyuda() {
		return "" + ayuda;
	}

	public int getIntentos() {
		return intentos;
	}
}
