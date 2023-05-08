package com.example.testgame.data;

public enum Ships {
    BLOB("Blob",3);





    private final String name;
    private final int weaponSlots;
    Ships(String name,int weaponSlots) {
        this.name=name;
        this.weaponSlots=weaponSlots;
    }
    public String getName(){
        return name;
    }

    public int getWeaponSlots() {
        return weaponSlots;
    }
}
