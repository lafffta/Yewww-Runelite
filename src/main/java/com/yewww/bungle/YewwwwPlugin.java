package com.yewww.bungle;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import javax.sound.sampled.*;
import java.io.*;

import static net.runelite.api.ObjectID.*;
import static net.runelite.api.NullObjectID.NULL_10823;

@Slf4j
@PluginDescriptor(
	name = "Yewww"
)
public class YewwwwPlugin extends Plugin
{
	@Inject
	private Client client;

	@Inject
	private YewwwwConfig config;

	private Clip clip = null;
	private Clip rareClip = null;

	@Override
	protected void startUp() {
		log.info("Yewww started!");
//		try (InputStream fileStream = new BufferedInputStream(YewwwwPlugin.class.getResourceAsStream("YOUUU.wav"));
		try (InputStream fileStream = new BufferedInputStream(new FileInputStream(new File("src/main/resources", "YOUUU.wav")));
			 AudioInputStream sound = AudioSystem.getAudioInputStream(fileStream))
		{
			clip = AudioSystem.getClip();
			clip.open(sound);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			log.warn("Unable to load builtin Yewww sound 1", e);
		}
//		try (InputStream fileStream = new BufferedInputStream(YewwwwPlugin.class.getResourceAsStream("YOUUUU.wav"));
		try (InputStream fileStream = new BufferedInputStream(new FileInputStream(new File("src/main/resources", "YOUUUU.wav")));
			 AudioInputStream sound = AudioSystem.getAudioInputStream(fileStream))
		{
			rareClip = AudioSystem.getClip();
			rareClip.open(sound);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			log.warn("Unable to load builtin Yewww sound 2", e);
		}
	}

	@Override
	protected void shutDown() {
		log.info("Yewww stopped!");
	}

//	static final int[] yew_ids = {YEW, NULL_10823, YEW_36683, YEW_40756}; // yew ids
	static final int[] yew_ids = {TREE, TREE_1277, TREE_1278, TREE_1279, TREE_1280, TREE_40750, TREE_40752}; // normal tree ids (DEBUG)

	@Subscribe
	public void onGameObjectDespawned(final GameObjectDespawned event)
	{
		final GameObject object = event.getGameObject();
		int id = object.getId();
		for (int yew_id : yew_ids) {
			if(object.getWorldLocation().distanceTo2D(client.getLocalPlayer().getWorldLocation()) <= 5) { // if close to player
				if (id == yew_id) { // if object id is a yew tree
					double rand = Math.random(); // 0.0 to 1.0
					if ((rand < 0.01) && config.rareSound()) { // 1% chance of rare sound
						rareClip.setFramePosition(0);
						rareClip.start();
					} else {
						clip.setFramePosition(0);
						clip.start();
					}
				}
			}
		}
	}

	@Provides
	YewwwwConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(YewwwwConfig.class);
	}
}
