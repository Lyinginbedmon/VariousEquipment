package com.lying.variousequipment.reference;

public class Reference
{
	public static class ModInfo
	{
		public static final String MOD_NAME	= "Various Equipment";
		public static final String MOD_ID	= "vareqp";
		public static final String MOD_PREFIX	= MOD_ID + ":";
		
		public static final String VERSION	= "1.16_001q6";
	}
	
	public static class Values
	{
		public static final int TICKS_PER_SECOND		= 20;
		public static final int TICKS_PER_MINUTE		= TICKS_PER_SECOND * 60;
		public static final int TICKS_PER_HOUR			= TICKS_PER_MINUTE * 60;
		public static final int ENTITY_MAX_AIR			= 300;
		public static final int TICKS_PER_BUBBLE		= ENTITY_MAX_AIR / TICKS_PER_SECOND;
		public static final int TICKS_PER_DAY			= TICKS_PER_SECOND * 1200;
	}
}
