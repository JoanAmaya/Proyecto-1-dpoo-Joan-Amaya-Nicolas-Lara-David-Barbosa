package Modulo;

import java.util.ArrayList;

public class Consumo {
	private ArrayList<Servicios> servicio;

	public Consumo(){
		servicio = new ArrayList<>();
	}
	private double TotalIva() {
		double total=0;
		for (Servicios i: servicio)
		{
			total+=i.GetTarifa();
		}
		double totalIva = total*0.19;
		return totalIva;
	}
	
	private double Total() {
		double total=0;
		for (Servicios i: servicio)
		{
			total+=i.GetTarifa();
		}
		
		return total;
	}
	
	public double GetTotal() {
		double total = Total()+TotalIva();
		return total;
	}

	public void anadirservicios(Servicios service) {
		servicio.add(service);
	}

}
