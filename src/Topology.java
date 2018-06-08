import org.apache.storm.Config;
import org.apache.storm.LocalCluster;
import org.apache.storm.topology.TopologyBuilder;

public class Topology {
	
	public static void main(String[] args) {
		
		Config config = new Config();
		
		TopologyBuilder builder = new TopologyBuilder();
		
		builder.setSpout("knot-spout", new KnotSpout());

		builder.setBolt("knot-bolt", new KnotBolt()).shuffleGrouping("knot-spout");
		
		LocalCluster local = new LocalCluster();

		try {
			local.submitTopology("cesar-school", config, builder.createTopology());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
}
