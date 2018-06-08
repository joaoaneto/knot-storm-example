import java.util.ArrayList;
import java.util.Map;

import org.apache.storm.task.OutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichBolt;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Tuple;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import br.org.cesar.knot.lib.Credentials;
import br.org.cesar.knot.lib.KnotOperation;
import br.org.cesar.knot.lib.KnotService;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class KnotBolt implements IRichBolt {
	
	private static final long serialVersionUID = 1L;
	
	@Override
	public void prepare(Map arg0, TopologyContext arg1, OutputCollector arg2) {}
	
	@Override
	public void execute(Tuple input) {
		Boolean sensorValue = input.getBooleanByField("sensor");
		
		System.out.println(sensorValue);
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer arg0) {}

	@Override
	public void cleanup() {}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
}
