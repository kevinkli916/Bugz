/*
 * For playing sounds
 */

package com.mycompany.a3.game.view.sound;

import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

import java.io.IOException;
import java.io.InputStream;

/*
 * Sound logic for the game
 * 
 * Codename One does not allow for resource files outside the source directory. Else, I would have moved .wav files to the sound directory
 */

public class Sound
{
    private Media media;

    public Sound(String fileName)
    {
        InputStream inputStream = Display.getInstance().getResourceAsStream(getClass(), "/" + fileName);
        try
        {
            media = MediaManager.createMedia(inputStream, "audio/wav");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public void play()
    {
        media.setTime(0);
        media.play();
    }
}
