package Modulo;
import java.util.HashMap;

public class Cama
{
	/* El alto y ancho de la cama estará dado en centímetros */
	
	private int alto;
	private int ancho;
	
		
	public int getAlto() {
		return alto;
	}

	public void setAlto(int alto) {
		this.alto = alto;
	}

	public int getAncho() {
		return ancho;
	}

	public void setAncho(int ancho) {
		this.ancho = ancho;
	}

	
	public Cama(int alto, int ancho) {
		this.alto = alto;
		this.ancho = ancho;
	}

	public HashMap<String, Integer> totalPersonas(int alto, int ancho)
	{
		int adultos = 0;
		int infantes = 0;
		HashMap<String, Integer> gente = new HashMap<String, Integer>();
		
		if (alto < 190 && ancho < 90)
		{
			infantes ++;
		}
		
		else if (alto > 190 && ancho > 90 && ancho < 135)
		{
			infantes += 2;
			adultos ++;
		}
		
		else;
		{
			infantes += 4;
			adultos += 2;
		}
		
		gente.put("adultos", adultos);
		gente.put("infantes", infantes);
		
	return gente;	
	
	}			
	
}
