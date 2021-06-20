package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelAntenna<T extends LivingEntity> extends ModelHorns<T>
{
	Antenna antennaMid;
	Antenna antennaRight;
	Antenna antennaLeft;
	
	public ModelAntenna()
	{
		super();
		this.textureHeight = 32;
		this.textureWidth = 64;
		
		this.antennaMid = new Antenna(this, this.horns, 0F);
		this.antennaRight = new Antenna(this, this.horns, -2F);
		this.antennaLeft = new Antenna(this, this.horns, +2F);
		
		setAntennaCount(false);
	}
	
	public void setRotationAngles(T entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
		switch(entityIn.getPose())
		{
			case SWIMMING:
			case FALL_FLYING:
			case CROUCHING:
				this.antennaMid.setRotationAngles(ModelUtils.toRadians(-25D), 0F, ModelUtils.toRadians(-50D), ageInTicks);
				
				this.antennaRight.setRotationAngles(ModelUtils.toRadians(-45D), ModelUtils.toRadians(-15D), ModelUtils.toRadians(-70D), ageInTicks);
				this.antennaLeft.setRotationAngles(ModelUtils.toRadians(-45D), ModelUtils.toRadians(15D), ModelUtils.toRadians(-70D), ageInTicks + 1000);
				break;
			default:
				this.antennaMid.setRotationAngles(ModelUtils.toRadians(-15D), 0F, ModelUtils.toRadians(-60D), ageInTicks);
				
				this.antennaRight.setRotationAngles(ModelUtils.toRadians(-15D), ModelUtils.toRadians(35D), ModelUtils.toRadians(90D), ageInTicks);
				this.antennaLeft.setRotationAngles(ModelUtils.toRadians(-15D), ModelUtils.toRadians(-35D), ModelUtils.toRadians(90D), ageInTicks + 1000);
				break;
		}
	}
	
	public void setAntennaCount(boolean isDouble)
	{
		if(isDouble)
		{
			this.antennaMid.setVisible(false);
			
			this.antennaRight.setVisible(true);
			this.antennaLeft.setVisible(true);
		}
		else
		{
			this.antennaMid.setVisible(true);
			
			this.antennaRight.setVisible(false);
			this.antennaLeft.setVisible(false);
		}
	}
	
	private class Antenna
	{
		private ModelRenderer base, top;
		
		public Antenna(Model model, ModelRenderer horns, float rotX)
		{
			this.base = ModelUtils.freshRenderer(model);
			this.base.setRotationPoint(rotX, -7.5F, 0F);
			this.base.setTextureOffset(0, 0).addBox(-1F, -4F, -1F, 2, 4, 2, -0.5F);
			horns.addChild(base);
			
			this.top = ModelUtils.freshRenderer(model);
			this.top.setRotationPoint(0F, -3.5F, 0F);
			this.top.setTextureOffset(8, 0).addBox(-1F, -5F, -1F, 2, 6, 2, -0.5F);
			this.top.setTextureOffset(0, 8).addBox(-2.5F, -6F, 0F, 5, 6, 0);
			this.base.addChild(top);
		}
		
		public void setRotationAngles(float baseX, float baseY, float topX, float ageInTicks)
		{
			this.base.rotateAngleX = baseX;
			this.base.rotateAngleY = baseY;
			
			this.top.rotateAngleX = topX + ((float)Math.sin(ageInTicks / 20)) * 0.25F;
			this.top.rotateAngleY = ((float)Math.sin(ageInTicks / 90)) * 0.25F;
		}
		
		public void setVisible(boolean bool)
		{
			this.base.showModel = bool;
		}
	}
}
