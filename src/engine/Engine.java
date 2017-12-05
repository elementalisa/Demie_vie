/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import tools.HardCodedParameters;
import tools.User;
import tools.Wall;
import userInterface.Viewer;
import tools.Position;

import specifications.EngineService;
import specifications.DataService;
import specifications.RequireDataService;
import specifications.PhantomService;

import java.util.Timer;
import java.util.TimerTask;
import java.util.Random;

public class Engine implements EngineService, RequireDataService{
  private Timer engineClock;
  private Timer engineClockSpwanEnnemie;
  private DataService data;
  private User.COMMAND command;
  private Random gen;
  private boolean startMove;

  public Engine(){}

  @Override
  public void bindDataService(DataService service){
    data=service;
  }
  
  @Override
  public void init(){
    engineClock = new Timer();
    command = User.COMMAND.NONE;
    gen = new Random();
    data.initWalls();
  }

  @Override
  public void start(){
	  
	  
	  
      engineClockSpwanEnnemie = new Timer();
      engineClockSpwanEnnemie.schedule(new TimerTask() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				spawnPhantom();
			}
		}, 0,10000);
    engineClock.schedule(new TimerTask(){
      public void run() {
        //System.out.println("Game step #"+data.getStepNumber()+": checked.");
    	 //System.out.println(data.getWalls().size() + "SIZE !");
    	//System.out.println("Hero X position : " + data.getHeroesPosition().x + "Hero Y position : " + data.getHeroesPosition().y);
    	testChocBatterie();
    	testContactZoneRadiation();
        if (command==User.COMMAND.LEFT){
	    	if(!wallCollisionLeft()){
	    		heroesMoveLeft();
	    	}
        }
        if (command==User.COMMAND.RIGHT){
        	if(!wallCollisionRight()){
        	heroesMoveRight();
        	}
        }
        if (command==User.COMMAND.UP){
        	if(!wallCollisionUp()){
        	heroesMoveUp();
        	}
        }
        if (command==User.COMMAND.DOWN){
        	if(!wallCollisionDown()){
        		heroesMoveDown();
        	}
        }
        //System.out.println("Game step #"+data.getStepNumber()+": checked.");
        //if (gen.nextInt(100)<3) spawnPhantom();

        for (PhantomService p:data.getPhantoms()){
        	
    		//moveRight(p);
        	if(p.getDep() == "Right"){
        		moveRight(p);
        	}else if(p.getDep() == "Up"){
        		moveUp(p);
        	}else if(p.getDep() == "Down"){
        		moveDown(p);
        	}else if(p.getDep() == "Left"){
        		moveLeft(p);
        	}
//        	switch (gen.nextInt(4)){
//            case 0:
//              moveRight(p);
//              break;
//            case 1:
//              moveLeft(p);
//              break;
//            case 2:
//              moveDown(p);
//              break;
//            default:
//              moveUp(p);
//              break;
//          }
        }

        command = User.COMMAND.NONE;
        //System.out.println(" X " + data.getHeroesPosition().x + " Y : " + data.getHeroesPosition().y);
        data.setStepNumber(data.getStepNumber()+1);
        
        
        
      }
    },0,90);
  }

  @Override
  public void stop(){
    engineClock.cancel();
  }
  
  public void testChocBatterie(){
        if (data.getBatteryEnnemiePosition().x-5 < data.getHeroesPosition().x && data.getBatteryEnnemiePosition().x+25 > data.getHeroesPosition().x){
        	if(data.getBatteryEnnemiePosition().y < data.getHeroesPosition().y+50 && data.getBatteryEnnemiePosition().y > data.getHeroesPosition().y-30){
        		  data.setBatteryEnnemiePosition(new Position(50, 100));
        	}
        }
  }
  
  public void testContactZoneRadiation(){
	  for (int i = 0; i<data.getPhantoms().size(); i++){
	        if (data.getPhantoms().get(i).getPosition().x-50 < data.getHeroesPosition().x && data.getPhantoms().get(i).getPosition().x+50 < data.getHeroesPosition().x){
	        	if(data.getPhantoms().get(i).getPosition().y < data.getHeroesPosition().y+50 && data.getPhantoms().get(i).getPosition().y > data.getHeroesPosition().y-60){
		            data.setHeroesResistance(data.getHeroesResistance()-1);
		            System.out.println(data.getHeroesResistance());
	        	}
	        }
	      }
}
  
  @Override
  public void setHeroesCommand(User.COMMAND c){
    command=c;
  }
  
//  private boolean wallCollision(){
//      for(Wall p: data.getWalls()){
//      	if(data.getHeroesPosition().y == p.getPosition().getMinY() +10 && data.getHeroesPosition().x >= p.getPosition().getMinX() && data.getHeroesPosition().x <= p.getPosition().getMaxX()){
//      		System.out.println("Il est en haut!!!!!");
//      		return true;
//      	}
//      	if(data.getHeroesPosition().y >= p.getPosition().getMinY() && data.getHeroesPosition().x == p.getPosition().getMinX() +10 && data.getHeroesPosition().y <= p.getPosition().getMaxY()){
//      		System.out.println("Il est � gauche!!!!!");
//      		return true;
//      	}
//      	if(data.getHeroesPosition().x >= p.getPosition().getMinX() && data.getHeroesPosition().x <= p.getPosition().getMaxX() && data.getHeroesPosition().y == p.getPosition().getMaxY() +10){
//      		System.out.println("Il est en bas!!!!!");
//      		return true;
//      	}
//      	if(data.getHeroesPosition().x == p.getPosition().getMaxX() +10 && data.getHeroesPosition().y >= p.getPosition().getMinY() && data.getHeroesPosition().y <= p.getPosition().getMaxY()){
//      		System.out.println("Il est � droite");
//      		return true;
//      	}
//      }
//      return false;
//  }
  
  private boolean wallCollisionDown(){
      for(Wall p: data.getWalls()){
      	if(data.getHeroesPosition().y == p.getPosition().getMinY() -10 && data.getHeroesPosition().x >= p.getPosition().getMinX() && data.getHeroesPosition().x <= p.getPosition().getMaxX()){
      		return true;
      	}
      }
      return false;
  }
  
  private boolean wallCollisionRight(){
      for(Wall p: data.getWalls()){
      	if(data.getHeroesPosition().y >= p.getPosition().getMinY() && data.getHeroesPosition().x == p.getPosition().getMinX() -10 && data.getHeroesPosition().y <= p.getPosition().getMaxY()){
      		return true;
      	}
      }
      return false;
  }
  

  
  private boolean wallCollisionUp(){
      for(Wall p: data.getWalls()){
      	if(data.getHeroesPosition().x >= p.getPosition().getMinX() && data.getHeroesPosition().x <= p.getPosition().getMaxX() && data.getHeroesPosition().y == p.getPosition().getMaxY() +10){
      		return true;
      	}
      }
      return false;
  }
  
  private boolean wallCollisionLeft(){
      for(Wall p: data.getWalls()){
      	if(data.getHeroesPosition().x == p.getPosition().getMaxX() +10 && data.getHeroesPosition().y >= p.getPosition().getMinY() && data.getHeroesPosition().y <= p.getPosition().getMaxY()){
      		return true;
      	}
      }
      return false;
  }

  private void heroesMoveLeft(){
	  if(data.getHeroesPosition().x >= 20){
		  //if(data.getHeroesPosition().equals(Viewer.class.getMethod(sc, parameterTypes))){
			  data.setHeroesPosition(new Position(data.getHeroesPosition().x-10,data.getHeroesPosition().y));
		  //}
	  }
  }
  
  private void heroesMoveRight(){
	  if(data.getHeroesPosition().x <= 1080){
		  data.setHeroesPosition(new Position(data.getHeroesPosition().x+10,data.getHeroesPosition().y));
	  }
  }
  
  private void heroesMoveUp(){
	  if(data.getHeroesPosition().y >= 30){
		  data.setHeroesPosition(new Position(data.getHeroesPosition().x,data.getHeroesPosition().y-10));
	  }
  }
  
  private void heroesMoveDown(){
	  if(data.getHeroesPosition().y <= 680){
		  data.setHeroesPosition(new Position(data.getHeroesPosition().x,data.getHeroesPosition().y+10));
	  }
  }

  private void spawnPhantom(){
    int x=(int)(HardCodedParameters.defaultWidth*.9);
    int y=0;
    boolean cont=true;
    while (cont) {
      y=(int)(gen.nextInt((int)(HardCodedParameters.defaultHeight*.6))+HardCodedParameters.defaultHeight*.1);
      cont=false;
      for (PhantomService p:data.getPhantoms()){
        if (p.getPosition().equals(new Position(x,y))) cont=true;
      }
    }
    int tempRandom = (Math.random() <= 0.5) ? 1 : 2;
	switch (tempRandom){
	  case 1:
		 data.addPhantom(new Position(100,100),"Down");
	    break;
	  case 2:
		data.addPhantom(new Position(440,440),"Down");
	    break;
	}
  }
  
  private void randomRight(PhantomService p){
		switch (gen.nextInt(3)){
        case 0:
      	  p.setDep("Up");
          return;
        case 1:
      	  p.setDep("Left");
          return;
        default:
      	  p.setDep("Down");
          return;
		}
  }
  
  private void randomLeft(PhantomService p){
		switch (gen.nextInt(3)){
        case 0:
      	  p.setDep("Up");
          return;
        case 1:
      	  p.setDep("Right");
          return;
        default:
      	  p.setDep("Down");
          return;
		}
  }
  
  private void randomUp(PhantomService p){
		switch (gen.nextInt(3)){
        case 0:
      	  p.setDep("Right");
          return;
        case 1:
      	  p.setDep("Left");
          return;
        default:
      	  p.setDep("Down");
          return;
		}
  }
  
  private void randomDown(PhantomService p){
		switch (gen.nextInt(3)){
        case 0:
      	  p.setDep("Down");
          return;
        case 1:
      	  p.setDep("Left");
          return;
        default:
      	  p.setDep("Right");
          return;
		}
  }

  private void moveLeft(PhantomService p){
	  for(Wall w: data.getWalls()){
	    	if(p.getPosition().x == w.getPosition().getMaxX() +10 && p.getPosition().y >= w.getPosition().getMinY() && p.getPosition().y <= w.getPosition().getMaxY()){
	    		p.setPosition(new Position(p.getPosition().x + 10, p.getPosition().y));
	      		randomLeft(p);
	    	}
	    }
      
	  if(!(p.getPosition().x >= 20)){
		  randomLeft(p);
	  }else{
		  p.setPosition(new Position(p.getPosition().x-10,p.getPosition().y));
	  }
  }

  private void moveRight(PhantomService p){
	 
      for(Wall w: data.getWalls()){
      	if(p.getPosition().y >= w.getPosition().getMinY() && p.getPosition().x +10 == w.getPosition().getMinX() && p.getPosition().y <= w.getPosition().getMaxY()){
      		p.setPosition(new Position(p.getPosition().x - 10, p.getPosition().y));
      		randomRight(p);
      	}
      }
	  if(!(p.getPosition().x <= 1080)){
		  randomRight(p);
		  
	  }else{
		  p.setPosition(new Position(p.getPosition().x+10,p.getPosition().y));
	  }
  }

  private void moveUp(PhantomService p){
	  
      for(Wall w: data.getWalls()){
        	if(p.getPosition().x >= w.getPosition().getMinX() && p.getPosition().x <= w.getPosition().getMaxX() && p.getPosition().y == w.getPosition().getMaxY()+10){
        		p.setPosition(new Position(p.getPosition().x, p.getPosition().y + 10));
        		randomUp(p);
        	}
        }
	  if(!(p.getPosition().y >= 30 )){
		  randomUp(p);
	  }else{
		  p.setPosition(new Position(p.getPosition().x,p.getPosition().y-10));
	  }
    
  }

  private void moveDown(PhantomService p){
      for(Wall w: data.getWalls()){
        	if(p.getPosition().y == w.getPosition().getMinY() -10 && p.getPosition().x >= w.getPosition().getMinX() && p.getPosition().x <= w.getPosition().getMaxX()){
        		p.setPosition(new Position(p.getPosition().x, p.getPosition().y -10));
        		randomDown(p);
        	}
        }
	  if(!(p.getPosition().y  <= 680 )){
		  randomDown(p);
	  }else{
		  p.setPosition(new Position(p.getPosition().x,p.getPosition().y+10));
	  }
  }
}
