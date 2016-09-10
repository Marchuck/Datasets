package pl.datasets.widgets.event_search;

import pl.datasets.DatasetDialog;
import pl.datasets.model.DatasetItem;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Lukasz
 * @since 07.09.2016.
 */
public class EventBasedDatasetDialog extends DatasetDialog implements ComputeButtonBehaviour {

    public EventBasedDatasetDialog(String path, List<DatasetItem> items, String[] propertyNames, String[] operations) {
        super(path, items, propertyNames, operations);
    }

    /**
     * note:
     *
     * @return compute button uses this implementation to perform calculations
     */
    @Override
    public ActionListener computeButtonClickListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo: JOHANNESS : logic for chained calculations should be made here
                //todo: Lukasz create view  for displaying results

                //List<?> out = yourLogicImpl.compute(this); or sth better

                //compute here and pass result when you will be creating
                // ImplicationDatasetDialog
                //just invoke:
                //note: break private->protected to get access to data from super class
            }
        };
    }
}
