package UserInterface;

import java.util.List;

public interface View {
    String getName();
    String getBirthday();
    <U> void printAll (List <U> list, Class <U> clas);
    void showMessage(String s);
}
