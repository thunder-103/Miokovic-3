package hr.vvg.java.vjezbe.entitet;

import java.math.BigDecimal;
import java.math.RoundingMode;

public interface ZaProdaju {
	
	default public BigDecimal cijenaPublikacije (int brojStranica, String vrstaPublikacije, 
			BigDecimal cijenaPoStranici) {
		
		BigDecimal cijena = cijenaPoStranici.multiply(new BigDecimal(brojStranica));
		
		BigDecimal cijenaUmanjena = cijena.divide(new BigDecimal("1.1"), 
				2, RoundingMode.HALF_UP);
		
		if (vrstaPublikacije.equals("VRSTA_PUBLIKACIJE_PAPIRNATA")) {
			
			return cijena;
		}
		else {
			
			return cijenaUmanjena;
		}
		
	}

}
