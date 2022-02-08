package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.ScreenUtils;

import java.io.IOException;

import networkManager.NetworkManager;


enum CurrentScreen{
	JOYSTICK, CONNEXION;
}

public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	private static final Object TAG = "Log ";
	SpriteBatch batch;
	Stage stage;
	ShapeRenderer renderer;
	BitmapFont textfont;
	DroneControl controller;
	CurrentScreen screen = CurrentScreen.JOYSTICK;

	// ------------------------------
	//	JOYSTICK PART
	// ------------------------------

	// utils
	float screenWidth;
	float screenHeight;

	// Constants
	float joystickXPosition = 350;
	float joystickYPosition = 350 ;
	float joystickFontRadius = 300;
	float joystickMobileRadius = 80;

	float mainRectangleXPosition = 600;
	float mainRectangleYPosition = 550;
	float mainRectangleWidth = 800;
	float mainRectangleHeight = 500;

	float speedRectangleXPosition = 1150;
	float speedRectangleYPosition = 800;
	float speedRectangleWidth = 200;
	float speedRectangleHeight = 80;

	float heightRectangleXPosition = 1150;
	float heightRectangleYPosition = 700;
	float heightRectangleWidth = 200;
	float heightRectangleHeight = 80;

	float positionRectangleXPosition = 1150;
	float positionRectangleYPosition = 600;
	float positionRectangleWidth = 200;
	float positionRectangleHeight = 80;

	float leftRotationRectangleXPosition = 1301;
	float leftRotationRectangleYPosition = 150;
	float leftRotationRectangleWidth = 200;
	float leftRotationRectangleHeight = 350;
	boolean isRotatingLeft;

	float rightRotationRectangleXPosition = 1500;
	float rightRotationRectangleYPosition = 150;
	float rightRotationRectangleWidth = 200;
	float rightRotationRectangleHeight = 350;
	boolean isRotatingRight;

	float mobileSliderRectangleXPosition = 1815;
	float mobileSliderRectangleYPosition = 100;
	float mobileSliderRectangleWidth = 100;
	float mobileSliderRectangleHeight = 40;

	float usedPowerRectangleXPosition = 1850;
	float usedPowerRectangleYPosition = 100;
	float usedPowerRectangleWidth = 30;
	float usedPowerRectangleHeight = 800;

	float unUsedPowerRectangleXPosition = 1850;
	float unUsedPowerRectangleYPosition = 100;
	float unUsedPowerRectangleWidth = 30;
	float unUsedPowerRectangleHeight = 0;

	float engineControlRectangleXPosition = 800;
	float engineControlRectangleYPosition = 150;
	float engineControlRectangleWidth = 200;
	float engineControlRectangleHeight = 150;
	float minEngineControlYPos = 100;
	float maxEngineControlYPos = 900;

	// Joystick elements
	Circle font;
	Circle mobileElement;
	Circle fontMovingLimit;

	// Information elements
	Rectangle mainRectangle;
	Rectangle speedRectangle;
	Rectangle heightRectangle;
	Rectangle positionRectangle;

	String rectangleTitle;
	String speedTitle;
	String heightTitle;
	String positionTitle;

	String speedValue;
	String heightValue;
	String positionValue;

	// Rotation Elements
	Rectangle leftRotationRectangle;
	Rectangle rightRotationRectangle;

	// booster slider elements

	Rectangle mobileSliderRectangle;
	Rectangle usedPowerRectangle;
	Rectangle unUsedPowerRectangle;

	// engine on/off elements
	Rectangle engineControlRectangle;
	boolean isEngineOn;

	// images
	Texture settingImage;
	Rectangle settingsRectangle;

	// ------------------------------
	//	CONNEXION PART
	// ------------------------------
	TextField ipField;
	TextField portField;
	TextButton connexionButton;
	TextButton returnButton;
	NetworkManager manager;

	boolean isThreadLaunched = false;
	String connexionButtonText = " Connect";


	public void setValues(){
		screenWidth  = Gdx.graphics.getWidth();
		screenHeight = Gdx.graphics.getHeight();

		joystickXPosition = (float) (screenWidth*0.2);
		joystickYPosition =  (float) (screenHeight*0.3);
		joystickFontRadius = (float) (screenWidth*0.15);
		joystickMobileRadius = (float) (screenWidth*0.03);

		mainRectangleXPosition = (float) (screenWidth*0.35);
		mainRectangleYPosition = (float) (screenHeight*0.5);
		mainRectangleWidth = (float) (screenWidth*0.45);
		mainRectangleHeight = (float) (screenHeight*0.4);

		speedRectangleXPosition =  (float) (screenWidth*0.7);
		speedRectangleYPosition =  (float) (screenHeight*0.75);
		speedRectangleWidth = 200;
		speedRectangleHeight = 100;

		heightRectangleXPosition = (float) (screenWidth*0.7);
		heightRectangleYPosition = (float) (screenHeight*0.65);
		heightRectangleWidth = 200;
		heightRectangleHeight = 100;

		positionRectangleXPosition = (float) (screenWidth*0.7);
		positionRectangleYPosition = (float) (screenHeight*0.55);
		positionRectangleWidth = 200;
		positionRectangleHeight = 100;

		leftRotationRectangleXPosition = (float) (screenWidth*0.65);
		leftRotationRectangleYPosition = 150;
		leftRotationRectangleWidth = (float) (screenWidth*0.1);
		leftRotationRectangleHeight = (float) (screenHeight*0.3);

		rightRotationRectangleXPosition = (float) (screenWidth*0.75);
		rightRotationRectangleYPosition = 150;
		rightRotationRectangleWidth = (float) (screenWidth*0.1);
		rightRotationRectangleHeight = (float) (screenHeight*0.3);

		mobileSliderRectangleXPosition = (float) (screenWidth*0.9) - 35;
		mobileSliderRectangleYPosition = (float) (screenHeight*0.4);
		mobileSliderRectangleWidth = 100;
		mobileSliderRectangleHeight = 40;

		usedPowerRectangleXPosition = (float) (screenWidth*0.9);
		usedPowerRectangleYPosition = (float) (screenHeight*0.1);
		usedPowerRectangleWidth = 30;
		usedPowerRectangleHeight = (float) (screenHeight*0.8);

		unUsedPowerRectangleXPosition = (float) (screenWidth*0.9);
		unUsedPowerRectangleYPosition = (float) (screenHeight*0.4);
		unUsedPowerRectangleWidth = 30;
		unUsedPowerRectangleHeight = 0;

		engineControlRectangleXPosition = (float) (screenWidth*0.4);
		engineControlRectangleYPosition = 150;
		engineControlRectangleWidth = 200;
		engineControlRectangleHeight = 150;
		minEngineControlYPos =  (float) (screenHeight*0.1);
		maxEngineControlYPos = (float) (screenHeight*0.746);

	}

	public void initJoystick() {
		// Joystick init;
		font = new Circle(joystickXPosition, joystickYPosition, joystickFontRadius);
		mobileElement = new Circle(joystickXPosition, joystickYPosition, joystickMobileRadius);
		fontMovingLimit = new Circle(joystickXPosition, joystickYPosition, joystickFontRadius - joystickMobileRadius);
		textfont = new BitmapFont(/*Gdx.files.internal("com/badlogic/gdx/utils/arial-15.png")*/);

		// informations rectangles init
		mainRectangle = new Rectangle(mainRectangleXPosition, mainRectangleYPosition, mainRectangleWidth, mainRectangleHeight);
		speedRectangle = new Rectangle(speedRectangleXPosition, speedRectangleYPosition, speedRectangleWidth, speedRectangleHeight);
		heightRectangle = new Rectangle(heightRectangleXPosition, heightRectangleYPosition, heightRectangleWidth, heightRectangleHeight);
		positionRectangle = new Rectangle(positionRectangleXPosition, positionRectangleYPosition, positionRectangleWidth, positionRectangleHeight);

		rectangleTitle = "Données du drone";
		speedTitle = "Vitesse sol";
		heightTitle = "Distance au sol";
		positionTitle = "Position";

		speedValue = "12m.s-1";
		heightValue = "12 m";
		positionValue = "48°12'1.0N\n 12°14'5.3E";

		// Rotation Rectangle init
		leftRotationRectangle = new Rectangle(leftRotationRectangleXPosition, leftRotationRectangleYPosition, leftRotationRectangleWidth, leftRotationRectangleHeight);
		rightRotationRectangle = new Rectangle(rightRotationRectangleXPosition, rightRotationRectangleYPosition, rightRotationRectangleWidth, rightRotationRectangleHeight);
		isRotatingLeft = false;
		isRotatingRight = false;

		// power slider rectangles init
		mobileSliderRectangle = new Rectangle(mobileSliderRectangleXPosition, mobileSliderRectangleYPosition, mobileSliderRectangleWidth, mobileSliderRectangleHeight);
		usedPowerRectangle = new Rectangle(usedPowerRectangleXPosition, usedPowerRectangleYPosition, usedPowerRectangleWidth, usedPowerRectangleHeight);
		unUsedPowerRectangle = new Rectangle(unUsedPowerRectangleXPosition, unUsedPowerRectangleYPosition, unUsedPowerRectangleWidth, unUsedPowerRectangleHeight);

		// engine control rectangle init
		engineControlRectangle = new Rectangle(engineControlRectangleXPosition, engineControlRectangleYPosition, engineControlRectangleWidth, engineControlRectangleHeight);
		//isEngineOn = false;

		// setting Rectangle init
		settingImage = new Texture("badlogic.jpg");
		settingsRectangle = new Rectangle(screenWidth - settingImage.getWidth(), screenHeight-settingImage.getHeight(), settingImage.getWidth(), settingImage.getHeight());
	}


	public void initConnexion(){
		Skin uiSkin = new Skin(Gdx.files.internal("uiskin.json"));

		ipField = new TextField("",uiSkin );
		portField = new TextField("", uiSkin);

		ipField.setPosition((float)(screenWidth * 0.5), (float) (screenHeight*0.7));
		ipField.setSize(500,100);
		ipField.setMessageText("IP Adress");
		portField.setPosition((float)(screenWidth * 0.5), (float) (screenHeight*0.5));
		portField.setSize(500,100);
		portField.setMessageText("Port");

		connexionButton = new TextButton("Connect", uiSkin);

		connexionButton.setPosition((float)(screenWidth * 0.5), (float) (screenHeight*0.3));
		connexionButton.setSize(500,100);
		connexionButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				if(!isThreadLaunched) {
					String strPort = portField.getText();

					try {
						manager = new NetworkManager(ipField.getText(), Integer.parseInt(strPort));
					} catch (IOException e) {
						e.printStackTrace();
					}

					manager.startSendingThread();
					screen = CurrentScreen.JOYSTICK;
					isThreadLaunched = true;
				}
				else{
					manager.disconnect();
					isThreadLaunched = false;
				}
			}
		});
		returnButton = new TextButton("Return", uiSkin);
		returnButton.setPosition((float)(screenWidth * 0.5), (float) (screenHeight*0.1));
		returnButton.setSize(500,100);
		returnButton.addListener(new ClickListener(){
			@Override
			public void clicked(InputEvent event, float x, float y){
				screen = CurrentScreen.JOYSTICK;
			}
		});
		stage.addActor(ipField);
		stage.addActor(portField);
		stage.addActor(connexionButton);
		stage.addActor(returnButton);
	}

	@Override
	public void create() {
		batch = new SpriteBatch();
		renderer = new ShapeRenderer();
		stage = new Stage();
		Gdx.input.setInputProcessor(this);

		controller = controller.getInstance();

		setValues();
		initJoystick();
		initConnexion();
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		if(screen == CurrentScreen.JOYSTICK) {
			Gdx.input.setInputProcessor(this);
			renderJoystick();
		}
		else if(screen == CurrentScreen.CONNEXION){
			Gdx.input.setInputProcessor(this.stage);
			renderConnexion();
			stage.act();
		}

		System.out.println(controller.toString());

	}

	private void renderConnexion(){
		batch.begin();

		if(!isThreadLaunched){
			connexionButton.setText("Connect");
		}
		else{
			connexionButton.setText("Disonnect");
		}


		for(Actor a : stage.getActors()) {
			a.draw(batch, 1);
		}

		batch.end();
	}

	private void renderJoystick(){
		batch.begin();
		// draw joystick
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.LIGHT_GRAY);
		renderer.circle(font.x, font.y, joystickFontRadius);
		renderer.setColor(Color.GREEN);
		renderer.circle(mobileElement.x, mobileElement.y, joystickMobileRadius);
		renderer.end();

		// draw informations rectangles
		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.setColor(Color.BLACK);
		renderer.rect(mainRectangleXPosition, mainRectangleYPosition, mainRectangleWidth, mainRectangleHeight);
		renderer.rect(speedRectangleXPosition, speedRectangleYPosition, speedRectangleWidth, speedRectangleHeight);
		renderer.rect(heightRectangleXPosition, heightRectangleYPosition, heightRectangleWidth, heightRectangleHeight);
		renderer.rect(positionRectangleXPosition, positionRectangleYPosition, positionRectangleWidth, positionRectangleHeight);
		renderer.end();

		// draw rotation Rectangles
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		if(!isRotatingLeft){
			renderer.setColor(Color.LIGHT_GRAY);
		}
		else{
			renderer.setColor(Color.GRAY);
		}
		renderer.rect(leftRotationRectangleXPosition, leftRotationRectangleYPosition, leftRotationRectangleWidth, leftRotationRectangleHeight);
		renderer.end();


		renderer.begin(ShapeRenderer.ShapeType.Filled);
		if(!isRotatingRight){
			renderer.setColor(Color.LIGHT_GRAY);
		}
		else{
			renderer.setColor(Color.GRAY);
		}
		renderer.rect(rightRotationRectangleXPosition, rightRotationRectangleYPosition, rightRotationRectangleWidth, rightRotationRectangleHeight);
		renderer.end();


		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.setColor(Color.BLACK);
		renderer.rect(leftRotationRectangleXPosition, leftRotationRectangleYPosition, leftRotationRectangleWidth, leftRotationRectangleHeight);
		renderer.rect(rightRotationRectangleXPosition, rightRotationRectangleYPosition, rightRotationRectangleWidth, rightRotationRectangleHeight);
		renderer.end();

//		textfont.draw(batch, "hello test", 1000,700);

		// draw power slider rectangles

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.GREEN);
		renderer.rect(usedPowerRectangle.x, usedPowerRectangle.y, usedPowerRectangle.width, usedPowerRectangle.height);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.RED);
		renderer.rect(unUsedPowerRectangle.x, unUsedPowerRectangle.y, unUsedPowerRectangle.width, unUsedPowerRectangle.height);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(Color.DARK_GRAY);
		renderer.rect(mobileSliderRectangle.x, mobileSliderRectangle.y, mobileSliderRectangle.width, mobileSliderRectangle.height);
		renderer.end();

		// on off engine button
		renderer.begin(ShapeRenderer.ShapeType.Filled);
		if (controller.isTakeOff()) {
			renderer.setColor(Color.GREEN);
		}
		else{
			renderer.setColor(Color.RED);
		}
		renderer.rect(engineControlRectangle.x, engineControlRectangle.y, engineControlRectangle.width, engineControlRectangle.height);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);

		// draw texts
		textfont.setColor(Color.BLACK);
		textfont.getData().setScale(30);
		//textfont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
		//textfont.draw(batch, rectangleTitle, 100, 100);

		// draw setting button
		//renderer.setColor(Color.CLEAR);
		//renderer.rect(settingsRectangle.x, settingsRectangle.y, settingsRectangle.width, settingsRectangle.height);
		TextureRegion settingsRegion = new TextureRegion(settingImage);
		batch.draw(settingImage, settingsRectangle.x, settingsRectangle.y);


		renderer.end();
		batch.end();
	}


	public void dispose() {
		textfont.dispose();
		batch.dispose();
		renderer.dispose();


	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		Vector3 touchPos = new Vector3(screenX, Gdx.graphics.getHeight() - screenY, 0);
		if(screen == CurrentScreen.JOYSTICK) {
			if (engineControlRectangle.contains(touchPos.x, touchPos.y)) {
				controller.switchTakeOff();
				if(controller.isTakeOff){
					manager.takeOffDrone();
				}
				else{

					manager.landDrone();

				}

			} else if (leftRotationRectangle.contains(touchPos.x, touchPos.y)) {
				isRotatingLeft = true;
				controller.leftRotation();
			} else if (rightRotationRectangle.contains(touchPos.x, touchPos.y)) {
				isRotatingRight = true;
				controller.rightRotation();
			}
		}


		if(settingsRectangle.contains(touchPos.x, touchPos.y)){
			if(screen == CurrentScreen.JOYSTICK)
				screen = CurrentScreen.CONNEXION;
			else if(screen == CurrentScreen.CONNEXION)
				screen = CurrentScreen.JOYSTICK;
		}

		if(connexionButton.isPressed()){
			screen = CurrentScreen.JOYSTICK;
		}



		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector3 touchPos = new Vector3(screenX, Gdx.graphics.getHeight() - screenY, 0);

		mobileElement.setPosition(font.x, font.y);
		controller.setMoves(0, 0);
		mobileSliderRectangle.setY(mobileSliderRectangleYPosition);
		unUsedPowerRectangle.setHeight(0);

		if(screen == CurrentScreen.JOYSTICK) {

			if (leftRotationRectangle.contains(touchPos.x, touchPos.y)) {
				isRotatingLeft = false;
				controller.endRotation();
			} else if (rightRotationRectangle.contains(touchPos.x, touchPos.y)) {
				isRotatingRight = false;
				controller.endRotation();
			}
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 touchPos = new Vector3(screenX, Gdx.graphics.getHeight() - screenY, 0);
		if(screen == CurrentScreen.JOYSTICK) {

			if (mobileSliderRectangle.contains(touchPos.x, touchPos.y)) {
				if (touchPos.y > minEngineControlYPos && touchPos.y < maxEngineControlYPos) {
					mobileSliderRectangle.setY(touchPos.y - (mobileSliderRectangleHeight / 2));
					unUsedPowerRectangle.setHeight(touchPos.y - unUsedPowerRectangle.y);

					//double powerPercentage = (((touchPos.y - unUsedPowerRectangle.y) / (usedPowerRectangleHeight / 100)) / 100);
					double powerPercentage = ((touchPos.y-25 - unUsedPowerRectangleYPosition)/ usedPowerRectangleHeight/4)*10;
					controller.setMotorPercentage(powerPercentage);
				}
			} else if (mobileElement.contains(touchPos.x, touchPos.y)) {
				if (fontMovingLimit.contains(touchPos.x, touchPos.y)) {
					mobileElement.setX(touchPos.x);
					mobileElement.setY(touchPos.y);

					float deltaFront = (mobileElement.x - font.x) / (font.radius - mobileElement.radius);
					float deltaLateral = (mobileElement.y - font.y) / (font.radius - mobileElement.radius);

					controller.setMoves(deltaFront, deltaLateral);
				}
			}
		}
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {

		return false;
	}

	@Override
	public boolean scrolled(float amountX, float amountY) {
		return false;
	}

}

