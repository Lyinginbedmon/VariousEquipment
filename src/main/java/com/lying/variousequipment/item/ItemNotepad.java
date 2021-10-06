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
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
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
				tooltip.add(new TranslationTextComponent("gui."+Reference.ModInfo.MOD_ID+".notepad.current_page"));
				tooltip.add(new StringTextComponent("   "+currentPage.title()));
				showingPage = true;
			}
			
			Map<Icon, Integer> iconTally = new HashMap<>();
			int markedLines = 0;
			String currentTask = "";
			for(int i=0; i<9; i++)
			{
				PadLine line = currentPage.getLine(i);
				if(line.icon().showInTooltip)
				{
					showingPage = true;
					
					if(currentTask.isEmpty())
						currentTask = line.text();
					
					Icon icon = line.icon();
					iconTally.put(icon, iconTally.get(icon) + 1);
					markedLines++;
				}
			}
			
			// Display next task in the list
			if(!currentTask.isEmpty())
			{
				showingPage = true;
				
				tooltip.add(new TranslationTextComponent("gui."+Reference.ModInfo.MOD_ID+".notepad.next_task"));
				tooltip.add(new StringTextComponent("   "+currentTask));
			}
			
			// Display breakdown of current task progress by icon type
			for(Icon icon : Icon.DISPLAY_ORDER)
				if(iconTally.containsKey(icon))
					tooltip.add(new StringTextComponent(" ").append(new TranslationTextComponent("gui."+Reference.ModInfo.MOD_ID+".notepad.tally_entry", iconTally.get(icon), markedLines, icon.translated())));
			
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
			pages.add(new PadPage(new CompoundNBT()));
		else if(compound.contains("Pages", 9))
		{
			ListNBT pageList = compound.getList("Pages", 11);
			for(int i=0; i<pageList.size(); i++)
				pages.add(new PadPage(pageList.getCompound(i)));
		}
		return pages;
	}
	
	public static enum Icon implements IStringSerializable
	{
		BLANK(false),
		QUESTION(true),
		TICK(true),
		CROSS(true);
		
		public static final EnumSet<Icon> DISPLAY_ORDER = EnumSet.<Icon>of(QUESTION, TICK, CROSS);
		
		private final boolean showInTooltip;
		
		private Icon(boolean display)
		{
			this.showInTooltip = display;
		}
		
		public String getString(){ return name().toLowerCase(); }
		
		public ITextComponent translated(){ return new TranslationTextComponent("enum."+Reference.ModInfo.MOD_ID+".icon."+getString()); }
		
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
		private final NonNullList<PadLine> lines;
		
		public PadPage(CompoundNBT pageData)
		{
			if(pageData.contains("Title", 8))
				headerText = pageData.getString("Title");
			
			lines = NonNullList.<PadLine>withSize(9, new PadLine(new CompoundNBT()));
			
			if(pageData.contains("Lines", 9))
			{
				ListNBT lineList = pageData.getList("Lines", 11);
				for(int i=0; i<lineList.size(); i++)
				{
					CompoundNBT lineData = lineList.getCompound(i);
					int lineInd = lineData.getInt("Index");
					lines.set(lineInd, new PadLine(lineData));
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
				int index = 0;
				for(PadLine line : lines)
				{
					if(line.isBlank()) continue;
					CompoundNBT lineData = line.writeToNBT(new CompoundNBT());
					lineData.putInt("Index", index++);
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
			
			for(PadLine line : lines)
				if(!line.isBlank())
					return false;
			
			return true;
		}
		
		public PadLine getLine(int i){ return this.lines.get(i); }
		
		public String title(){ return this.headerText; }
		public void setTitle(String par1String){ this.headerText = par1String; }
	}
	
	public static class PadLine
	{
		private Icon icon = Icon.BLANK;
		private String text = "";
		
		public PadLine(CompoundNBT compound)
		{
			if(compound.contains("Text", 8))
				text = compound.getString("Text");
			
			if(compound.contains("Box", 8))
				icon = Icon.fromString(compound.getString("Icon"));
		}
		
		public CompoundNBT writeToNBT(CompoundNBT compound)
		{
			if(!text.isEmpty())
				compound.putString("Text", text);
			if(icon != Icon.BLANK)
				compound.putString("Icon", icon.getString());
			return compound;
		}
		
		public boolean isBlank()
		{
			return text.isEmpty() && icon == Icon.BLANK;
		}
		
		public String text(){ return this.text; }
		public void setText(String par1String){ this.text = par1String; }
		
		public Icon icon(){ return this.icon; }
		public void setIcon(Icon iconIn){ this.icon = iconIn; }
		public void nextIcon()
		{
			int ordinal = this.icon.ordinal() + 1;
			this.icon = Icon.values()[ordinal % Icon.values().length];
		}
	}
}
