package consolideCollections;

import java.util.List;
import java.util.Set;
import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeSet;
 


//									Mots communs - Consignes
//
//Écrire un programme qui permette d’afficher (un mot par ligne) les mots qui sont communs aux deux fichiers.
//
//Bonus
//
//        prendre les noms de fichiers / chemins en argument    : OK + testbench pour tester plusieurs solutions
//        afficher les mots communs dans l’ordre alphabétique   : OK (recuperer les mots communs dans une liste de type set, 
//																		ils sont automatiquement trié par ordre alphabétique)
//
//


public class CommonWord {
	
	private final static String nameRootFic1 = "ethics.txt";
	private final static String nameRootFic2 = "wealth.txt";
	

	
//******************** Main ****************************************************
	public static void main(String[] args) throws IOException {
		
//***** Arguments  Explain
//*   empty  	or Arg[0] <> 1 and 2		==> 2 files on project root, ethics et wealth
//*   Arg[0]  = 1						==> 2 pathfiles in Arg[1] et Arg[2] 
//*	  Arg[0]  = 2						==> testbench	with rootfiles and Arg[1] = nb loop

//*** get names files*****************************	
		String nameFil1 = "";
		String nameFil2 = "";
		if ((args.length == 0) || (!args[0].equals("1"))){
				nameFil1 = nameRootFic1;
				nameFil2 = nameRootFic2;}
		else {
				if (args[0].equals("1")){
					nameFil1 = args[1] ;
					nameFil2 =  args[2];}
		}
					

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
			
		   testbench(nameFil1,nameFil2,nbLoop);
		   }	
	
		else
			{	 
				try {		 	
					Set<String>list1 = readWordsWithSet(nameFil1, 1);
	 				Set<String>list2 =  readWordsWithSet(nameFil2, 1);
	 				list1.retainAll(list2);
	 				printWithForEach(list1);
			}
			catch (IOException e) {
				System.out.println("Error file " + e);
			} }
	}
				
 
		 
		//****** get common word between 2 list  
		private static Set<String> getCommonWordC(Collection<String> l1, Collection<String> l2) {
			//first param must have most little size
			Set<String> res = getCommonWordCTri((l1.size() < l2.size()) ? l1 : l2 , (l1.size() < l2.size()) ? l2 : l1 );
			return res;
		}
		private static Set<String> getCommonWordCTri(Collection<String> l1, Collection<String> l2)  {
			Set<String> res= new TreeSet<>();			
					l1.forEach(item ->{ if (l2.contains(item)) {res.add(item);}});	
					return res;	
		}	
	
//*=========================================================================================
//
//                 TEST BENCH
//
//=======================================================================================			
				
private static void testbench (String arg1, String arg2, int nbLoop) throws IOException {

	//init file to save results of bench
	FileWriter fichier = new FileWriter("monFichier3.csv");

	//Execution of test bench nbLoop times		
	for (int j = 1 ;j<nbLoop + 1;j++){
		
	 
		
		//init du chrono au jème bench
		new Chrono(j);
 
		//read file in a SET : delete the multiple words, it will be stay one.
		// To increasing file a word will be write j times (word, word1, ...,wordj)
 		
		Set<String>list1 = readWordsWithSet(arg1,j);
		new Chrono("readWordswithSet 1 ");
		Set<String>list2 =  readWordsWithSet(arg2,j);
		new Chrono("readWordswithSet 2 ");
		
		//read file in a List : multiple words stay
		List<String> listWord1 = readWords(arg1);
		new Chrono("readWords 1 ");
		List<String> listWord2 = readWords(arg2);
		new Chrono("readWords 2 ");	      
        //increase list whith themselves x j
		List<String> listWord3 = readWords(arg1);
		List<String> listWord4 = readWords(arg2);
        for (int i = 1; i < j; i ++) { 
             	 listWord1.addAll(listWord3);
            	 listWord2.addAll(listWord4);
            }
		new Chrono("listes ralongées ");

	
	//* method with retain all sur arraylisy : TOO LONG....
	//*	listWord1.retainAll(listWord2);
	//*	new Chrono("methode retain all whit list ");
 			
	//* method with retain all sur SET....
		list1.retainAll(list2);
		new Chrono("methode retain all whit set ");
 
			
	//* method with retain all sur SET....
		Set<String> commonWord = getCommonWordC(list1,list2);
		new Chrono("getCommonWord ");
		
	//* print common words.
 	//	printWithForEach(commonWord); 
 	//	new Chrono("printWithForEach  ");
	 
  		
	//* End bench : save all 
		
        Chrono.saveBenchChrono(j);
        
        
		//System.out.println("********************************************* " );
		//System.out.println("" );
		//System.out.println("" );
 		
     }
	
	//*  save result benchs into file    
    writeFileChrono(fichier);

	//* close file  
	fichier.close();

}
 

	
	private static void printWithForEach (Collection<?> l) {
		
		System.out.println("******START********");
		l.forEach(item-> { System.out.println( item); });
		System.out.println("********END********");
		System.out.println(" ");

		System.out.println("Nb common words : " + l.size());
	}
	
 
	 
//	
//********* read file , and return an ArrayList********************
	public static List<String> readWords(String fileName) throws IOException {
		 
		List<String> res= new ArrayList<String>();	
//*** try with resource  
		try(BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
			for(String word = br.readLine(); word != null ; word= br.readLine()){
				res.add(word);
			}
		}
		return res;
	}

	//********* read file , and return an TreeSet*****************************
		public static Set<String> readWordsWithSet(String fileName, int dif) throws IOException {
		
			Set<String> res= new TreeSet<>();
	//*** try with ressource  
			try(BufferedReader br = Files.newBufferedReader(Paths.get(fileName))) {
				for(String word = br.readLine(); word != null ; word= br.readLine()){
					res.add(word);
					for(int u = 1; u < dif; u++)
						{res.add(word + u);};
				}
			}
			return res;
		}

 
		//****save result chrono in a file  
		public static void writeFileChrono(FileWriter f) throws IOException {
				for(ArrayList<String> l : Chrono.getSaveChrono()) {
					for(String c : l) {
						f.write(c + ";");
					}
					f.write("\n");
				}}
	 
		 
 	}
