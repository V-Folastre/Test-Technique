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
		
		/* Déclarations et initialisations à null des variables qui seront utilisé selon la 
		 * durée du congé.*/
		LocalDate finPremPer = null;
		LocalDate debDernPer = null;
		int nbMois = 0;
		LocalDate[] tabPerInter = null;
        boolean debutPeriode = true;
		
		// Découpage si le congé est à cheval sur plusieurs périodes mensuelles.
		if (this.debut.getMonth() != this.fin.getMonth() | this.debut.getYear() != this.fin.getYear()) {
	        
	        /* Extraction des paramètres du début de la première période ainsi que la fin 
	         * de la dernière période de congés.*/
	        int anneeDebPP = this.debut.getYear();       
	        int moisDebPP = this.debut.getMonthValue();
	        int jourDebPP = this.debut.getDayOfMonth();
	        int anneeFinDP = this.fin.getYear();       
	        int moisFinDP = this.fin.getMonthValue();
			
	        /* Conversion de la date du début de la première période de congés (LocalDate) 
	         * en GregorianCalendar.*/
	        Calendar dPPGC = new GregorianCalendar(this.debut.getYear(), this.debut.getMonthValue()-1, this.debut.getDayOfMonth());
	        
	        // Récupération du nombre total de jour du premier mois.
	        int nbTotalJourPremMois = dPPGC.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	               
	        // Nombre de jour de la première période (sans compter le premier jour de congé).
	        int nbJourPremPer = (nbTotalJourPremMois - jourDebPP);
	        
			// Déclaration de la date de fin de la première période de congés.
	        finPremPer = LocalDate.of(anneeDebPP, moisDebPP, jourDebPP + nbJourPremPer);
	        
	        // Déclaration de la date du début de la dernière période de congés
	        debDernPer = LocalDate.of(anneeFinDP, moisFinDP, 1);
	        
			/* Création de nouvelles déclarations pour le début et la fin du congé 
			 * afin de vérifier si la durée est à cheval sur plus de 2 périodes mensuelles.*/        
	        LocalDate debutConge = LocalDate.of(anneeDebPP, moisDebPP, 1);
	        LocalDate finConge = LocalDate.of(anneeFinDP, moisFinDP, 20);
	
	        // Calcul du nombre de mois où le congé est à cheval.
	        long l = debutConge.until(finConge, ChronoUnit.MONTHS)+1;
	        nbMois = (int)l;
	        
	        /* Création de la ou des période(s) de congés intermédiaire(s) si 
	         * la durée du congé est à cheval sur plus de 2 périodes mensuelles.*/
	        if (nbMois > 2) {
	        	
	    		/* Déclaration du nombre de période(s) de congés intermédiaire(s) 
	    		 * en soustrayant la première et dernière période.*/
	        	int nbPerInter = nbMois - 2;
	        	
	        	/* Création d'un tableau qui contiendra la date de debut et de fin 
	        	 * de chaque période intermédiaire.*/
		        tabPerInter = new LocalDate [nbPerInter * 2];
		        
		        // Déclarations reprenant l'année et le mois de la première période.
		        int anneePeriodeinter = anneeDebPP;
    			int moisPeriodeinter = moisDebPP;
	        	
    			/* Création d'une boucle permettant d'incrémenter la date de debut et de fin 
    			 * de chaque période intermédiaire.*/
	        	for (int i = 0; i < tabPerInter.length; i++) {        		
	        		
	        		/* Incrémentation de la date du début si celle-ci en est bien le début de 
	        		 * la période intermédiaire.*/
	        		if (debutPeriode == true) {
	        			
	        			// Passage de la période intermédiaire à la période mensuelle suivante.
	        			moisPeriodeinter++;
	        			
	        			/* Réinitialisation du mois de la période intermédiaire au mois de janvier 
	        			 * et augmentation de l'année de la période d'un an si celle-ci dépasse
	        			 * le mois de décembre.*/
	        			if (moisPeriodeinter > 12) {        				
	        				moisPeriodeinter = 1;	        				
	        				anneePeriodeinter++;	        				
	        			}
	        			
	        			// Incrémentation de la date du début de la période inermédiaire.
	        			tabPerInter[i] = LocalDate.of(anneePeriodeinter, moisPeriodeinter, 1);
	        			
	    		        /* Modification de la variable debut période de vrai à faux pour que la 
	    		         * prochaine date soit la fin de la période.*/
	        			debutPeriode = false;
	        			
		        	// Incrémentation de la date de fin si celle-ci n'est pas le début de la période intermédiaire.
	        		} else {
	        			
	        	        // Conversion de la période intermédiaire (LocalDate) en GregorianCalendar.
	        	        Calendar pIGC = new GregorianCalendar(anneePeriodeinter, moisPeriodeinter-1, 1);
	        	        
	        	        // Récupération du nombre total de jour de cette période mensuel.
	        	        int nbTotalJourPerInter = pIGC.getActualMaximum(GregorianCalendar.DAY_OF_MONTH);
	        	        
	        			// Incrémentation de la date de fin de la période inermédiaire.
	        			tabPerInter[i] = LocalDate.of(anneePeriodeinter, moisPeriodeinter, nbTotalJourPerInter);
	        			
	    		        /* Modification de la variable debut période de faux à vrai pour que la 
	    		         * prochaine date soit le début de la prochaine période (si il y en a une).*/
	        			debutPeriode = true;
	        		
	        		}
        			
	        	}
      	
	        }
			
		}
		return "" + this.debut + ", " + finPremPer + ", " + Arrays.toString(tabPerInter) + ", " + debDernPer + ", " + this.fin;	
	}
}
