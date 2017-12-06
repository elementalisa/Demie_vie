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
import java.util.ArrayList;
import java.util.Random;

public class Engine implements EngineService, RequireDataService{
  private Timer engineClock;
  private Timer engineClockSpwanEnnemie;
  private Timer engineClockNiveau;
  private Timer launcherClock;
  private DataService data;
  private User.COMMAND command;
  private Random gen;
  private boolean startMove;
  private int batteryEnnemieIncr;
  private int batteryHealIncr;
  private int spawn;
  private int scoreIteration;
  private int niveauIteration;

  public Engine(){}

  @Override
  public void bindDataService(DataService service){
    data=service;
  }
  
  @Override
  public void init(){
	batteryEnnemieIncr = 0;
	batteryHealIncr = 0;
	engineClock = new Timer();
	engineClockNiveau = new Timer();
	launcherClock = new Timer();
	engineClockSpwanEnnemie = new Timer();
	command = User.COMMAND.NONE;
	gen = new Random();
	data.initWalls();
	spawn = 20000;
	scoreIteration = 0;
	niveauIteration = 1;
  }

  @Override
  public void start(){
	 
	launcherClock.schedule(new TimerTask() {
		
		@Override
		public void run() {
			System.out.println("Hello");
			if(data.getIsStart() == true) {
			  	engineClockSpwanEnnemie.schedule(new TimerTask() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
					spawnPhantom();
				}
				}, 0,spawn);
				
				  	engineClockNiveau.schedule(new TimerTask() {
					@Override
					public void run() {
						niveauIteration ++;
						data.setNiveau(niveauIteration);
						System.out.println("NIVEAU = " + niveauIteration);
					}
				}, 0,10000);
				
				
			    engineClock.schedule(new TimerTask(){
			      public void run() {
		
			    	data.setScore(data.getScore()+1);
			    	System.out.println("Score" + data.getScore());
			    	batteryCollision();
			    	testContactZoneRadiation();
			    	if(data.getHeroesResistance() >=300){
						data.setHeroesResistance(300);
					}
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
			        }
		
			        command = User.COMMAND.NONE;
			        //System.out.println(" X " + data.getHeroesPosition().x + " Y : " + data.getHeroesPosition().y);
			        data.setStepNumber(data.getStepNumber()+1);
			      }
			    },0,90);
				data.setIsStart(false);
			}
		}
	}, 0, 1000);
  }

  @Override
  public void stop(){
    engineClock.cancel();
  }
  
  public void batteryCollision(){
      if (data.getBatteryEnnemiePosition().x-5 < data.getHeroesPosition().x && data.getBatteryEnnemiePosition().x+25 > data.getHeroesPosition().x){
      	if(data.getBatteryEnnemiePosition().y < data.getHeroesPosition().y+10 && data.getBatteryEnnemiePosition().y > data.getHeroesPosition().y-30){
      		changeBatteryEnnemiePosition();
			data.setScore(data.getScore()+5);
  			int sizetpm = data.getPhantoms().size() ;
  			if(!(data.getPhantoms().isEmpty())){
  				data.setScore(data.getScore()+20);
  	  			int randomTpm = gen.nextInt(sizetpm);
  	  			PhantomService ennemie = data.getPhantoms().get(randomTpm);
  	  			data.getPhantoms().remove(ennemie);
  			}else{
  				
  			}
      	}
      }
      if (data.getBatteryHealPosition().x-5 < data.getHeroesPosition().x && data.getBatteryHealPosition().x+25 > data.getHeroesPosition().x){
        	if(data.getBatteryHealPosition().y < data.getHeroesPosition().y+10 && data.getBatteryHealPosition().y > data.getHeroesPosition().y-30){
        		changeBatteryHealPosition();
        		data.setHeroesResistance(data.getHeroesResistance()+50);
  				data.setScore(data.getScore()+20);
        	}
        }
}
  
  public void testContactZoneRadiation(){
	  for (int i = 0; i<data.getPhantoms().size(); i++){
	        if (data.getPhantoms().get(i).getPosition().x-60 < data.getHeroesPosition().x && data.getPhantoms().get(i).getPosition().x+60 > data.getHeroesPosition().x){
	        	if(data.getPhantoms().get(i).getPosition().y-60 < data.getHeroesPosition().y && data.getPhantoms().get(i).getPosition().y+60 > data.getHeroesPosition().y){
		            data.setHeroesResistance(data.getHeroesResistance()-1);
	        	}
	        }
	      }
}
  
  @Override
  public void setHeroesCommand(User.COMMAND c){
    command=c;
  }
  
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
			  data.setHeroesPosition(new Position(data.getHeroesPosition().x-10,data.getHeroesPosition().y));
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
		 data.addPhantom(new Position(100,100),"Right");
	    break;
	  case 2:
		data.addPhantom(new Position(440,440),"Right");
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
  
  public void changeBatteryEnnemiePosition(){
	  ArrayList<Position> positionTpm = data.getAllBatterysEnnemiePosition();
	  if(batteryEnnemieIncr >= positionTpm.size() -1){
		  batteryEnnemieIncr =0;
	  }else{
		  batteryEnnemieIncr++; 
	  }
	  Position newPosTpm = positionTpm.get(batteryEnnemieIncr);
	  data.setBatteryEnnemiePosition(newPosTpm);
  }
  
  public void changeBatteryHealPosition(){
	  ArrayList<Position> positionTpm = data.getAllBatterysHealPosition();
	  if(batteryHealIncr >= positionTpm.size() -1){
		  batteryHealIncr =0;
	  }else{
		  batteryHealIncr++; 
	  }
	  Position newPosTpm = positionTpm.get(batteryHealIncr);
	  data.setBatteryHealPosition(newPosTpm);
  }
}
