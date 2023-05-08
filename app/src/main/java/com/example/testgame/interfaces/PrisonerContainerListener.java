package com.example.testgame.interfaces;

import com.example.testgame.models.Prisoner;

public interface PrisonerContainerListener {
    void onPrisonerContainerUpdated(Prisoner prisoner);
    void setButtonActivated(boolean active);

}