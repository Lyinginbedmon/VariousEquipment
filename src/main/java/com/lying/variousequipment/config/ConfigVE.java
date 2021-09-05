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
		
		private boolean verbose = false;
		
		public General(ForgeConfigSpec.Builder builder)
		{
			builder.push("general");
				verboseLog = builder.define("verboseLog", false);
			builder.pop();
		}
		
		private void checkDirty(){ if(dirty) updateCache(); }
		
		public void updateCache()
		{
			if(verboseLog != null)
				verbose = verboseLog.get();
			
			dirty = false;
		}
		
		public boolean verboseLogs()
		{
			checkDirty();
			return verbose;
		}
	}
	
	public static final ForgeConfigSpec server_spec = SERVER_BUILDER.build();
}
