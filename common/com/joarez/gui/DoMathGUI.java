package com.joarez.gui;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import com.joarez.mathmod.MathMod;
import com.sun.jna.platform.win32.WinUser.INPUT;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import scala.swing.event.Key;

public class DoMathGUI extends GuiScreen {

	ResourceLocation bg_texture = new ResourceLocation(MathMod.MOD_ID, "textures/gui/math_bg.png");
	
	GuiTextField expression_textfield;
	GuiButton bt_check;
	int bgW,bgH,bgX,bgY;
	float titleX, titleY;
	int expX, expY;
	int textW = 170, textH = 20, textX, textY;
	int btX, btY;
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		
		//DRAW BACKGROUND
		this.drawDefaultBackground();
		bgW = 192;
		bgH = 192;
		bgX = (width - bgW)/2;
		bgY = (height - bgH)/2;		
		Minecraft.getMinecraft().renderEngine.bindTexture(bg_texture);		
		this.drawTexturedModalRect(bgX,bgY,0,0,bgW,bgH);
		//super.drawScreen(mouseX, mouseY, partialTicks);
		
		//DRAW TITLE
		String title = "Realize a Conta!";
		titleX =(width)/1.5F - this.fontRendererObj.getStringWidth(title);
		titleY = bgY+10;
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(titleX, titleY, 0);
			GlStateManager.scale(1.5, 1.5, 1);
			this.fontRendererObj.drawString(title, 0 ,0,  0x000000);
		}
		GlStateManager.popMatrix();
		
		
		//DRAW EXPRESSION		
		String expression =  MathMethods.A + " " + MathMethods.SINAL + " " + MathMethods.B;		
		expX = (width - 2*this.fontRendererObj.getStringWidth(expression))/2;
		expY = (int)titleY + 40;		
		GlStateManager.pushMatrix();
		{
			GlStateManager.translate(expX, expY, 0);
			GlStateManager.scale(2, 2, 1);			
			this.fontRendererObj.drawString(expression, 0 ,0,  0xff0000);
		}
		GlStateManager.popMatrix();
	
			
		//DRAW TEXTFIELD														
		expression_textfield.drawTextBox();		
				
		//DRAW BUTTON
		String bt_text = "Conferir";
		btX = (width-100)/2;
		btY = textY + 50;
		bt_check = new GuiButton(1,btX,btY,100,20,bt_text);
		this.buttonList.clear();
		this.buttonList.add(bt_check);
		
		super.drawScreen(mouseX, mouseY, partialTicks);
	}
	
	
	
	@Override
	public void initGui() {
		MathMethods.Randomize();
		textX = (width-textW)/2;
		textY = (height - textH)/2;
		expression_textfield = new GuiTextField(0,this.fontRendererObj,textX,textY,textW,textH);		
		expression_textfield.setTextColor(0xffffff);
		//super.initGui();
	}



	@Override
	protected void actionPerformed(GuiButton button) throws IOException {
		
		switch(button.id) {
			case 1:
				bt_check.enabled = false;
				int r = 0;
				try {
					r =  Integer.parseInt(expression_textfield.getText());
					if(r == MathMethods.GetResult()) {
						//Add points & randomize numbers
						expression_textfield.setText("");
						expression_textfield.setFocused(true);
						MathMethods.Randomize();
					}
				}catch(Exception e) {
					r = 0;
				}
				
				
				break;
		}
		super.actionPerformed(button);
	}

	@Override
	public boolean doesGuiPauseGame() {
		// TODO Auto-generated method stub
		return false;
	}


	@Override
	protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
		expression_textfield.mouseClicked(mouseX, mouseY, mouseButton);
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}


	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		expression_textfield.textboxKeyTyped(typedChar, keyCode);		
		super.keyTyped(typedChar, keyCode);
	}



	@Override
	public void updateScreen() {
		expression_textfield.updateCursorCounter();
		super.updateScreen();
	}

	

	
	
}
