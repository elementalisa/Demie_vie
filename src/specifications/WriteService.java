/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/WriteService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import tools.Position;

public interface WriteService {
  public void setHeroesPosition(Position p);
  public void setStepNumber(int n);
  public void addPhantom(Position p);
  public void addPhantom(Position p, String d);
  public void initWalls();
  public void setBatteryEnnemiePosition(Position p);
  int setHeroesResistance(int i);
  public void setBatteryHealPosition(Position p);
  int setScore(int i);
  public void setNiveau(int i);
  public void setIsStart(boolean s);
  public void setLog(String s);
  
}
