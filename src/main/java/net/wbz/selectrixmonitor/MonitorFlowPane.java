package net.wbz.selectrixmonitor;

import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import net.wbz.selectrix4java.api.bus.AllBusDataConsumer;
import net.wbz.selectrix4java.api.bus.BusDataConsumer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Daniel Tuerk (daniel.tuerk@w-b-z.com)
 */
public class MonitorFlowPane extends FlowPane {

    private final Map<Integer, Map<Integer, BusAddressItemPanel>> addressItemMapping = new HashMap<>();

    public MonitorFlowPane() {

        for (int busNr = 0; busNr < 2; busNr++) {
            getChildren().add(new Label("Bus " + busNr));
            Map<Integer, BusAddressItemPanel> addressItemPanelMap = new HashMap<>();
            for (int addressNr = 0; addressNr < 128; addressNr++) {
                BusAddressItemPanel itemPanel = new BusAddressItemPanel(busNr, addressNr);
                addressItemPanelMap.put(addressNr, itemPanel);
                getChildren().add(itemPanel);
            }
            addressItemMapping.put(busNr, addressItemPanelMap);

        }
    }

    public BusDataConsumer getConsumer() {
        return new AllBusDataConsumer() {

            @Override
            public void valueChanged(int bus, int address, int value) {
                addressItemMapping.get(bus).get(address).updateData(value);
            }
        };
    }
}