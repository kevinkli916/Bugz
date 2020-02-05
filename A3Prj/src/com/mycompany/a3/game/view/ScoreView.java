package com.mycompany.a3.game.view;

import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.a3.game.model.object.GameWorld;
import com.mycompany.a3.game.model.IObserver;

/*
 * Logic of the score board that keeps track of time, lives, food, health, and sound.
 */

public class ScoreView extends Container implements IObserver<GameWorld>
{
    private Label timeLabel = new Label("Time: 0");
    private Label livesLabel = new Label("Lives left: 0");
    private Label foodLabel = new Label("Food left: 0");
    private Label healthLabel = new Label("Health level: 0");
    private Label soundLabel = new Label("Sound: OFF");

    public ScoreView()
    {
        setLayout(new FlowLayout(Component.CENTER));
        addComponent(timeLabel);
        addComponent(livesLabel);
        addComponent(foodLabel);
        addComponent(healthLabel);
        addComponent(soundLabel);
    }

    /*
     * Update scoreboard displaying values: time, lives, food, health, and sound
     */
    
    @Override
    public void update(GameWorld world)
    {
        timeLabel.setText("Time: " + world.getTime());
        livesLabel.setText("Lives left: " + world.getAntLives());
        foodLabel.setText("Food level: " + world.getAntFood());
        healthLabel.setText("Health level: " + world.getAntHealth());
        soundLabel.setText("Sound: " + (world.getSound() ? "ON" : "OFF"));
        repaint();
    }
}
