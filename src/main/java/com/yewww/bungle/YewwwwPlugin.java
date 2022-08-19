package com.yewww.bungle;

import com.google.inject.Provides;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.*;
import net.runelite.api.events.GameObjectDespawned;
import net.runelite.api.events.GameStateChanged;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;

import javax.inject.Inject;
import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import static net.runelite.api.ObjectID.*;

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

	private Clip clip;
	@Override
	protected void startUp() {
		log.info("Yewww started!");
		try (InputStream fileStream = new BufferedInputStream(YewwwwPlugin.class.getResourceAsStream("YOUUU.mp3"));
			 AudioInputStream sound = AudioSystem.getAudioInputStream(fileStream))
		{
			clip.open(sound);
		}
		catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			log.warn("Unable to load builtin Yewww sound", e);
		}
	}

	@Override
	protected void shutDown() {
		log.info("Yewww stopped!");
	}

	@Subscribe
	public void onGameStateChanged(GameStateChanged gameStateChanged)
	{
		if (gameStateChanged.getGameState() == GameState.LOGGED_IN)
		{
			client.addChatMessage(ChatMessageType.GAMEMESSAGE, "", "Yewww says " + config.greeting(), null);
		}
	}

//	static final int[] yew_ids = {YEW, NULL_10823, YEW_36683, YEW_40756};
	static final int[] yew_ids = {TREE, TREE_1277, TREE_1278, TREE_1279, TREE_1280, TREE_40750, TREE_40752};

	@Subscribe
	public void onGameObjectDespawned(final GameObjectDespawned event)
	{
		final GameObject object = event.getGameObject();
		int id = object.getId();
		for (int yew_id : yew_ids) {
			if (id == yew_id) {
//				client.playSoundEffect(SoundEffectID.GE_DECREMENT_PLOP, SoundEffectVolume.HIGH); // play sound
				clip.start();
			}
		}
	}

	@Provides
	YewwwwConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(YewwwwConfig.class);
	}
}
