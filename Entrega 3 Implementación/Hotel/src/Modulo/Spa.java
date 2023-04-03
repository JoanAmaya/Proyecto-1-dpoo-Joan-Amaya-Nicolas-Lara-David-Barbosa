package Modulo;


import java.time.LocalTime;
import java.util.ArrayList;

public class Spa implements Servicios{
    private double Tarifa;
    private LocalTime HoraInicio;
    private LocalTime HoraFinal;
    
	
    
	public Spa(double tarifa, LocalTime horaInicio, LocalTime horaFinal) {
		Tarifa = tarifa;
		HoraInicio = horaInicio;
		HoraFinal = horaFinal;
	}


	public double GetTarifa() {
		return Tarifa;
	}

	
	public ArrayList<LocalTime> GetHorarios() {
		ArrayList<LocalTime> Horarios= new ArrayList<>();
		Horarios.add(HoraInicio);
		Horarios.add(HoraFinal);
		
		return Horarios;
	}

	public void setTarifa(double tarifa) {
		Tarifa = tarifa;
	}
	public void setHoraInicio(LocalTime horaInicio) {
		HoraInicio = horaInicio;
	}


	public void setHoraFinal(LocalTime horaFinal) {
		HoraFinal = horaFinal;
	}

	public String getSpend(){
		return "Subtotal Neto: " + Tarifa + "\nTotal: " + Tarifa * 1.19f;
	}
}
