package com.lying.variousequipment.item.bauble;

import com.lying.variousequipment.client.model.bauble.ModelTailAnt;
import com.lying.variousequipment.client.model.bauble.ModelTailCat;
import com.lying.variousequipment.client.model.bauble.ModelTailCat2;
import com.lying.variousequipment.client.model.bauble.ModelTailDevil;
import com.lying.variousequipment.client.model.bauble.ModelTailDragon;
import com.lying.variousequipment.client.model.bauble.ModelTailDragonfly;
import com.lying.variousequipment.client.model.bauble.ModelTailFox;
import com.lying.variousequipment.client.model.bauble.ModelTailHorse;
import com.lying.variousequipment.client.model.bauble.ModelTailKirin;
import com.lying.variousequipment.client.model.bauble.ModelTailKobold;
import com.lying.variousequipment.client.model.bauble.ModelTailLizard;
import com.lying.variousequipment.client.model.bauble.ModelTailRabbit;
import com.lying.variousequipment.client.model.bauble.ModelTailRat;
import com.lying.variousequipment.client.model.bauble.ModelTailWolf;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurio;

public abstract class ItemTails extends ItemCosmetic implements IDyeableArmorItem
{
	protected Object model = null;
	
	public ItemTails(Properties properties)
	{
		super(properties.maxStackSize(1).maxDamage(0));
	}
	
	public int getColor(ItemStack stack)
	{
		CompoundNBT stackData = stack.getChildTag("display");
		return stackData != null && stackData.contains("color", 99) ? stackData.getInt("color") : getDefaultColor();
	}
	
	public int getDefaultColor(){ return DyeColor.WHITE.getColorValue(); }
	
	@OnlyIn(Dist.CLIENT)
	public abstract BipedModel<LivingEntity> getModel();
	
	@OnlyIn(Dist.CLIENT)
	public abstract ResourceLocation getTexture();
	
	@SuppressWarnings("unchecked")
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
			int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		if(this.model == null)
			this.model = getModel();
		
		BipedModel<LivingEntity> tail = ((BipedModel<LivingEntity>)this.model); 
		ICurio.RenderHelper.followBodyRotations(living, new BipedModel[]{tail});
		tail.setRotationAngles(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, tail.getRenderType(getTexture()), false, stack.hasEffect());
		
        int i = getColor(stack);
        float r = (float)(i >> 16 & 255) / 255.0F;
        float g = (float)(i >> 8 & 255) / 255.0F;
        float b = (float)(i & 255) / 255.0F;
        
		tail.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
	}
	
	public static abstract class ItemTailsOverlay extends ItemTails
	{
		public ItemTailsOverlay(Properties properties)
		{
			super(properties);
		}
		
		@SuppressWarnings("unchecked")
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
				int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
				float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			if(this.model == null)
				this.model = getModel();
			BipedModel<LivingEntity> tail = ((BipedModel<LivingEntity>)this.model);
			super.render(identifier, index, matrixStackIn, renderTypeBuffer, light, living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
			IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, tail.getRenderType(getOverlayTexture()), false, stack.hasEffect());
			tail.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
		}
		
		@OnlyIn(Dist.CLIENT)
		public abstract ResourceLocation getOverlayTexture();
	}
	
	public static class Kobold extends ItemTails
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_kobold.png");
		
		public Kobold(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailKobold(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		public int getDefaultColor(){ return 15369498; }
	}
	
	public static class Kirin extends ItemTails
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_kirin.png");
		
		public Kirin(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailKirin(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		public int getDefaultColor(){ return 16105086; }
	}
	
	public static class Wolf extends ItemTails
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_wolf.png");
		
		public Wolf(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailWolf(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
	}
	
	public static class Cat extends ItemTails
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_cat.png");
		
		public Cat(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailCat(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		public int getDefaultColor(){ return 16768372; }
	}
	
	public static class Cat2 extends Cat
	{
		public Cat2(Item.Properties propertiesIn)
		{
			super(propertiesIn);
		}
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailCat2(); }
		
		public int getDefaultColor(){ return 8873290; }
	}
	
	public static class Fox extends ItemTailsOverlay
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_fox.png");
		private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_fox_overlay.png");
		
		public Fox(Properties properties)
		{
			super(properties);
		}
		
		public ITextComponent getDisplayName(ItemStack stack)
		{
			return getTails(stack) == 1 ? super.getDisplayName(stack) : new TranslationTextComponent("item."+Reference.ModInfo.MOD_ID+".tail_kitsune", getTails(stack));
		}
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailFox(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getOverlayTexture(){ return TEXTURE_OVERLAY; }
		
		public int getDefaultColor(){ return 14842913; }
		
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
				int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
				float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			if(this.model == null)
				this.model = getModel();
			ModelTailFox tail = ((ModelTailFox)this.model);
			tail.setTailCount(getTails(stack));
			
			super.render(identifier, index, matrixStackIn, renderTypeBuffer, light, living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
		}
		
		public static int getTails(ItemStack stack)
		{
			CompoundNBT stackData = stack.getChildTag("display");
			return stackData != null && stackData.contains("count", 99) ? stackData.getInt("count") : 1;
		}
		
		public static void setTails(ItemStack stack, int count)
		{
			CompoundNBT displayData = stack.getOrCreateChildTag("display");
			displayData.putInt("count", count);
			
			CompoundNBT stackData = stack.getTag();
			stackData.put("display", displayData);
			stack.setTag(stackData);
		}
	}
	
	public static class Rat extends ItemTailsOverlay
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_rat.png");
		private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_rat_overlay.png");
		
		public Rat(Item.Properties propertiesIn)
		{
			super(propertiesIn);
		}

		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }

		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getOverlayTexture(){ return TEXTURE_OVERLAY; }

		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailRat(); }
	}
	
	public static class Dragon extends ItemTails
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_dragon.png");
		
		public Dragon(Item.Properties propertiesIn)
		{
			super(propertiesIn);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailDragon(); }
		
		public int getDefaultColor(){ return 1908001; }
	}
	
	public static class Devil extends ItemTails
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_devil.png");
		
		public Devil(Item.Properties propertiesIn)
		{
			super(propertiesIn);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailDevil(); }
		
		public int getDefaultColor(){ return 12851481; }
	}
	
	public static class Rabbit extends ItemTails
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_rabbit.png");
		
		public Rabbit(Item.Properties propertiesIn)
		{
			super(propertiesIn);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailRabbit(); }
		
		public int getDefaultColor(){ return 9731426; }
	}
	
	public static class Horse extends ItemTails
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_horse.png");
		
		public Horse(Item.Properties propertiesIn)
		{
			super(propertiesIn);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailHorse(); }
		
		public int getDefaultColor(){ return 8607267; }
	}
	
	public static class Dragonfly extends ItemTails
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_dragonfly.png");
		
		public Dragonfly(Item.Properties propertiesIn)
		{
			super(propertiesIn);
		}
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailDragonfly(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		public int getDefaultColor(){ return 6919449; }
	}
	
	public static class Ant extends ItemTailsOverlay
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_ant.png");
		private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_ant_overlay.png");
		
		public Ant(Item.Properties propertiesIn)
		{
			super(propertiesIn);
		}
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailAnt(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getOverlayTexture(){ return TEXTURE_OVERLAY; }
		
		public int getDefaultColor(){ return 11612230; }
	}
	
	public static class Lizard extends ItemTails
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_lizard.png");
		
		public Lizard(Item.Properties propertiesIn)
		{
			super(propertiesIn);
		}
		
		@OnlyIn(Dist.CLIENT)
		public BipedModel<LivingEntity> getModel(){ return new ModelTailLizard(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		public int getDefaultColor(){ return 6919449; }
	}
	
	public static class Lizard2 extends Lizard
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/tail_lizard_barbed.png");
		
		public Lizard2(Item.Properties propertiesIn)
		{
			super(propertiesIn);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(){ return TEXTURE; }
		
		public int getDefaultColor(){ return 15132410; }
	}
}
