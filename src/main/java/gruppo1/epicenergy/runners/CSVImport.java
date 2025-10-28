package gruppo1.epicenergy.runners;

import gruppo1.epicenergy.entities.Comune;
import gruppo1.epicenergy.entities.Provincia;
import gruppo1.epicenergy.repositories.ComuneRepository;
import gruppo1.epicenergy.repositories.ProvinciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
            List<Comune> comuni = loadComuni("comuni-italiani.csv.csv", provinciaMap);
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
            throw new RuntimeException("Errore nel caricare il CSV di province");
        }
    }

    private List<Comune> loadComuni (String nomeFile, Map<String, Provincia> provinciaMap){
        try(BufferedReader reader = new BufferedReader(new InputStreamReader(
                getClass().getClassLoader().getResourceAsStream(nomeFile), StandardCharsets.UTF_8
        ))) {
            return reader.lines()
                    .skip(1)
                    .map(line -> {
                        String[] parts = line.split(";");
                        String provinciaNome = parts[3].trim();
                        Provincia provincia = provinciaMap.get(provinciaNome);
                        if(provincia == null){
                            System.out.println(provincia.getNomeProvincia() + " non trovata.");
                            throw new RuntimeException("Provincia non trovata per comune: " + provincia.getNomeProvincia());
                        }
                        return new Comune(
                                Integer.parseInt(parts[0].trim()),
                                Integer.parseInt(parts[1].trim()),
                                parts[2].trim(),
                                provincia
                        );
                    }).collect(Collectors.toList());
        } catch (Exception e){
            throw new RuntimeException("Errore nel caricare il CSV di comuni");
        }
    }
}
