/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: specifications/ReadService.java 2015-03-11 buixuan.
 * ******************************************************/
package specifications;

import tools.Position;
import tools.Wall;

import java.util.ArrayList;
import java.util.List;

public interface ReadService {
  public Position getHeroesPosition();
  public int getStepNumber();
  public ArrayList<PhantomService> getPhantoms();
  public ArrayList<Wall> getWalls();
  public Position getBatteryEnnemiePosition();
int getHeroesResistance();
  public ArrayList<Position> getAllBatterysEnnemiePosition();
  public ArrayList<Position> getAllBatterysHealPosition();
  public Position getBatteryHealPosition();
  int getScore();
  int getNiveau();
  public boolean getIsStart();
  public String getLog();
boolean getMusic();
boolean getGameOver();
boolean getReplay();
}
