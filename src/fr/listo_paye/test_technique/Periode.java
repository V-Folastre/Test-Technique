package fr.listo_paye.test_technique;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;

public class Periode {
	
	private LocalDate debut;
	private LocalDate fin;
	
	public Periode(LocalDate debut, LocalDate fin) {
		super();
		this.debut = debut;
		this.fin = fin;
	}

	
	
	public String controle() {
		
		/* D�clarations et initialisations � null des variables qui seront utilis� selon la 
		 * dur�e du cong�.*/
		LocalDate finPremPer = null;
		LocalDate debDernPer = null;
		int nbMois = 0;
		LocalDate[] tabPerInter = null;
        boolean debutPeriode = true;
		
		// D�coupage si le cong� est � cheval sur plusieurs p�riodes mensuelles.
		if (this.debut.getMonth() != this.fin.getMonth() | this.debut.getYear() != this.fin.getYear()) {
	        
	        /* Extraction des param�tres du d�but de la premi�re p�riode ainsi que la fin 
	         * de la derni�re p�riode de cong�s.*/
	        int anneeDebPP = this.debut.getYear();       
	        int moisDebPP = this.debut.getMonthValue();
	        int jourDebPP = this.debut.getDayOfMonth();
	        int anneeFinDP = this.fin.getYear();       
	        int moisFinDP = this.fin.getMonthValue();
			
	        /* Conversion de la date du d�but de la premi�re p�riode de cong�s (LocalDate) 
	         * en GregorianCalendar.*/
	        Calendar dPPGC = new GregorianCalendar(this.debut.getYear(), this.debut.getMonthValue()-1, this.debut.getDayOfMonth());
	        
	        // R�cup�ration du nombre total de jour du premier mois.
	        int nbTotalJourPremMois = dPPGC.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	               
	        // Nombre de jour de la premi�re p�riode (sans compter le premier jour de cong�).
	        int nbJourPremPer = (nbTotalJourPremMois - jourDebPP);
	        
			// D�claration de la date de fin de la premi�re p�riode de cong�s.
	        finPremPer = LocalDate.of(anneeDebPP, moisDebPP, jourDebPP + nbJourPremPer);
	        
	        // D�claration de la date du d�but de la derni�re p�riode de cong�s
	        debDernPer = LocalDate.of(anneeFinDP, moisFinDP, 1);
	        
			/* Cr�ation de nouvelles d�clarations pour le d�but et la fin du cong� 
			 * afin de v�rifier si la dur�e est � cheval sur plus de 2 p�riodes mensuelles.*/        
	        LocalDate debutConge = LocalDate.of(anneeDebPP, moisDebPP, 1);
	        LocalDate finConge = LocalDate.of(anneeFinDP, moisFinDP, 20);
	
	        // Calcul du nombre de mois o� le cong� est � cheval.
	        long l = debutConge.until(finConge, ChronoUnit.MONTHS)+1;
	        nbMois = (int)l;
	        
	        /* Cr�ation de la ou des p�riode(s) de cong�s interm�diaire(s) si 
	         * la dur�e du cong� est � cheval sur plus de 2 p�riodes mensuelles.*/
	        if (nbMois > 2) {
	        	
	    		/* D�claration du nombre de p�riode(s) de cong�s interm�diaire(s) 
	    		 * en soustrayant la premi�re et derni�re p�riode.*/
	        	int nbPerInter = nbMois - 2;
	        	
	        	/* Cr�ation d'un tableau qui contiendra la date de debut et de fin 
	        	 * de chaque p�riode interm�diaire.*/
		        tabPerInter = new LocalDate [nbPerInter * 2];
		        
		        // D�clarations reprenant l'ann�e et le mois de la premi�re p�riode.
		        int anneePeriodeinter = anneeDebPP;
    			int moisPeriodeinter = moisDebPP;
	        	
    			/* Cr�ation d'une boucle permettant d'incr�menter la date de debut et de fin 
    			 * de chaque p�riode interm�diaire.*/
	        	for (int i = 0; i < tabPerInter.length; i++) {        		
	        		
	        		/* Incr�mentation de la date du d�but si celle-ci en est bien le d�but de 
	        		 * la p�riode interm�diaire.*/
	        		if (debutPeriode == true) {
	        			
	        			// Passage de la p�riode interm�diaire � la p�riode mensuelle suivante.
	        			moisPeriodeinter++;
	        			
	        			/* R�initialisation du mois de la p�riode interm�diaire au mois de janvier 
	        			 * et augmentation de l'ann�e de la p�riode d'un an si celle-ci d�passe
	        			 * le mois de d�cembre.*/
	        			if (moisPeriodeinter > 12) {        				
	        				moisPeriodeinter = 1;	        				
	        				anneePeriodeinter++;	        				
	        			}
	        			
	        			// Incr�mentation de la date du d�but de la p�riode inerm�diaire.
	        			tabPerInter[i] = LocalDate.of(anneePeriodeinter, moisPeriodeinter, 1);
	        			
	    		        /* Modification de la variable debut p�riode de vrai � faux pour que la 
	    		         * prochaine date soit la fin de la p�riode.*/
	        			debutPeriode = false;
	        			
		        	// Incr�mentation de la date de fin si celle-ci n'est pas le d�but de la p�riode interm�diaire.
	        		} else {
	        			
	        	        // Conversion de la p�riode interm�diaire (LocalDate) en GregorianCalendar.
	        	        Calendar pIGC = new GregorianCalendar(anneePeriodeinter, moisPeriodeinter-1, 1);
	        	        
	        	        // R�cup�ration du nombre total de jour de cette p�riode mensuel.
	        	        int nbTotalJourPerInter = pIGC.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	        	        
	        			// Incr�mentation de la date de fin de la p�riode inerm�diaire.
	        			tabPerInter[i] = LocalDate.of(anneePeriodeinter, moisPeriodeinter, nbTotalJourPerInter);
	        			
	    		        /* Modification de la variable debut p�riode de faux � vrai pour que la 
	    		         * prochaine date soit le d�but de la prochaine p�riode (si il y en a une).*/
	        			debutPeriode = true;
	        		
	        		}
        			
	        	}
      	
	        }
			
		}
		return "" + this.debut + ", " + finPremPer + ", " + Arrays.toString(tabPerInter) + ", " + debDernPer + ", " + this.fin;	
	}
}
