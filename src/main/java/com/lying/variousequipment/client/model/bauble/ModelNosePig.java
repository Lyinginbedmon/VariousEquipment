package com.lying.variousequipment.client.model.bauble;

import net.minecraft.entity.LivingEntity;

public class ModelNosePig<T extends LivingEntity> extends ModelHorns<T>
{
	public static class Pig<T extends LivingEntity> extends ModelNosePig<T>
	{
		public Pig()
		{
			super();
			this.horns.setTextureOffset(0, 0).addBox(-2F, -4F, -5F, 4, 3, 1);
		}
	}
	
	public static class Piglin<T extends LivingEntity> extends ModelNosePig<T>
	{
		public Piglin()
		{
			super();
			this.horns.setTextureOffset(0, 4).addBox(-2F, -4F, -5F, 4, 4, 1);
			this.horns.setTextureOffset(4, 9).addBox(2.0F, -2.0F, -5.0F, 1, 2, 1);
			this.horns.setTextureOffset(0, 9).addBox(-3.0F, -2.0F, -5.0F, 1, 2, 1);
		}
	}
}
