/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: tools/Position.java 2015-03-11 buixuan.
 * ******************************************************/
package tools;

public class Position {
  public double x,y;
  public String dep;
  public Position(double x, double y){
    this.x=x;
    this.y=y;
  }
  
  public Position(double x, double y, String dep){
	    this.x=x;
	    this.y=y;
	    this.dep = dep;
  }
}
