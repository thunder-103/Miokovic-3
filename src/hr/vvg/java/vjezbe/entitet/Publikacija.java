package hr.vvg.java.vjezbe.entitet;

import java.math.BigDecimal;

public abstract class Publikacija implements ZaProdaju {
	
	private BigDecimal cijena;
	private int godinaIzdanja;
	private int brojStranica;
	private String vrstaPublikacije;
	private String naziv;
	
	private static final String VRSTA_PUBLIKACIJE_ELEKTRONICKA = "Vrsta publikacije elektronièka";
	private static final String VRSTA_PUBLIKACIJE_PAPIRNATA = "Vrsta publikacije papirnata";
	
	
	public Publikacija(String naziv, int godinaIzdanja, int brojStranica, 
			String vrstaPublikacije, BigDecimal cijenaPoStranici) {
		this.naziv = naziv;
		this.godinaIzdanja = godinaIzdanja;
		this.brojStranica = brojStranica;
		this.vrstaPublikacije = vrstaPublikacije;
		this.cijena = cijenaPublikacije(brojStranica, vrstaPublikacije, cijenaPoStranici);
	}

	
	public BigDecimal getCijena() {
		return cijena;
	}


	public String getNaziv() {
		return naziv;
	}

	public int getGodinaIzdanja() {
		return godinaIzdanja;
	}


	public int getBrojStranica() {
		return brojStranica;
	}


	public String getVrstaPublikacije() {
		return vrstaPublikacije;
	}


	public static String getVrstaPublikacijeElektronicka() {
		return VRSTA_PUBLIKACIJE_ELEKTRONICKA;
	}


	public static String getVrstaPublikacijePapirnata() {
		return VRSTA_PUBLIKACIJE_PAPIRNATA;
	}

}
