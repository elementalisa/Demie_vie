/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: data/Data.java 2015-03-11 buixuan.
 * ******************************************************/
package data;

import tools.HardCodedParameters;
import tools.Position;

import specifications.DataService;
import specifications.PhantomService;

import data.ia.MoveLeftPhantom;

import java.util.ArrayList;

public class Data implements DataService{
  //private Heroes hercules;
  Position heroesPosition;
  int stepNumber;
  ArrayList<PhantomService> phantoms;

  public Data(){}

  @Override
  public void init(){
    //hercules = new Heroes;
    heroesPosition = new Position(HardCodedParameters.heroesStartX,HardCodedParameters.heroesStartY);
    phantoms = new ArrayList<PhantomService>();
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
}
