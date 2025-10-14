package body;

import Exceptions.InvalidTime;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import body.let.Let;
import body.let.LetContainer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportFlightTable {
    Map<Aerodrom, List<Let>> flightsByAirport = new HashMap<>();

    public AirportFlightTable(AerodromContainer aerodromContainer, LetContainer letContainer) {
        for(Aerodrom a : aerodromContainer.getAerodroms()){
            flightsByAirport.put(a, new ArrayList<>());
            for(Let l : letContainer.getFlights()){
                if(l.getStart().getCode().equals(a.getCode())){
                    flightsByAirport.get(a).add(l);
                }
            }
        }
    }

    public void sort() {
        for (Map.Entry<Aerodrom, List<Let>> entry : flightsByAirport.entrySet()) {
            List<Let> letovi = entry.getValue();

            //sort
            letovi.sort((l1, l2) -> {
                if (l1.getSat() != l2.getSat()) return l1.getSat() - l2.getSat();
                return l1.getMinut() - l2.getMinut();
            });

            //validate
            Let prev = null;
            for (Let l : letovi) {
                if (prev != null) {
                    int prevTime = prev.getSat() * 60 + prev.getMinut();
                    int currTime = l.getSat() * 60 + l.getMinut();
                    if (currTime - prevTime < 10) {
                        currTime = prevTime + 10;
                        try {
                            l.setSat(currTime / 60);
                            l.setMinut(currTime % 60);
                        } catch (InvalidTime e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                prev = l;
            }
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Aerodrom, List<Let>> entry : flightsByAirport.entrySet()) {
            sb.append(entry.getKey().getCode()).append(":\n");
            for (Let l : entry.getValue()) {
                sb.append("  ")
                        .append(l.getStart().getCode()).append(" -> ")
                        .append(l.getEnd().getCode())
                        .append(", Takeoff: ").append(l.getSat()).append(":").append(l.getMinut())
                        .append(", Duration: ").append(l.getTrajanjeMin())
                        .append("\n");
            }
        }
        return sb.toString();
    }
}
