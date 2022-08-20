package com.yewww.bungle;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("yewwww")
public interface YewwwwConfig extends Config
{
	@ConfigItem(
		keyName = "rareSound",
		name = "Play Rare Sound",
		description = "Rare sound (1/100) - slightly longer, with music",
		position = 1
	)
	default boolean rareSound()
	{
		return true;
	}
}
