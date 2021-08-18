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
		
		private final ForgeConfigSpec.IntValue torchBurnout;
		
		private boolean verbose = false;
		
		private int torchBurnoutRate = -1;
		
		public General(ForgeConfigSpec.Builder builder)
		{
			builder.push("general");
				verboseLog = builder.define("verboseLog", false);
				builder.comment("The rough number of random ticks a torch must receive before burning out, on average.", "-1 disables torch burnout entirely");
				torchBurnout = builder.defineInRange("torch_burnout_rate_ticks", -1, -1, Integer.MAX_VALUE);
			builder.pop();
		}
		
		private void checkDirty(){ if(dirty) updateCache(); }
		
		public void updateCache()
		{
			if(verboseLog != null)
				verbose = verboseLog.get();
			
			if(torchBurnout != null)
				torchBurnoutRate = torchBurnout.get();
			
			dirty = false;
		}
		
		public boolean verboseLogs()
		{
			checkDirty();
			return verbose;
		}
		
		public int torchBurnoutRate()
		{
			checkDirty();
			return torchBurnoutRate;
		}
		
		public boolean torchesBurnout(){ return torchBurnoutRate() != -1; }
	}
	
	public static final ForgeConfigSpec server_spec = SERVER_BUILDER.build();
}
