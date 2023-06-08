package org.example.randomnumbergenerator.encode;

import com.google.gson.Gson;
import org.example.randomnumbergenerator.model.BigNumber;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

public class BigNumberEncoder implements Encoder.Text<BigNumber> {

	private static Gson gson = new Gson();

	@Override
	public String encode(BigNumber object) throws EncodeException {
		return gson.toJson(object);
	}

	@Override
	public void init(EndpointConfig config) {

	}

	@Override
	public void destroy() {

	}
}
