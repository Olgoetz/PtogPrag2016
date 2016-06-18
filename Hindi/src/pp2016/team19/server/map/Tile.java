package pp2016.team19.server.map;


import java.util.Vector;

import pp2016.team19.server.map.*;
import java.util.*;

public class Tile extends Content{
		
		public int Type;
		public boolean ContainsKey = false;
		public boolean ContainsPotion = false;
		public boolean ContainsMonster = false;
		
		public static final int Rock = 1;
		public static final int Floor = 0;
		public static final int Entry = 2;
		public static final int Exit = 3;
		
		public static final int Key = 11;
		public static final int Potion = 12;
		public static final int Monster = 13;
		
		
		
		private Vector<Content> content = null;
		
	
		
		
		
		
		
		// Input = Rock, Floor, Door, 
		public void setType(int input){
			Type = input;
		}
		
		
		
		public boolean isRock(){
			
			if (Type == Rock){
				return true;
			}
			
			return false;
		}
		
		public boolean isFloor(){
			
			if (Type == Floor){
				return true;
			}
			
			return false;
		}
		
		public boolean isEntry(){
			
			if (Type == Entry){
				return true;
			}
			return false;
		}
		
		public boolean isExit(){
			
			if (Type == Exit){
				return true;
			}
			return false;
		}
		
		public boolean containsKey(){
			if(ContainsKey == true){
				return true;
			}
			return false;
		}
		
		
		// Schlüssel Methoden:
		public void placeKeyT(boolean input){
			ContainsKey = true;
		}
		
		public void droppedKeyT(boolean input){
			ContainsKey = false;
		}
		
		public boolean containsPotion(){
			if(ContainsPotion == true){
				return true;
			}
			return false;
		}
		
		
		// Monster Methoden:
		public boolean containsMonster(){
			if(ContainsMonster == true){
				return true;
			}
			return false;
		}

		public boolean isWalkable(){
			if(Type == Floor || Type == Entry || Type == Exit || Type == Key || Type == Potion || Type == Monster){
				System.out.println("and is walkable");
				return true;
			}
			return false;
		}
		

	}
	
	
	

