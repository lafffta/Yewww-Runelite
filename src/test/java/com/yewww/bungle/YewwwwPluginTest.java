package com.yewww.bungle;

import net.runelite.client.RuneLite;
import net.runelite.client.externalplugins.ExternalPluginManager;

public class YewwwwPluginTest
{
	public static void main(String[] args) throws Exception
	{
		ExternalPluginManager.loadBuiltin(YewwwwPlugin.class);
		RuneLite.main(args);
	}
}