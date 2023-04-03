package Modulo;

import java.util.ArrayList;
import java.util.Random;

public class Grupo {
	
	private ArrayList<Huesped> huespedes;
	private Huesped HuespedPrincipal;
	private int id_grupo;
	private ArrayList<GuiaTuristico> guiaturistico;
	public Grupo(ArrayList<Huesped> huespedes, Huesped huespedPrincipal) {
		this.huespedes = huespedes;
		Random random= new Random();
		id_grupo=random.nextInt(100000000);
		HuespedPrincipal = huespedPrincipal;
		guiaturistico = new ArrayList<>();

	}

	public int totalguia() {
		int total = 0;
		for (GuiaTuristico i: guiaturistico)
		{
			total+= i.getTarifa();
		}
		return total;
	}
	public void anadirguia(GuiaTuristico guia)
	{
		guiaturistico.add(guia);
	}

	public void anadirhuesped(Huesped nuevohuesped)
	{
		huespedes.add(nuevohuesped);
	}
	public ArrayList<Huesped> getHuespedes() {
		return huespedes;
	}
	public Huesped getHuespedPrincipal() {
		return HuespedPrincipal;
	}
	public int getId_grupo() {
		return id_grupo;
	}
	public int totaladultosgrupo()
	{
		int j = 0;
		for (Huesped i: huespedes)
		{
			if (i.getEdad()>15)
			{
				j+=1;
			}

		}

		return j;
	}
	public int totalninosgrupo()
	{
		int j = 0;
		for (Huesped i: huespedes)
		{
			if (i.getEdad()<=15)
			{
				j+=1;
			}

		}

		return j;
	}

}