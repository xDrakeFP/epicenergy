package gruppo1.epicenergy.runners;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import gruppo1.epicenergy.entities.Comune;
import gruppo1.epicenergy.entities.Provincia;
import gruppo1.epicenergy.repositories.ComuneRepository;
import gruppo1.epicenergy.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class CSVImport implements CommandLineRunner {
    @Autowired
    private ProvinciaRepository provinciaRepository;
    @Autowired
    private ComuneRepository comuneRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        if(provinciaRepository.count() == 0){
            List<Provincia> province = loadProvince("province-italiane.csv");
            provinciaRepository.saveAll(province);
            System.out.println("Importate " + province.size() + " province.");
        }

        if(comuneRepository.count() == 0){
            Map<String, Provincia> provinciaMap = provinciaRepository.findAll()
                    .stream().collect(Collectors.toMap(Provincia::getNomeProvincia, p -> p));
            List<Comune> comuni = loadComuni("comuni-italiani.csv", provinciaMap);
            comuneRepository.saveAll(comuni);
            System.out.println("Importati " + comuni.size() + " comuni.");
        }
    }

    private List<Provincia> loadProvince (String nomeFile){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(nomeFile), StandardCharsets.UTF_8
        ))) {
            return reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] parts = line.split(";");
                        return new Provincia(
                                parts[0].trim(),
                                parts[1].trim(),
                                parts[2].trim()
                        );
                    }).collect(Collectors.toList());
        }catch (Exception e){
            throw new RuntimeException("Errore nel caricare il CSV di province" + e + e.getMessage());
        }
    }

//    private List<Comune> loadComuni (String nomeFile, Map<String, Provincia> provinciaMap){
//        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
//                getClass().getClassLoader().getResourceAsStream(nomeFile), StandardCharsets.UTF_8
//        ))) {
//            List<Comune> listaComuni= reader.lines()
//                    .skip(1)
//                    .map(line -> {
//                        System.out.println(line);
//                        String[] parts = line.split(",");
//                        String verify = parts[3];
//                        if(verify.contains("ì") || verify.contains(" nell'") || verify.contains(" e ") || verify.contains(" e della ")){
//                            verify.replace("ì", "i");
//                            verify.replace(" nell'", "-");
//                            verify.replace(" e ", "-");
//                            verify.replace(" e della ", "-");
//                        }
//                        if(verify.contains("/Bozen") || verify.contains("/Vallée d'Aoste")){
//                            verify.replace("/Bozen", "");
//                            verify.replace("/Vallée d'Aoste", "");
//                        }
//                        if(verify.contains("Verbano-Cusio-Ossola") || verify.contains("Sud Sardegna")){
//                            verify.replaceAll("Verbano-Cusio-Ossola", "Verbania");
//                            verify.replaceAll("Sud Sardegna", "Cagliari");
//                        }
//                        System.out.println(newProvincia);
//                        Provincia provincia = provinciaMap.get(parts[3]);
//                        if(provincia == null){
//                            throw new RuntimeException("Provincia non trovata");
//                        }
//                        return new Comune(
//                                Integer.parseInt(parts[0].trim()),
//                                Integer.parseInt(parts[1].trim()),
//                                parts[2],
//                                provincia
//                        );
//                    }).collect(Collectors.toList());
//            System.out.println(listaComuni.size() + " " + listaComuni.get(1));
//            return listaComuni;
//        } catch (Exception e){
//            throw new RuntimeException("Errore nel caricare il CSV di comuni" + e + e.getMessage());
//        }
//    }
private List<Comune> loadComuni(String nomeFile, Map<String, Provincia> provinciaMap) {
    try (CSVReader csvReader = new CSVReader(new InputStreamReader(
            getClass().getClassLoader().getResourceAsStream(nomeFile), StandardCharsets.UTF_8
    ))) {

        List<String[]> rows = csvReader.readAll();

        List<Comune> comuni = new ArrayList<>();
        for (int i = 1; i < rows.size(); i++) {
            String[] parts = rows.get(i);
            try {
                if (parts.length < 4) {
                    System.err.println("⚠️ Riga " + (i + 1) + " malformata (campi insufficienti): " + String.join(",", parts));
                    continue;
                }
                int codiceComune = Integer.parseInt(parts[0].trim());
                int codiceProvincia = Integer.parseInt(parts[1].trim());
                String nomeComune = parts[2].trim();
                String provinciaNome = parts[3].trim();
                Provincia provincia = provinciaMap.get(provinciaNome);
                if (provincia == null) {
                    System.err.println("⚠️ Riga " + (i + 1) + " - Provincia non trovata: " + provinciaNome + " (comune: " + nomeComune + ")");
                    continue;
                }
                comuni.add(new Comune(codiceComune, codiceProvincia, nomeComune, provincia));

            } catch (NumberFormatException e) {
                System.err.println("⚠️ Riga " + (i + 1) + " - Errore parsing numeri: " + e.getMessage());
                System.err.println("   Contenuto: " + String.join(",", parts));
            } catch (Exception e) {
                System.err.println("⚠️ Riga " + (i + 1) + " - Errore generico: " + e.getMessage());
                System.err.println("   Contenuto: " + String.join(",", parts));
            }
        }

        System.out.println("✓ Caricati " + comuni.size() + " comuni su " + (rows.size() - 1) + " righe totali");
        if (!comuni.isEmpty()) {
            System.out.println("   Primo comune: " + comuni.get(0));
        }

        return comuni;

    } catch (IOException | CsvException e) {
        throw new RuntimeException("Errore nel caricare il CSV di comuni: " + e.getMessage(), e);
    }
}
}
