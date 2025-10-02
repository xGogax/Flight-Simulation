package body.aerodrom;

import Exceptions.CodeMustBeUnique;

import java.util.ArrayList;
import java.util.List;

public class AerodromContainer {
    private final List<Aerodrom> aerodroms = new ArrayList<>();

    public void add(Aerodrom a) throws  CodeMustBeUnique {
        for(Aerodrom ex : aerodroms) {
            if(ex.getCode().equals(a.getCode())) {
                throw new CodeMustBeUnique(a.getCode());
            }
        }
        aerodroms.add(a);
    }

    public List<Aerodrom> getAerodroms() {
        return aerodroms;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        System.out.println("--- AERODROMI ---");
        for (Aerodrom ex : aerodroms) {
            sb.append(ex.toString());
        }
        return sb.toString();
    }
}
