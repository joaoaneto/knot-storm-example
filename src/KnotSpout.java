import java.util.ArrayList;
import java.util.Map;

import org.apache.storm.spout.SpoutOutputCollector;
import org.apache.storm.task.TopologyContext;
import org.apache.storm.topology.IRichSpout;
import org.apache.storm.topology.OutputFieldsDeclarer;
import org.apache.storm.tuple.Fields;
import org.apache.storm.tuple.Values;
import org.json.JSONException;
import org.json.JSONObject;

import br.org.cesar.knot.lib.Credentials;
import br.org.cesar.knot.lib.KnotOperation;
import br.org.cesar.knot.lib.KnotService;
import io.socket.client.Socket;
import io.socket.emitter.Emitter;

public class KnotSpout implements IRichSpout {
	
	static final String KNOT_SERVER_NAME = "knot-test.cesar.org.br";
	static final int KNOT_PORT = 3000;
	
	private static final long serialVersionUID = 1L;
	
	private SpoutOutputCollector collector;
	
	private ArrayList<Boolean> dataList = new ArrayList<Boolean>();

	@Override
	public void open(Map arg0, TopologyContext context, SpoutOutputCollector arg2) {
		this.collector = arg2;
		
		KnotService knot = new KnotOperation();
		
		Credentials credentials = new Credentials(
				"909f4aec-63cb-4da4-9f64-8243395d0000",
				"b79034fbf0e975e217f90ef0dcb59e87b26a01fd"
		);
		
		Socket connection = knot.connect(KNOT_SERVER_NAME, KNOT_PORT, credentials);
		
		knot.subscribe(connection, "666b2c14-f89c-48ee-a2b2-c795b3050000", new Emitter.Listener() {
			
			@Override
			public void call(Object... args) {
				JSONObject json = (JSONObject) args[0];

				try {
					Boolean value = json.getJSONObject("payload").getJSONObject("data").getBoolean("value");
					dataList.add(value);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		});
	}


	@Override
	public void nextTuple() {
		
		if(dataList.size() > 0) {
			this.collector.emit(new Values(dataList.get(dataList.size()-1)));
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
	}

	@Override
	public void declareOutputFields(OutputFieldsDeclarer declarer) {
		declarer.declare(new Fields("sensor"));
	}

	@Override
	public Map<String, Object> getComponentConfiguration() {
		return null;
	}
	
	@Override
	public void ack(Object arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void activate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void close() {
		// TODO Auto-generated method stub

	}

	@Override
	public void deactivate() {
		// TODO Auto-generated method stub

	}

	@Override
	public void fail(Object arg0) {
		// TODO Auto-generated method stub

	}
	
}
