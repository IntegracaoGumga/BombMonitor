package br.datacoper.control;

import java.io.File;

import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.datacoper.model.Configuracoes;
import br.datacoper.model.Abastecida;

public class Rest {

	public static void enviarAbastecida(final Abastecida supplyAux) {
		ObjectMapper mapper = new ObjectMapper();
		Logger logger = Logger.getLogger("br.datacoper.control.Rest");

		String jsonInString = null;
		try {
			logger.info("Conversao do objeto para JSON");
			jsonInString = mapper.writeValueAsString(supplyAux);
		} catch (JsonProcessingException e) {
			logger.error("Erro na conversao do objeto para JSON");
		}

		String url = Configuracoes.getInstancia().getParam(Configuracoes.PARAM_URL_POST);
		String origem = Configuracoes.getInstancia().getParam(Configuracoes.PARAM_DIRETORIO_IMPORTACAO);
		String destino = Configuracoes.getInstancia().getParam(Configuracoes.PARAM_DIRETORIO_IMPORTADOS);

		Client client = ClientBuilder.newClient();

		for (int i = 1; i <= 3; i++) {
			Response retorno = client.target(url).request(MediaType.APPLICATION_JSON)
					.post(Entity.entity(jsonInString.toString(), MediaType.APPLICATION_JSON));

			if (retorno.getStatus() == 200){
				String fileOrigem = origem + supplyAux.getArquivo();
				String fileDestino = destino + "ok_" + supplyAux.getArquivo();

				File file = new File(fileOrigem);
				file.renameTo(new File(fileDestino));

				logger.info(String.format("Arquivo renomeado de %s para %s", fileOrigem, fileDestino));
				break;
			}
			if (i == 3){		
				logger.error(String.format("Erro no envio do JSON - %s", supplyAux.getArquivo()));
			}
		}

	}

}
