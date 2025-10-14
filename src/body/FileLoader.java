package body;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import body.aerodrom.Aerodrom;
import body.aerodrom.AerodromContainer;
import body.let.Let;
import body.let.LetContainer;

public class FileLoader {
    private AerodromContainer aerodroms;
    private LetContainer letContainer;
    private TextArea consoleArea;

    public FileLoader(AerodromContainer aerodroms, LetContainer letContainer, TextArea consoleArea) {
        this.aerodroms = aerodroms;
        this.letContainer = letContainer;
        this.consoleArea = consoleArea;
    }

    public void loadFile(String path){
        try (BufferedReader br = new BufferedReader(new FileReader(path))){
            String header = br.readLine();
            if(header == null) {
                consoleArea.append("ERROR: Fajl je prazan\n");
                return;
            }

            if(header.equalsIgnoreCase("Name,Code,X,Y")){
                String line;
                while((line = br.readLine()) != null){
                    try {
                        String[] parts = line.split(",");
                        if(parts.length != 4) continue;

                        String name = parts[0].trim();
                        String code = parts[1].trim();
                        int x = Integer.parseInt(parts[2].trim());
                        int y = Integer.parseInt(parts[3].trim());

                        aerodroms.add(new Aerodrom(name, code, x, y));
                        consoleArea.append("UPDATE: Added airport " + name + " (" + code + ") - (" + x + ", " + y + ")\n");
                    } catch (Exception e) {
                        consoleArea.append("ERROR: Line format invalid: " + line + "\n");
                    }
                }
            } else if(header.equalsIgnoreCase("Start,End,TakeOff,Duration")){
                String line;
                while((line = br.readLine()) != null){
                    try {
                        String[] parts = line.split(",");
                        if(parts.length != 4) continue;

                        String startCode = parts[0].trim();
                        String endCode = parts[1].trim();
                        String[] timeParts = parts[2].split(":");
                        int first = Integer.parseInt(timeParts[0].trim());
                        int second = Integer.parseInt(timeParts[1].trim());
                        int duration = Integer.parseInt(parts[3].trim());

                        Aerodrom start = null, end = null;
                        for (Aerodrom a : aerodroms.getAerodroms()) {
                            if (a.getCode().equals(startCode)) start = a;
                            if (a.getCode().equals(endCode)) end = a;
                        }

                        if(start != null && end != null){
                            letContainer.add(new Let(start, end, first, second, duration));
                            consoleArea.append("UPDATE: Added flight (" + startCode + " -> " + endCode + "), TakeOff: " + parts[2] + ", Duration: " +  duration + "\n");
                        } else {
                            consoleArea.append("ERROR: Flight has invalid airports: " + startCode + " -> " + endCode + "\n");
                        }
                    } catch (Exception e) {
                        consoleArea.append("ERROR: Line format invalid: " + line + "\n");
                    }
                }
            } else {
                consoleArea.append("ERROR: Unknown file format\n");
            }

        } catch (Exception e) {
            consoleArea.append("ERROR: " + e.getMessage() + "\n");
        }
    }
}