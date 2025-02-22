package Controller;

import Models.*;
import Services.Repository;
import Services.PetRepository;
import UserInterface.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import Exception.UncorrectData;

public class ControllerPet {
    private Repository<Pet> petRepository;
    private Creator petCreator;
    private final View<Pet> view;
    private Validator validator;

    public ControllerPet(Repository<Pet> petRepository){
        this.petRepository = petRepository;
        this.petCreator = new PetCreator();
        this.view = new ConsolView();
        this.validator = new Validator();
    }

    public void createPet(PetTipe tipe){
        String[] data = new String[] {view.getName(), view.getBirthday()};
        validator.validate(data);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        try {
            int res = petRepository.create(petCreator.createPet(tipe, data[0], birthday));
            view.showMessage(String.format("%d Запись добавлена", res));
        } catch (RuntimeException e){
            view.showMessage(e. getMessage());
        }
    }

    public void updatePet(int id){
        Pet pet = getById(id);
        String[] data = new String[] {view.getName(), view.getBirthday()};

        validator.validate(data);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate birthday = LocalDate.parse(data[1], formatter);
        pet.setName(data[0]);
        pet.setBirthday(birthday);
        try{
            int res = petRepository.update(pet);
            view.showMessage(String.format("%d Запись изменена \n", res));
        }catch (RuntimeException e){
            view.showMessage(e. getMessage());
        }
    }

    public void getAllPet(){
        try{
            view.printAll(petRepository.getAll(), Pet.class);
        } catch (RuntimeException e){
            view.showMessage(e.getMessage());
        }
    }

    public boolean trainPet(int id, String comanda) {
        try{
            if(((PetRepository)petRepository).getComandsById(id, 1).contains(comanda))
                view.showMessage("это мы умеем");
            else {
                if (!((PetRepository)petRepository).getComandsById(id,2).contains(comanda))
                    view.showMessage("эту команду нельзя выполнить");
                else {
                    ((PetRepository)petRepository).train(id,comanda);
                    view.showMessage("команда разучена\n");
                    return true;
                }
            }
        }catch (RuntimeException e){
            view.showMessage(e.getMessage());
        }
        return false;
    }

    public Pet getById(int id){
        try{
            return petRepository.getById(id);
        }catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
        return null;
    }

    public void delete(int id){
        try{
            petRepository.delete(id);
            view.showMessage("запись удалена");
        }catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }

    public void getComandy(int id){
        try{
            view.printAll(((PetRepository)petRepository).getComandsById(id, 1), String.class);
        }catch (RuntimeException e) {
            view.showMessage(e.getMessage());
        }
    }
}
