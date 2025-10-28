package gruppo1.epicenergy.tools;


import gruppo1.epicenergy.entities.Cliente;
import gruppo1.epicenergy.entities.Utente;
import kong.unirest.core.HttpResponse;
import kong.unirest.core.JsonNode;
import kong.unirest.core.Unirest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class MailGun {
    private String domain;
    private String apiKey;

    public MailGun(@Value("${mailgun.domain}") String domain, @Value("${mailgun.apiKey}") String apiKey) {
        this.domain = domain;
        this.apiKey = apiKey;
    }

    //TODO: passare utente o cliente?
    public void sendWelcomeEmailUtente(Utente recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages").basicAuth("api", apiKey)
                .queryString("from", "mancinidavide73@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Registrazione completata")
                .queryString("text", "Ciao " + recipient.getNome() + " " + recipient.getCognome() + " benevenuto sulla nostra piattaforma!")
                .asJson();
        System.out.println(response.getBody());
    }

    public void sendWelcomeEmailCliente(Cliente recipient) {
        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + domain + "/messages").basicAuth("api", apiKey)
                .queryString("from", "mancinidavide73@gmail.com")
                .queryString("to", recipient.getEmail())
                .queryString("subject", "Contratto registrato")
                .queryString("text", "Ciao " + recipient.getNomeContatto() + " " + recipient.getCognomeContatto() +
                        " il contratto per la fornitura di energia intestato a " + recipient.getRagioneSociale() + " è stato registrato con successo")
                .asJson();
    }


}
