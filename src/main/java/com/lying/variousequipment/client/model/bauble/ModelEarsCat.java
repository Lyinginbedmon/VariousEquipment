package com.lying.variousequipment.client.model.bauble;

import net.minecraft.entity.LivingEntity;

public class ModelEarsCat<T extends LivingEntity> extends ModelHorns<T>
{
	public ModelEarsCat()
	{
		super();
		this.horns.setTextureOffset(0, 3).addBox(-3F, -9F, 0F, 1, 1, 2);
		this.horns.setTextureOffset(0, 3).addBox(2F, -9F, 0F, 1, 1, 2);
	}
}
