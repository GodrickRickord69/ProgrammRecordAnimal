package org.example;

import Controller.ControllerPetomcev;
import Modeli.Petomec;
import Servis.PetomecRepositorij;
import Servis.Repositorij;
import UserInterface.ConsolMenu;

public class Programm {
    public static void main(String[] args) throws Exception{
        Repositorij <Pet> Petomnik = new  PetRepository();
        ControllerPet controll = new ControllerPet(Petomnik);
        new ConsolMenu (controll).start();
    }
}