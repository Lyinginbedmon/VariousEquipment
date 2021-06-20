package com.lying.variousequipment.client.model.bauble;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

public class ModelTailFox extends ModelTail<LivingEntity>
{
	ModelRenderer[] tails = new ModelRenderer[9];
	ModelRenderer tailRoot;
	
	private int tailCount = 1;
	
	public ModelTailFox()
	{
		super();
		
		this.tailRoot = ModelUtils.freshRenderer(this);
		this.tailRoot.setRotationPoint(0F, 10F, 1.5F);
		for(int i=0; i<tails.length; i++)
		{
			this.tails[i] = ModelUtils.freshRenderer(this);
			this.tails[i].mirror = i%2 > 0;
			this.tails[i].setTextureOffset(0, 0).addBox(-2F, 0F, -2.5F, 4, 9, 5);
			
			this.tailRoot.addChild(tails[i]);
		}
		
		this.bipedBody.addChild(this.tailRoot);
	}
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
		float swing = MathHelper.cos(limbSwing * 0.6662F) * 0.4F * limbSwingAmount;
		switch(this.tailCount)
		{
			case 1:
				this.tails[0].rotateAngleZ = swing;
				break;
			case 3:
				this.tails[2].rotateAngleZ = swing;
				this.tails[2].rotateAngleX = ModelUtils.toRadians(25D);
			case 2:
				this.tails[0].rotateAngleZ = swing + ModelUtils.toRadians(25D);
				this.tails[1].rotateAngleZ = swing - ModelUtils.toRadians(25D);
				break;
			case 5:
				this.tails[4].rotateAngleZ = swing;
			case 4:
				this.tails[0].rotateAngleZ = swing - ModelUtils.toRadians(35D);
				this.tails[1].rotateAngleZ = swing - ModelUtils.toRadians(35D);
				this.tails[2].rotateAngleZ = swing + ModelUtils.toRadians(35D);
				this.tails[3].rotateAngleZ = swing + ModelUtils.toRadians(35D);
				
				this.tails[0].rotateAngleX = +ModelUtils.toRadians(35D);
				this.tails[1].rotateAngleX = -ModelUtils.toRadians(35D);
				this.tails[2].rotateAngleX = -ModelUtils.toRadians(35D);
				this.tails[3].rotateAngleX = +ModelUtils.toRadians(35D);
				break;
			case 7:
				this.tails[6].rotateAngleZ = swing;
			case 6:
				this.tails[0].rotateAngleZ = swing + ModelUtils.toRadians(45D);
				this.tails[1].rotateAngleZ = swing + ModelUtils.toRadians(20D);
				this.tails[2].rotateAngleZ = swing + ModelUtils.toRadians(20D);
				this.tails[3].rotateAngleZ = swing - ModelUtils.toRadians(20D);
				this.tails[4].rotateAngleZ = swing - ModelUtils.toRadians(20D);
				this.tails[5].rotateAngleZ = swing - ModelUtils.toRadians(45D);
				
				this.tails[1].rotateAngleX = +ModelUtils.toRadians(35D);
				this.tails[2].rotateAngleX = -ModelUtils.toRadians(35D);
				this.tails[3].rotateAngleX = +ModelUtils.toRadians(35D);
				this.tails[4].rotateAngleX = -ModelUtils.toRadians(35D);
				break;
			case 9:
				this.tails[8].rotateAngleZ = swing;
			case 8:
				this.tails[0].rotateAngleZ = swing - ModelUtils.toRadians(50D);
				this.tails[1].rotateAngleZ = swing - ModelUtils.toRadians(50D);
				this.tails[2].rotateAngleZ = swing - ModelUtils.toRadians(20D);
				this.tails[3].rotateAngleZ = swing - ModelUtils.toRadians(20D);
				this.tails[4].rotateAngleZ = swing + ModelUtils.toRadians(20D);
				this.tails[5].rotateAngleZ = swing + ModelUtils.toRadians(20D);
				this.tails[6].rotateAngleZ = swing + ModelUtils.toRadians(50D);
				this.tails[7].rotateAngleZ = swing + ModelUtils.toRadians(50D);
				
				this.tails[0].rotateAngleX = +ModelUtils.toRadians(20D);
				this.tails[1].rotateAngleX = -ModelUtils.toRadians(20D);
				this.tails[2].rotateAngleX = +ModelUtils.toRadians(40D);
				this.tails[3].rotateAngleX = -ModelUtils.toRadians(40D);
				this.tails[4].rotateAngleX = +ModelUtils.toRadians(40D);
				this.tails[5].rotateAngleX = -ModelUtils.toRadians(40D);
				this.tails[6].rotateAngleX = +ModelUtils.toRadians(20D);
				this.tails[7].rotateAngleX = -ModelUtils.toRadians(20D);
				break;
		}
		for(int i=0; i<this.tailCount; i++)
			addWiggle(this.tails[i], ageInTicks, i%2 == 0 ? 1 : -1, i);
		
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tailRoot.rotateAngleX = (float)ModelUtils.toRadians(60D);
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tailRoot.rotateAngleX = (float)ModelUtils.toRadians(20D);
				break;
			case SLEEPING:
				this.tailRoot.rotateAngleX = (float)ModelUtils.toRadians(90D);
				break;
			default:
		    	this.tailRoot.rotateAngleX = (float)ModelUtils.toRadians(50D);
				break;
		}
		
		this.tailRoot.rotateAngleX += ModelUtils.toRadians((40D / 9D) * this.tailCount);
    }
	
	public void setTailCount(int par1Int)
	{
		this.tailCount = par1Int;
		
		par1Int--;
		for(int i=0; i<this.tails.length; i++)
		{
			this.tails[i].rotateAngleX = 0F;
			this.tails[i].rotateAngleY = 0F;
			this.tails[i].rotateAngleZ = 0F;
			
			this.tails[i].showModel = i <= par1Int;
		}
	}
	
	private void addWiggle(ModelRenderer part, float ageInTicks, int mult, int offset)
	{
		float time = ageInTicks + offset * 1000;
		part.rotateAngleZ += (MathHelper.cos(time * 0.09F) * 0.05F + 0.05F) * mult;
		part.rotateAngleX += (MathHelper.sin(time * 0.067F) * 0.05F) * mult;
	}
}
