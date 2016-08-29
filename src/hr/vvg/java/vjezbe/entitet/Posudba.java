package hr.vvg.java.vjezbe.entitet;

import java.time.LocalDateTime;

public class Posudba {
	
	private Clan nekiClan;
	
	private Publikacija publikacija;
	
	private LocalDateTime datumPosudbe;

	public Posudba(Clan nekiClan, Publikacija publikacija, LocalDateTime datumPosudbe) {
		this.nekiClan = nekiClan;
		this.publikacija = publikacija;
		this.datumPosudbe = datumPosudbe;
	}

	public Clan getNekiClan() {
		return nekiClan;
	}

	public Publikacija getPublikacija() {
		return publikacija;
	}

	public LocalDateTime getDatumPosudbe() {
		return datumPosudbe;
	}
}
