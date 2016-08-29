package hr.vvg.java.vjezbe.glavna;

import hr.vvg.java.vjezbe.entitet.Casopis;
import hr.vvg.java.vjezbe.entitet.Clan;
import hr.vvg.java.vjezbe.entitet.Izdavac;
import hr.vvg.java.vjezbe.entitet.Knjiga;
import hr.vvg.java.vjezbe.entitet.Posudba;
import hr.vvg.java.vjezbe.entitet.Publikacija;
import hr.vvg.java.vjezbe.iznimke.DuplikatPublikacijeException;
import hr.vvg.java.vjezbe.iznimke.NeisplativoObjavljivanjeException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.Arrays;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

public class Glavna {
	
	private static final Logger log = LoggerFactory.getLogger(Glavna.class);
	
	public static void main(String[] args) {
		
		 Scanner ulazni = new Scanner(System.in);
		 
		 Publikacija publikacija[] = new Publikacija[4];
		 
		 
		
		 Clan clan;
		
		 Posudba posudba = null;
		 Boolean dobraKnjiga = false;
		 Boolean dobarCasopis = false;
		 
		
		for (int i = 0; i < 2; i++){
			int k = 1;
			do {
				
				try {
					
					log.info("Unos " + k + ". publikacije");
					publikacija[i] = unesiKnjigu(publikacija, i, ulazni);
					k++;
					dobraKnjiga = true;
				
				}
				catch (NeisplativoObjavljivanjeException ek) {
					
					System.out.println(ek.getMessage());
					log.error("Nije isplativo za objavu", ek);
					dobraKnjiga = false;
				}
				catch (DuplikatPublikacijeException exk) {
					
					System.out.println(exk.getMessage());
					log.error("Dupla publikacija!", exk);
					dobraKnjiga = false;
				}
			
			}while(dobraKnjiga == false);
		} 
		
		for (int i = 2; i < 4; i++){
			int j = 1;
			do {
				
				try {
					
					log.info("Unos " + j + ". publikacije");
					publikacija[i] = unesiCasopis(publikacija, i, ulazni);
					j++;
					dobarCasopis = true;
				
				}
				catch (NeisplativoObjavljivanjeException ec) {
					
					System.out.println(ec.getMessage());
					log.error("Nije isplativo za objavu", ec);
					dobarCasopis = false;
				}
				catch (DuplikatPublikacijeException exc) {
					
					System.out.println(exc.getMessage());
					log.error("Dupla publikacija!", exc);
					dobarCasopis = false;
				}
			
			}while(dobarCasopis == false);
		}
		
		najskupljaPublikacija(publikacija);
		
		najjeftinijaPublikacija(publikacija);
		
		
		clan = unosClana(ulazni);
		log.info("Unos clana", clan);
		
		do {
		
		posudba = odaberiPublikaciju(publikacija, clan, ulazni);
		log.info("Odabrana publikacija: ", posudba.getPublikacija().getNaziv());
		
		} while (posudba == null);
		
		stanjePosudbe(posudba);
		
		ulazni.close();	
		
	}
	
	private static Knjiga unesiKnjigu(Publikacija poljePublikacija[] ,int kolikoKnjiga, 
			Scanner ulazni) throws DuplikatPublikacijeException, NeisplativoObjavljivanjeException {
		
			String vrstaPublikacije = null;
			Publikacija publikacija = null;
			BigDecimal cijenaPoStranici;
			
			System.out.println("Unos " + (kolikoKnjiga + 1) + ". knjige:");
			
			System.out.println("Molimo, unesite naslov knjige >>");
			String naslovKnjige = ulazni.nextLine();
			
			System.out.println("Molimo, unesite jezik >>");
			String jezikKnjige = ulazni.nextLine();
	
			System.out.println("Molimo, unesite godinu izdanja >>");
			int godinaIzdanja = ulazni.nextInt();
			
			System.out.println("Molimo, unesite broj stranica knjige >>");
			int brojStranica = ulazni.nextInt();
			
			do {
			System.out.println("Molimo, unesite vrstu >>");
			System.out.println("1." + Publikacija.getVrstaPublikacijeElektronicka());
			System.out.println("2." + Publikacija.getVrstaPublikacijePapirnata());
			
			vrstaPublikacije = odaberiBrojPublikacije(publikacija, ulazni);
				
			} while (vrstaPublikacije == null);
			
			System.out.println("Molimo, unesite izdavaèa >>");
			String izdavacKnjige = ulazni.nextLine();
			
			System.out.println("Molimo, unesite državu  >>");
			String drzavaKnjige = ulazni.nextLine();
			
			Izdavac izdavac = new Izdavac(izdavacKnjige, drzavaKnjige);
			
			if (izdavac.getDrzavaIzdavaca().equals("Hrvatska")) {
				
				cijenaPoStranici = Knjiga.getCijenaPoStraniciHr();
			}
			else {
				
				cijenaPoStranici = Knjiga.getCijenaPoStraniciForeign();
			}
			
			Boolean dostupnaZaPosudbu = true;
			
			Knjiga knjiga = new Knjiga(naslovKnjige, jezikKnjige, izdavac, godinaIzdanja,
					vrstaPublikacije, cijenaPoStranici, brojStranica, dostupnaZaPosudbu);
	
			for (int j = 0; j < poljePublikacija.length; j++) {
				
				if (knjiga.equals(poljePublikacija[j])) {
						
					throw new DuplikatPublikacijeException("Pogreška! Unijeli ste duplikat knjige,"
							+ " molimo ponovite upis!");
				}
			}
			
			return knjiga;
	}
	
	private static Clan unosClana(Scanner ulazni) {
		
		System.out.println("Unos èlana:");
		
		System.out.println("Molimo, unesite OIB èlana >>");
		String oibClana = ulazni.nextLine();
		
		System.out.println("Molimo, unesite ime èlana >>");
		String imeClana = ulazni.nextLine();
		
		System.out.println("Molimo, unesite prezime èlana >>");
		String prezimeClana = ulazni.nextLine();
		
		return new Clan(imeClana, prezimeClana, oibClana);
	}
	
	private static Casopis unesiCasopis(Publikacija poljePublikacija[] ,int kolikoCasopisa, 
			Scanner ulazni) throws DuplikatPublikacijeException, NeisplativoObjavljivanjeException {
		
		String vrstaPublikacije = null;
		Publikacija publikacija = null;
		
		System.out.println("Unos " + (kolikoCasopisa - 1) + ". casopisa:");
		
		System.out.println("Molimo, unesite naslov casopisa >>");
		String naslovCasopisa = ulazni.nextLine();

		System.out.println("Molimo, unesite godinu izdanja casopisa >>");
		int godinaIzdanjaCasopisa = ulazni.nextInt();
		
		System.out.println("Molimo, unesite broj stranica casopisa >>");
		int brojStranicaCasopisa = ulazni.nextInt();
		
		System.out.println("Molimo, unesite mjesec izdanja casopisa >>");
		int mjesecIzdanjaCasopisa = ulazni.nextInt();
		
		do {
		System.out.println("Molimo, unesite vrstu >>");
		System.out.println("1." + Publikacija.getVrstaPublikacijeElektronicka());
		System.out.println("2." + Publikacija.getVrstaPublikacijePapirnata());
		
		vrstaPublikacije = odaberiBrojPublikacije(publikacija, ulazni);
			
		} while (vrstaPublikacije == null);
		
		Casopis casopis = new Casopis(naslovCasopisa, godinaIzdanjaCasopisa, brojStranicaCasopisa, 
				vrstaPublikacije, mjesecIzdanjaCasopisa);
		
		for (int j = 0; j < poljePublikacija.length; j++) {
			
			if (casopis.equals(poljePublikacija[j])) {
					
				throw new DuplikatPublikacijeException("Pogreška! Unijeli ste duplikat casopisa,"
						+ " molimo ponovite upis!");
			}
		}
		
		return casopis;
	}
	
	private static Posudba odaberiPublikaciju(Publikacija publikacija[], Clan clan, 
			Scanner ulazni) {
		
		System.out.println("Molimo, odaberite publikaciju:");
		
			for (int i = 0; i < publikacija.length; i++) {
		
				System.out.println((i+1) + ")" + publikacija[i].getNaziv());
				
			}
			
			LocalDateTime datumPosudbe = LocalDateTime.now();
			
			
			int brojPublikacije = ulazni.nextInt();
			
			if (brojPublikacije >= 1 && brojPublikacije <= 4) {
				
				return new Posudba(clan, publikacija[brojPublikacije - 1], datumPosudbe);
			}
			else {
				
				System.out.println("Ta knjiga ne postoji!");
				
				return null;
			}
	}
	
	private static void stanjePosudbe(Posudba posudba) {
		
		System.out.println("Stanje posudbe:");
		
		System.out.println("Naziv publikacije: " + posudba.getPublikacija().getNaziv());
		
		System.out.println("Vrsta publikacije: " + posudba.getPublikacija().getVrstaPublikacije());
		
		System.out.println("Broj stranica: " + posudba.getPublikacija().getBrojStranica());
		
		System.out.println("Cijena: " + posudba.getPublikacija().getCijena());
		
		Publikacija publikacija = posudba.getPublikacija();
		
		if (publikacija instanceof Knjiga){
			
			Knjiga knjiga = (Knjiga) publikacija;
		
			System.out.println("Jezik: " + knjiga.getJezikKnjige());
			
			System.out.println("Izdavaè: " + knjiga.getIzdavacKnjige().getNazivIzdavaca());
			
			System.out.println("Država izdavaèa: " + knjiga.getIzdavacKnjige().getDrzavaIzdavaca());
			
			boolean raspolozivost = knjiga.provjeriRaspolozivost();
			String raspolozivostKaoString = new Boolean(raspolozivost).toString();
			
			raspolozivostKaoString = raspolozivostKaoString.replaceAll("false", "NE");
			raspolozivostKaoString = raspolozivostKaoString.replaceAll("true", "DA");
			
			System.out.println("Raspoloživo za posudbu: " + raspolozivostKaoString);
		}
		else {
			
			Casopis casopis = (Casopis) publikacija;
			
			System.out.println("Mjesec izdanja: " + casopis.getMjesecIzdavanjaCasopisa());
		}
		
		System.out.println("Podaci korisnika: ");
		
		System.out.println("Prezime: " + posudba.getNekiClan().getPrezimeClana());
		
		System.out.println("Ime: " + posudba.getNekiClan().getImeClana());
		
		System.out.println("OIB: " + posudba.getNekiClan().getOibClana());
		
		DateTimeFormatter formaterDatuma = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss");
		
		System.out.println("Datum posudbe: " + 
							posudba.getDatumPosudbe().format(formaterDatuma));
			
	}
	
	private static String odaberiBrojPublikacije (Publikacija publikacija, Scanner ulazni) {
		
		int brojVrstePublikacije = ulazni.nextInt();
		ulazni.nextLine();
		
		
		if (brojVrstePublikacije == 1) {
			
			return Publikacija.getVrstaPublikacijePapirnata();
		}
		else if (brojVrstePublikacije == 2){
			
			return Publikacija.getVrstaPublikacijePapirnata();
			
		}
		else {
			
			System.out.println("Ta vrsta publikacije ne postoji!");
			
			return null;
		}
	}
	
	private static Publikacija najmanjaPublikacija(Publikacija publikacija[]) {
		
		Arrays.sort(publikacija, (Publikacija p1, Publikacija p2) -> 
		(p1.getCijena().compareTo(p2.getCijena())));
		
		return publikacija[0];
		}

	private static Publikacija najvecaPublikacija(Publikacija publikacija[]) {
	
	Arrays.sort(publikacija, (Publikacija p1, Publikacija p2) -> 
	(p1.getCijena().compareTo(p2.getCijena())));
	
	return publikacija[3];
	}
	
	private static void najjeftinijaPublikacija(Publikacija publikacija[]) {
		
		System.out.println("Najjeftinija publikacija:");
		
		Publikacija najjeftinijaPublikacija = najmanjaPublikacija(publikacija);
		
		System.out.println("Naziv publikacije: " + najjeftinijaPublikacija.getNaziv());
		
		System.out.println("Vrsta publikacije: " + najjeftinijaPublikacija.getVrstaPublikacije());
		
		System.out.println("Broj stranica: " + najjeftinijaPublikacija.getBrojStranica());
		
		System.out.println("Cijena: " + najjeftinijaPublikacija.getCijena());
		
		if (najjeftinijaPublikacija instanceof Knjiga){
			
			Knjiga knjiga = (Knjiga) najjeftinijaPublikacija;
		
			System.out.println("Jezik: " + knjiga.getJezikKnjige());
			
			System.out.println("Izdavaè: " + knjiga.getIzdavacKnjige().getNazivIzdavaca());
			
			System.out.println("Država izdavaèa: " + knjiga.getIzdavacKnjige().getDrzavaIzdavaca());
			
			boolean raspolozivost = knjiga.provjeriRaspolozivost();
			String raspolozivostKaoString = new Boolean(raspolozivost).toString();
			
			raspolozivostKaoString = raspolozivostKaoString.replaceAll("false", "NE");
			raspolozivostKaoString = raspolozivostKaoString.replaceAll("true", "DA");
			
			System.out.println("Raspoloživo za posudbu: " + raspolozivostKaoString);
		}
		
		else {
			
			Casopis casopis = (Casopis) najjeftinijaPublikacija;
			
			System.out.println("Mjesec izdanja: " + casopis.getMjesecIzdavanjaCasopisa());
		}
	}
	
	private static void najskupljaPublikacija(Publikacija publikacija[]) {
		
		System.out.println("Najskuplja publikacija:");
		
		Publikacija najskupljaPublikacija = najvecaPublikacija(publikacija);
		
		System.out.println("Naziv publikacije: " + najskupljaPublikacija.getNaziv());
		
		System.out.println("Vrsta publikacije: " + najskupljaPublikacija.getVrstaPublikacije());
		
		System.out.println("Broj stranica: " + najskupljaPublikacija.getBrojStranica());
		
		System.out.println("Cijena: " + najskupljaPublikacija.getCijena());
		
		if (najskupljaPublikacija instanceof Knjiga){
			
			Knjiga knjiga = (Knjiga) najskupljaPublikacija;
		
			System.out.println("Jezik: " + knjiga.getJezikKnjige());
			
			System.out.println("Izdavaè: " + knjiga.getIzdavacKnjige().getNazivIzdavaca());
			
			System.out.println("Država izdavaèa: " + knjiga.getIzdavacKnjige().getDrzavaIzdavaca());
			
			boolean raspolozivost = knjiga.provjeriRaspolozivost();
			String raspolozivostKaoString = new Boolean(raspolozivost).toString();
			
			raspolozivostKaoString = raspolozivostKaoString.replaceAll("false", "NE");
			raspolozivostKaoString = raspolozivostKaoString.replaceAll("true", "DA");
			
			System.out.println("Raspoloživo za posudbu: " + raspolozivostKaoString);
		}
		
		else {
			
			Casopis casopis = (Casopis) najskupljaPublikacija;
			
			System.out.println("Mjesec izdanja: " + casopis.getMjesecIzdavanjaCasopisa());
		}
	}
	
//	public static void provjeriCijenuKnjige(Publikacija publikacija) throws
//	NeisplativoObjavljivanjeException {
//		
//		BigDecimal cijenaPublikacije = publikacija.getCijena();
//		
//		BigDecimal usporednaCijenaKnjiga = new BigDecimal("100.00");
//		
//		int usporedba = cijenaPublikacije.compareTo(usporednaCijenaKnjiga);
//		
//		if (usporedba == (-1)) {
//			
//			throw new NeisplativoObjavljivanjeException("Pogreška! Unijeli ste neisplativu "
//					+ "knjigu za objavljivanje, molimo ponovite upis!");
//		}
//		else if (usporedba == 0) {
//			
//			throw new NeisplativoObjavljivanjeException("Pogreška! Unijeli ste neisplativu "
//					+ "knjigu za objavljivanje, molimo ponovite upis!");
//			
//		}
//	}
//	
//	public static void provjeriCijenuCasopisa(Publikacija publikacija) throws
//	NeisplativoObjavljivanjeException {
//		
//		BigDecimal cijenaPublikacije = publikacija.getCijena();
//		
//		BigDecimal usporednaCijenaCasopis = new BigDecimal("1.00");
//		
//		int usporedba2 = cijenaPublikacije.compareTo(usporednaCijenaCasopis);
//		
//		if (usporedba2 == (-1)) {
//			
//			throw new NeisplativoObjavljivanjeException("Pogreška! Unijeli ste neisplativu "
//					+ "knjigu za objavljivanje, molimo ponovite upis!");
//		}
//		else if (usporedba2 == 0) {
//			
//			throw new NeisplativoObjavljivanjeException("Pogreška! Unijeli ste neisplativu "
//					+ "knjigu za objavljivanje, molimo ponovite upis!");
//			
//		}
//	}
//	
//	public static void provjeriDaLiJeKnjigaDuplikat(Publikacija publikacija[], int j) throws 
//	DuplikatPublikacijeException {
//		
//			
//		if (j == 0) {
//			
//			return;
//		}
//			
//		else if (publikacija[j].equals(publikacija[j-1])) {
//				
//			throw new DuplikatPublikacijeException("Pogreška! Unijeli ste duplikat knjige,"
//					+ " molimo ponovite upis!");
//			}
//	}
//	
//	public static void provjeriDaLiJeCasopisDuplikat(Publikacija publikacija[]) throws 
//	DuplikatPublikacijeException {
//		
//		for (int j = 0; j < publikacija.length; j++) {
//			
//			if (j == 0) {
//				
//				continue;
//			}
//			
//			else if (publikacija[j].equals(publikacija[j+1])) {
//				
//				throw new DuplikatPublikacijeException("Pogreška! Unijeli ste duplikat casopisa,"
//						+ " molimo ponovite upis!");
//			}
//		
//		}
//		
//	}
}
	

