package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.async.AsyncExecutor;
import com.badlogic.gdx.utils.async.AsyncResult;
import com.badlogic.gdx.utils.async.AsyncTask;

import java.io.IOException;
import java.net.SocketException;
import java.net.UnknownHostException;


public class MyGdxGame extends ApplicationAdapter implements InputProcessor {
	private static final Object TAG = "Log ";
	SpriteBatch batch;
	ShapeRenderer renderer;
	BitmapFont textfont;
	DroneControl controller;
	AsyncResult<Void> transmitter;
	AsyncExecutor executor;

	// utils
	float screenWidth;
	float screenHeight;

	// Constants
	// 350 350 300 80
	float joystickXPosition = 350;
	float joystickYPosition = 350 ;
	float joystickFontRadius = 300;
	float joystickMobileRadius = 80;

	// 600 550 800 500
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
		mobileSliderRectangleYPosition = (float) (screenHeight*0.1);
		mobileSliderRectangleWidth = 100;
		mobileSliderRectangleHeight = 40;

		usedPowerRectangleXPosition = (float) (screenWidth*0.9);
		usedPowerRectangleYPosition = (float) (screenHeight*0.1);
		usedPowerRectangleWidth = 30;
		usedPowerRectangleHeight = (float) (screenHeight*0.8);

		unUsedPowerRectangleXPosition = (float) (screenWidth*0.9);
		unUsedPowerRectangleYPosition = (float) (screenHeight*0.1);
		unUsedPowerRectangleWidth = 30;
		unUsedPowerRectangleHeight = 0;

		engineControlRectangleXPosition = (float) (screenWidth*0.45);
		engineControlRectangleYPosition = 150;
		engineControlRectangleWidth = 200;
		engineControlRectangleHeight = 150;
		minEngineControlYPos =  (float) (screenHeight*0.1);
		maxEngineControlYPos = (float) (screenHeight*0.9);

	}

	public void init() {
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
	}


	@Override
	public void create() {
		batch = new SpriteBatch();
		renderer = new ShapeRenderer();
		Gdx.input.setInputProcessor(this);
		controller = controller.getInstance();
//		executor = new AsyncExecutor(2);
		Thread networkThread = new Thread(new NetworkThread());
		networkThread.start();
		textfont =  new BitmapFont();

		setValues();
		init();
	}

	@Override
	public void render() {
		ScreenUtils.clear(1, 1, 1, 1);
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
		if (controller.isSecurity()) {
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
		renderer.end();
		batch.end();


		System.out.println(controller.toString());
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

		if(engineControlRectangle.contains(touchPos.x, touchPos.y) ){
			controller.switchSecurity();
			/*if(isEngineOn){
				isEngineOn = false;
			}
			else{
				isEngineOn = true;
			}*/
		}
		else if(leftRotationRectangle.contains(touchPos.x, touchPos.y)){
			isRotatingLeft = true;
			controller.leftRotation();
		}
		else if(rightRotationRectangle.contains(touchPos.x, touchPos.y)){
			isRotatingRight = true;
			controller.rightRotation();
		}

		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		Vector3 touchPos = new Vector3(screenX, Gdx.graphics.getHeight() - screenY, 0);

		mobileElement.setPosition(font.x, font.y);
		controller.setMoves(0, 0);

		if(leftRotationRectangle.contains(touchPos.x, touchPos.y)){
			isRotatingLeft = false;
			controller.endRotation();
		}
		else if(rightRotationRectangle.contains(touchPos.x, touchPos.y)){
			isRotatingRight = false;
			controller.endRotation();
		}
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		Vector3 touchPos = new Vector3(screenX, Gdx.graphics.getHeight() - screenY, 0);

		if(mobileSliderRectangle.contains(touchPos.x, touchPos.y)){
			if(touchPos.y > minEngineControlYPos && touchPos.y < maxEngineControlYPos){
				mobileSliderRectangle.setY(touchPos.y - (mobileSliderRectangleHeight/2));
				unUsedPowerRectangle.setHeight(touchPos.y  - unUsedPowerRectangle.y);

				double powerPercentage = (((touchPos.y - unUsedPowerRectangle.y) / (usedPowerRectangleHeight/100))/100);
				controller.setMotorPercentage(powerPercentage );
			}
		}
		else if(mobileElement.contains(touchPos.x, touchPos.y) ){
			if(fontMovingLimit.contains(touchPos.x, touchPos.y)){
				mobileElement.setX(touchPos.x);
				mobileElement.setY(touchPos.y);

				float deltaFront = (mobileElement.x - font.x ) / (font.radius - mobileElement.radius);
				float deltaLateral = (mobileElement.y - font.y ) / (font.radius - mobileElement.radius);

				controller.setMoves(deltaFront, deltaLateral);
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

