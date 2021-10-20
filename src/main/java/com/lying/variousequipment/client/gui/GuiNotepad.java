package com.lying.variousequipment.client.gui;

import java.util.List;

import com.google.common.collect.Lists;
import com.lying.variousequipment.item.ItemNotepad;
import com.lying.variousequipment.item.ItemNotepad.Icon;
import com.lying.variousequipment.item.ItemNotepad.PadPage;
import com.lying.variousequipment.network.PacketHandler;
import com.lying.variousequipment.network.PacketNotepadUpdate;
import com.lying.variousequipment.reference.Reference;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.AbstractButton;
import net.minecraft.client.gui.widget.button.ChangePageButton;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class GuiNotepad extends Screen
{
	private static final ResourceLocation NOTEPAD_TEXTURES = new ResourceLocation(Reference.ModInfo.MOD_ID, "textures/gui/notepad.png");
	private static final int TEXT_COLOR = -16777216;
	
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
	private final List<IconButton> iconButtons = Lists.newArrayList();
	
	private ChangePageButton nextPage;
	private ChangePageButton prevPage;
	
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
	
	public boolean isPauseScreen(){ return false; }
	
	protected void init()
	{
		this.minecraft.keyboardListener.enableRepeatEvents(true);
		this.currentPage = getCurrentPage();
		
		this.buttons.clear();
		this.children.clear();
		initFields();
		
		int midX = this.width / 2;
		this.addButton(prevPage = new ChangePageButton(midX - 40, 195, false, (action) -> {
			savePageContents();
			setCurrentPage(this.pageIndex - 1);
			loadPageContents();
			updateButtons();
		}, true));
		this.addButton(nextPage = new ChangePageButton(midX + 40 - 23, 195, true, (action) -> {
			savePageContents();
			if(this.pageIndex == (this.pages.size() - 1))
				this.pages.add(new PadPage(new CompoundNBT()));
			setCurrentPage(this.pageIndex + 1);
			loadPageContents();
			updateButtons();
		}, true));
		
		updateButtons();
	}
	
	private void initFields()
	{
		this.iconButtons.clear();
		this.textFields.clear();
		
		int yPos = 22;
		int midX = this.width / 2;
		header = new TextFieldWidget(font, midX - 67, yPos, 134, 15, new StringTextComponent("Header"));
		header.setMaxStringLength(20);
		addField(header);
		setCurrentField(header);
		setFocusedDefault(header);
		
		yPos += 18;
		midX -= 19;
		for(int i=0; i<lines.length; i++)
		{
			TextFieldWidget textField = new TextFieldWidget(font, midX - 40, yPos + 18 * i, 134, 15, new StringTextComponent("Line "+i));
			textField.setMaxStringLength(20);
			lines[i] = addField(textField);
		}
		
		for(int i=0; i<lines.length; i++)
			this.iconButtons.add(addButton(new IconButton(midX - 58, yPos - 6 + 18 * i, i, this)));
		
		loadPageContents();
	}
	
	private void loadPageContents()
	{
		this.currentPage = getCurrentPage();
		if(this.currentPage == null) return;
		
		header.setText(this.currentPage.title());
		for(int i=0; i<lines.length; i++)
			lines[i].setText(this.currentPage.getLine(i));
	}
	
	private TextFieldWidget addField(TextFieldWidget textField)
	{
		textField.setTextColor(TEXT_COLOR);
		textField.setDisabledTextColour(TEXT_COLOR);
		textField.setEnableBackgroundDrawing(false);
		textField.setEnabled(true);
		this.textFields.add(textField);
		return addListener(textField);
	}
	
	public void updateButtons()
	{
		prevPage.active = prevPage.visible = this.pageIndex > 0;
		nextPage.active = nextPage.visible = this.pageIndex < 49;
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
		while(!pages.isEmpty() && pages.get(pages.size() - 1).isBlank())
			pages.remove(pages.size() - 1);
		
		this.pageIndex = MathHelper.clamp(pageIndex, 0, pages.size() - 1);
	}
	
	@SuppressWarnings("deprecation")
	public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
	{
		this.renderBackground(matrixStack);
		RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.minecraft.getTextureManager().bindTexture(NOTEPAD_TEXTURES);
		int texWidth = 172;
		int i = (this.width - texWidth) / 2;
		this.blit(matrixStack, i, 2, 0, 0, texWidth, 215);
		
		for(TextFieldWidget field : this.textFields)
			this.font.func_243248_b(matrixStack, new StringTextComponent(field.getText()), (float)field.x, (float)field.y, TEXT_COLOR);
		
		for(IconButton button : iconButtons)
			button.render(matrixStack, mouseX, mouseY, partialTicks);
		
		ITextComponent pageNum = new StringTextComponent(String.valueOf(this.pageIndex + 1));
		int pageWidth = this.font.getStringPropertyWidth(pageNum);
		this.font.func_243248_b(matrixStack, pageNum, (float)(this.width - pageWidth) / 2F, 200F, TEXT_COLOR);
		
		super.render(matrixStack, mouseX, mouseY, partialTicks);
	}
	
	public void setCurrentPage(int index)
	{
		if(index == this.pageIndex) return;
		
		this.pageIndex = MathHelper.clamp(index, 0, pages.size());
		this.currentPage = getCurrentPage();
	}
	
	private void savePageContents()
	{
		if(this.currentPage == null)
			return;
		
		this.currentPage.setTitle(header.getText());
		for(int i=0; i<lines.length; i++)
			this.currentPage.setLine(i, lines[i].getText());
		
		this.pages.set(pageIndex, currentPage);
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
			ItemNotepad.writePagesToNBT(this.pages, compound);
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
		savePageContents();
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
				setCurrentField(field);
			else
				field.setFocused2(false);
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}
	
	private void setCurrentField(TextFieldWidget field)
	{
		this.currentField = field;
		setListener(field);
		field.setFocused2(true);
	}
	
	private static class IconButton extends AbstractButton
	{
		private final int index;
		private final GuiNotepad parentScreen;
		
		public IconButton(int x, int y, int indexIn, GuiNotepad guiIn)
		{
			super(x, y, 16, 16, new StringTextComponent("Icon "+indexIn));
			index = indexIn;
			parentScreen = guiIn;
		}
		
		public void onPress()
		{
			parentScreen.currentPage.nextIcon(index);
		}
		
		@SuppressWarnings("deprecation")
		public void renderButton(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks)
		{
			Icon icon = parentScreen.currentPage.getIcon(index);
			if(icon == Icon.BLANK) return;
			
			Minecraft minecraft = Minecraft.getInstance();
			minecraft.getTextureManager().bindTexture(NOTEPAD_TEXTURES);
			RenderSystem.color4f(1F, 1F, 1F, this.alpha);
			RenderSystem.enableBlend();
			RenderSystem.defaultBlendFunc();
			RenderSystem.enableDepthTest();
			this.blit(matrixStack, this.x, this.y, icon.textureIndex() * 16, 215, 16, 16);
		}
	}
}
