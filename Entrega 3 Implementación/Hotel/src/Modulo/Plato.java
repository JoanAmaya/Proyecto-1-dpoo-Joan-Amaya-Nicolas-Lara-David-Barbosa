package Modulo;

import java.time.LocalTime;
import java.util.ArrayList;

public class Plato {
	private String NombreItem;
	private int Precio;
	private LocalTime HoraInicio;
	private LocalTime HoraFinal;
	private ArrayList<String> Lugares;
	public Plato(String nombreItem, int precio, LocalTime horaInicio, LocalTime horaFinal) {
		NombreItem = nombreItem;
		Precio = precio;
		HoraInicio = horaInicio;
		HoraFinal = horaFinal;
		Lugares = new ArrayList<String>();
		
	}
	public String getNombreItem() {
		return NombreItem;
	}
	public int getPrecio() {
		return Precio;
	}
	//setter plato
	
	public LocalTime getHoraInicio() {
		return HoraInicio;
	}
	public void setPrecio(int precio) {
		Precio = precio;
	}
	public LocalTime getHoraFinal() {
		return HoraFinal;
	}
	public ArrayList<String> getLugares() {
		return Lugares;
	}
	public void anadirlugares(String nuevolugar)
	{
		Lugares.add(nuevolugar);
	}
	public boolean Disponible(LocalTime HoraActual) {
		
		boolean r=false;
		        if (HoraActual.isBefore(HoraFinal) && HoraActual.isAfter(HoraInicio))
				{
					 r=true;
				}

		return r;
	}
	public boolean LugarDisponible (String lugar)
	{
		boolean r=false;
		for (String i: Lugares)
		{
			if (i.equals(lugar)){
				r=true;
			}
		}
		return r;
		
	}
	
}
