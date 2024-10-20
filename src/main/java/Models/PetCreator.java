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
        }
        return null;
    }
}
