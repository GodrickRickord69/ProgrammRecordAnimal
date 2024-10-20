package UserInterface;

import java.util.Scanner;
import Controller.*;
import Exception.UncorrectData;
import Modeli.PetomecTipe;

public class ConsolMenu {
    ControllerPet controllerPet;

    public ConsolMenu( ControllerPet controll){
        this.controllerPet = controll;
    }

    public void start() {
        System.out.println();
        try (Scanner in = new Scanner(System.in); Count count = new Count()){

            boolean flag = true;
            int id;
            while (flag) {

                System.out.println(
                        "\n1 - Список всех животных\n2 - Завести новое животное\n3 - Изменить данные о животном\n4 - Что умеет животное\n5 - Дрессировка\n6 - Удалить запись\n7 - Выход");
                String key = in.next();
                switch (key) {
                    case "1":
                        controllerPet.getAllPet();
                        break;
                    case "2":
                        PetTipe tipe = menuChoice(in);
                        if (tipe != null) {
                            try {
                                controllerPet.createPet(tipe);
                                count.add();
                                System.out.println("ОК");
                            } catch (UncorrectData e) {
                                System.out.println(e.getMessage());
                            }
                        }
                        break;
                    case "3":
                        while (true) {
                            id = menuChoicePet(in);
                            if (id != 0)
                                try {
                                    controllerPet.updatePet(id);
                                } catch (UncorrectData e) {
                                    System.out.println(e.getMessage());
                                }
                            else
                                break;
                        }
                        break;
                    case "4":
                        while (true) {
                            id = menuChoicePet(in);
                            if (id != 0)
                                controllerPet.getComandy(id);
                            else
                                break;
                        }
                        break;
                    case "5":
                        id = menuChoicePet(in);
                        if (id != 0)
                            menuTrainPet(id, in);
                        break;
                    case "6":
                        id = menuChoicePet(in);
                        if (id != 0)
                            controllerPet.delete(id);
                        break;
                    case "7":
                        flag = false;
                        break;
                    default:
                        System.out.println("такого варианта нет");
                        break;
                }
            }
        }
    }

    private PetTipe menuChoice(Scanner in){
        System.out.println("Какое животное добавить:\n1 - Кошка\n2 - Собака\n3 - Хомяк\n4 - Возврат в меню");

        while (true) {
            String key = in.next();
            switch (key) {
                case "1":
                    return PetTipe.Cat;
                case "2":
                    return PetTipe.Dog;
                case "3":
                    return PetTipe.Hamster;
                case "7":
                    return null;
                default:
                    System.out.println("Такого варианта нет, введите число от 0 до 3");
                    break;
            }
        }
    }

    private int menuChoicePet(Scanner in) {
        System.out.println("\nВведите номер животного, 7 для возврата в основное меню: ");
        while (true) {
            int id = in.nextInt();
            in.nextLine();
            if (id == 0)
                return id;
            if (controllerPet.getById(id) == null) {
                System.out.println("Животного с таким номером нет, попробуйте еще раз, 7 для возврата в основное меню:");
            } else
                return id;

        }
    }

    private void menuTrainPet(int petomecId, Scanner in) {
        Scanner sc = in;
        while (true) {
            System.out.println("Введите новую команду, 7 для возврата в основное меню: ");
            String command = sc.nextLine();
            if (command.length() == 1 && command.equals("0"))
                return;
            if (controllerPet.trainPet(petomecId, command))
                System.out.println("получилось!");
        }
    }
}

