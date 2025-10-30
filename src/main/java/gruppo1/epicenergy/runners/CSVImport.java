package gruppo1.epicenergy.runners;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import gruppo1.epicenergy.entities.Comune;
import gruppo1.epicenergy.entities.Provincia;
import gruppo1.epicenergy.repositories.ComuneRepository;
import gruppo1.epicenergy.repositories.ProvinciaRepository;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
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
        Map<String, String> gestioneIncongruenze = Map.ofEntries(
                Map.entry("Bolzano", "Bolzano/Bozen"),
                Map.entry("Monza-Brianza", "Monza e della Brianza"),
                Map.entry("Forli-Cesena", "Forlì-Cesena"),
                Map.entry("Reggio-Emilia", "Reggio nell'Emilia"),
                Map.entry("Aosta", "Valle d'Aosta/Vallée d'Aoste"),
                Map.entry("Verbania", "Verbano-Cusio-Ossola"),
                Map.entry("La-Spezia","La Spezia"),
                Map.entry("Pesaro-Urbino", "Pesaro e Urbino"),
                Map.entry("Ascoli-Piceno", "Ascoli Piceno"),
                Map.entry("Reggio-Calabria", "Reggio Calabria"),
                Map.entry("Vibo-Valentia", "Vibo Valentia")
        );
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(nomeFile), StandardCharsets.UTF_8
        ))) {
            List<Provincia> listProvince = reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] parts = line.split(";");
                        String nomeProvincia = parts[1];
                        if (gestioneIncongruenze.containsKey(nomeProvincia)){
                            nomeProvincia = gestioneIncongruenze.get(nomeProvincia);
                        }
                        return new Provincia(
                                parts[0].trim(),
                                nomeProvincia,
                                parts[2].trim()
                        );
                    }).collect(Collectors.toList());
            log.info("Sono stati inserite {} province nel database, ora è aggiornato!", listProvince.size());
            return listProvince;
        }catch (Exception e){
            throw new RuntimeException("Errore nel caricare il CSV di province" + e + e.getMessage());
        }
    }

    private List<Comune> loadComuni (String nomeFile, Map<String, Provincia> provinciaMap){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(nomeFile), StandardCharsets.UTF_8
        ))) {
            List<Comune> listaComuni= reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] parts = line.split(",");
                        Provincia provincia = provinciaMap.get(parts[3]);
                        if(provincia == null){
                            throw new RuntimeException("Provincia non trovata");
                        }
                        return new Comune(
                                Integer.parseInt(parts[0].trim()),
                                Integer.parseInt(parts[1].trim()),
                                parts[2],
                                provincia
                        );
                    }).collect(Collectors.toList());
            log.info("Sono stati inserite {} comuni nel database, ora è aggiornato!", listaComuni.size());
            return listaComuni;
        } catch (Exception e){
            throw new RuntimeException("Errore nel caricare il CSV di comuni" + e + e.getMessage());
        }
    }
}

