package com.lying.variousequipment.config;

import net.minecraftforge.common.ForgeConfigSpec;

public class ConfigVE
{
	public static final ForgeConfigSpec.Builder SERVER_BUILDER = new ForgeConfigSpec.Builder();
	public static final General GENERAL = new General(SERVER_BUILDER);
	
	public static void updateCache()
	{
		GENERAL.dirty = true;
	}
	
	public static class General
	{
		private boolean dirty = true;
		
		private final ForgeConfigSpec.BooleanValue verboseLog;
		
		private final ForgeConfigSpec.BooleanValue batsPoop;
		
		private boolean verbose = false;
		private boolean guano = true;
		
		public General(ForgeConfigSpec.Builder builder)
		{
			builder.push("general");
				verboseLog = builder.define("verboseLog", false);
				batsPoop = builder.define("bat_guano", true);
			builder.pop();
		}
		
		private void checkDirty(){ if(dirty) updateCache(); }
		
		public void updateCache()
		{
			if(verboseLog != null)
				verbose = verboseLog.get();
			
			if(batsPoop != null)
				guano = batsPoop.get();
			
			dirty = false;
		}
		
		public boolean verboseLogs()
		{
			checkDirty();
			return verbose;
		}
		
		public boolean shouldBatsPoop()
		{
			checkDirty();
			return guano;
		}
	}
	
	public static final ForgeConfigSpec server_spec = SERVER_BUILDER.build();
}
