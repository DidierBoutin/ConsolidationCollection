package consolideCollections;

import java.util.ArrayList;

public class Chrono {

	//tag chrono
	private  final String commentary;
	//time chrono
	private final Long time;
	//Liste to save all chrono of a bench test
	final static ArrayList<Chrono> listChrono = new ArrayList<Chrono>();
    
	//Liste de chronos reformatés 
	// All the end of a bench, it will have a same list of commentary Chrono.
	// The first columm of saveChrono will contains these commentary ;
	// Others collumns will contains all times of a bench
	//   bench  1        2        3    4 ....
	// tag 1    time11  time21  ...
	// tag 2    time21  time22
	// tag 3    time31  time23
	//....
	static Long timePrec = (long) 0;
	final static ArrayList<ArrayList<String>> saveChrono = new ArrayList<ArrayList<String>>();
	
    public Chrono() {
    	this("no comment");
    };
    
    public Chrono(int numBench) {	
    	this("*************   start " + numBench + "**************");
    };
    
    public Chrono(String s , int numBench) {	
    	this("*************   end " + numBench + "**************");
    };
    
    
    //At instance of objet Chrono, save time system,
    //and add instance in ListChrono 
    public Chrono(String commentary) {
		this.commentary = commentary;
		this.time = java.lang.System.nanoTime();
		saveChrono();
	}
	
    private  void saveChrono () {
        listChrono.add(this);
        
        System.out.println("time : " + (this.time - timePrec)/1.e9 + ", " + this.commentary );
        timePrec = this.time;
   }
     
    
    
 //*** public Method of instance***********
    
    public String getCommentary() {
    	return this.commentary;	
    }
    public Long getTime() {
    	return this.time;	
    }
   
    
 //*** Static Method to get static variables

    public static ArrayList<Chrono> getListChrono() {
    	return listChrono;
    }
    
    public static ArrayList<ArrayList<String>> getSaveChrono() {
    	return saveChrono;
    }
    
    //*** Static Method to  use list of Chrono
     
    //to clear listChron, can't be use be another class (private)
    private static void initListChrono() {
    	 listChrono.clear();
    }
    
    //* to initialize the first column of saveChrono with commentary
    //* use only by saveBenchChrono, if saveChrono is empty
    private static void initTagColumn() {
	    int k = 0;
    	for(Chrono c :  listChrono) {
    		saveChrono.add(new ArrayList<String>());
    		saveChrono.get(k).add(c.getCommentary());
    		k++;
    	}
    }
    
    //* At the end of a bench, to save  times
    public static void saveBenchChrono(int numBench) {

    	new Chrono("End", numBench);
    	System.out.println(" ");
    	
    	if (saveChrono.isEmpty())
			{initTagColumn();}
    	
	   	Long prec = (long) 0;
   	 	int k = 0;
    	for(Chrono c : listChrono)
    	{	
    		Double time = (c.getTime() - prec)/1.e9;
    		String t = time.toString().replace(".", ",");
    		saveChrono.get(k).add(t);
    	   prec = c.getTime();
    	   k ++;
    	}
    	//initialize of listChrono (to be used on an another bench)
    	initListChrono();
    	
    	}
   
  }
    	
     
  
    
	
