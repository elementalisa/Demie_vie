/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import tools.HardCodedParameters;
import tools.Position;
import tools.Wall;
import tools.WallPosition;
import specifications.DataService;
import specifications.PhantomService;
import specifications.WallService;
import data.ia.MoveLeftPhantom;

import java.util.ArrayList;

public class Data implements DataService{
	
  //private Heroes hercules;
  Position heroesPosition;
  int stepNumber;
  ArrayList<PhantomService> phantoms;
  ArrayList<Wall> walls;

  public Data(){}

  @Override
  public void init(){
    //hercules = new Heroes;
    heroesPosition = new Position(HardCodedParameters.heroesStartX,HardCodedParameters.heroesStartY);
    phantoms = new ArrayList<PhantomService>();
    walls = new ArrayList<Wall>();
    initWalls();
    stepNumber = 0;
  }

  @Override
  public Position getHeroesPosition(){ return heroesPosition; }

  @Override
  public int getStepNumber(){ return stepNumber; }

  @Override
  public ArrayList<PhantomService> getPhantoms(){ return phantoms; }

  @Override
  public void setHeroesPosition(Position p) { heroesPosition=p; }
  
  @Override
  public void setStepNumber(int n){ stepNumber=n; }

  @Override
  public void addPhantom(Position p) { phantoms.add(new MoveLeftPhantom(p)); }
  
  public ArrayList<Wall> getWalls(){
	  return walls;
  }
  
  public void initWalls(){
	  Wall wall1 = new Wall(new WallPosition(50, 150, 60, 180));
	  Wall wall2 = new Wall(new WallPosition(80, 180, 270, 650));
	  Wall wall2b = new Wall(new WallPosition(80, 360, 270, 370));
	  Wall wall3 = new Wall(new WallPosition(300, 520, 100, 160));
	  Wall wall3b = new Wall(new WallPosition(450, 550, 100, 350));
	  Wall wall4 = new Wall(new WallPosition(400, 480, 400, 480));
	  Wall wall5 = new Wall(new WallPosition(280, 560, 530, 610));
	  Wall wall5b = new Wall(new WallPosition(280, 370, 530, 670));
	  Wall wall6 = new Wall(new WallPosition(650, 820, 60, 430));
	  Wall wall4a = new Wall(new WallPosition(650, 730, 580, 660));
	  Wall wall7 = new Wall(new WallPosition(780, 950, 480, 660));
	  Wall wall7b = new Wall(new WallPosition(920, 950, 280, 660));
	  
	  
	  walls.add(wall1);
	  
	  walls.add(wall2);
	  walls.add(wall2b);
	  
	  walls.add(wall3);
	  walls.add(wall3b);
	  
	  walls.add(wall4);
	  
	  walls.add(wall5);
	  walls.add(wall5b);
	  
	  walls.add(wall6);
	  
	  walls.add(wall4a);
	  
	  walls.add(wall7);
	  walls.add(wall7b);
	  
	  
  }
}
