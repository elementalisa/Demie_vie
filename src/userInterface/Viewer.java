/* ******************************************************
 * Project alpha - Composants logiciels 2017.
 * Copyright (C) 2015 <Binh-Minh.Bui-Xuan@ens-lyon.org>.
 * GPL version>=3 <http://www.gnu.org/licenses/>.
 * $Id: userInterface/Viewer.java 2017-03-11 buixuan.
 * ******************************************************/
package userInterface;

import tools.HardCodedParameters;

import specifications.ViewerService;
import sun.rmi.runtime.Log;
import specifications.PhantomService;
import specifications.ReadService;
import specifications.RequireReadService;


import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;

import javafx.scene.paint.ImagePattern;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.scene.text.Font;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
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
  Button btn_start ;
  Button btn_regle ;
  Button btn_pause ;
  Button btn_quite ;
  Button btn_info ;
  Button btn_son ;
  Group panel; 
  String color = "#808080";  
  private static int pnl;
  EventHandler<MouseEvent> mouseHandler;
  private int ennemieAvatarViewportIndex;
  private ImageView bg;
	private Image img;
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
    //-----Bouton regles de jeu
    btn_regle = new Button("R�gles de jeux");
    btn_regle.setPrefSize(150, 60);
    btn_regle.setTranslateX(800);
    btn_regle.setTranslateY(220);
    btn_regle.setTextFill(Color.BLACK);
    btn_regle.setStyle("-fx-background-color: \r\n" + 
    		"        linear-gradient(#f2f2f2, #d6d6d6),\r\n" + 
    		"        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\r\n" + 
    		"        linear-gradient(#dddddd 0%, #f6f6f6 50%);\r\n" + 
    		"    -fx-background-radius: 8,7,6;\r\n" + 
    		"    -fx-background-insets: 0,1,2;\r\n" + 
    		"    -fx-text-fill: black;\r\n" + 
    		"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
    //-----Bouton Start
    btn_start = new Button("Nouveau jeux");
    btn_start.setPrefSize(150, 60);
    btn_start.setTranslateX(180);
    btn_start.setTranslateY(220);
    btn_start.setTextFill(Color.BLACK);
    btn_start.setStyle("-fx-background-color: \r\n" + 
    		"        linear-gradient(#f2f2f2, #d6d6d6),\r\n" + 
    		"        linear-gradient(#fcfcfc 0%, #d9d9d9 20%, #d6d6d6 100%),\r\n" + 
    		"        linear-gradient(#dddddd 0%, #f6f6f6 50%);\r\n" + 
    		"    -fx-background-radius: 8,7,6;\r\n" + 
    		"    -fx-background-insets: 0,1,2;\r\n" + 
    		"    -fx-text-fill: black;\r\n" + 
    		"    -fx-effect: dropshadow( three-pass-box , rgba(0,0,0,0.6) , 5, 0.0 , 0 , 1 );");
    pnl=0;
    ennemieAvatarXModifiers = new ArrayList<Integer>();
    ennemieAvatarYModifiers = new ArrayList<Integer>();

    heroesAvatarViewportIndex=0;
    ennemieAvatarViewportIndex= 0;
    
 
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
	  Rectangle map1 = new Rectangle(HardCodedParameters.defaultWidth-10,-100+HardCodedParameters.defaultHeight);
	   
	   // map1.setFill(Color.WHITE);
	    map1.setStroke(Color.DIMGRAY);
	    map1.setStrokeWidth(5);
	    map1.setArcWidth(20);
	    map1.setArcHeight(20);
	    map1.setTranslateX(5);
	    map1.setTranslateY(5);
	    img = new Image("file:src/images/interface.png");
	    bg =new ImageView(img);
	 
	    btn_start.setOnMousePressed(mouseHandler);
	   

	     


	      
	    // ----------Afficher POPUP --------------------
	    
	    
	 btn_regle.setOnAction(
	            new EventHandler<ActionEvent>() {
	                @Override
	                public void handle(ActionEvent event) {
	                    final Stage dialog = new Stage();
	                    dialog.initModality(Modality.NONE);
	                    Window primaryStage = null;
	                  
						dialog.initOwner(primaryStage);
	                    VBox dialogVbox = new VBox(20);
	                    dialogVbox.getChildren().add(new Text("Comment jouer....."));
	                    Scene dialogScene = new Scene(dialogVbox, 900, 600);
	                    dialog.setScene(dialogScene);
	                    dialog.show();
	                }
	             });
	    //  ------------*-------------
	    
	    
	   
	 // EventHandler<MouseEvent> 
	  mouseHandler = new EventHandler<MouseEvent>() {
		  
	        @Override
	        public void handle(MouseEvent mouseEvent) {
	        	if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
		          
	        		pnl=1;
	        	}
	        }
	      };
	  
	  Text text1 = new Text(120, 220, "Bienvenue au jeux de plateforme de Survie");
      text1.setFont(new Font(45));
      text1.setFill(Color.BLACK);
      Text t = new Text();
      t.setText("This is a text sample");
      
     
	    Group panel1 = new Group();
	    panel1.getChildren().addAll(map1,bg,btn_start,btn_regle);
	    
	  
		

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
    
    Text greets = new Text(-100+HardCodedParameters.defaultWidth/2.,-40+HardCodedParameters.defaultHeight, "Round 1");
    greets.setFont(new Font(50));
    //btn pause
    btn_pause = new Button("Pause");
    btn_pause.setPrefSize(140, 55);
    //btn_pause.setTranslateX(180);
    //btn_pause.setTranslateY(220);
    btn_pause.setTextFill(Color.BLACK);
    btn_pause.setTranslateX(20);
    btn_pause.setTranslateY(820);
    btn_pause.setStyle("-fx-background-color: \r\n" + 
    		"        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n" + 
    		"        linear-gradient(#020b02, #3a3a3a),\r\n" + 
    		"        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n" + 
    		"        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n" + 
    		"        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n" + 
    		"    -fx-background-insets: 0,1,4,5,6;\r\n" + 
    		"    -fx-background-radius: 9,8,5,4,3;\r\n" + 
    		"    -fx-padding: 15 30 15 30;\r\n" + 
    		"    -fx-font-family: \"Helvetica\";\r\n" + 
    		"    -fx-font-size: 18px;\r\n" + 
    		"    -fx-font-weight: bold;\r\n" + 
    		"    -fx-text-fill: white;\r\n" + 
    		"    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);");
  //btn quite
    btn_quite = new Button("Quiter");
    btn_quite.setPrefSize(140, 55);
   // btn_quite.setTranslateX(180);
   // btn_quite.setTranslateY(220);
    btn_quite.setTextFill(Color.BLACK);
    btn_quite.setTranslateX(180);
    btn_quite.setTranslateY(820);
    btn_quite.setStyle("-fx-background-color: \r\n" + 
    		"        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n" + 
    		"        linear-gradient(#020b02, #3a3a3a),\r\n" + 
    		"        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n" + 
    		"        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n" + 
    		"        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n" + 
    		"    -fx-background-insets: 0,1,4,5,6;\r\n" + 
    		"    -fx-background-radius: 9,8,5,4,3;\r\n" + 
    		"    -fx-padding: 15 30 15 30;\r\n" + 
    		"    -fx-font-family: \"Helvetica\";\r\n" + 
    		"    -fx-font-size: 18px;\r\n" + 
    		"    -fx-font-weight: bold;\r\n" + 
    		"    -fx-text-fill: white;\r\n" + 
    		"    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);");
  //btn son
    btn_son = new Button("Son");
    btn_son.setPrefSize(140, 55);
   // btn_son.setTranslateX(340);
   // btn_son.setTranslateY(220);
    btn_son.setTextFill(Color.BLACK);
    btn_son.setTranslateX(340);
    btn_son.setTranslateY(820);
    btn_son.setStyle("-fx-background-color: \r\n" + 
    		"        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n" + 
    		"        linear-gradient(#020b02, #3a3a3a),\r\n" + 
    		"        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n" + 
    		"        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n" + 
    		"        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n" + 
    		"    -fx-background-insets: 0,1,4,5,6;\r\n" + 
    		"    -fx-background-radius: 9,8,5,4,3;\r\n" + 
    		"    -fx-padding: 15 30 15 30;\r\n" + 
    		"    -fx-font-family: \"Helvetica\";\r\n" + 
    		"    -fx-font-size: 18px;\r\n" + 
    		"    -fx-font-weight: bold;\r\n" + 
    		"    -fx-text-fill: white;\r\n" + 
    		"    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);");
 
  //btn info
    btn_info = new Button("Infos");
    btn_info.setPrefSize(140, 55);
   // btn_info.setTranslateX(180);
   // btn_info.setTranslateY(220);
    btn_info.setTextFill(Color.BLACK);
    btn_info.setTranslateX(500);
    btn_info.setTranslateY(820);
    btn_info.setStyle("-fx-background-color: \r\n" + 
    		"        linear-gradient(#686868 0%, #232723 25%, #373837 75%, #757575 100%),\r\n" + 
    		"        linear-gradient(#020b02, #3a3a3a),\r\n" + 
    		"        linear-gradient(#9d9e9d 0%, #6b6a6b 20%, #343534 80%, #242424 100%),\r\n" + 
    		"        linear-gradient(#8a8a8a 0%, #6b6a6b 20%, #343534 80%, #262626 100%),\r\n" + 
    		"        linear-gradient(#777777 0%, #606060 50%, #505250 51%, #2a2b2a 100%);\r\n" + 
    		"    -fx-background-insets: 0,1,4,5,6;\r\n" + 
    		"    -fx-background-radius: 9,8,5,4,3;\r\n" + 
    		"    -fx-padding: 15 30 15 30;\r\n" + 
    		"    -fx-font-family: \"Helvetica\";\r\n" + 
    		"    -fx-font-size: 18px;\r\n" + 
    		"    -fx-font-weight: bold;\r\n" + 
    		"    -fx-text-fill: white;\r\n" + 
    		"    -fx-effect: dropshadow( three-pass-box , rgba(255,255,255,0.2) , 1, 0.0 , 0 , 1);");
 
    
    
    // console log
   // Rectangle map2 = new Rectangle(260,700);
    TextArea map2 = new TextArea();
    map2.setTranslateX(1100);
    map2.setTranslateY(5);
   // map2.setFill(Color.rgb(215, 217, 224));
   // map2.setStroke(Color.DIMGRAY);
   // map2.setStrokeWidth(5);
  //  map2.setArcWidth(18);
   /// map2.setArcHeight(18);
    /***************************/
    
    
    
  

   

    //Text Box 1
    
    map2.setPrefWidth(260.0);
    map2.maxWidth(100.0);
    map2.setPrefHeight(700.0);
    map2.maxHeight(700.0);
    map2.setStyle("-fx-control-inner-background:#000000; -fx-font-family: Consolas; -fx-highlight-fill: #00ff00; -fx-highlight-text-fill: #000000; -fx-text-fill: #00ff00;-fx-border-color: DIMGRAY; -fx-border-width: 6; -fx-border-radius: 5;");
   map2.appendText("je suis une console \n\n\n");
   map2.appendText("tourner � gauche  \n");
   for(int i=0; i<4;i++) {
	   
	   map2.appendText(i + "Tourner � gauche... \n");
	   map2.appendText(i + "Marchevers le haut \n");
	   map2.appendText(i + "Tourner � Droite... \n");
	   map2.appendText(i + "Marche vers le le bas...!  \n");
   }

   
    
    
    
    
    
    
    
    
    /*******************************/
    
   
    int index=heroesAvatarViewportIndex/spriteSlowDownRate;
    heroesAvatar.setViewport(heroesAvatarViewports.get(index));
    heroesAvatar.setScaleX(0.5);
    heroesAvatar.setScaleY(0.5);
    heroesAvatar.setTranslateX(data.getHeroesPosition().x+(-heroesAvatarViewports.get(index).getWidth()/2.+0.5*heroesAvatarXModifiers.get(index)));
    heroesAvatar.setTranslateY(data.getHeroesPosition().y+(-heroesAvatarViewports.get(index).getHeight()/2.+0.5*heroesAvatarYModifiers.get(index)));
    heroesAvatarViewportIndex=(heroesAvatarViewportIndex+1)%(heroesAvatarViewports.size()*spriteSlowDownRate);
    
    Group panel = new Group();
    panel.getChildren().addAll(map,obstacle1,obstacle2,obstacle2b,obstacle3,obstacle3b,
    		obstacle4,obstacle5,obstacle5b,obstacle6,obstacle4a,obstacle7,obstacle7b,greets,map2,heroesAvatar,btn_pause,btn_quite,btn_info,btn_son);

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

    if(pnl==0)
    { return panel1;}
      else {return panel;}
      
      
      
      }
  }
    

