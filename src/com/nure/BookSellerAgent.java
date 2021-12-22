package com.nure;

import jade.core.Agent;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

import java.util.*;

public class BookSellerAgent extends Agent {
    private Hashtable catalogue;

    private BookSellerGui myGui;

    @Override
    protected void setup() {
        catalogue = new Hashtable();

        myGui = new BookSellerGui(this);
        myGui.showGui();

        addBehaviour(new OfferRequestsServer(catalogue));
        addBehaviour(new PurchaseOrdersServer(catalogue));
    }

    @Override
    protected void takeDown() {
        myGui.dispose();
        System.out.println("Seller-agent " + getAID().getName() + " terminating.");
    }

    public void updateCatalogue(final String title, final int price) {
        addBehaviour(new OneShotBehaviour() {
            @Override
            public void action() {
                catalogue.put(title, new Integer(price));
            }
        });
    }
}
