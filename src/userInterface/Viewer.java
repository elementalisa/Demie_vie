/* ******************************************************
 * Project alpha - Composants logiciels 2015.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2015-03-11 buixuan.
 * ******************************************************/
package userInterface;

import tools.HardCodedParameters;

import specifications.ViewerService;
import specifications.WriteService;
import sun.rmi.runtime.Log;
import specifications.PhantomService;
import specifications.ReadService;
import specifications.RequireReadService;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.effect.Lighting;
import javafx.scene.paint.Color;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Rectangle2D;

import java.util.ArrayList;

import com.sun.xml.internal.ws.api.pipe.Engine;

import alpha.Main;

public class Viewer implements ViewerService, RequireReadService{
  private static final int spriteSlowDownRate=HardCodedParameters.spriteSlowDownRate;
  private ReadService data;
  private WriteService dataW;
  private ImageView heroesAvatar;
  private Image heroesSpriteSheet;
  private Image ennemieSpriteSheet;
  private Image pileRouge;
  private Image pileVerte;
  private Image greenGate;
  private Image greenGate2;
  private ImageView greenGateView;
  private ImageView greenGateView2;
  private ImageView pileRougeView;
  private ImageView pileVerteView;
  private ArrayList<Rectangle2D> heroesAvatarViewports;
  private ArrayList<Rectangle2D> ennemieAvatarViewports;
  private ArrayList<Integer> heroesAvatarXModifiers;
  private ArrayList<Integer> heroesAvatarYModifiers;
  private ArrayList<Integer> ennemieAvatarXModifiers;
  private ArrayList<Integer> ennemieAvatarYModifiers;
  private int heroesAvatarViewportIndex;
  private int ennemieAvatarViewportIndex;
  private Button buttonStart;
  private Button buttonRegle;
  EventHandler<MouseEvent> mouseHandler;
  public Viewer(){}
  String panelTpm;

  @Override
  public void bindReadService(ReadService service, WriteService serviceR){
    data=service;
    dataW=serviceR;
  }

  @Override
  public void init(){
	//-----Bouton regles de jeu
	    
    //Yucky hard-conding
    heroesSpriteSheet = new Image("file:src/images/sprite-hero.png");
    heroesAvatar = new ImageView(heroesSpriteSheet);
    ennemieSpriteSheet = new Image("file:src/images/sprite-ennemie.png");
    panelTpm = "Start";

    
    
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
    
    buttonStart = new Button("Start");
    buttonStart.setPrefSize(150, 60);
    buttonStart.setTranslateX(180);
    buttonStart.setTranslateY(220);
    buttonStart.setTextFill(Color.BLACK);
    buttonStart.setStyle("-fx-background-color: \r\n" + 
    		"        linear-gradient(#f2f2f2, #d6d6d6),\r\n" + 
    		"        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\r\n" + 
    		"        linear-gradient(#dddddd 0%, #f6f6f6 50%);\r\n" + 
    		"    -fx-background-radius: 8,7,6;\r\n" + 
    		"    -fx-background-insets: 0,1,2;\r\n" + 
    		"    -fx-text-fill: black;\r\n" + 
    		"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
    
    buttonRegle = new Button("R�gles de jeux");
    buttonRegle.setPrefSize(150, 60);
    buttonRegle.setTranslateX(800);
    buttonRegle.setTranslateY(220);
    buttonRegle.setTextFill(Color.BLACK);
    buttonRegle.setStyle("-fx-background-color: \r\n" + 
    		"        linear-gradient(#f2f2f2, #d6d6d6),\r\n" + 
    		"        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\r\n" + 
    		"        linear-gradient(#dddddd 0%, #f6f6f6 50%);\r\n" + 
    		"    -fx-background-radius: 8,7,6;\r\n" + 
    		"    -fx-background-insets: 0,1,2;\r\n" + 
    		"    -fx-text-fill: black;\r\n" + 
    		"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
  }

  @Override
  public Parent getPanel(){
	  
    Group panel = new Group();
    Group panelStart = new Group();
	  
	buttonStart.setOnMousePressed(mouseHandler);
	mouseHandler = new EventHandler<MouseEvent>() {
		@Override
		public void handle(MouseEvent e) {
			if(e.getEventType() == MouseEvent.MOUSE_PRESSED){
				dataW.setIsStart(true);
				panelTpm = "Game";
			}
		}
	};
	
//	  mouseHandler = new EventHandler<MouseEvent>() {
//		  
//	        @Override
//	        public void handle(MouseEvent mouseEvent) {
//	        	if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
//		          
//	        		pnl=1;
//	        	}
//	        }
//	      };
//	 
	  
    pileRouge = new Image("file:src/images/pile-rouge-2.png");
    pileRougeView = new ImageView(pileRouge);
    pileRougeView.setTranslateX(data.getBatteryEnnemiePosition().x);
    pileRougeView.setTranslateY(data.getBatteryEnnemiePosition().y);
    pileRougeView.setFitHeight(25);
    pileRougeView.setFitWidth(21);
    
    pileVerte = new Image("file:src/images/pile-verte-2.png");
    pileVerteView = new ImageView(pileVerte);
    pileVerteView.setTranslateX(data.getBatteryHealPosition().x);
    pileVerteView.setTranslateY(data.getBatteryHealPosition().y);
    pileVerteView.setFitHeight(25);
    pileVerteView.setFitWidth(21);
    
    
    greenGate = new Image("file:src/images/portal_green_2.png");
    greenGateView = new ImageView(greenGate);
    greenGateView.setTranslateX(84);
    greenGateView.setTranslateY(80);
    greenGateView.setFitHeight(35);
    greenGateView.setFitWidth(35);
    
    greenGate2 = new Image("file:src/images/portal_green_2.png");
    greenGateView2 = new ImageView(greenGate2);
    greenGateView2.setTranslateX(425);
    greenGateView2.setTranslateY(420);
    greenGateView2.setFitHeight(35);
    greenGateView2.setFitWidth(35);
    

	Image brique = new Image("file:src/images/briquesplus.png");
    //Yucky hard-conding
	Rectangle map = new Rectangle(HardCodedParameters.defaultWidth-10,-100+HardCodedParameters.defaultHeight);
	Rectangle mapStart = new Rectangle(HardCodedParameters.defaultWidth-10,-100+HardCodedParameters.defaultHeight);
	
    map.setFill(Color.WHITE);
    map.setStroke(Color.DIMGRAY);
    map.setStrokeWidth(5);
    map.setArcWidth(20);
    map.setArcHeight(20);
    map.setTranslateX(5);
    map.setTranslateY(5);
	Image back_img = new Image("file:src/images/background.png");
    map.setFill(new ImagePattern(back_img, 1, 1, 0.7, 0.9, false));
    
    mapStart.setStroke(Color.DIMGRAY);
    mapStart.setStrokeWidth(5);
    mapStart.setArcWidth(20);
    mapStart.setArcHeight(20);
    mapStart.setTranslateX(5);
    mapStart.setTranslateY(5);
    
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
    obstacle6.setTranslateX(660);
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
    

    Rectangle obstacle13 = new Rectangle(20,20);
    obstacle13.setFill(Color.WHITE);
    obstacle13.setStroke(Color.DIMGRAY);
    obstacle13.setStrokeWidth(0.1);
    obstacle13.setTranslateX(180);
    obstacle13.setTranslateY(531);
    

    obstacle1.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle2.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle2b.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle3.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle3b.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle4.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle4a.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle5.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle5b.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle6.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle7.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle7b.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle8.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle9.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle10.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle11.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle12.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    obstacle13.setFill(new ImagePattern(brique, 1, 1, 0.7, 0.9, false));
    
    Text greets = new Text(-100+HardCodedParameters.defaultWidth/2.,-40+HardCodedParameters.defaultHeight, "Round 1");
    greets.setFont(new Font(50));
    
    int index=heroesAvatarViewportIndex/spriteSlowDownRate;
    heroesAvatar.setViewport(heroesAvatarViewports.get(index));
    heroesAvatar.setScaleX(0.5);
    heroesAvatar.setScaleY(0.5);
    heroesAvatar.setTranslateX(data.getHeroesPosition().x+(-heroesAvatarViewports.get(index).getWidth()/2.+0.5*heroesAvatarXModifiers.get(index)));
    heroesAvatar.setTranslateY(data.getHeroesPosition().y+(-heroesAvatarViewports.get(index).getHeight()/2.+0.5*heroesAvatarYModifiers.get(index)));
    heroesAvatarViewportIndex=(heroesAvatarViewportIndex+1)%(heroesAvatarViewports.size()*spriteSlowDownRate);
   
    panel.getChildren().addAll(map,obstacle1,obstacle2,obstacle2b,obstacle3,obstacle3b,
    		obstacle4,obstacle5,obstacle5b,obstacle6,obstacle4a,obstacle7,obstacle7b,obstacle8,
    		obstacle9, obstacle10,obstacle11,obstacle12,obstacle13,greets,heroesAvatar, pileRougeView, pileVerteView, greenGateView, greenGateView2);
    panelStart.getChildren().addAll(mapStart, buttonStart, buttonRegle);
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
  	  Circle cercleDanger = new Circle();
  	cercleDanger.setCenterX(p.getPosition().x);
  	cercleDanger.setCenterY(p.getPosition().y);
  	cercleDanger.setRadius(50);
  	cercleDanger.setFill(Color.RED);
  	cercleDanger.setStroke(Color.YELLOW);
  	cercleDanger.setStrokeWidth(5);
  	cercleDanger.setOpacity(0.2);
  	  
  	  	panel.getChildren().add(cercleDanger);
    }
    
    if(panelTpm == "Start"){
    	return panelStart;
    }else{
    	return panel;
    }
    
  }
}
