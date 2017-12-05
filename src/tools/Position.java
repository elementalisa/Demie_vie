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
  
  
//Return	> 0 if the x of object in parameter is greater than this position
	//		< 0 if the x of object in parameter is less than this position
	//		= 0 if x are equals
	public double compareX (Position position) {
		return position.x - this.x;
	}


	//Return	> 0 if the y of object in parameter is greater than this position
	//		< 0 if the y of object in parameter is less than this position
	//		= 0 if y are equals
	public double compareY (Position position) {
		return position.y - this.y;
	}

}
