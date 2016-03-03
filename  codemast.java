// This entire file is part of my masterpiece.
// Michelle Chen
package game_mmc56;
    import java.util.ArrayList;
    import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javafx.animation.KeyFrame;
    import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.Group;
    import javafx.scene.Scene;
    import javafx.scene.canvas.Canvas;
    import javafx.scene.canvas.GraphicsContext;
    import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Path;
import javafx.scene.shape.Rectangle;
    import javafx.scene.shape.Shape;
    import javafx.scene.text.Font;
    import javafx.scene.text.FontWeight;
    import javafx.stage.Stage;
    import javafx.util.Duration;
    import javafx.stage.Stage;
    import javafx.scene.text.FontPosture;
    import javafx.scene.image.Image;
    import javafx.scene.image.ImageView;
    import javafx.scene.input.KeyCode;
    import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;

    class Game {
    	public static final String gameName = "B T B";
    	private static final int numberOf = 30;
        public static final int KEY_INPUT_SPEED = 13;

    	private Scene theScene;
    	private Canvas canvas;
    	private GraphicsContext gc2;
        
        private Group root;
        private Rectangle catcher;
        private Rectangle object;
        
        private ArrayList<Rectangle> rectz;
        private List<Shape> targets;
        
        private boolean start = false;        
        
    	//name of the game
    	public String getName() {
    		return gameName;
    	}
    	
    	//setting up scene
    	public Scene myScene(int width, int height) {
            //scene 
    	    root = new Group();
    	    theScene = new Scene(root, width, height, Color.LAVENDERBLUSH);
    	    //splash screen    	    
	    	canvas = new Canvas(width, height);
	    	gc2 = splashScreen(width, height, canvas);
    	    root.getChildren().add(canvas);
    	    //starting game
        	startGame(width, height);
        	//level two.. not working
        	levelTwo(width, height, gc2);
        	//cheat code
        	cheat();
    	    return theScene;       
    	}
    	
    	//starting game
		private void startGame(int width, int height) {
			theScene.addEventFilter(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {
        	    @Override
        	    public void handle(MouseEvent mouseEvent) {
        	    	if (mouseEvent.getClickCount() ==2) {
        	    	start = true;
        	    	gc2.clearRect(0, 0, 500, 500);
//            	    square
            	    catcher = new Rectangle(width /9, height /9, 30, 30);
            	    catcher.setFill(Color.DARKBLUE);
            	    root.getChildren().add(catcher);
//            	    other squares
            	    rectz= rectangles(numberOf); 
            	    for(Rectangle rect: rectz) {
            	    	root.getChildren().add(rect);
            	    }	
            	    //respond to key presses
            	    theScene.setOnKeyPressed(e -> keyInput(e.getCode()));
            	    //winning game
           		 	canvas = new Canvas(width, height);
           		 	GraphicsContext gc = canvas.getGraphicsContext2D();
            	    root.getChildren().add(canvas);   
        	    	}
        	    }
        	});	
			
    	    cheat();
    	    levelTwo(width, height, gc2);

		}
		
		private void levelTwo(int width, int height, GraphicsContext gcc) {
			theScene.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					gcc.clearRect(0, 0, 500, 500);
					gcc.setFill(Color.AQUA);
					final Arc gun = new Arc(width/2, height, 50, 50, 0, 180);
					gun.setFill(Color.DARKBLUE);
					gun.setType(ArcType.ROUND);
					root.getChildren().add(gun);
					//square
					root.getChildren().add(new Rectangle(215, 25, 75, 75));
					fire(targets);
				}
				
			});		

		}
		
		//this doesn't work 
		private void fire(List<Shape> targets) {
			gc2.clearRect(0, 0, 500, 500);
			final Shape bullet = new Circle(4, Color.BLACK);
			root.getChildren().add(bullet);
		    final TranslateTransition bulletAnimation = new TranslateTransition(Duration.seconds(1), bullet);
		    final int bulletTargetX = new Random().nextInt(500);
		    
		    bullet.boundsInParentProperty().addListener(new ChangeListener<Bounds>() {
	            @Override
	            public void changed(ObservableValue<? extends Bounds> observable,
	                    Bounds oldValue, Bounds newValue) {
	                for (final Shape target : new ArrayList<Shape>(targets)) {
	                    if (((Path)Shape.intersect(bullet, target)).getElements().size() > 0) {
	                            targets.remove(target);
	                            root.getChildren().remove(target);                	
	                    }
	                }
	            }
	        });
		    bulletAnimation.setOnFinished(new EventHandler<ActionEvent>() {
	            @Override
	            public void handle(ActionEvent event) {
	                root.getChildren().remove(bullet);
	            }
	        });
		    bulletAnimation.play();
		}
		
		//cheat key
		private void cheat(){
			theScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
				@Override
				public void handle(KeyEvent event) {
					if (event.getCode() == KeyCode.ENTER){
						gc2.clearRect(0, 0, 500, 500);
	           		 	winningScreen(canvas);
					}					
				}				
			});
		}

		//splash screen
    	public GraphicsContext splashScreen(int width, int height, Canvas canvas) {	
	    	GraphicsContext gc2 = canvas.getGraphicsContext2D();
    		Font myFont = Font.font("Times New Roman", FontWeight.BOLD, 30);
    	    gc2.setFont(myFont);
    	    gc2.setFill(Color.DARKBLUE);
    	    gc2.fillText("BTB : BEAT THE BLOX", 80, 36);
    		Font myFont2 = Font.font("Times New Roman", FontWeight.BOLD, 15);
    	    gc2.setFont(myFont2);
    	    gc2.fillText("GTHC! You're a loyal Duke fan on a mission to shut down pesky UNC", 20, 80);
    	    gc2.fillText("fans. Use your left and right keys to move your Duke Blue block around", 20, 98);
    	    gc2.fillText("the screen, and touch the spinning UNC blox to make them dissapear.", 20, 116);
    	    Font myFont3 = Font.font("Times New Roman", FontWeight.BOLD, 18);
    	    gc2.setFont(myFont3);
    	    gc2.fillText("double tap to start the game!", 125, 160);
    	    gc2.drawImage(new Image("/images/ddmf.png"), 100, 200);
    	    return gc2;
    	}
    	
    	//arraylist of blox
    	private ArrayList<Rectangle> rectangles(int numberOf) {
	    	ArrayList<Rectangle> theRectangles = new ArrayList<Rectangle>();
	    	for (int a = 0; a<numberOf; a++) {
	    		object = new Rectangle(300*Math.random()+100, 300*Math.random()+100, 15, 15);
	    		object.setFill(Color.LIGHTSKYBLUE);
	    		theRectangles.add(object);
    	}
    	return theRectangles;
    }	
    	
    	//removing blox and checking if they're all gone
    	public void movement(double elapsedTime) {
    		if (start) {
        		for (Rectangle recta: rectz) {
        			recta.setRotate(recta.getRotate()-Math.random());
        		}   			
        		for (Rectangle rectan: rectz) {
        	        Shape intersector = Shape.intersect(catcher, rectan);
        	        if (intersector.getBoundsInLocal().getWidth()!=-1) {
        				rectan.setFill(Color.BLACK);
        				root.getChildren().remove(rectan);
        				rectz.remove(rectan);
        				break;
        				}
        	        }	
        		if (rectz.isEmpty()) {
        			gc2.clearRect(0, 0, 512, 512);
        			winningScreen(canvas);
        		}	
    		}  	
    	}
    	
    	//reading key input
    	private void keyInput (KeyCode code) {
            switch (code) {
                case RIGHT:
                    catcher.setX(catcher.getX() + KEY_INPUT_SPEED);
                    break;
                case LEFT:
                	catcher.setX(catcher.getX() - KEY_INPUT_SPEED);
                    break;
                case UP:
                	catcher.setY(catcher.getY() - KEY_INPUT_SPEED);
                    break;
                case DOWN:
                	catcher.setY(catcher.getY() + KEY_INPUT_SPEED);
                    break;
                default:
            }
        }
    	
    	//screen after level one passed
    	public Canvas winningScreen(Canvas canvas){
    	    GraphicsContext gc = canvas.getGraphicsContext2D();
    	    gc.clearRect(0, 0, 500, 500);
    	    Font myFont = Font.font("Times New Roman", FontWeight.BOLD, 24);
    	    gc.setFont(myFont);
    	    gc.setFill(Color.DARKBLUE);
    	    gc.fillText("CONGRATULATIONS!!!!!!", 100, 60);
    	    Font myFont2 = Font.font("Times New Roman", FontWeight.BOLD, 17);
    	    	gc.setFont(myFont2);
    	    	gc.fillText("You've beaten all of the pesky UNC blox.", 100, 90);
    	    Font myFont3 = Font.font("Times New Roman", FontWeight.NORMAL, 15);
    	    	gc.setFont(myFont3);
    	    	gc.fillText("But you're not done just yet... looks like UNC fans are back for more.", 40, 120);
    	    gc.setFont(myFont2);
    	    	gc.fillText("Drag your mouse to start the next level!", 115, 160);
    	    Font myFont4 = Font.font("Times New Roman", FontWeight.NORMAL, 13);
    	    gc.setFont(myFont4);
    	    	gc.fillText("(ok to be honest this level pretty much doesn't work at all)", 100, 180);
    	    gc.drawImage(new Image("/images/gthc.jpg"), 165, 200);
    	    return canvas;
    	}	    
}