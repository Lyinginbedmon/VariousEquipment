package com.lying.variousequipment.client.model.bauble;

import java.util.ArrayList;

import com.lying.variousequipment.client.model.ModelUtils;

import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.LivingEntity;

public class ModelTailKirin extends ModelTail<LivingEntity>
{
	ModelRenderer tail;
	ArrayList<ModelRenderer> tailSegments = new ArrayList<ModelRenderer>();
	
	public ModelTailKirin()
	{
		super();
		
		this.tail = ModelUtils.freshRenderer(this);
		this.tail.setRotationPoint(0F, 12F, 2F);
		this.tail.setTextureOffset(0, 0).addBox(-1F, -1F, -1F, 2, 6, 2, -0.2F);
		tailSegments.add(tail);
		
		for(int i=0; i<3; i++)
		{
			ModelRenderer segment = makeTailSegment(i);
			tailSegments.get(i).addChild(segment);
			tailSegments.add(segment);
		}
		tailSegments.get(tailSegments.size() - 2).setTextureOffset(8, 0).addBox(0F, 1F, 0F, 0, 5, 2);
		tailSegments.get(tailSegments.size() - 1).setTextureOffset(0, 11).addBox(-1.5F, 1.5F, -1.5F, 3, 7, 3, -0.25F);
		tailSegments.get(tailSegments.size() - 1).setTextureOffset(8, 7).addBox(0F, 1F, 0F, 0, 2, 2);
		
		this.bipedBody.addChild(tail);
	}
	
    private ModelRenderer makeTailSegment(int tailPosition)
    {
    	ModelRenderer segment = ModelUtils.freshRenderer(this);
    	segment.mirror = (tailPosition % 2) == 0;
    	segment.setTextureOffset(0, 0).addBox(-1F, 0F, -1F, 2, 6, 2, -0.2F);
    	segment.rotationPointY = 3.5F + (1F*tailPosition);
    	segment.rotateAngleX = (1+tailPosition) * ModelUtils.degree10;
    	
    	return segment;
    }
    
	public void setRotationAngles(LivingEntity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch)
    {
    	float timeMag = 1F;
    	float curl = ModelUtils.degree10;
		switch(entityIn.getPose())
		{
			case CROUCHING:
				curl = ModelUtils.degree5 * 3F;
				timeMag = 0.3F;
				break;
			case SWIMMING:
			case FALL_FLYING:
				curl = ModelUtils.degree5;
				timeMag = 0.5F;
				break;
			case SLEEPING:
				curl = 0F;
				timeMag = 0F;
				break;
			default:
				break;
		}
    	float time = (((float)Math.sin(ageInTicks / 10)) * 0.5F) * timeMag;
		
    	int i = 0;
    	for(ModelRenderer segment : tailSegments)
    	{
    		segment.rotateAngleY = time / 1.5F;
    		segment.rotateAngleX = (time * Math.signum(time)) / 8 + (curl * i++);
    	}
		
		switch(entityIn.getPose())
		{
			case CROUCHING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(0D);
				break;
			case SWIMMING:
			case FALL_FLYING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(10D);
				break;
			case SLEEPING:
				this.tail.rotateAngleX = (float)ModelUtils.toRadians(0D);
				break;
			default:
		    	this.tail.rotateAngleX = (float)ModelUtils.toRadians(45D);
				break;
		}
    }
}
