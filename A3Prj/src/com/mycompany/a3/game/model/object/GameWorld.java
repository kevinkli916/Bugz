package com.mycompany.a3.game.model.object;

import com.codename1.charts.models.Point;
import com.codename1.ui.Graphics;
import com.codename1.ui.util.UITimer;
import com.mycompany.a3.game.controller.Game;
import com.mycompany.a3.game.model.*;
import com.mycompany.a3.game.model.component.Observable;
import com.mycompany.a3.game.view.sound.BGSound;
import com.mycompany.a3.game.view.sound.Sound;
import com.mycompany.a3.util.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/*
 * The controller of this MVC pattern
 */

public class GameWorld implements IObservable<GameWorld>
{
    private int speedIncrement = 2;
    private int headingIncrement = 15;
    private int startingLives = 3;

    private static GameWorld instance;
    private Clock clock = new Clock();
    private int antLives = startingLives;
    private ArrayList<GameObject> gameObjects;
    private Ant ant;
    private boolean sound = true;
    private UITimer timer;
    private int frameTime;
    private boolean positionMode = false;
    private boolean paused = false;
    private Sound antDamaged = new Sound("hurt.wav");
    private Sound foodStationInteraction = new Sound("eat.wav");
    private Sound flagInteraction = new Sound("fireworks.wav");
    private BGSound backgroundSound = new BGSound("outside.wav");

    private GameWorld()
    {
        backgroundSound.play();
    }

    /**
     * 
     * @return	Get an instance of GameWorld. Ensures there is no more than 1 instance.
     */
    
    public static GameWorld getInstance()
    {
        /*
         * Singleton implementation
         */
        if (instance == null)
        {
            instance = new GameWorld();
        }
        return instance;
    }

    /*
     * Starts or restarts the game
     *
     * All game objects are deleted (if they exist) and recreated
     * This does not include the clock, which is not reset between games
     */
    
    public void loadLevel(Game game)
    {
        loadLevel();
        timer = new UITimer(game);
        timer.schedule(20, true, game);
    }

    private void loadLevel()
    {
        ant = new Ant(Util.viewCenter());
        gameObjects = new ArrayList<>();
        gameObjects.add(ant);
        gameObjects.add(new Flag(1, Util.viewCenter()));
        gameObjects.add(new Flag(2, new Point(350, 1000)));
        gameObjects.add(new Flag(3, new Point(850, 120)));
        gameObjects.add(new Flag(4, new Point(1000, 1000)));
        gameObjects.add(new Flag(5, new Point(250, 350)));
        gameObjects.add(new Spider());
        gameObjects.add(new Spider());
        gameObjects.add(new FoodStation());
        gameObjects.add(new FoodStation());
        gameObjects.add(new FoodStation());
        gameObjects.add(new FoodStation());
        gameObjects.add(new FoodStation());
        frameTime = getTime();
    }

    public void antAccelerate()
    {
        ant.setSpeed(ant.getSpeed() + speedIncrement);
        notifyObservers();
    }

    public void antBrake()
    {
        ant.setSpeed(ant.getSpeed() - speedIncrement);
        notifyObservers();
    }

    /*
     * In the event the ant(player) dies, reduce lives and restart
     * 
     * If there are no more lives, end game with a loss
     */
    
    private void antDie()
    {
        antLives--;

        if (antLives < 0)
        {
            System.out.println("Game over, you failed!");
            System.exit(0);
        }
        loadLevel();
        notifyObservers();
    }

    /*
     * In the event the ant(player) collides with a flag, set target flag to next one
     * 
     * If the ant collided with the last flag, end game with a win
     */
    
    public void antHitFlag(Flag flag)
    {
        if (flag.getSequenceNumber() == ant.getLastFlagReached() + 1)
        {
            ant.hitNextFlag();
            if (sound) flagInteraction.play();
            if (ant.getLastFlagReached() == 5)
            {
                System.out.println("Game over, you win! Total time: " + clock.getTime());
                System.exit(0);
            }
            notifyObservers();
        }
    }

    /*
     * In the event the ant(player) collides with a food station, increase food levels by food station's capacity
     * 
     * Then, generate a new food station
     */
    
    public void antHitFoodStation(FoodStation foodStation)
    {
        if (foodStation.getCapacity() == 0) return;
        if (sound) foodStationInteraction.play();
        ant.setFood(ant.getFood() + foodStation.getCapacity());
        foodStation.setCapacity(0);
        gameObjects.add(new FoodStation());
        notifyObservers();
    }

    /*
     * In the event the ant(player) collides with a spider(enemy), decrease health
     * 
     * If post-collision health is 0, antDie() is triggered
     */
    
    public void antHitSpider()
    {
        if (sound) antDamaged.play();
        ant.hitSpider();

        if (ant.getHealth() == 0)
        {
            System.out.println("The ant is dead.");
            antDie();
        }
        notifyObservers();
    }

    public void antTurnLeft()
    {
    	ant.setHeading(ant.getHeading() + headingIncrement);
        notifyObservers();
    }

    public void antTurnRight()
    {
    	ant.setHeading(ant.getHeading() - headingIncrement);
        notifyObservers();
    }

    /*
     * A list that contains all objects in collision range
     */
    
    /**
     * 
     * @param location	Point to check if a collision has happened
     * @return	All objects that have collided at determined location
     */
    
    public List<GameObject> getObjectsAtPoint(Point location)
    {
        List<GameObject> objects = new LinkedList<>();
        for (GameObject object : gameObjects)
        {
            float radius = object.getCollisionRadius();
            float radiusSquared = radius * radius;
            float distanceSquared = Util.distanceSquared(location, object.getCenterPoint());

            if (distanceSquared < radiusSquared)
            {
                objects.add(object);
            }
        }
        return objects;
    }

    /*
     * Actions taken in a single game tick
     */
    
    public void clockTick()
    {
        // increase CNO timer by 1
         
    	clock.tick();

    	
    	// check for collisions
    	 
        for (int i = 0; i < gameObjects.size(); ++i)
        {
            if (ant.collidesWith(gameObjects.get(i)))
            {
                ant.handleCollision(gameObjects.get(i), this);
            }
        }
        
        // reduce ant's food level 
        ant.consumeFood();

        // check if ant is starved 
        if (ant.getFood() == 0)
        {
            System.out.println("The ant starved to death.");

            antDie();
        }

        // redraw ant in accordance to time ticked
        {
            int deltaTime = getTime() - frameTime;
            for (IDrawable obj : gameObjects)
            {
                if (obj instanceof IMovable)
                {
                    ((IMovable) obj).move(deltaTime);
                }
            }
            frameTime = getTime();
        }
        notifyObservers();
    }
    
    // redraw gameObjects in accordance to time ticked	
    public void drawAll(Graphics g, Point containerOrigin)
    {
        for (IDrawable gameObject : gameObjects)
        {
            gameObject.draw(g, containerOrigin);
        }
    }

    /**
     * 
     * @return Get the foodLevel of Ant
     */
    
    public int getAntFood() { return ant.getFood(); }
    
    /**
     * 
     * @return	Get the healthLevel of Ant
     */
    
    public int getAntHealth() { return ant.getHealth(); }

    /**
     * 
     * @return	Get the lives of Ant
     */
    
    public int getAntLives() { return antLives; }

    /**
     * 
     * @return	If false, sound is off. If true, sound is on
     */
    
    public boolean getSound() { return sound; }

    /**
     * 
     * @return	Get time(tick) of Clock
     */
    
    public int getTime() { return clock.getTime(); }

    public void showMap()
    {
        for (IDrawable obj : gameObjects)
        {
            System.out.println(obj);
        }
    }

    public void toggleSound()
    {
        sound = !sound;
        if (sound)
        {
            backgroundSound.play();
        }
        else
        {
            backgroundSound.pause();
        }
        notifyObservers();
    }

    private Observable<GameWorld> observable = new Observable<>();

    @Override
    public void addObserver(IObserver<GameWorld> observer)
    {
        observable.addObserver(observer);
    }

    @Override
    public void notifyObservers()
    {
        observable.notifyObservers(this);
    }

    /*
     * Play instantiates an instance of game
     * 
     * Starts background music
     * 
     * Starts timer
     */
    
    public void play()
    {
        timer.schedule(20, true, Game.getInstance());
        if (sound) backgroundSound.play();
        paused = false;
    }

    public void pause()
    {
        timer.cancel();
        backgroundSound.pause();
        paused = true;
    }

    public boolean paused() { return paused; }

    public void enablePositionMode() { positionMode = true; }

    public void disablePositionMode() { positionMode = false; }

    public boolean positionModeEnabled() { return positionMode; }
}
