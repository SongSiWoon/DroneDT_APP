package Drone.DigitalTwin.Application;

import org.ros.concurrent.CancellableLoop;
import org.ros.namespace.GraphName;
import org.ros.node.AbstractNodeMain;
import org.ros.node.ConnectedNode;
import org.ros.node.topic.Publisher;

public class ZTalker extends AbstractNodeMain {
    private String topic_name;
    //public double sequenceNumber;
    public static String cmd;
    public ZTalker(){
        topic_name = "/zcontrol";
    }

    public ZTalker(String topic) {
        topic_name = topic;
    }

    @Override
    public GraphName getDefaultNodeName() {
        return GraphName.of("ros/node/ztalker");
    }

    @Override
    public void onStart(ConnectedNode connectedNode) {
        final Publisher<std_msgs.String> publisher =
                connectedNode.newPublisher(topic_name, std_msgs.String._TYPE);
        connectedNode.executeCancellableLoop(new CancellableLoop() {
            private int sequenceNumber;

            @Override
            protected void setup() {
                cmd = "0";
                //sequenceNumber = 0;
            }

            @Override
            protected void loop() throws InterruptedException {
                std_msgs.String str;
                str = publisher.newMessage();
                //str.setData("Hello world! " + sequenceNumber);
                str.setData(cmd);
                publisher.publish(str);
                //sequenceNumber++;
                Thread.sleep(10);
            }
        });
    }
}

