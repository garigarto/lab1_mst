package com.nure;

import java.util.Hashtable;

import jade.core.behaviours.CyclicBehaviour;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class PurchaseOrdersServer extends CyclicBehaviour {
	private Hashtable catalogue;
	public PurchaseOrdersServer(Hashtable catalogue) {
		this.catalogue = catalogue;
	}
	
    public void action() {
        MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
        ACLMessage msg = myAgent.receive(mt);
        if (msg != null) {
            String title = msg.getContent();
            ACLMessage reply = msg.createReply();

            Integer price = (Integer) catalogue.remove(title);
            if (price != null) {
                reply.setPerformative(ACLMessage.INFORM);
                System.out.println(title+" sold to agent "+msg.getSender().getName());
            }
            else {
                reply.setPerformative(ACLMessage.FAILURE);
                reply.setContent("not-available");
            }
            myAgent.send(reply);
        }
        else {
            block();
        }
    }
}
