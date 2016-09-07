package pl.datasets.widgets.implication;

import pl.datasets.DatasetDialog;
import pl.datasets.model.DatasetItem;
import pl.datasets.widgets.event_search.ComputeButtonBehaviour;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

/**
 * @author Lukasz
 * @since 07.09.2016.
 */
public class ImplicationDatasetDialog extends DatasetDialog implements ComputeButtonBehaviour {

    public ImplicationDatasetDialog(String path, List<DatasetItem> items, String[] propertyNames, String[] operations) {
        super(path, items, propertyNames, operations);
    }

    @Override
    public ActionListener computeButtonClickListener() {
        return new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //todo: JOHANNES create logic for List
                //this should be made

                //List<?> out = yourLogicImpl.compute(this); or sth better

                //compute here and pass result when you will be creating
                // ImplicationDatasetDialog
                //just invoke:
                // new ImplicationSolverDialog(out);

                //note: break private->protected to get access to data from super class
            }
        };
    }
}
