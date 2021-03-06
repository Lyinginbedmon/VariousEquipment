package com.lying.variousequipment.item.bauble;

import com.lying.variousequipment.client.model.bauble.*;
import com.lying.variousequipment.init.VEItems;
import com.lying.variousequipment.item.VEItemGroup;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;

import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.DyeColor;
import net.minecraft.item.IDyeableArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import top.theillusivec4.curios.api.type.capability.ICurio;

public abstract class ItemHorns extends ItemCosmetic implements IDyeableArmorItem
{
	protected Object model = null;
	
	public ItemHorns(Properties properties)
	{
		super(properties.maxStackSize(1).maxDamage(0).group(VEItemGroup.PROPS));
	}
	
	public int getColor(ItemStack stack)
	{
		CompoundNBT stackData = stack.getChildTag("display");
		return stackData != null && stackData.contains("color", 99) ? stackData.getInt("color") : getDefaultColor();
	}
	
	protected int getDefaultColor(){ return DyeColor.WHITE.getColorValue(); }
	
	@OnlyIn(Dist.CLIENT)
	public abstract ModelHorns<LivingEntity> getModel();
	
	@OnlyIn(Dist.CLIENT)
	public abstract ResourceLocation getTexture(ItemStack stack);
	
	@OnlyIn(Dist.CLIENT)
	@SuppressWarnings("unchecked")
	public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
			int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
			float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
	{
		if(this.model == null)
			this.model = getModel();
		
		ModelHorns<LivingEntity> horns = (ModelHorns<LivingEntity>)this.model;
		ICurio.RenderHelper.followHeadRotations(living, horns.horns);
		horns.setRotationAngles(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
		IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, horns.getRenderType(getTexture(stack)), false, stack.hasEffect());
		
        int i = getColor(stack);
        float r = (float)(i >> 16 & 255) / 255.0F;
        float g = (float)(i >> 8 & 255) / 255.0F;
        float b = (float)(i & 255) / 255.0F;
        
		horns.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
	}
	
	public static abstract class ItemHornsOverlay extends ItemHorns
	{
		public ItemHornsOverlay(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		@SuppressWarnings("unchecked")
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
				int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
				float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			if(this.model == null)
				this.model = getModel();
			super.render(identifier, index, matrixStackIn, renderTypeBuffer, light, living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
			
			ModelHorns<LivingEntity> horns = (ModelHorns<LivingEntity>)this.model;
			ICurio.RenderHelper.followHeadRotations(living, horns.horns);
			horns.setRotationAngles(living, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
			IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, horns.getRenderType(getOverlayTexture()), false, stack.hasEffect());
			horns.render(matrixStackIn, vertexBuilder, light, OverlayTexture.NO_OVERLAY, 1F, 1F, 1F, 1F);
		}
		
		@OnlyIn(Dist.CLIENT)
		public abstract ResourceLocation getOverlayTexture();
	}
	
	public static class Deer extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/horns_deer.png");
		
		@OnlyIn(Dist.CLIENT)
		private Object model;
		
		public Deer(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelHornsDeer<LivingEntity>(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
	}
	
	public static class Ram extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/horns_ram.png");
		
		@OnlyIn(Dist.CLIENT)
		private Object model;
		
		public Ram(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelHornsRam<LivingEntity>(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
	}
	
	public static class Hartebeest extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/horns_hartebeest.png");
		
		public Hartebeest(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelHornsHartebeest<LivingEntity>(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
	}
	
	public static class Kirin extends ItemHorns
	{
		public Kirin(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer, int light, LivingEntity living, 
				float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			((ItemHorns.Deer)VEItems.HORNS_DEER).render(identifier, index, matrixStackIn, renderTypeBuffer, light, living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
			((ItemHorns.Ram)VEItems.HORNS_RAM).render(identifier, index, matrixStackIn, renderTypeBuffer, light, living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return null; }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return null; }
	}
	
	public static class KirinStorm extends ItemHorns
	{
	    public static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/horns_lightning.png");
	    
	    private Object hornsRam = null;
	    private Object hornsDeer = null;
	    
		public KirinStorm(Properties properties)
		{
			super(properties);
		}
		
		protected int getDefaultColor(){ return DyeColor.CYAN.getColorValue(); }
		
		@OnlyIn(Dist.CLIENT)
		@SuppressWarnings({ "unchecked", "deprecation" })
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
				int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
				float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			if(hornsRam == null)
				hornsRam = new ModelHornsRam<LivingEntity>();
			if(hornsDeer == null)
				hornsDeer = new ModelHornsDeer<LivingEntity>();
			
			float f = ((float)living.ticksExisted + partialTicks) * 0.004F;
	        
	        ModelHorns<LivingEntity> hornsA = (ModelHorns<LivingEntity>)hornsRam;
	        ModelHorns<LivingEntity> hornsB = (ModelHorns<LivingEntity>)hornsDeer;
	        
	        matrixStackIn.push();
		        RenderSystem.disableLighting();
				ICurio.RenderHelper.followHeadRotations(living, hornsA.horns);
				ICurio.RenderHelper.followHeadRotations(living, hornsB.horns);
				IVertexBuilder vertexBuilder = ItemRenderer.getBuffer(renderTypeBuffer, RenderType.getEnergySwirl(getTexture(stack), f, f), false, false);
				
		        int i = getColor(stack);
		        float r = (float)(i >> 16 & 255) / 255.0F;
		        float g = (float)(i >> 8 & 255) / 255.0F;
		        float b = (float)(i & 255) / 255.0F;
		        
				hornsA.render(matrixStackIn, vertexBuilder, 15728640, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
				hornsB.render(matrixStackIn, vertexBuilder, 15728640, OverlayTexture.NO_OVERLAY, r, g, b, 1F);
				RenderSystem.enableLighting();
			matrixStackIn.pop();
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return null; }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
	}
	
	public static class Wolf extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/ears_wolf.png");
		
		public Wolf(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelEarsWolf<LivingEntity>(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
	}
	
	public static class Cat extends Wolf
	{
		public Cat(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelEarsCat<LivingEntity>(); }
		
		protected int getDefaultColor(){ return 15840099; }
	}
	
	public static class Rabbit extends Wolf
	{
		public Rabbit(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelEarsRabbit<LivingEntity>(); }
		
		protected int getDefaultColor(){ return 9731426; }
	}
	
	public static class Axolotl extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/gills_axolotl.png");
		
		public Axolotl(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelGillsAxolotl<LivingEntity>(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
		
		protected int getDefaultColor(){ return 10832996; }
	}
	
	public static class Antenna extends ItemHorns
	{
		private static final ResourceLocation TEXTURE_A = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/antenna_geniculate.png");
		private static final ResourceLocation TEXTURE_B = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/antenna_plumose.png");
		
		public Antenna(Item.Properties properties)
		{
			super(properties);
		}
		
		public ITextComponent getDisplayName(ItemStack stack)
		{
			return new TranslationTextComponent("item."+Reference.ModInfo.MOD_ID+".antenna."+getType(stack).name().toLowerCase());
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelAntenna<LivingEntity>(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return getType(stack) == Type.PLUMOSE ? TEXTURE_B : TEXTURE_A; }
		
		protected int getDefaultColor(){ return 11612230; }
		
		public static Type getType(ItemStack stack)
		{
			CompoundNBT stackData = stack.getChildTag("display");
			if(stackData != null && stackData.contains("type", 8))
				return Type.fromString(stackData.getString("type"));
			else
			{
				setType(stack, Type.SINGLE);
				return Type.SINGLE;
			}
		}
		
		public static void setType(ItemStack stack, Type type)
		{
			CompoundNBT displayData = stack.getOrCreateChildTag("display");
			displayData.putString("type", type.getString());
			
			CompoundNBT stackData = stack.getTag();
			stackData.put("display", displayData);
			stack.setTag(stackData);
		}
		
		@OnlyIn(Dist.CLIENT)
		@SuppressWarnings("unchecked")
		public void render(String identifier, int index, MatrixStack matrixStackIn, IRenderTypeBuffer renderTypeBuffer,
				int light, LivingEntity living, float limbSwing, float limbSwingAmount, float partialTicks,
				float ageInTicks, float netHeadYaw, float headPitch, ItemStack stack)
		{
			if(this.model == null)
				this.model = getModel();
			ModelAntenna<LivingEntity> tail = ((ModelAntenna<LivingEntity>)this.model);
			tail.setAntennaCount(getType(stack).isDouble);
			
			super.render(identifier, index, matrixStackIn, renderTypeBuffer, light, living, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, stack);
		}
		
		public static enum Type implements IStringSerializable
		{
			SINGLE(false),
			PLUMOSE(true),
			GENICULATE(true);
			
			private final boolean isDouble;
			
			private Type(boolean doubleIn){ isDouble = doubleIn; }
			
			public String getString(){ return name().toLowerCase(); }
			
			public static Type fromString(String nameIn)
			{
				for(Type type : values())
					if(nameIn.equalsIgnoreCase(type.getString()))
						return type;
				return SINGLE;
			}
		}
	}
	
	public static class NosePig extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/nose_pig.png");
		
		public NosePig(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelNosePig.Pig<LivingEntity>(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
		
		public int getDefaultColor(){ return 15834776; }
	}
	
	public static class NosePiglin extends ItemHornsOverlay
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/nose_pig.png");
		private static final ResourceLocation TEXTURE_OVERLAY = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/nose_pig_overlay.png");
		
		public NosePiglin(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelNosePig.Piglin<LivingEntity>(); }
		
		@OnlyIn(Dist.CLIENT)
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }

		@Override
		public ResourceLocation getOverlayTexture(){ return TEXTURE_OVERLAY; }
		
		public int getDefaultColor(){ return 15245428; }
	}
	
	public static class Piglin extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/ears_piglin.png");
		
		public Piglin(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelEarsPiglin<LivingEntity>(); }

		@Override
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
		
		public int getDefaultColor(){ return 15245428; }
	}
	
	public static class NoseVillager extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/nose_villager.png");
		
		public NoseVillager(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelNoseVillager<LivingEntity>(); }
		
		@Override
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
		
		public int getDefaultColor(){ return 9461315; }
	}
	
	public static class NoseWitch extends NoseVillager
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/nose_witch.png");
		
		public NoseWitch(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelNoseVillager.Witch<LivingEntity>(); }
		
		@Override
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
	}
	
	public static class EarsElf extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/ears_pointy.png");
		
		public EarsElf(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelEarsElf<LivingEntity>(); }
		
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
		
		public int getDefaultColor(){ return 16764057; }
	}
	
	public static class EarsGoblin extends ItemHorns
	{
		private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/models/bauble/ears_goblin.png");
		
		public EarsGoblin(Properties properties)
		{
			super(properties);
		}
		
		@OnlyIn(Dist.CLIENT)
		public ModelHorns<LivingEntity> getModel(){ return new ModelEarsGoblin<LivingEntity>(); }
		
		public ResourceLocation getTexture(ItemStack stack){ return TEXTURE; }
		
		public int getDefaultColor(){ return 2394429; }
	}
}
