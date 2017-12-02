//Written by Cole Thomas and Riley Kalb
// This program reads in information from the file named Input.txt and 
//then calculates the stable marriage of either the women choosing
// first or the men choosing first depending on the button that is pressed. 


package stablemarriagefinal;
import java.util.*;
import java.io.*;

public class StableMarriageFinal {
    static LinkedList<Person> listPeople;
    static LinkedList<Pair> listPairs;
    static Scanner fileReader;
    static int numPairs, numFree;
    public static Person m1,m2,m3,m4,m5,f1,f2,f3,f4,f5;
    public static Pair   p1,p2,p3,p4,p5;
                             
    public static void readFile(String fileName) throws FileNotFoundException {                                          
        Scanner fileReader;
        String docName, listName;
        Person tempPerson;
        boolean flag;
        
        fileReader = new Scanner(new FileReader(fileName));
        listPeople = new LinkedList();
        numPairs   = fileReader.nextInt();
        flag       = false;
            
        m1 = new Person(fileReader.next());
        m2 = new Person(fileReader.next());
        m3 = new Person(fileReader.next());
        m4 = new Person(fileReader.next());
        m5 = new Person(fileReader.next());
        listPeople.add(m1);
        listPeople.add(m2);
        listPeople.add(m3);
        listPeople.add(m4);
        listPeople.add(m5);
                
        f1 = new Person(fileReader.next());
        f2 = new Person(fileReader.next());
        f3 = new Person(fileReader.next());
        f4 = new Person(fileReader.next());
        f5 = new Person(fileReader.next());
        listPeople.add(f1);
        listPeople.add(f2);
        listPeople.add(f3);
        listPeople.add(f4);
        listPeople.add(f5);
            
        for (int i=0; i<numPairs; i++){ 
            flag    = false;
            docName = fileReader.next();
            docName = docName.substring(0,docName.length()-1);
            while(!flag){
                for (int j=0; j<listPeople.size(); j++){
                    if (docName.equals(listPeople.get(j).name)){
                        listPeople.get(j).preferences.add(fileReader.next());
                        listPeople.get(j).preferences.add(fileReader.next());
                        listPeople.get(j).preferences.add(fileReader.next());
                        listPeople.get(j).preferences.add(fileReader.next());
                        listPeople.get(j).preferences.add(fileReader.next());
                        flag = true;
                        break;
                    }
                }
            }
        }
        
        fileReader.nextLine();

        for (int i=0; i<numPairs; i++){ 
            flag    = false;
            docName = fileReader.next();
            docName = docName.substring(0,docName.length()-1);
            while(!flag){
                for (int j=0; j<listPeople.size(); j++){
                    if (docName.equals(listPeople.get(j).name)){
                        listPeople.get(j).preferences.add(fileReader.next());
                        listPeople.get(j).preferences.add(fileReader.next());
                        listPeople.get(j).preferences.add(fileReader.next());
                        listPeople.get(j).preferences.add(fileReader.next());
                        listPeople.get(j).preferences.add(fileReader.next());
                        flag = true;
                        break;
                    }
                }
            }
        }
    }
    
    public static void arrangeMarriages(String g){
        String mName,fName;
        int i,k,oldM_pIndex, newM_pIndex, oldM_lIndex, 
                oldF_pIndex, newF_pIndex, oldF_lIndex;
        Person male,female;

        numFree = 5;
        
        if ("m".equals(g)){
            while (numFree>0){
                for(i=0; i<5; i++){
                    if (listPeople.get(i).takenBy.equals("")){
                        fName  = listPeople.get(i).preferences.poll();
                        k      = retrieveIndex(listPeople, fName);
                        male   = listPeople.get(i);
                        female = listPeople.get(k);
                        oldM_lIndex = -1;                                    
                                                                              
                        if (female.takenBy.equals("")){
                            female.takenBy = male.name;                                                                    
                            male.takenBy = female.name;                                                                        
                            numFree--;
                        }else{                                                       
                            newM_pIndex = retrievePreferenceIndex(
                                    female.preferences, male.name);
                            oldM_pIndex = retrievePreferenceIndex(
                                    female.preferences, female.takenBy);
                            oldM_lIndex = retrieveIndex(listPeople, 
                                    female.preferences.get(oldM_pIndex));
                            if(oldM_pIndex > newM_pIndex){                        
                                listPeople.get(oldM_lIndex).takenBy = "";  
                                female.takenBy = male.name;
                                male.takenBy   = female.name;
                            }
                        }
                    if (i==4 && checkFree()){i=0;}
                    }  
                }
            }
        }
        
        if ("f".equals(g)){
            while (numFree>0){
                for(i=5; i<10; i++){
                    if (listPeople.get(i).takenBy.equals("")){
                        mName  = listPeople.get(i).preferences.poll();
                        k      = retrieveIndex(listPeople, mName);
                        female = listPeople.get(i);
                        male   = listPeople.get(k);
                        oldF_lIndex = -1;                                    
                                                                              
                        if (male.takenBy.equals("")){
                            male.takenBy   = female.name;                                                                    
                            female.takenBy = male.name;                                                                        
                            numFree--;
                        }else{                                                       
                            newF_pIndex = retrievePreferenceIndex(
                                  male.preferences, female.name);
                            oldF_pIndex = retrievePreferenceIndex(
                                  male.preferences, male.takenBy);
                            oldF_lIndex = retrieveIndex(
                                  listPeople,male.preferences.get(oldF_pIndex));
                            if(oldF_pIndex > newF_pIndex){                        
                                listPeople.get(oldF_lIndex).takenBy = "";  
                                male.takenBy   = female.name;
                                female.takenBy = male.name;
                            }
                        }
                    if (i==9 && checkFree()){i=5; }
                    }  
                }
            }  
        }
    }
        
    public static void printList(){
        System.out.println("Printing list....");
        for (int i=0; i<listPeople.size(); i++){
            System.out.println(listPeople.get(i).toString());
        }
    }
    
    public static int retrieveIndex(LinkedList<Person> list, String name){
        String n;
        for (Person p : list){
            n = p.getName();
            if (n.equals(name)){
                return list.indexOf(p);
            }
        }
        return -1;
    }
    
    public static int retrievePreferenceIndex(LinkedList<String> l,String name){
        String n;
        for (String s : l){
            if (s.equals(name)){
                return l.indexOf(s);
            }
        }
        return -1;
    }

    public static boolean checkFree(){
        boolean result;
        result = false;
        for (Person p : listPeople){
            if (p.takenBy.equals("")){
                result = true;
                return result;
            }
        }
        return result;
    }
    
    public static void setPairList(){
        p1 = new Pair(listPeople.get(0).name, listPeople.get(0).takenBy);
        p2 = new Pair(listPeople.get(1).name, listPeople.get(1).takenBy);
        p3 = new Pair(listPeople.get(2).name, listPeople.get(2).takenBy);
        p4 = new Pair(listPeople.get(3).name, listPeople.get(3).takenBy);
        p5 = new Pair(listPeople.get(4).name, listPeople.get(4).takenBy);
    }
            
    static class Person{
        String name, takenBy;
        LinkedList<String> preferences;
        
        Person(String name){
            this.name        = name;
            this.preferences = new LinkedList();
            this.takenBy     = "";
        }
        
        public String getName(){
            return this.name;
        }
        
        public void addPreference(String n){
            this.preferences.add(n);
        }
      
        @Override
        public String toString(){
            String output;
            output = (this.name + ": " + this.preferences);
            return output;
        }
    }
    
    static class Pair{
        public String man,woman;
        String        tempString;
        
        Pair(String m, String w){
            man   = m;
            woman = w;
        }
        
        Pair(){
            man   = "";
            woman = "";
        }
        
        @Override
        public String toString(){
            tempString = "(" + man + ", " + woman + ")"; 
            return tempString;
        }       
    }
}