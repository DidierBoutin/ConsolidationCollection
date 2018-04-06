package consolideCollections ;


 


 import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import consolideCollections.CommonWord;

//*******************CONSIGNES**************************************
//Écrire un programme qui, pour un fichier, affiche le nombre d’occurrences de chaque mot.
//%
//Bonus
//
//        prendre le nom de fichier / chemin en argument
//        afficher les résultats par ordre alphabétique des mots
//        afficher les résultats par ordre décroissant des nombres d’occurrences.
//******************************************************************

// WordNb utilise des methodes de CommonWord (pas trouvé autrement que de passer par extends... )
public class WordNb extends CommonWord {
	
	private final static String nameRootFic1 = "ethics.txt";
	//**to save System.nanoTime with a commentary
	private static ArrayList <Chrono> times = new ArrayList<Chrono>();
	//**to save line of file
	private static ArrayList<ArrayList<String>> saveLineFile = new ArrayList<ArrayList<String>>() ;

//================================================================================================
//
//							MAIN
//
//================================================================================================
	public static void main(String[] args) throws IOException {

		//***** Arguments  Explain
		//*   empty  	or Arg[0] <> 1 and 2		==> 1 files on project root, ethics 
		//*   Arg[0]  = 1						==> 1 pathfiles in Arg[1]  
		//*	  Arg[0]  = 2						==> testbench	with rootfiles and Arg[1] = nb loop

		//*** get names files*****************************	
		String nameFil1 = "";
		if ((args.length == 0) || (!args[0].equals("1"))){
			nameFil1 = nameRootFic1;}
		else
			if (args[0].equals("1")){
				nameFil1 = args[1] ;}


		//********* choice treatment ****************************	
		if ((args.length != 0) && (args[0].equals("2"))) {
			int nbLoop;
			try
			{
				// checking valid integer using parseInt() method
				nbLoop = (Integer.parseInt(args[1])== 0 ? 1 : Integer.parseInt(args[1]));
			} 
			catch (NumberFormatException e) 
			{  System.out.println("argument nbLoop invalide, forcé à 1") ;
				nbLoop = 1;
			}
			catch (ArrayIndexOutOfBoundsException e) 
			{  System.out.println("argument nbLoop non indiqué, forcé à 1") ;
				nbLoop = 1;
			};
			{testbench2(nameFil1,nbLoop);}}
		else {
			try {	
				
				//*Lecture fichier et svde dans une liste 
				List<String> listWord1 = readWords(nameFil1);
				
				//*Comptage des mots par la methode "remove" 
				// savgde ds une map (clé = un mot; valeur : nb occurence)
				TreeMap<String,Integer> m = countEqualItemWithRemove(listWord1);
				printWithForEachMap(m);
			
				//*tri sur la valeur de la map
				Map<String,Integer> sortedMap = sortByValue(m);
				printWithForEachMap(sortedMap);
				}
			
			catch (IOException e) {
				System.out.println("Error file " + e);
			}}
	}
		 

//**** Count how times an item is in list l and save it in TreeMap 
//*****  TROP LONG
	public static TreeMap<String,Integer> countEqualItem(List<String> l) {  

		//init return TreeMap
		TreeMap<String,Integer> m= new TreeMap<>();
		int nbItemTot = 0;
		//Master Loop on each item of l*
		for(String s : l) {
			//if MasterLoop never get current item, let's loop another time to count
			//how time it appears		
			if (!m.containsKey(s) ) {
				//Loop on l only from current item until the last one	
				List<String> temp = l;	 
				temp.subList(l.indexOf(s),l.lastIndexOf(s));
				int ind = 0;
				for(String w : temp)
				{if (s.equals(w)) {ind++;}
				}
				m.put(s, ind);
				nbItemTot = nbItemTot + ind;
			}
		}	
		System.out.println("countEqualItem, nb word read : " + nbItemTot);
		return m;
	}
	
	
	private static void printWithForEachMap (Map<String,Integer> l) {
 		l.forEach( (key , value) -> 
			{ System.out.println( key + " : 			" + value + " times" ); 
			}	);
 		System.out.println("nb items : " + l.size());
	}
	
	
	//**** Count how times an item is in list l and save it in TreeMap , with remove method	
	//*** Rapide
	public static TreeMap<String,Integer> countEqualItemWithRemove(List<String> l) {  

		//init return TreeMap
		TreeMap<String,Integer> m= new TreeMap<>();
		int nbItemTot = 0;

		//Master Loop on each item of l
		while (l.size() != 0) {
			String s = l.get(0);
			int ind = 0;
			for(String w : l)
			{if (s.equals(w)) 
				{ind++;}
			}
			m.put(s, ind);
			nbItemTot = nbItemTot + ind;

			//removeAll mehode needs an ArrayList as argument, we create one which contains only current word
			ArrayList<String> h = new ArrayList<String>() ;
			h.add(s);
			l.removeAll(h);
		}	
		System.out.println("countEqualItemWithRemove, nb word read : " + nbItemTot);
		return m;
	}
	
	
	private static Set<String> occurenceCountWithSet(List<String> l1) throws IOException  {
  		Set<String> res = new TreeSet<>( Collections.reverseOrder() ) ;
		l1.forEach(item ->{res.add(String.format("%1$05d", Collections.frequency(l1, item)) + item);});
	    return res;
	}
	
	//
 	//***************************** TEST BENCH ************************************
	//
		public static void testbench2(String nameFil1, int nbLoop) throws IOException { 
			
			
			FileWriter fichier = new FileWriter("monFichier2.csv");
			
	// execution of test bench nbLoop times		 
           for (int j = 1 ;j<nbLoop + 1;j++) {
					         
			//*init  Chrono au jème bench
       		new Chrono(j);
 			 			
			//*** récuperation du fichier dans une ArrayList
 			//(on doit garde les doublons pour pouvoir les compter)
            List<String> listWord1 = readWords(nameFil1);
            new Chrono("readWords");
            
            //on ralonge la liste avec elle même autant de fois que j
            List<String> listWord2 = readWords(nameFil1);
            for (int i = 1; i < j; i ++) {
            	 listWord1.addAll(listWord2);
            	}
            
            new Chrono("readWords concatenée " + j + " fois");

            
            //Methode bcp trops longue : Plus de 30  secondes...
            Set<String> res = occurenceCountWithSet(listWord1);
            new Chrono("occurenceCountWithSet  ");
    		printWithForEach(res);
            
  //          TreeMap<String,Integer> m = countEqualItem(listWord1);
            
            //Methode rapide (2 secondes)
    		countEqualItem(listWord1);
    		new Chrono("countEqualItem");
	
//			printWithForEachMap(m);
//            saveTime("printWithForEachMap 1: ");
             //Methode la plus rapide (moins d'un seconde)
              Map<String,Integer> m =countEqualItemWithRemove(listWord1);
              new Chrono("countEqualItemWithRemove");
	
//			printWithForEachMap(n);
//            saveTime("printWithForEachMap 2 : ");
            
          

            Map<String,Integer> sortedMap = sortByValue(m);
//			sortedMap.forEach((k,v) -> System.out.println("key: " + k + ", value: " + v));
            new Chrono("tri on value Comparable");

            ValueComparatorMap comparateur = new ValueComparatorMap(m);
            TreeMap<String,Integer> mapTriee = new TreeMap<String,Integer>(comparateur);
            mapTriee.putAll(m);
//	  	    printWithForEachMap(mapTriee);
            new Chrono("tri on value Comparator");    
            
//          fin bench
            Chrono.saveBenchChrono(j);
            
 		}
           
           writeFileChrono(fichier);

	    	fichier.close();

		}
		
		
		//**** tag chrono and save it in tab times 	
		public static void writeFileChrono(FileWriter f) throws IOException {
			 
			for(ArrayList<String> l : saveLineFile) {
					for(String c : l) {
						f.write(c + ";");
					}
					f.write("\n");
			}	
			}
		
		

 
 	    
 	    
 	   @SuppressWarnings("rawtypes")
	public static <K extends Comparable,V extends Comparable> Map<K,V> sortByValue(Map<K,V> map) {
 		  
 		  List< Map.Entry<K,V> > entries = new LinkedList< Map.Entry<K,V> >(map.entrySet());
 		  
 		  Collections.sort(entries, new Comparator<Map.Entry<K,V>>() {
 		    @SuppressWarnings("unchecked")
			@Override
 		    public int compare(Map.Entry<K, V> entry1, Map.Entry<K, V> entry2) {
 		      return entry2.getValue().compareTo(entry1.getValue());
 		    }
 		  });
 		      
 		  Map<K,V> sortedMap = new LinkedHashMap<K,V>();
 		      
 		  for (Map.Entry<K,V> entry: entries) {
 		    sortedMap.put(entry.getKey(), entry.getValue());
 		  }
 		      
 		  return sortedMap;
 		}
 	    
 	  private static void printWithForEach (Collection<?> l) {
 			
 			System.out.println("******START********");
 			l.forEach(item-> { System.out.println( item); });
 			System.out.println("********END********");
 			System.out.println(" ");

 			 System.out.println("Nb common words : " + l.size());
 		}    
 	    
 	    

}
