package pp2016.team19.server.map;

/**
 * Generally:
 * 		
 * 		A Class, which contains a method called "generateLabyrinth()" which uses the 
 * 		floodFill method to create a Labyrinth, and uses methods to place Potion the Entry 
 * 		or the Exit in the map.
 * 
 * 		It uses a data structure (2d Array) "gameMap" of the data structure "Tile", which will be returned 
 * 		at the end of the method.
 * 
 * 		For a short time, it contains a method "paintTest()", to check if the Labyrinth 
 * 		will be generated correct, and puts the Labyrinth on the console.
 * 
 * Contains:
 * 
 * 		1 (2-dim) data structure:	gameMap[][] of type Tile
 * 
 * 		7 constants:				Which represent the types and contants.
 * 
 *		2 essential method:			Called "generateLabyrinth(gameSize)"
 *									Called "floodFill(1, gameSize-2, gameSize)"
 *
 *		A few helpful methods:		"createRockMap(gameSize)" 		= "Creates an Object Array, sets every Tile to ROCK."
 *									
 *									"edgeCheckUp(x, y, gameSize)" 	= "Check if there is an edge."
 *									"edgeCheckDown(..)"
 *									"edgeCheckLeft(..)"
 *									"edgeCheckRight(..)"
 *									
 *									"countNeighbors(x, y)"			= "returns amount of neighbors of a Tile (up,down,left,right)."
 *									"diagNeighbors(x, y)"			= "returns amount of neighbors of a Tile (diagonal)."
 *									
 *									"placeKey(1, gameSize)"			= "places a key in a quadrant of the gameMap."
 *									"placeExit(..)"		
 *									"placeEntry(gameSize)"
 *									"placePotion(..)"
 *									"placeMonster(..)"
 *
 *									"paintTest(gameSize)"			= "returns the Labyrinth in the console.
 *
 * @author < Czernik, Christof Martin, 5830621 >
 */

public class Labyrinth {

	/**
	 * 7 Constants
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static final int ROCK = 1;
	public static final int FLOOR = 0;
	public static final int ENTRY = 2;
	public static final int EXIT = 3;

	public static final int KEY = 11;
	public static final int POTION = 12;
	public static final int MONSTER = 13;
	public static final int PLAYER = 14;
	
	/**
	 * 2-Dim Tile-Array called "gameMap"
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	private static Tile[][] gameMap;
	//private static int gameSize;
	//private static int monsterNumber;
	
	/**public Labyrinth(int gameSize, int monsterNumber){
		this.gameSize = gameSize;
		this.monsterNumber = monsterNumber;
	}
	*/
	
	/**
	 * "generateLabyrinth()" is an essential method, which gets the gameSize as Input and returns the gameMap.
	 * 
	 * @param gameSize Size of the GameMap
	 * @param monsterNumber Amount of Monsters that will be created
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static Tile[][] generate(int gameSize, int monsterNumber) {

		gameMap = new Tile[gameSize][gameSize];

		// Creates a Map of ROCKS.
		createRockMap(gameSize);

		// Starts to switch Rock-Tiles to Floor-Tiles, to make a maze.
		floodFill(1, gameSize - 2, gameSize);

		// Places an Entry.
		placeEntry(gameSize);

		// Places an Exit.
		placeExit(1, gameSize);

		// Places a Key
		placeKey(1, gameSize);

		// Places a Potion.
		placePotion(gameSize);

		// Places a number of Monster.
		placeMonster(gameSize,monsterNumber);

		return gameMap;
	}
	
	/**
	 * Creates an Object for every index, and sets the type to ROCK.
	 * 
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void createRockMap(int gameSize){
		
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {
				gameMap[i][j] = new Tile();
				gameMap[i][j].setType(ROCK);

			}
		}
		
	}
	
	/**
	 * Checks if we are at the Upper-Edge, returns true when not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static boolean edgeCheckUp(int x, int y) {

		if (x - 1 == 0) {
			return false;
		}
		if (x - 1 > 1) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if we are at the Right-Edge, returns true when not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static boolean edgeCheckRight(int x, int y, int gameSize) {

		if (y + 1 == gameSize - 1) {
			return false;
		}
		if (y + 1 < gameSize - 1) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if we are at the Lower-Edge, returns true when not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static boolean edgeCheckDown(int x, int y, int gameSize) {

		if (x + 1 == gameSize - 1) {
			return false;
		}
		if (x + 1 < gameSize - 1) {
			return true;
		}
		return false;
	}

	/**
	 * Checks if we are at the Left-Edge, returns true when not.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static boolean edgeCheckLeft(int x, int y, int gameSize) {

		if (y - 1 == 0) {
			return false;
		}
		if (y - 1 > 0) {
			return true;
		}
		return false;
	}

	/**
	 * Counts the Neighbors of a Tile in the directions: (left, right, up, down)
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static int countNeighbors(int x, int y, int gameSize) {

		int counter = 0;

		if (gameMap[x + 1][y].isFloor()) {
			counter = counter + 1;
		}
		if (gameMap[x - 1][y].isFloor()) {
			counter = counter + 1;
		}
		if (gameMap[x][y + 1].isFloor()) {
			counter = counter + 1;
		}
		if (gameMap[x][y - 1].isFloor()) {
			counter = counter + 1;
		}

		return counter;
	}

	/**
	 * Checks if the diagonal Tile is connected with our current tile.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static boolean diagNeighbors(int x, int y) {

		if (gameMap[x + 1][y + 1].isFloor()) {
			if (!gameMap[x + 1][y].isFloor() || !gameMap[x][y + 1].isFloor()) {
				return false;
			}
		}
		if (gameMap[x - 1][y + 1].isFloor()) {
			if (!gameMap[x - 1][y].isFloor() || !gameMap[x][y + 1].isFloor()) {
				return false;
			}
		}
		if (gameMap[x + 1][y - 1].isFloor()) {
			if (!gameMap[x + 1][y].isFloor() || !gameMap[x][y - 1].isFloor()) {
				return false;
			}
		}
		if (gameMap[x - 1][y + 1].isFloor()) {
			if (!gameMap[x - 1][y].isFloor() || !gameMap[x][y + 1].isFloor()) {
				return false;
			}
		}
		return true;
	}

	/**
	 * Searches for a Tile = Floor in a quadrant, to put a Potion at this Tile. (Recursiv)
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void placePotion(int gameSize) {
		boolean br = false;
		for (int i = (gameSize - 2); i > ((gameSize - 2) / 2 + 1); i--) {
			if (gameMap[i][gameSize - 2].isFloor()) {

				gameMap[i][(gameSize - 2)].setContainsPotion(true);
				br = true;
				System.out.print("\nPotion placed randomly in a blind alley.");
				gameMap[i][(gameSize - 2)].isWalkable();
				break;

			}
		}
		if (gameSize < 2) {
			return;
		} else if (br == false) {
			placePotion((gameSize - 1));
		}

	}
	
	/**
	 * Searches for a random walkable Tile in a quadrant, to put a Monster at this Tile (Recursiv)
	 * 
	 * @param gameSize size of the gameMap
	 * @param monster number of all monsters
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void placeMonster(int gameSize, int monsterNumber){
		
		// create Vector, for possible monster Tiles
		int[] possibleMonsterPlaces = new int[gameSize*gameSize];
	
		// Counter-Variable
		int counter = 0;
		int x;
		int y;
		
		// put all Floor-Tiles indizes in an int Array
		for (int i = 1; i < gameSize; i++){
			for (int j = 1; j < gameSize; j++){
				if(gameMap[i][j].isFloor()){
					if(!gameMap[i][j].containsMonster() && !gameMap[i][j].containsPlayer()){
					possibleMonsterPlaces[counter] = i;
					possibleMonsterPlaces[counter + 1] = j;
					counter = counter + 2;
					}
				}	
			}
		}
		
		// For, to generate the number of Monster..
		for (int m = 1; m <= monsterNumber; m++){
		
			// Random number to get a random floor
			int number = (int) ((Math.random()) * counter + 1);
			
			if(number <= 1){
				number = number + 2;
			}
		
			// Gets the x and y Coordinate of gameMap
			if (number%2 == 0){
				x = possibleMonsterPlaces[number];
				y = possibleMonsterPlaces[number + 1];
			}
			else{
				y = possibleMonsterPlaces[number];
				x = possibleMonsterPlaces[number - 1];
			}
			
			if(gameMap[x][y].containsMonster){
				m = m-1;
				continue;
			}
			// sets the Tile where the monster spawns True.
			gameMap[x][y].setContainsMonster(true);
		
		}
		System.out.print("\n" + monsterNumber + " Monster placed randomly over the map.");
	}

	/**
	 * Searches for a blind alley in a quadrant, to put a Key at this blind alley. (Recursiv)
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void placeKey(int j, int gameSize) {
		boolean br = false;
		for (int i = 1; i < (gameSize / 2 + 1); i++) {
			if (gameMap[i][j].isFloor()) {
				if (countNeighbors(i, j, gameSize) < 2) {
					gameMap[i][j].setContainsKey(true);
					br = true;
					System.out.print("\nKey placed randomly in a blind alley.");
					gameMap[i][j].isWalkable();
					break;

				}
			}
		}

		if (br == false) {
			placeKey(j + 1, gameSize);
		}
	}

	/**
	 * Searches for a blind alley in the lower-Left quadrant, to put an exit at this blind alley. (Recursiv)
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void placeExit(int j, int gameSize) {
		boolean br = false;
		for (int i = (gameSize - 2); i > (gameSize / 2 + 1); --i) {
			if (gameMap[i][j].isFloor()) {
				if (countNeighbors(i, j, gameSize) < 2) {
					gameMap[i][j].setType(EXIT);
					br = true;
					System.out.print("\nExit placed in lower left Corner\n");
					break;

				}
			}
		}

		if (br == false) {
			placeExit((j + 1), gameSize);
		}
	}

	/**
	 * Places an entry at the beginning Spot of floodFill.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void placeEntry(int gameSize) {
		gameMap[1][gameSize - 2].setType(ENTRY);
		gameMap[1][gameSize - 2].setContainsPlayer(true);
		System.out.print("\nEntry placed in upper right Corner");
	}

	/**
	 * A Random FloodFill Algorithm.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void floodFill(int x, int y, int gameSize) {

		int zahl = (int) ((Math.random()) * 4 + 1);

		int counter = 0;

		gameMap[x][y].setType(FLOOR);

		switch (zahl) {

		case 1:
			if (edgeCheckUp(x, y)) {
				if (gameMap[x - 1][y].isRock()) {
					if (countNeighbors(x - 1, y, gameSize) < 2) {
						// if(diagNeighbours(x-1,y)){
						floodFill(x - 1, y, gameSize);
						// }

					}
				}
			} else if (counter < 4) {
				counter = counter + 1;
				zahl = 2;
			} else if (counter == 4) {
				break;
			}

		case 2:
			if (edgeCheckRight(x, y, gameSize)) {
				if (gameMap[x][y + 1].isRock()) {
					if (countNeighbors(x, y + 1, gameSize) < 2) {
						// if(diagNeighbours(x,y+1)){
						floodFill(x, y + 1, gameSize);
						// }
					}
				}
			} else if (counter < 4) {
				counter = counter + 1;
				zahl = 3;
			} else if (counter == 4) {
				break;
			}

		case 3:
			if (edgeCheckDown(x, y, gameSize)) {
				if (gameMap[x + 1][y].isRock()) {
					if (countNeighbors(x + 1, y, gameSize) < 2) {
						// if(diagNeighbours(x+1,y)){
						floodFill(x + 1, y, gameSize);
						// }
					}
				}
			} else if (counter < 4) {
				counter = counter + 1;
				zahl = 4;
			} else if (counter == 4) {
				break;
			}

		case 4:
			if (edgeCheckLeft(x, y, gameSize)) {
				if (gameMap[x][y - 1].isRock()) {
					if (countNeighbors(x, y - 1, gameSize) < 2) {
						// if(diagNeighbours(x,y-1)){
						floodFill(x, y - 1, gameSize);
						// }
					}
				}
			} else if (counter < 4) {
				counter = counter + 1;
				zahl = 1;
			} else if (counter == 4) {
				break;
			}

		}
	}

	/**
	 * Checks every Tile in the Array, and prints it on the console.
	 * 
	 * @author < Czernik, Christof, 5830621 >
	 */
	
	public static void PaintTest(int gameSize) {
		System.out.println("\n");
		for (int i = 0; i < gameSize; i++) {
			for (int j = 0; j < gameSize; j++) {

				if (gameMap[i][j].isEntry()) {
					System.out.print("S ");

				} else if (gameMap[i][j].isExit()) {
					System.out.print("Z ");

				} else if (gameMap[i][j].containsKey()) {
					System.out.print("K ");

				} else if (gameMap[i][j].containsPotion()) {
					System.out.print("P ");
					
				} else if (gameMap[i][j].containsMonster()) {
					System.out.print("M ");

				} else if (gameMap[i][j].isRock()) {
					System.out.print("+ ");

				} else if (gameMap[i][j].isFloor()) {
					System.out.print("o ");

				}

			}
			System.out.println("");
		}
	}
}
