package com.mycompany.a3.game.controller;

import com.codename1.ui.*;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.a3.game.model.object.GameWorld;
import com.mycompany.a3.game.view.MapView;
import com.mycompany.a3.game.view.ScoreView;
import com.mycompany.a3.game.view.ui.Button;
import com.mycompany.a3.game.view.ui.MenuButton;
import com.mycompany.a3.util.Util;

/*
 * Game controller manages world state, and links commands to user input
 */

public class Game extends Form implements Runnable
{
	
	/*
	 * CNO runnable creates thread, allowing for run() to be called in that separately executing thread
	 * run() allows for clock to tick
	 */
	
    private static Game instance;
    private GameWorld world;

    /*
     * Instantiates GameWorld which starts keyboard event listeners
     */
    
    private Game()
    {
    	/*
    	 * Creating UI by putting buttons on areas assigned by BoxLayout
    	 */
        world = GameWorld.getInstance();
        setLayout(new BorderLayout());
        {
            Container leftMargin = new Container();
            leftMargin.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            {
                /*
                 * "Accelerate" button on left side
                 */
                Button accelerateButton = new Button("Accelerate");
                accelerateButton.getAllStyles().setMarginTop(100);
                accelerateButton.addActionListener((ActionEvent e) -> world.antAccelerate());
                leftMargin.addComponent(accelerateButton);
            }
            {
                /*
                 * "Left" button on left side
                 */
                Button leftButton = new Button("Left");
                leftButton.addActionListener((ActionEvent e) -> world.antTurnLeft());
                leftMargin.addComponent(leftButton);
            }
            addComponent(BorderLayout.WEST, leftMargin);
        }
        {
            Container rightMargin = new Container();
            rightMargin.setLayout(new BoxLayout(BoxLayout.Y_AXIS));
            {
                /*
                 * "Brake" button on right side
                 */
                Button brakeButton = new Button("Brake");
                brakeButton.getAllStyles().setMarginTop(100);
                brakeButton.addActionListener((ActionEvent e) -> world.antBrake());
                rightMargin.addComponent(brakeButton);
            }
            {
                /*
                 * "Right" button on right side
                 */
                Button rightButton = new Button("Right");
                rightButton.addActionListener((ActionEvent e) -> world.antTurnRight());
                rightMargin.addComponent(rightButton);
            }
            addComponent(BorderLayout.EAST, rightMargin);
        }
        {
            Container bottomMargin = new Container();
            bottomMargin.setLayout(new FlowLayout(Component.CENTER));
            {
                /*
                 * "Position" button on bottom
                 */
            	Button positionButton = new Button("Position");
                positionButton.setEnabled(false);
                positionButton.addActionListener(new Command("Position") {
                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if (world.positionModeEnabled())
                        {
                            world.disablePositionMode();
                        }
                        else
                        {
                            world.enablePositionMode();
                        }
                    }
                });
                
                Button playPauseButton = new Button("Pause");
                /*
                 * "Pause/Play" button on bottom
                 * When game is playing, button shows "Paused"
                 * When game is paused, button shows "Play"
                 */
                playPauseButton.addActionListener(new Command("Play/Pause") {
                    private GameWorld world = GameWorld.getInstance();

                    @Override
                    public void actionPerformed(ActionEvent e)
                    {
                        if (world.paused())
                        {
                            world.play();
                            playPauseButton.setText("Pause");
                            positionButton.setEnabled(false);
                            world.disablePositionMode();
                        }
                        else
                        {
                            world.pause();
                            playPauseButton.setText("Play");
                            positionButton.setEnabled(true);
                        }
                    }
                });
                bottomMargin.addComponent(playPauseButton);
                bottomMargin.addComponent(positionButton);
            }
            addComponent(BorderLayout.SOUTH, bottomMargin);
        }
        {
            /*
             * Scoreboard showing time, lives, food, health, and sound on top
             */
            ScoreView scoreView = new ScoreView();
            addComponent(BorderLayout.NORTH, scoreView);
            world.addObserver(scoreView);
        }

        MapView mapView = new MapView(world);
        addComponent(BorderLayout.CENTER, mapView);
        /*
         * Map of game in center
         */
        world.addObserver(mapView);
        {
            Toolbar toolbar = new Toolbar();
            setToolbar(toolbar);
            /*
             * Collapsed sidebar containing "Accelerate", "Exit", "Toggle Sound", and "About"
             */
            {
                MenuButton accelerateButton = new MenuButton("Accelerate");
                accelerateButton.addActionListener((ActionEvent e) -> world.antAccelerate());
                toolbar.addComponentToSideMenu(accelerateButton);
            }
            {
                MenuButton exitButton = new MenuButton("Exit");
                exitButton.addActionListener((ActionEvent e) -> Dialog.show("Exit", "Are you sure you want to exit?",
                                                                            new Command("Cancel"), new Command("Exit")
                        {
                            @Override
                            public void actionPerformed(ActionEvent evt)
                            {
                                System.exit(0);
                            }
                        }));
                toolbar.addComponentToSideMenu(exitButton);
            }
            {
                MenuButton toggleSoundButton = new MenuButton("Toggle Sound");
                toggleSoundButton.addActionListener((ActionEvent e) -> world.toggleSound());
                toolbar.addComponentToSideMenu(toggleSoundButton);
            }
            {
                MenuButton aboutButton = new MenuButton("About");
                aboutButton.addActionListener((ActionEvent e) -> Dialog.show(
                        "About", "Created by: Kevin Li", "Ok", null
                ));
                toolbar.addComponentToSideMenu(aboutButton);
            }
            toolbar.addCommandToRightBar(new Command("Help")
            {
                @Override
                public void actionPerformed(ActionEvent e)
                {
                    String text =
                            "W: Accelerate\n" +
                            "S: Brake\n" +
                            "A: Turn Left\n" +
                            "D: Turn Right\n";
                    Dialog.show("Help", text, "Okay", null);
                }
            });
            setTitle("BugZ Game");
        }
        /*
         * Keyboard event listeners to allow "w", "a", "s", "d" to navigate Bug
         */
        addKeyListener('w', (ActionEvent e) -> world.antAccelerate());
        addKeyListener('s', (ActionEvent e) -> world.antBrake());
        addKeyListener('a', (ActionEvent e) -> world.antTurnLeft());
        addKeyListener('d', (ActionEvent e) -> world.antTurnRight());
        show();
        Util.viewWidth = mapView.getWidth();
        Util.viewHeight = mapView.getHeight();
        world.loadLevel(this);
        world.notifyObservers();
    }

    @Override
    public void run()
    /*
     * Ticks clock
     */
    {
        world.clockTick();
    }

    public static Game getInstance()
    {
        if (instance == null)
        {
            instance = new Game();
        }
        return instance;
    }
}
