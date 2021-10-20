package com.lying.variousequipment.item;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.lying.variousequipment.client.gui.GuiNotepad;
import com.lying.variousequipment.reference.Reference;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class ItemNotepad extends Item
{
	public ItemNotepad(Properties properties)
	{
		super(properties.maxStackSize(1));
	}
	
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn)
	{
		ItemStack itemstack = playerIn.getHeldItem(handIn);
		if(worldIn.isRemote)
			GuiNotepad.open(playerIn, itemstack);
		playerIn.addStat(Stats.ITEM_USED.get(this));
		return ActionResult.func_233538_a_(itemstack, worldIn.isRemote());
	}
	
	@OnlyIn(Dist.CLIENT)
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn)
	{
		CompoundNBT stackData = stack.getOrCreateTag();
		if(stackData.contains("Pages", 9))
		{
			List<PadPage> pages = getPagesFromNBT(stackData);
			if(pages.size() == 0) return;
			
			boolean showingPage = false;
			
			// Display page header
			PadPage currentPage = pages.get(stackData.getInt("Page"));
			if(!currentPage.title().isEmpty())
			{
				tooltip.add(new TranslationTextComponent("gui."+Reference.ModInfo.MOD_ID+".notepad.current_page").modifyStyle((style) -> { return style.setBold(true).setFormatting(TextFormatting.GRAY); }));
				tooltip.add(new StringTextComponent("   "+currentPage.title()));
				showingPage = true;
			}
			
			Map<Icon, Integer> iconTally = new HashMap<>();
			String currentTask = "";
			for(int i=0; i<9; i++)
			{
				String text = currentPage.getLine(i);
				Icon icon = currentPage.getIcon(i);
				if(icon.showInTooltip)
				{
					showingPage = true;
					
					if(currentTask.isEmpty())
						currentTask = text;
					
					iconTally.put(icon, iconTally.getOrDefault(icon, 0) + 1);
				}
			}
			
			// Display next task in the list
			if(!currentTask.isEmpty())
			{
				showingPage = true;
				
				tooltip.add(new TranslationTextComponent("gui."+Reference.ModInfo.MOD_ID+".notepad.next_task").modifyStyle((style) -> { return style.setBold(true).setFormatting(TextFormatting.GRAY); }));
				tooltip.add(new StringTextComponent("   "+currentTask));
			}
			
			// Display breakdown of current task progress by icon type
			for(Icon icon : Icon.DISPLAY_ORDER)
				if(iconTally.containsKey(icon))
					tooltip.add(new StringTextComponent(" ").append(icon.format(new TranslationTextComponent("gui."+Reference.ModInfo.MOD_ID+".notepad.tally_entry", iconTally.get(icon), icon.translated()))));
			
			// Display page index in total
			if(pages.size() > 1)
			{
				if(showingPage)
					tooltip.add(new StringTextComponent(""));
				tooltip.add(new TranslationTextComponent("gui."+Reference.ModInfo.MOD_ID+".notepad.page_count", (stackData.getInt("Page") + 1), pages.size()));
			}
		}
	}
	
	public static List<PadPage> getPagesFromNBT(CompoundNBT compound)
	{
		List<PadPage> pages = Lists.newArrayList();
		if(compound.isEmpty())
			return Lists.newArrayList(new PadPage(new CompoundNBT()));
		else if(compound.contains("Pages", 9))
		{
			ListNBT pageList = compound.getList("Pages", 10);
			for(int i=0; i<pageList.size(); i++)
				pages.add(new PadPage(pageList.getCompound(i)));
		}
		return pages;
	}
	
	public static CompoundNBT writePagesToNBT(List<PadPage> pages, CompoundNBT compound)
	{
		if(!pages.isEmpty())
		{
			ListNBT pageList = new ListNBT();
			pages.forEach((page) -> { pageList.add(page.writeToNBT(new CompoundNBT())); });
			compound.put("Pages", pageList);
		}
		
		return compound;
	}
	
	public static enum Icon implements IStringSerializable
	{
		BLANK(),
		QUESTION(TextFormatting.WHITE, 0),
		TICK(TextFormatting.GREEN, 1),
		CROSS(TextFormatting.RED, 2);
		
		public static final EnumSet<Icon> DISPLAY_ORDER = EnumSet.<Icon>of(QUESTION, TICK, CROSS);
		
		private final boolean showInTooltip;
		private final TextFormatting displayColor;
		private final int textureIndex;
		
		private Icon()
		{
			this.showInTooltip = false;
			this.displayColor = TextFormatting.GRAY;
			this.textureIndex = 0;
		}
		private Icon(TextFormatting color, int index)
		{
			this.showInTooltip = true;
			this.displayColor = color;
			this.textureIndex = index;
		}
		
		public String getString(){ return name().toLowerCase(); }
		
		public ITextComponent translated(){ return new TranslationTextComponent("enum."+Reference.ModInfo.MOD_ID+".icon."+getString()); }
		public IFormattableTextComponent format(IFormattableTextComponent textComponent)
		{
			return textComponent.modifyStyle((style) -> { return style.applyFormatting(displayColor); });
		}
		
		public int textureIndex(){ return this.textureIndex; }
		
		public boolean isDisplayed()
		{
			return DISPLAY_ORDER.contains(this);
		}
		
		public static Icon fromString(String nameIn)
		{
			for(Icon icon : values())
				if(icon.getString().equalsIgnoreCase(nameIn))
					return icon;
			return BLANK;
		}
	}
	
	public static class PadPage
	{
		private String headerText = "";
		
		private final NonNullList<String> strings;
		private final NonNullList<Icon> icons;
		
		public PadPage(CompoundNBT pageData)
		{
			if(pageData.contains("Title", 8))
				headerText = pageData.getString("Title");
			
			strings = NonNullList.<String>withSize(9, "");
			icons = NonNullList.<Icon>withSize(9, Icon.BLANK);
			
			if(pageData.contains("Lines", 9))
			{
				ListNBT lineList = pageData.getList("Lines", 10);
				for(int i=0; i<lineList.size(); i++)
				{
					CompoundNBT lineData = lineList.getCompound(i);
					int lineInd = lineData.getInt("Index");
					strings.set(lineInd, lineData.contains("Text", 8) ? lineData.getString("Text") : "");
					icons.set(lineInd, lineData.contains("Icon", 8) ? Icon.fromString(lineData.getString("Icon")) : Icon.BLANK);
				}
			}
		}
		
		public CompoundNBT writeToNBT(CompoundNBT compound)
		{
			if(!isBlank())
			{
				if(!headerText.isEmpty())
					compound.putString("Title", headerText);
				
				ListNBT list = new ListNBT();
				for(int index=0; index<9; index++)
				{
					if(isLineBlank(index)) continue;
					String text = strings.get(index);
					Icon icon = icons.get(index);
					
					CompoundNBT lineData = new CompoundNBT();
					if(!text.isEmpty())
						lineData.putString("Text", text);
					if(icon != Icon.BLANK)
						lineData.putString("Icon", icon.getString());
					lineData.putInt("Index", index);
					list.add(lineData);
				}
				if(list.size() > 0)
					compound.put("Lines", list);
			}
			
			return compound;
		}
		
		public boolean isBlank()
		{
			if(!headerText.isEmpty())
				return false;
			
			for(int i=0; i<strings.size(); i++)
				if(!isLineBlank(i))
					return false;
			
			return true;
		}
		
		public String title(){ return this.headerText; }
		public void setTitle(String par1String){ this.headerText = par1String; }
		
		public boolean isLineBlank(int index){ return this.strings.get(index).isEmpty(); }
		
		public String getLine(int index){ return this.strings.get(index); }
		public void setLine(int index, String text)
		{
			this.strings.set(index, text);
		}
		
		public Icon getIcon(int index){ return this.icons.get(index); }
		public void setIcon(int index, Icon iconIn){ this.icons.set(index, iconIn); }
		
		public void nextIcon(int index)
		{
			Icon icon = this.getIcon(index);
			int ordinal = icon.ordinal() + 1;
			setIcon(index, Icon.values()[ordinal % Icon.values().length]);
		}
	}
}
