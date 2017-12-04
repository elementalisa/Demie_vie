package userInterface;


import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import specifications.ReadService;
import specifications.RequireReadService;
import specifications.ViewerService;
import tools.HardCodedParameters;

public class start implements ViewerService, RequireReadService{
	 private ReadService data;
	
	
	 public start(){}
	 
	 @Override
	  public Parent getPanel(){

		
	    Rectangle map = new Rectangle(HardCodedParameters.defaultWidth-50,-200+HardCodedParameters.defaultHeight);
	   
	    map.setFill(Color.WHITE);
	    map.setStroke(Color.DIMGRAY);
	    map.setStrokeWidth(5);
	    map.setArcWidth(20);
	    map.setArcHeight(20);
	    map.setTranslateX(5);
	    map.setTranslateY(5);
	    map.setFill(Color.DIMGRAY);
	   
	  
	   
	   
	   Button btn = new Button("START");
	    btn.setPrefSize(180, 80);
	    btn.setTranslateX(450);
	    btn.setTranslateY(400);
	    btn.setTextFill(Color.RED);

	   
	    
	    btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
               
                btn.setStyle("-fx-font: 30 arial; -fx-base: #b6e7c9;");
            }
        });
	    
	   
	  
	   
	    
	    Text text = new Text(120, 220, "Bienvenue au jeux de plateforme de Survie");
        text.setFont(new Font(45));
        text.setFill(Color.BLACK);
        
        
       
	    Group panel = new Group();
	    panel.getChildren().addAll(map,btn,text);
	    
	    return panel;
		
	 
	 }
	 

	@Override
	public void bindReadService(ReadService service) {
		data=service;
		
	}

	@Override
	public void init() {}

	
	
	 
	

}
