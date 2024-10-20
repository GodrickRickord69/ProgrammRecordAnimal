package org.example;

import Controller.ControllerPet;
import Models.Pet;
import Services.PetRepository;
import Services.Repository;
import UserInterface.ConsolMenu;

public class Programm {
    public static void main(String[] args) throws Exception{
        Repository <Pet> Petomnik = new  PetRepository();
        ControllerPet controll = new ControllerPet(Petomnik);
        new ConsolMenu (controll).start();
    }
}