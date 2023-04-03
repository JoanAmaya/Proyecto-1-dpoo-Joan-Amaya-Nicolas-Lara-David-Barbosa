package Modulo;
import java.util.ArrayList;

public class ReservaCompleta 
{
	
	private ReservaHabitacion rHabitacion;
	public ReservaCompleta(ReservaHabitacion rHabitacion) {
		this.rHabitacion = rHabitacion;
	}

	public ReservaHabitacion getrHabitacion() {
		return rHabitacion;
	}

	public void setrHabitacion(ReservaHabitacion rHabitacion) {
		this.rHabitacion = rHabitacion;
	}
	
	public double calcularCostoTotalEstadia() {
		int precio_total = 0;
		
		int precioHabitacion = rHabitacion.calcularPrecioHabitacion();
		precio_total += precioHabitacion;
		
		ArrayList<Huesped> huespedes = rHabitacion.getGrupo().getHuespedes();	
		for (Huesped i: huespedes) {
			precio_total += i.getConsumo();
		}
		
		precio_total += rHabitacion.getGrupo().totalguia();
		
		return precio_total;
	}	
   
}