package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;
//import java.lang.Math;

import java.util.Iterator;

public class GameScreen implements Screen {

	final RocketGame game;
	OrthographicCamera camera;
	SpriteBatch batch;
	Texture img;
	Texture rocketTexture;
	Sound boom;
	Rectangle man;
	//Rectangle rocket;
	Vector3 touchPos ;
	Array<Rectangle> rockets;
	long lastDropTime;

	public GameScreen (final RocketGame gam) {
		game = gam;
		camera = new OrthographicCamera();
		camera.setToOrtho(false,800,480);

		boom = Gdx.audio.newSound(Gdx.files.internal("tuk.mp3"));
		batch = new SpriteBatch();
		img = new Texture("man.png");
		rocketTexture = new Texture("rocket.png");

		touchPos = new Vector3();


		//rocket = new Rectangle();


		man = new Rectangle();
		man.x = 800/2 -64/2;
		man.y = 20;
		man.width = 64;
		man.height = 64;

		rockets = new Array<Rectangle>();
		spawnRocket();
	}

	@Override
	public void render (float delta) {
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		camera.update();

		game.batch.setProjectionMatrix(camera.combined);

		game.batch.begin();
		game.batch.draw(img, man.x, man.y);
		float rotation;
		for (Rectangle r: rockets){

			rotation = (float) Math.toDegrees(Math.atan((man.x - r.x) / (800-r.y-man.y)));

			if(rotation>0)
				r.x += 100 * Gdx.graphics.getDeltaTime();
			else
				r.x -= 100 * Gdx.graphics.getDeltaTime();
			
			game.batch.draw(rocketTexture,r.x,r.y,32,32,32,32,1,1,rotation,0,0,64,64,false,false);
		}
		game.batch.end();

		if (Gdx.input.isTouched()){

			touchPos.set(Gdx.input.getX(),Gdx.input.getY(),0);
			camera.unproject(touchPos);
			man.x = touchPos.x - 64 /2 ;
			man.y = touchPos.y - 64/ 2;

		}
		if(TimeUtils.millis()-lastDropTime>1000){
			spawnRocket();
		}
		Iterator<Rectangle> iter = rockets.iterator();
		while (iter.hasNext()){
			Rectangle rocket = iter.next();
			rocket.y -=100 * Gdx.graphics.getDeltaTime();
			if(rocket.y+64<0) iter.remove();
			if(rocket.overlaps(man)) {
				boom.play();
				iter.remove();
			}
		}
		//if(Gdx.input.isKeyPressed(Input.Keys.LEFT))
	}
	
	@Override
	public void dispose () {
		img.dispose();
		boom.dispose();
		rocketTexture.dispose();
	}

	private void spawnRocket(){
		Rectangle rocket = new Rectangle();
		rocket.x = MathUtils.random(0, 800-64);
		rocket.y = 480;
		rocket.height = 64;
		rocket.width = 64;
		lastDropTime = TimeUtils.millis();
		rockets.add(rocket);

	}

	@Override
	public void show() {

	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void hide() {

	}
}
