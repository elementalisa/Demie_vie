/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import tools.HardCodedParameters;

import specifications.ViewerService;
import sun.rmi.runtime.Log;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.PhantomService;

import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

public class Viewer implements ViewerService, RequireReadService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private ReadService data;
  private ImageView heroesAvatar;
  private Image heroesSpriteSheet;
  private Image ennemieSpriteSheet;
  private ArrayList<Rectangle2D> heroesAvatarViewports;
  private ArrayList<Rectangle2D> ennemieAvatarViewports;
  private ArrayList<Integer> heroesAvatarXModifiers;
  private ArrayList<Integer> heroesAvatarYModifiers;
  private ArrayList<Integer> ennemieAvatarXModifiers;
  private ArrayList<Integer> ennemieAvatarYModifiers;
  private int heroesAvatarViewportIndex;
  private int ennemieAvatarViewportIndex;

  public Viewer(){}

  @Override
  public void bindReadService(ReadService service){
    data=service;
  }

  @Override
  public void init(){
    //Yucky hard-conding
    heroesSpriteSheet = new Image("file:src/images/sprite-hero.png");
    heroesAvatar = new ImageView(heroesSpriteSheet);
    ennemieSpriteSheet = new Image("file:src/images/sprite-ennemie.png");
    
    heroesAvatarViewports = new ArrayList<Rectangle2D>();
    ennemieAvatarViewports = new ArrayList<Rectangle2D>();
    
    heroesAvatarXModifiers = new ArrayList<Integer>();
    heroesAvatarYModifiers = new ArrayList<Integer>();
    ennemieAvatarXModifiers = new ArrayList<Integer>();
    ennemieAvatarYModifiers = new ArrayList<Integer>();

    heroesAvatarViewportIndex=0;
    ennemieAvatarViewportIndex= 0;
    
    //TODO: replace the following with XML loader
    //heroesAvatarViewports.add(new Rectangle2D(301,386,95,192));
    heroesAvatarViewports.add(new Rectangle2D(20,207,25,47));
    heroesAvatarViewports.add(new Rectangle2D(84,207,25,47));
    heroesAvatarViewports.add(new Rectangle2D(148,207,25,47));
    heroesAvatarViewports.add(new Rectangle2D(211,207,25,47));
    heroesAvatarViewports.add(new Rectangle2D(274,207,25,47));
    heroesAvatarViewports.add(new Rectangle2D(336,207,25,47));
    heroesAvatarViewports.add(new Rectangle2D(402,207,25,47));
    heroesAvatarViewports.add(new Rectangle2D(467,207,25,47));
    heroesAvatarViewports.add(new Rectangle2D(532,207,25,47));
    
    //ENNEMIE SPIRITE
    ennemieAvatarViewports.add(new Rectangle2D(20,207,25,47));
    ennemieAvatarViewports.add(new Rectangle2D(84,207,25,47));
    ennemieAvatarViewports.add(new Rectangle2D(148,207,25,47));
    ennemieAvatarViewports.add(new Rectangle2D(211,207,25,47));
    ennemieAvatarViewports.add(new Rectangle2D(274,207,25,47));
    ennemieAvatarViewports.add(new Rectangle2D(336,207,25,47));
    ennemieAvatarViewports.add(new Rectangle2D(402,207,25,47));
    ennemieAvatarViewports.add(new Rectangle2D(467,207,25,47));
    ennemieAvatarViewports.add(new Rectangle2D(532,207,25,47));

    //heroesAvatarXModifiers.add(10);heroesAvatarYModifiers.add(-7);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(0);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(0);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(0);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(0);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(0);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(0);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(0);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(0);
    heroesAvatarXModifiers.add(0);heroesAvatarYModifiers.add(0);
    
    //ENNEMIE MODIFER
    ennemieAvatarXModifiers.add(0);ennemieAvatarYModifiers.add(0);
    ennemieAvatarXModifiers.add(0);ennemieAvatarYModifiers.add(0);
    ennemieAvatarXModifiers.add(0);ennemieAvatarYModifiers.add(0);
    ennemieAvatarXModifiers.add(0);ennemieAvatarYModifiers.add(0);
    ennemieAvatarXModifiers.add(0);ennemieAvatarYModifiers.add(0);
    ennemieAvatarXModifiers.add(0);ennemieAvatarYModifiers.add(0);
    ennemieAvatarXModifiers.add(0);ennemieAvatarYModifiers.add(0);
    ennemieAvatarXModifiers.add(0);ennemieAvatarYModifiers.add(0);
    ennemieAvatarXModifiers.add(0);ennemieAvatarYModifiers.add(0);
  }

  @Override
  public Parent getPanel(){

	Image camembert_img = new Image("file:src/images/briquesplus.png");
    //Yucky hard-conding
    Rectangle map = new Rectangle(HardCodedParameters.defaultWidth-10,-100+HardCodedParameters.defaultHeight);
    map.setFill(Color.WHITE);
    map.setStroke(Color.DIMGRAY);
    map.setStrokeWidth(5);
    map.setArcWidth(20);
    map.setArcHeight(20);
    map.setTranslateX(5);
    map.setTranslateY(5);
	Image back_img = new Image("file:src/images/background.png");
    map.setFill(new ImagePattern(back_img, 1, 1, 0.7, 0.9, false));
    
    Rectangle obstacle1 = new Rectangle(100,120);
    obstacle1.setFill(Color.WHITE);
    obstacle1.setStroke(Color.DIMGRAY);
    obstacle1.setStrokeWidth(0.1);
    obstacle1.setTranslateX(50);
    obstacle1.setTranslateY(60);
//    Image img5 = new Image("images/flasche.jpg");
//    obstacle1.setFill(new ImagePattern(img5));
    
    Rectangle obstacle2 = new Rectangle(100,380);
    obstacle2.setFill(Color.WHITE);
    obstacle2.setStroke(Color.DIMGRAY);
    obstacle2.setStrokeWidth(0.1);
    obstacle2.setTranslateX(80);
    obstacle2.setTranslateY(270);
    Rectangle obstacle2b = new Rectangle(280,100);
    obstacle2b.setFill(Color.WHITE);
    obstacle2b.setStroke(Color.DIMGRAY);
    obstacle2b.setStrokeWidth(0.1);
    obstacle2b.setTranslateX(80);
    obstacle2b.setTranslateY(270);
    
    
    Rectangle obstacle3 = new Rectangle(220,60);
    obstacle3.setFill(Color.WHITE);
    obstacle3.setStroke(Color.DIMGRAY);
    obstacle3.setStrokeWidth(0.1);
    obstacle3.setTranslateX(300);
    obstacle3.setTranslateY(100);
    
    Rectangle obstacle3b = new Rectangle(100,250);
    obstacle3b.setFill(Color.WHITE);
    obstacle3b.setStroke(Color.DIMGRAY);
    obstacle3b.setStrokeWidth(0.1);
    obstacle3b.setTranslateX(450);
    obstacle3b.setTranslateY(100);
    
    Rectangle obstacle4 = new Rectangle(80,80);
    obstacle4.setFill(Color.WHITE);
    obstacle4.setStroke(Color.DIMGRAY);
    obstacle4.setStrokeWidth(0.1);
    obstacle4.setTranslateX(400);
    obstacle4.setTranslateY(400);
    
    Rectangle obstacle5 = new Rectangle(280,80);
    obstacle5.setFill(Color.WHITE);
    obstacle5.setStroke(Color.DIMGRAY);
    obstacle5.setStrokeWidth(0.1);
    obstacle5.setTranslateX(280);
    obstacle5.setTranslateY(530);
    Rectangle obstacle5b = new Rectangle(90,140);
    obstacle5b.setFill(Color.WHITE);
    obstacle5b.setStroke(Color.DIMGRAY);
    obstacle5b.setStrokeWidth(0.1);
    obstacle5b.setTranslateX(280);
    obstacle5b.setTranslateY(530);
    

    Rectangle obstacle6 = new Rectangle(170,370);
    obstacle6.setFill(Color.WHITE);
    obstacle6.setStroke(Color.DIMGRAY);
    obstacle6.setStrokeWidth(0.1);
    obstacle6.setTranslateX(650);
    obstacle6.setTranslateY(60);
    
    Rectangle obstacle4a = new Rectangle(80,80);
    obstacle4a.setFill(Color.WHITE);
    obstacle4a.setStroke(Color.DIMGRAY);
    obstacle4a.setStrokeWidth(0.1);
    obstacle4a.setTranslateX(650);
    obstacle4a.setTranslateY(580);
    
    Rectangle obstacle7 = new Rectangle(170,180);
    obstacle7.setFill(Color.WHITE);
    obstacle7.setStroke(Color.DIMGRAY);
    obstacle7.setStrokeWidth(0.1);
    obstacle7.setTranslateX(780);
    obstacle7.setTranslateY(480);
    Rectangle obstacle7b = new Rectangle(30,380);
    obstacle7b.setFill(Color.WHITE);
    obstacle7b.setStroke(Color.DIMGRAY);
    obstacle7b.setStrokeWidth(0.1);
    obstacle7b.setTranslateX(920);
    obstacle7b.setTranslateY(280);
    
    
    Rectangle obstacle8 = new Rectangle(80,40);
    obstacle8.setFill(Color.WHITE);
    obstacle8.setStroke(Color.DIMGRAY);
    obstacle8.setStrokeWidth(0.1);
    obstacle8.setTranslateX(250);
    obstacle8.setTranslateY(7);
    
    Rectangle obstacle9 = new Rectangle(60,40);
    obstacle9.setFill(Color.WHITE);
    obstacle9.setStroke(Color.DIMGRAY);
    obstacle9.setStrokeWidth(0.1);
    obstacle9.setTranslateX(950);
    obstacle9.setTranslateY(7);
    
    Rectangle obstacle10 = new Rectangle(50,400);
    obstacle10.setFill(Color.WHITE);
    obstacle10.setStroke(Color.DIMGRAY);
    obstacle10.setStrokeWidth(0.1);
    obstacle10.setTranslateX(1043);
    obstacle10.setTranslateY(200);
    
    Rectangle obstacle11 = new Rectangle(20,40);
    obstacle11.setFill(Color.WHITE);
    obstacle11.setStroke(Color.DIMGRAY);
    obstacle11.setStrokeWidth(0.1);
    obstacle11.setTranslateX(500);
    obstacle11.setTranslateY(663);
    
    Rectangle obstacle12 = new Rectangle(20,20);
    obstacle12.setFill(Color.WHITE);
    obstacle12.setStroke(Color.DIMGRAY);
    obstacle12.setStrokeWidth(0.1);
    obstacle12.setTranslateX(8);
    obstacle12.setTranslateY(220);
    

    obstacle1.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle2.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle2b.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle3.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle3b.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle4.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle4a.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle5.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle5b.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle6.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle7.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle7b.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle8.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle9.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle10.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle11.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    obstacle12.setFill(new ImagePattern(camembert_img, 1, 1, 0.7, 0.9, false));
    
    Text greets = new Text(-100+HardCodedParameters.defaultWidth/2.,-40+HardCodedParameters.defaultHeight, "Round 1");
    greets.setFont(new Font(50));
    
    int index=heroesAvatarViewportIndex/spriteSlowDownRate;
    heroesAvatar.setViewport(heroesAvatarViewports.get(index));
    heroesAvatar.setScaleX(0.5);
    heroesAvatar.setScaleY(0.5);
    heroesAvatar.setTranslateX(data.getHeroesPosition().x+(-heroesAvatarViewports.get(index).getWidth()/2.+0.5*heroesAvatarXModifiers.get(index)));
    heroesAvatar.setTranslateY(data.getHeroesPosition().y+(-heroesAvatarViewports.get(index).getHeight()/2.+0.5*heroesAvatarYModifiers.get(index)));
    heroesAvatarViewportIndex=(heroesAvatarViewportIndex+1)%(heroesAvatarViewports.size()*spriteSlowDownRate);
    
    Group panel = new Group();
    panel.getChildren().addAll(map,obstacle1,obstacle2,obstacle2b,obstacle3,obstacle3b,
    		obstacle4,obstacle5,obstacle5b,obstacle6,obstacle4a,obstacle7,obstacle7b,obstacle8,obstacle9, obstacle10,obstacle11,obstacle12,greets,heroesAvatar);

    for (PhantomService p:data.getPhantoms()){
   
    	//ENNEMIE
    	ImageView ennemieAvatar;
    	int ennemieIndex=ennemieAvatarViewportIndex/spriteSlowDownRate;
    	ennemieAvatar = new ImageView(ennemieSpriteSheet);
        ennemieAvatar.setViewport(heroesAvatarViewports.get(ennemieIndex));
  	  	ennemieAvatar.setScaleX(0.5);
  	  	ennemieAvatar.setScaleY(0.5);
  	  	ennemieAvatar.setTranslateX(p.getPosition().x+(-ennemieAvatarViewports.get(ennemieIndex).getWidth()/2.+0.5*ennemieAvatarXModifiers.get(ennemieIndex)));
  	  	ennemieAvatar.setTranslateY(p.getPosition().y+(-ennemieAvatarViewports.get(ennemieIndex).getHeight()/2.+0.5*ennemieAvatarXModifiers.get(ennemieIndex)));
  	  	ennemieAvatarViewportIndex=(ennemieAvatarViewportIndex+1)%(ennemieAvatarViewports.size()*spriteSlowDownRate);
  	  
  	  	panel.getChildren().add(ennemieAvatar);
    }

    return panel;
  }
}
