package consolideCollections;

import java.util.Comparator;
 import java.util.Map;
 
 

 class ValueComparatorMap implements Comparator<String> {
	 
	 Map<String, Integer> base;
	 
	 //en variable du %constructeur on donne la map à trier sur la valeur et non la clé
	 public ValueComparatorMap() {
		 this.base = null;
	 }
	 
	 public ValueComparatorMap(Map<String, Integer> base) {
		 this.base = base;
	 }


     //on implemente la comparaison,,
	 //on trie d'abord sur la valeur de la plus grande à la plus peite
	 //si elles sont égales, on trie sur la cle par orde alpahbétique
	 public int compare(String a, String b) {    
		 if (base.get(a) > base.get(b)) 
		 {return -1;}
		 else 
		 {if (base.get(a).compareTo(base.get(b)) == 0) {
			 if (a.compareTo(b) > 0) 
			 {return 1;}
			 else
			 {return -1;}
		 }
		 else
		 	{return 1;}
		 }
	 }
 }
	
	
	
	
	

