package me.ma1de.practice.gui;

import java.util.List;

public interface Gui {
    String getId();
    String getDisplayName();

    List<GuiButton> getButtons();
    
    boolean isEnabled();
}

