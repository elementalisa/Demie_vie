/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: engine/Engine.java 2015-03-11 buixuan.
 * ******************************************************/
package engine;

import tools.HardCodedParameters;
import tools.User;
import userInterface.Viewer;
import userInterface.start;
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
  private DataService data;
  private User.COMMAND command;
  private Random gen;

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
  }

  @Override
  public void start(){
    engineClock.schedule(new TimerTask(){
      public void run() {
       // System.out.println("Game step #"+data.getStepNumber()+": checked.");
        
        if (command==User.COMMAND.LEFT) heroesMoveLeft();
        if (command==User.COMMAND.RIGHT) heroesMoveRight();
        if (command==User.COMMAND.UP) heroesMoveUp();
        if (command==User.COMMAND.DOWN) heroesMoveDown();
        command = User.COMMAND.NONE;

        data.setStepNumber(data.getStepNumber()+1);
      }
    },0,HardCodedParameters.enginePaceMillis);
  }

  @Override
  public void stop(){
    engineClock.cancel();
  }

  @Override
  public void setHeroesCommand(User.COMMAND c){
    command=c;
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
    data.addPhantom(new Position(x,y));
  }
  
  
  
  

  private void moveLeft(PhantomService p){
    p.setPosition(new Position(p.getPosition().x-10,p.getPosition().y));
  }

  private void moveRight(PhantomService p){
    p.setPosition(new Position(p.getPosition().x+10,p.getPosition().y));
  }

  private void moveUp(PhantomService p){
    p.setPosition(new Position(p.getPosition().x,p.getPosition().y-10));
  }

  private void moveDown(PhantomService p){
    p.setPosition(new Position(p.getPosition().x,p.getPosition().y+10));
  }
}
