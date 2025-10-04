package body;

import Exceptions.CodeMustBeUnique;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import body.let.Let;
import body.let.LetContainer;

public class Test {
    public static void main(String[] args) {
        AerodromContainer container = new AerodromContainer();
        try {
            container.add(new Aerodrom("Nikola Tesla", "BGT" , 3 , -34));
            container.add(new Aerodrom("Nis Aerodrom jebo mater", "ATY", 84, 29));
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
        System.out.println(container);
    }
}
