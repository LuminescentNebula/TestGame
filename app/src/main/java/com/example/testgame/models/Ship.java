package com.example.testgame.models;

import com.example.testgame.data.Ships;

public class Ship {
    private PrisonersContainer prisonersContainer;
    private WeaponContainer weaponContainer;

    private Sector sector;
    private InventoryContainer inventoryContainer;
    private Ships type=Ships.BLOB;

    public Ship() {
        prisonersContainer=new PrisonersContainer();
        inventoryContainer=new InventoryContainer();
        weaponContainer=new WeaponContainer();
        prisonersContainer.setInventoryContainer(inventoryContainer);
        //TODO:Удалить позже
        inventoryContainer.setFood(10);
    }

    public Prisoner getPrisoner(int index) {
        return prisonersContainer.getPrisoner(index);
    }

    public PrisonersContainer getPrisonersContainer() {
        return prisonersContainer;
    }

    public WeaponContainer getWeaponContainer() {
        return weaponContainer;
    }

    public Ships getType() {
        return type;
    }

    public InventoryContainer getInventoryContainer() {
        return inventoryContainer;
    }
}
