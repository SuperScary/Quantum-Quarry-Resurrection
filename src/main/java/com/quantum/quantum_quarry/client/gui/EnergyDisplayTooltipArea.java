package com.quantum.quantum_quarry.client.gui;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.neoforge.energy.EnergyStorage;

import java.util.List;

public class EnergyDisplayTooltipArea {

    private final int xPos;
    private final int yPos;
    private final int width;
    private final int height;
    private final EnergyStorage energyStorage;

    public EnergyDisplayTooltipArea(int xPos, int yPos, EnergyStorage energyStorage, int width, int height) {
        this.xPos = xPos;
        this.yPos = yPos;
        this.energyStorage = energyStorage;
        this.width = width;
        this.height = height;
    }

    public EnergyDisplayTooltipArea (int xMin, int yMin, EnergyStorage energyStorage) {
        this(xMin, yMin, energyStorage, 8, 64);
    }

    public List<Component> getTooltips() {
        return List.of(Component.literal(energyStorage.getEnergyStored() + "/" + energyStorage.getMaxEnergyStored() + " FE"));
    }

    public void render (GuiGraphics guiGraphics) {
        render(guiGraphics, 0, 0);
    }

    public void render (GuiGraphics guiGraphics, int x, int y) {
        int stored = (int) (height * (energyStorage.getEnergyStored() / (float) energyStorage.getMaxEnergyStored()));
        guiGraphics.fillGradient(x, y + (height - stored), x + width, y + height, 0xffb51500, 0xff600b00);
    }

}
