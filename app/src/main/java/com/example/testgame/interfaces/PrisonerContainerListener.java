package com.example.testgame.interfaces;

import com.example.testgame.Prisoner;

public interface PrisonerContainerListener {
    void onPrisonerContainerUpdated(Prisoner prisoner);
    void setButtonActivated(boolean active);

}