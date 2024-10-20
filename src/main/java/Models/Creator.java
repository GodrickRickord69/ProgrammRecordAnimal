package Models;

import java.time.LocalDate;

public abstract class Creator {

    protected abstract Pet createNewPet(PetTipe tipe);

    public Pet createPet(PetTipe tipe, String name, LocalDate date){

        Pet pet = createNewPet(tipe);
        pet.setName(name);
        pet.setBirthday(date);
        return pet;
    }
}
