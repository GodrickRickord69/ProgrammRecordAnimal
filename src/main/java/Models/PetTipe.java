package Models;

public enum PetTipe {

    Cat,
    Dog,
    Hamster;

    public static PetTipe getTipe (int tipeId){
        switch (tipeId){
            case 1:
                return PetTipe.Cat;
            case 2:
                return PetTipe.Dog;
            case 3:
                return PetTipe.Hamster;
            default:
                return null;
        }
    }
}
