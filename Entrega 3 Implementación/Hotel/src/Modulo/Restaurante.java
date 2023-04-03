package Modulo;
import java.time.LocalTime;
import java.util.ArrayList;

public class Restaurante implements Servicios{

	private LocalTime HoraInicio;
	private LocalTime HoraFinal;
	private ArrayList<Plato> Platos;
	private ArrayList<Bebida> Bebidas;
	private ArrayList<Plato> PedidosP;
	private ArrayList<Bebida> PedidosB;
	
	
	
	
	public Restaurante(LocalTime HoraInicio, LocalTime HoraFinal) {
		this.HoraInicio =HoraInicio;
		this.HoraFinal = HoraFinal;
		PedidosP=new ArrayList<>();
		PedidosB=new ArrayList<>();
		Platos=new ArrayList<>();
		Bebidas=new ArrayList<>();
	}
	
	
	public ArrayList<Plato> getPlatos() {
		return Platos;
	}


	public ArrayList<Bebida> getBebidas() {
		return Bebidas;
	}


	public void anadirplatos(Plato nuevoplato) {
		Platos.add(nuevoplato);
		
	}
	
	public void anadirbebias(Bebida nuevabebida) {
		Bebidas.add(nuevabebida);
		
	}
	
	public void anadirpedidosplatos(String pedidoplato, LocalTime hora, String Lugar)
	{
		for (Plato i: Platos)
		{
			if (i.getNombreItem().equals(pedidoplato) && i.Disponible(hora) && i.LugarDisponible(Lugar))
			{
				PedidosP.add(i);
			}
		}
	}
	public void anadirpedidosbebida(String pedidobebida, LocalTime hora, String Lugar)
	{
		
		for (Bebida i: Bebidas)
		{
			if (i.getNombreItem().equals(pedidobebida) && i.Disponible(hora) && i.LugarDisponible(Lugar))
			{
				PedidosB.add(i);
			}
		}
	}

	public double GetTarifa() {
		double Tarifa=0;
		for (Bebida i: PedidosB)
		{
			Tarifa+=i.getPrecio();
			
		}
		for (Plato i: PedidosP)
		{
			Tarifa+=i.getPrecio();
			
		}
		
		
		
		return Tarifa;
	}

	@Override
	public ArrayList<LocalTime> GetHorarios() {
		ArrayList<LocalTime> Horarios= new ArrayList<>();
		Horarios.add(HoraInicio);
		Horarios.add(HoraFinal);
		
		return Horarios;
	}
	public void setHoraInicio(LocalTime horaInicio) {
		HoraInicio = horaInicio;
	}


	public void setHoraFinal(LocalTime horaFinal) {
		HoraFinal = horaFinal;
	}

	public String getSpend(){
		String bill = "=================================================\n";
		for (Plato plato: PedidosP){
			bill = bill.concat(plato.getNombreItem() + ": " + plato.getPrecio() + "\n");
		}
		for (Bebida bebida: PedidosB){
			bill = bill.concat(bebida.getNombreItem() + ": " + bebida.getPrecio() + "\n");
		}
		bill = bill.concat("=================================================\n");
		bill = bill.concat("Subtotal Neto: " + GetTarifa() + "\n");
		bill = bill.concat("Total: " + GetTarifa() * 1.19);
		bill = bill.concat("\n=================================================");
		return bill;
	}
}
