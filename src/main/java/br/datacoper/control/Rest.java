package br.datacoper.control;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.datacoper.model.Supply;

public class Rest {

	// private final static String APPLICATION_JSON = "application/json";

	public static void postSupply(final Supply supplyAux) {
		ObjectMapper mapper = new ObjectMapper();

		String jsonInString = null;
		try {
			jsonInString = mapper.writeValueAsString(supplyAux);
		} catch (JsonProcessingException e) {
			System.err.println("Erro na conversao do objeto para JSON");
		}

		String url = "http://10.150.61.20:8080/IntegradorProgress/rest/Dread";

		Client client = ClientBuilder.newClient();

		for (int i = 1; i <= 3; i++) {
			Response retorno = client.target(url).request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(jsonInString.toString(), MediaType.APPLICATION_JSON));

			if (retorno.getStatus() == 200){
				File file = new File("/home/dread/workspace/BombMonitor/src/main/resources/monitorar/" + supplyAux.getArquivo());
				file.renameTo(
						new File("/home/dread/workspace/BombMonitor/src/main/resources/importados/ok_" + file.getName()));
				break;
			}
			if (i == 3){		
				System.err.println(String.format("Erro no envio do JSON - %d", supplyAux.getNumeroBico()));
			}
		}

	}

}
