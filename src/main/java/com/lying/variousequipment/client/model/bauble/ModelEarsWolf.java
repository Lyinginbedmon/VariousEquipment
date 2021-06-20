package com.lying.variousequipment.client.model.bauble;

import net.minecraft.entity.LivingEntity;

public class ModelEarsWolf<T extends LivingEntity> extends ModelHorns<T>
{
	public ModelEarsWolf()
	{
		super();
		this.horns.setTextureOffset(0, 0).addBox(-4F, -10F, 1F, 2, 2, 1);
		this.horns.setTextureOffset(0, 0).addBox(2F, -10F, 1F, 2, 2, 1);
	}
}
