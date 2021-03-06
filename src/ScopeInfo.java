//import java.io.*;
import java.util.*;

// ScopeInfo is the primary data structure used to hold information on identifier
//  declarations and uses. Each ScopeInfo node contains information for one scope.
// ScopeInfo nodes are linked together, in order of appearance of scopes in the CSX Lite
//  program. The entire list of nodes contains count information for the entire program.

// Note that ScopeInfo is NOT a block-structured symbol table because:
//   (1) It stores information on a per-scope rather than per-identifier basis
//   (2) It incorrectly assumes that all identifier uses in a scope belong to that scope.
//       In a modern programming languages the use of an identifier belongs to the scope
//        containing its declaration. That is why you will need to extend (or replace)
//        this data structure with on that uses a block-structured symbol table. 

public class ScopeInfo {
	int number; 	// sequence number of this scope (starting at 1)
	int line;   	// Source line this scope begins at
	int declsCount;	// Number of declarations in this scope 
	int usesCount;	// Number of identifier uses in this scope 
	Hashtable<String, Integer> ident = new Hashtable<String, Integer>();					//*RAS - This hashtable takes identifier names as keys
	ScopeInfo next; // Next ScopeInfo node (in list of all scopes found and processed)		//*RAS - and maps them to the lines that corresponding
																							//*RAS - identifiers were declared in. This allows us to
	// A useful constructor																	//*RAS - look up identifier uses by their name and match
	ScopeInfo(int num, int l){																//*RAS - identifiers that were declared on the same line
		number=num;																			//*RAS - with each other. The addition of this hashtable
		line=l;																				//*RAS - is the only update I made to ScopeInfo.
		declsCount=0;
		usesCount=0;
		next=null;
	}
	
	// A useful constructor
	ScopeInfo(int l){
		number=0;
		line=l;
		declsCount=0;
		usesCount=0;
		next=null; 
	}
	
	// This method converts a list of ScopeInfo nodes into string form.
	// It controls what the caller sees as the result of the analysis after
	//  the ScopeInfo list is built.
	public String toString() {
		String thisLine="Scope "+number+ " (at line "+line+"): "+declsCount+" declaration(s), "+
	                     usesCount+" identifier use(s)"+"\n";
		if (next == null)
			return thisLine;
		else return thisLine+next.toString();
	   }
	
	// Method append follows list to its end. Then it appends newNode as the new end of list.
	// It also sets number in newNode to be one more than number in the previous
	//  end of list node. Thus append numbers list nodes in sequence as
	//  the list is built.
	public static void append(ScopeInfo list, ScopeInfo newNode){
		while (list.next != null){
			list=list.next;
		}
		list.next=newNode;
		newNode.number=list.number+1;
		
	}

//  This is used only to test this class (during development or modification).
	public static void  main(String args[]) {
		ScopeInfo test = new ScopeInfo(1,1);
		System.out.println("Begin test of ScopeInfo");
		append(test,new ScopeInfo(2));
		append(test,new ScopeInfo(3));
		System.out.println(test);
		System.out.println("End test of ScopeInfo");
	}

}
