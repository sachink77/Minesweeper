
//TESTING THIS STUFF SYNC
import java.util.ArrayList;
import java.util.Random;

import tester.*;
import javalib.funworld.World;
import javalib.funworld.WorldScene;
//import javalib.impworld.*;
import java.awt.Color;
import javalib.worldimages.*;

//represents a cell in Minesweeper
class Cell {
  ArrayList<Cell> neighbors;
  boolean isMine;
  //int numberOfMinesAround;
  boolean flagged;
  boolean clicked;

  Cell(boolean isMine) {
    this.neighbors = new ArrayList<Cell>();
    this.isMine = isMine;
    this.flagged = false;
    this.clicked = false;
  }

  // counts the number of neighbors that are mines in this cell
  public int countMines() {
    int i = 0;
    for (Cell c : neighbors) {
      if (c.isMine) {
        i++;
      }
    }
    // System.out.println(this.neighbors);
    return i;
  }
}

//represents the GameWorld
class GameWorld extends World {
  int rows;
  int columns;
  int mines;
  ArrayList<ArrayList<Cell>> boardOfCells;
  Random rand;

  GameWorld(int rows, int columns, int mines, Random rand) {
    this.rows = rows;
    this.columns = columns;
    this.mines = mines;
    this.boardOfCells = new ArrayList<ArrayList<Cell>>();
    this.rand = rand;
    this.buildGameBoard();
    this.addNeighbors();
  }

  // builds the game board
  void buildGameBoard() {
    int m = 0;
    for (int i = 0; i < this.rows; i++) {
      boardOfCells.add(new ArrayList<Cell>());
      for (int j = 0; j < this.columns; j++) {

        if (this.rand.nextBoolean() && m < this.mines) {
          m++;
          boardOfCells.get(i).add(new Cell(true));

        }
        else {
          boardOfCells.get(i).add(new Cell(false));
        }

        // scene = boardOfCells.get(i).get(j).draw(scene, j * 20 + 10, i * 20 + 10);
      }
    }
  }

  // adding neighbors to each cell
  void addNeighbors() {
    for (int ro = 0; ro < this.rows; ro++) {
      for (int col = 0; col < this.columns; col++) {
        Cell currentCell = boardOfCells.get(ro).get(col);

        // top left corner
        if (ro == 0 && col == 0) {
          currentCell.neighbors.add(boardOfCells.get(ro).get(col + 1));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col + 1));
        }

        // top right corner
        else if (ro == 0 && col == this.columns - 1) {
          currentCell.neighbors.add(boardOfCells.get(ro).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col - 1));
        }

        // bottom left corner
        else if (ro == this.rows - 1 && col == 0) {
          currentCell.neighbors.add(boardOfCells.get(ro).get(col + 1));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col + 1));
        }

        // bottom right corner
        else if (ro == this.rows - 1 && col == this.columns - 1) {
          currentCell.neighbors.add(boardOfCells.get(ro).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col - 1));
        }

        // first row with 5 neighbors
        else if (ro == 0) {
          currentCell.neighbors.add(boardOfCells.get(ro).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro).get(col + 1));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col + 1));
        }

        // bottom row with 5 neighbors
        else if (ro == this.rows - 1) {
          currentCell.neighbors.add(boardOfCells.get(ro).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro).get(col + 1));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col + 1));
        }

        // left border with 5 neighbors
        else if (col == 0) {
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col + 1));
          currentCell.neighbors.add(boardOfCells.get(ro).get(col + 1));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col + 1));
        }

        // right border with 5 neighbors
        else if (col == this.columns - 1) {
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col - 1));
        }

        // normal cells with 8 neighbors
        else {
          currentCell.neighbors.add(boardOfCells.get(ro).get(col + 1));
          currentCell.neighbors.add(boardOfCells.get(ro).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col + 1));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro + 1).get(col - 1));
          currentCell.neighbors.add(boardOfCells.get(ro - 1).get(col + 1));
        }
      }
    }
  }

  // creates a WorldScene representing Minesweeper
  public WorldScene makeScene() {
    WorldScene scene = this.getEmptyScene();

    // int m = 0;

    WorldImage rect1 = new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK);
    WorldImage rect2 = new RectangleImage(20, 20, OutlineMode.SOLID, Color.CYAN);
    WorldImage dot = new CircleImage(5, OutlineMode.SOLID, Color.RED);

    for (int i = 0; i < this.rows; i++) {
      // boardOfCells.add(new ArrayList<Cell>());
      for (int j = 0; j < this.columns; j++) {
        Cell cell = boardOfCells.get(i).get(j);

        if (cell.isMine ) {
          scene.placeImageXY(new OverlayImage(dot, new OverlayImage(rect1, rect2)), j * 20 + 10,
              i * 20 + 10);
        }
        else if (cell.countMines() > 0 ) {
          scene.placeImageXY(
              new OverlayImage(new TextImage(Integer.toString(cell.countMines()), 10,
                  FontStyle.BOLD, Color.BLUE), new OverlayImage(rect1, rect2)),
              j * 20 + 10, i * 20 + 10);
        }
        else {
          scene.placeImageXY(new OverlayImage(rect1, rect2), j * 20 + 10, i * 20 + 10);
        }
        //System.out.println(cell.clicked);
      }
    }
    return scene;
  }
}

//examples and tests for the Minesweeper game
class RunGameWorld {

  // runs the Minesweeper game
  boolean testGo(Tester t) {
    Random rand = new Random();
    GameWorld myWorld = new GameWorld(5, 5, 8, rand);
    return myWorld.bigBang(500, 500);
  }

  Cell emptyCell = new Cell(false);
  Cell mine = new Cell(true);
  Cell numberCell = new Cell(false);

  WorldScene scene = new WorldScene(0, 0);
  WorldImage rect1 = new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK);
  WorldImage rect2 = new RectangleImage(20, 20, OutlineMode.SOLID, Color.CYAN);
  WorldImage dot = new CircleImage(5, OutlineMode.SOLID, Color.RED);

  // EFFECT: resets all examples to their original states
  void reset() {
    emptyCell = new Cell(false);
    mine = new Cell(true);
    numberCell = new Cell(false);
    numberCell.neighbors.add(mine);

    scene = new WorldScene(0, 0);
    rect1 = new RectangleImage(20, 20, OutlineMode.OUTLINE, Color.BLACK);
    rect2 = new RectangleImage(20, 20, OutlineMode.SOLID, Color.CYAN);
    dot = new CircleImage(5, OutlineMode.SOLID, Color.RED);
  }

  // tests for draw
//  void testDraw(Tester t) {
//    this.reset();
//
//    t.checkExpect(emptyCell.draw(scene, 0, 0),
//        scene.placeImageXY(new OverlayImage(rect1, rect2), 0, 0));
//    t.checkExpect(mine.draw(scene, 0, 0),
//        scene.placeImageXY(new OverlayImage(dot, new OverlayImage(rect1, rect2)), 0, 0));
//    t.checkExpect(numberCell.draw(scene, 0, 0),
//        scene.placeImageXY(new OverlayImage(new TextImage("1", 10, FontStyle.BOLD, Color.BLUE),
//            new OverlayImage(rect1, rect2)), 0, 0));
//  }

  // tests for countMines
  void testCountMines(Tester t) {
    this.reset();

    t.checkExpect(emptyCell.countMines(), 0);
    t.checkExpect(mine.countMines(), 0);
    t.checkExpect(numberCell.countMines(), 1);

    numberCell.neighbors.add(mine);
    numberCell.neighbors.add(mine);

    t.checkExpect(numberCell.countMines(), 3);

    numberCell.neighbors.add(mine);
    numberCell.neighbors.add(mine);

    t.checkExpect(numberCell.countMines(), 5);

    numberCell.neighbors.add(mine);
    numberCell.neighbors.add(mine);
    numberCell.neighbors.add(mine);

    t.checkExpect(numberCell.countMines(), 8);
  }

  // tests for MakeScene
  void testMakeScene(Tester t) {
    this.reset();

    Random rand = new Random(1);
    GameWorld worldTest = new GameWorld(3, 3, 8, rand);
    GameWorld worldTest2 = new GameWorld(100, 100, 100, rand);
    GameWorld worldTest3 = new GameWorld(0, 0, 0, rand);

    t.checkExpect(worldTest.makeScene(), new WorldScene(0, 0));
    t.checkExpect(worldTest2.makeScene(), new WorldScene(0, 0));
    t.checkExpect(worldTest3.makeScene(), new WorldScene(0, 0));

  }
}

