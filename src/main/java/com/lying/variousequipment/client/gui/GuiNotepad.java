package com.lying.variousequipment.client.gui;

import java.util.List;

import com.google.common.collect.Lists;
import com.lying.variousequipment.item.ItemNotepad;
import com.lying.variousequipment.item.ItemNotepad.PadPage;
import com.lying.variousequipment.network.PacketHandler;
import com.lying.variousequipment.network.PacketNotepadUpdate;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.screen.ReadBookScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class GuiNotepad extends Screen
{
	private static final int TEXT_COLOR = -1;
	
	private final PlayerEntity player;
	private final ItemStack notepad;
	private final List<PadPage> pages = Lists.newArrayList();
	private int pageIndex = 0;
	
	private PadPage currentPage;
	
	private final FontRenderer font;
	private TextFieldWidget header;
	private TextFieldWidget[] lines = new TextFieldWidget[9];
	private TextFieldWidget currentField = null;
	private final List<TextFieldWidget> textFields = Lists.newArrayList();
	
	private Button nextPage;
	private Button prevPage;
	
	protected GuiNotepad(PlayerEntity playerIn, ItemStack notepadIn)
	{
		super(notepadIn.getDisplayName());
		this.player = playerIn;
		this.notepad = notepadIn;
		this.font = Minecraft.getInstance().fontRenderer;
		generatePagesFromPad(this.notepad.getOrCreateTag());
		setCurrentPage(Math.min(this.notepad.getOrCreateTag().getInt("Page"), this.pages.size() - 1));
	}
	
	public static void open(PlayerEntity player, ItemStack tableIn)
	{
		Minecraft.getInstance().displayGuiScreen(new GuiNotepad(player, tableIn));
	}
	
	protected void init()
	{
		this.minecraft.keyboardListener.enableRepeatEvents(true);
		this.currentPage = getCurrentPage();
		
		initFields();
		
		this.buttons.clear();
		int midX = this.width / 2;
		this.addButton(nextPage = new Button(midX + 40 - 20, 195, 20, 20, new StringTextComponent(">"), (action) -> {
			if(this.pageIndex == (this.pages.size() - 1))
				this.pages.add(new PadPage(new CompoundNBT()));
			setCurrentPage(this.pageIndex + 1);
			loadPageText();
		}));
		this.addButton(prevPage = new Button(midX - 40, 195, 20, 20, new StringTextComponent("<"), (action) -> {
			setCurrentPage(this.pageIndex - 1);
			loadPageText();
		}));
		
		updateButtons();
		loadPageText();
	}
	
	private void initFields()
	{
		this.textFields.clear();
		this.children.clear();
		
		int yPos = 20;
		int midX = this.width / 2;
		header = new TextFieldWidget(font, midX - 50, yPos, 100, 15, new StringTextComponent("Header"));
		header.setMaxStringLength(20);
		this.currentField = header;
		this.children.add(header);
		addField(header);
		setFocusedDefault(header);
		
		yPos += 20;
		for(int i=0; i<lines.length; i++)
		{
			TextFieldWidget textField = new TextFieldWidget(font, midX - 40, yPos + 17 * i, 90, 15, new StringTextComponent("Line "+i));
			textField.setMaxStringLength(20);
			lines[i] = addField(textField);
			this.children.add(textField);
		}
	}
	
	private void loadPageText()
	{
		if(this.currentPage == null) return;
		
		header.setText(this.currentPage.title());
		for(int i=0; i<lines.length; i++)
			lines[i].setText(this.currentPage.getLine(i).text());
	}
	
	private TextFieldWidget addField(TextFieldWidget textField)
	{
//		textField.setEnableBackgroundDrawing(false);
		textField.setTextColor(TEXT_COLOR);
		textField.setEnabled(true);
		this.textFields.add(textField);
		return textField;
	}
	
	public void updateButtons()
	{
		prevPage.active = this.pageIndex > 0;
		nextPage.active = this.pageIndex < 49;
	}
	
	public void tick()
	{
		super.tick();
		for(TextFieldWidget line : textFields)
			line.tick();
	}
	
	/** Removes trailing blank pages to conserve space */
	public void trimBlankPages()
	{
		int lastPage = 0;
		for(int i=0; i<pages.size(); i++)
		{
			PadPage page = pages.get(i);
			if(!page.isBlank())
				lastPage = i;
		}
		if(lastPage == pages.size() - 1)
			return;
		
		List<PadPage> pagesTrimmed = Lists.newArrayList();
		pagesTrimmed.addAll(pages.subList(0, lastPage));
		pages.clear();
		pages.addAll(pagesTrimmed);
	}
	
	@SuppressWarnings("deprecation")
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		this.setListener((IGuiEventListener)null);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(ReadBookScreen.BOOK_TEXTURES);
		int i = (this.width - 192) / 2;
		this.blit(matrixStack, i, 2, 0, 0, 192, 192);
		
		for(TextFieldWidget field : this.textFields)
			field.render(matrixStack, mouseX, mouseY, partialTicks);
		
		ITextComponent pageNum = new StringTextComponent(String.valueOf(this.pageIndex + 1));
		int pageWidth = this.font.getStringPropertyWidth(pageNum);
		this.font.func_243248_b(matrixStack, pageNum, (float)(this.width - pageWidth) / 2F, 200F, 0);
		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	public void setCurrentPage(int index)
	{
		if(index == this.pageIndex) return;
		
		// Save current contents of page
		if(this.currentPage != null)
		{
			this.currentPage.setTitle(header.getText());
			for(int i=0; i<lines.length; i++)
				this.currentPage.getLine(i).setText(lines[i].getText());
		}
		
		this.pageIndex = MathHelper.clamp(index, 0, pages.size());
		this.currentPage = getCurrentPage();
	}
	
	private void generatePagesFromPad(CompoundNBT compound)
	{
		pages.clear();
		pages.addAll(ItemNotepad.getPagesFromNBT(compound));
		this.currentPage = getCurrentPage();
	}
	
	private CompoundNBT writePagesToNBT(CompoundNBT compound)
	{
		if(!pages.isEmpty())
		{
			compound.putInt("Page", pageIndex);
			ListNBT pageList = new ListNBT();
			for(int i=0; i<pages.size(); i++)
				pageList.add(pages.get(i).writeToNBT(new CompoundNBT()));
			compound.put("Pages", pageList);
		}
		return compound;
	}
	
	public PadPage getCurrentPage()
	{
		if(pages.isEmpty())
			pages.add(new PadPage(new CompoundNBT()));
		
		if(pageIndex >= pages.size())
			pageIndex = Math.max(0, pages.size() - 1);
		
		return this.pages.get(pageIndex);
	}
	
	public void onClose()
	{
		super.onClose();
		this.minecraft.keyboardListener.enableRepeatEvents(false);
		trimBlankPages();
		CompoundNBT notepadData = writePagesToNBT(this.notepad.getOrCreateTag());
		PacketHandler.sendToServer(new PacketNotepadUpdate(this.player, notepadData));
		this.notepad.setTag(notepadData);
	}
	
	public boolean keyPressed(int keyCode, int scanCode, int modifiers)
	{
		if(super.keyPressed(keyCode, scanCode, modifiers))
			return true;
		else if(keyCode == 256)
		{
			this.minecraft.player.closeScreen();
			return true;
		}
		
		if(this.currentField != null)
			this.currentField.keyPressed(keyCode, scanCode, modifiers);
		
		return this.currentField != null;
	}
	
	public boolean mouseClicked(double mouseX, double mouseY, int button)
	{
		this.currentField = null;
		for(TextFieldWidget field : this.textFields)
		{
			boolean isOver = field.isMouseOver(mouseX, mouseY);
			if(isOver)
				this.currentField = field;
			field.setFocused2(isOver);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
}
