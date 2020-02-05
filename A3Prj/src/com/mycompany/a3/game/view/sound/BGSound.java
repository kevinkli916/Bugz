package com.mycompany.a3.game.view.sound;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.IOException;
import java.io.InputStream;

/*
 * Background music logic for the game
 */

public class BGSound implements Runnable
{
    private Media media;

    public BGSound(String fileName)
    {
        InputStream inputStream = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
        try
        {
            media = MediaManager.createMedia(inputStream, "audio/wav", () -> {
                media.setTime(0);
                media.play();
            });
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void play() 
    { 
    	media.play(); 
    }
    
    public void pause() 
    { 
    	media.pause(); 
	}
    
    @Override
    public void run() {}
}
