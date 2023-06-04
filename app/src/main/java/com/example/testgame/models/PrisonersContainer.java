package com.example.testgame.models;

import android.os.Handler;

import com.example.testgame.interfaces.PrisonerContainerListener;
import com.example.testgame.interfaces.PrisonerMenuListener;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class PrisonersContainer implements PrisonerMenuListener {
    Date lastUpdate;
    Integer displayedIndex;
    private transient PrisonerContainerListener prisonerContainerListener;
    private transient InventoryContainer inventoryContainer;

    List<Prisoner> list = Arrays.asList(new Prisoner(10,10,0,100,"John"),
            new Prisoner(10,10,0,100,"Kevin"));

    private Handler handler = new Handler();
    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            updateAll();
            handler.postDelayed(this, 1000);
        }
    };

    public PrisonersContainer() {
        lastUpdate = Calendar.getInstance().getTime();
        //handler.postDelayed(runnable, 1000);
    }

    public void updateAll(){
        int delta = (int) (Calendar.getInstance().getTime().getTime()-lastUpdate.getTime())/1000;
        lastUpdate = Calendar.getInstance().getTime();
        for (Prisoner p :list){
            if (!p.isDead()) {
                p.update(delta);
                if (p.isDisplayed()){
                    updateData(p);
                }
            }

        }
    }

    public Prisoner getPrisoner(int index) {
        return list.get(index);
    }

    public Prisoner setDisplayedPrisoner(int index){
        if (displayedIndex != null) {
            getPrisoner(displayedIndex).setDisplayed(false);
        }
        displayedIndex = index;
        getPrisoner(index).setDisplayed(true);
        return getPrisoner(index);
    }

    public Prisoner getDisplayedPrisoner() {
        return getPrisoner(displayedIndex);
    }

    public void updateData(Prisoner prisoner) {
        if (prisonerContainerListener != null) {
            prisonerContainerListener.onPrisonerContainerUpdated(prisoner);
        }
    }
    public void setPrisonerContainerListener(PrisonerContainerListener listener) {
        this.prisonerContainerListener = listener;
    }

    @Override
    public void changeDisplayedPrisoner(boolean isNext) {
        if (isNext) {
            setDisplayedPrisoner((displayedIndex + 1) % list.size());
        } else{
            if (displayedIndex==0){
                setDisplayedPrisoner(list.size()-1);
            } else{
                setDisplayedPrisoner(displayedIndex - 1);
            }
        }
        updateData(getDisplayedPrisoner());
    }

    @Override
    public void feedPrisoner(int amount) {
        Prisoner prisoner=getDisplayedPrisoner();
        prisoner.changeHunger(amount);
        if (!prisoner.isDead()) {
            updateData(prisoner);
            inventoryContainer.changeFood(-1);
            if (inventoryContainer.getFood() == 0) {
                prisonerContainerListener.setButtonActivated(false);
            }
        }
    }


    public void setInventoryContainer(InventoryContainer container) {
        this.inventoryContainer = container;
    }


}


































