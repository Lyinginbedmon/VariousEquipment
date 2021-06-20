package com.lying.variousequipment.client.model.bauble;

import java.util.ArrayList;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelTailKobold extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	ArrayList<ModelRenderer> tailSegments = new ArrayList<ModelRenderer>();
	
	public ModelTailKobold()
	{
		super();
		
		tail = ModelUtils.freshRenderer(this).setTextureOffset(0, 0).addBox(-1F, -1F, 0F, 2, 2, 8);
		tail.setRotationPoint(0F, 10F, 2F);
		tailSegments.add(tail);
		
		for(int i=0; i<2; i++)
		{
			ModelRenderer segment = makeTailSegment(i);
			tailSegments.get(i).addChild(segment);
			tailSegments.add(segment);
		}
		
		this.bipedBody.addChild(tail);
	}
    
    private ModelRenderer makeTailSegment(int tailPosition)
    {
    	ModelRenderer segment = ModelUtils.freshRenderer(this).setTextureOffset(20+(20*tailPosition), 0).addBox(-1F, -1F, 0F, 2, 2, 8 - (2*tailPosition));
    	segment.rotationPointZ = 8F - tailPosition;
    	
    	return segment;
    }
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
	{
    	float timeMag = 1F;
		switch(entityIn.getPose())
		{
			case CROUCHING:
				timeMag = 0.3F;
				break;
			case SWIMMING:
			case FALL_FLYING:
				timeMag = 0.5F;
				break;
			case SLEEPING:
				timeMag = 0F;
				break;
			default:
				break;
		}
    	float time = (((float)Math.sin(ageInTicks / 20)) * 0.5F) * timeMag;
		for(ModelRenderer segment : tailSegments)
		{
			segment.rotateAngleY = time / 3;
			segment.rotateAngleX = (time * Math.signum(time)) / 8;
		}
		
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail.rotateAngleX += (float)ModelUtils.toRadians(-25D);
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail.rotateAngleX += (float)ModelUtils.toRadians(-75D);
				break;
			case SLEEPING:
				this.tail.rotateAngleX += (float)ModelUtils.toRadians(70D);
				break;
			default:
				break;
		}
	}
}
