package body;

import Exceptions.InvalidTime;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import body.let.Let;
import body.let.LetContainer;
import gui.AppContext;
import gui.L2Panels.FlightsPanel;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AirportFlightTable {
    Map<Aerodrom, List<Let>> flightsByAirport = new HashMap<>();
    FlightsPanel flightsPanel;
    TextArea console;

    public AirportFlightTable(AerodromContainer aerodromContainer, LetContainer letContainer) {
        AppContext ctx = AppContext.getInstance();
        console = ctx.getConsole();
        flightsPanel = ctx.getFlightsPanel();
        for (Aerodrom a : aerodromContainer.getAerodroms()) {
            flightsByAirport.put(a, new ArrayList<>());
            for (Let l : letContainer.getFlights()) {
                if (l.getStart().getCode().equals(a.getCode())) {
                    flightsByAirport.get(a).add(l);
                }
            }
        }
    }

    public List<Let> getFlightsInterval (int hour, int min){
        List<Let> result = new ArrayList<>();
        int intervalStart = hour * 60 + min;
        int intervalEnd = intervalStart + 9;

        for(List<Let> letovi: flightsByAirport.values()){
            for(Let l : letovi){
                int letMinutes = l.getSat() * 60 + l.getMinut();
                if(letMinutes >= intervalStart && letMinutes <= intervalEnd){
                    result.add(l);
                }
            }
        }

        return result;
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
                            l.setChanged(true);
                            l.setSat(currTime / 60);
                            l.setMinut(currTime % 60);
                            console.append("UPDATE: Flight " + l.getStart().getCode() + " -> " + l.getEnd().getCode() + " has changed take-off to " + String.format("%02d:%02d", currTime / 60, currTime % 60) + "\n");

                        } catch (InvalidTime e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
                prev = l;
            }
        }

        flightsPanel.refreshTable();
    }

    public void reset() {
        for(Map.Entry<Aerodrom, List<Let>> entry : flightsByAirport.entrySet()){
            for(Let l : entry.getValue()){
                l.setChanged(false);
            }
        }
        flightsPanel.refreshTable();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<Aerodrom, List<Let>> entry : flightsByAirport.entrySet()) {
            sb.append(entry.getKey().getCode()).append(":\n");
            for (Let l : entry.getValue()) {
                sb.append("    ").append(l.toString()).append("\n");
            }
        }
        return sb.toString();
    }
}
