package body.let;

import java.util.ArrayList;
import java.util.List;

public class LetContainer {
    private final List<Let> flights = new ArrayList<>();

    public void add(Let let) {
        flights.add(let);
    }

    public List<Let> getFlights() {
        return flights;
    }

    @Override
    public String toString() {
        return "LetContainer{" +
                "flights=" + flights +
                '}';
    }
}
