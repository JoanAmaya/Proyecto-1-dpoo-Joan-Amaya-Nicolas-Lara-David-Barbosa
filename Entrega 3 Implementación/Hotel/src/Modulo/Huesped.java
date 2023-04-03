package Modulo;

public class Huesped {

	private String Nombre;
	private int Edad;
	private long Documento;
	private String Correo;
	private Consumo consumohuesped;
	
	public Huesped(String nombre, int edad, long documento, String correo) {
		Nombre = nombre;
		Edad = edad;
		Documento = documento;
		Correo = correo;
		consumohuesped = new Consumo();
	}
	
	public String getNombre() {
		return Nombre;
	}
	public int getEdad() {
		return Edad;
	}
	public long getDocumento() {
		return Documento;
	}
	public String getCorreo() {
		return Correo;
	}
	public double getConsumo()
	{
		return consumohuesped.GetTotal();
	}

	public Consumo getConsumoHuesped()
	{
		return consumohuesped;
	}
	
}
