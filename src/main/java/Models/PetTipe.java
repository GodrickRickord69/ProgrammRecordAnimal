package Models;

public enum PetTipe {

    Cat,
    Dog,
    Hamster,
    Camel,
    Danke,
    Horse;

    public static PetTipe getTipe (int tipeId){
        switch (tipeId){
            case 1:
                return PetTipe.Cat;
            case 2:
                return PetTipe.Dog;
            case 3:
                return PetTipe.Hamster;
            case 4:
                return PetTipe.Camel;
            case 5:
                return PetTipe.Danke;
            case 6:
                return PetTipe.Horse;
            default:
                return null;
        }
    }
}
