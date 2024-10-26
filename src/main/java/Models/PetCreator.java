package Models;

public class PetCreator extends Creator{

    @Override
    protected Pet createNewPet (PetTipe tipe){

        switch (tipe) {
            case Cat:
                return new Cat();
            case Dog:
                return new Dog();
            case Hamster:
                return new Hamster();
            case Horse:
                return new Horse();
            case Danke:
                return new Danke();
            case Camel:
                return new Camel();
        }
        return null;
    }
}
