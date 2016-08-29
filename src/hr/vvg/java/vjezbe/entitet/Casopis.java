package hr.vvg.java.vjezbe.entitet;

import hr.vvg.java.vjezbe.iznimke.NeisplativoObjavljivanjeException;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Casopis extends Publikacija {
	
	private int mjesecIzdavanjaCasopisa;
	
	private final static BigDecimal CIJENA_PO_PRIMJERKU = new BigDecimal("10.00");
	private final static BigDecimal USPOREDNA_CIJENA_CASOPISA = new BigDecimal("1.00");
			

	public Casopis(String naziv, int godinaIzdanja, int brojStranica, String vrstaPublikacije,
			int mjesecIzdavanjaCasopisa) {
		super(naziv, godinaIzdanja, brojStranica, vrstaPublikacije, 
				CIJENA_PO_PRIMJERKU.divide(new BigDecimal(brojStranica),2, RoundingMode.HALF_UP));
		
		this.mjesecIzdavanjaCasopisa = mjesecIzdavanjaCasopisa;
		
		if (this.getCijena().compareTo(USPOREDNA_CIJENA_CASOPISA) <= 0) {
			
			throw new NeisplativoObjavljivanjeException("Pogre�ka! Unijeli ste neisplativ "
					+ "casopis za objavljivanje, molimo ponovite upis!");

		}
		
	}

	public int getMjesecIzdavanjaCasopisa() {
		return mjesecIzdavanjaCasopisa;
	}
	
	@Override
	public boolean equals(Object objekt) {
		
		boolean provjeraCasopisa = false;
		
		if (objekt instanceof Casopis) {
			
			Casopis casopis = (Casopis)objekt;
			
		    if(getNaziv().equals(casopis.getNaziv()) && 
		    	getGodinaIzdanja() == casopis.getGodinaIzdanja() &&
		    	getBrojStranica() == casopis.getBrojStranica() &&
		    	getVrstaPublikacije().equals(casopis.getVrstaPublikacije()) &&
		    	getMjesecIzdavanjaCasopisa() == casopis.getMjesecIzdavanjaCasopisa()){
		    	
		    	provjeraCasopisa = true;
		    }
		    else {
		    	
		    	provjeraCasopisa = false;
		    }
		    
		}
		
		return provjeraCasopisa;
		 
	}

}
