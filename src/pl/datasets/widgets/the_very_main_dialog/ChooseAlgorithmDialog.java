package pl.datasets.widgets.the_very_main_dialog;

import pl.datasets.DatasetDialog;
import pl.datasets.MainDatasetOperationFrame;
import pl.datasets.model.DatasetItem;
import pl.datasets.rx_utils.DatasetsObservableProvider;
import pl.datasets.rx_utils.OnButton;
import pl.datasets.widgets.event_search.Behaviour;
import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.subjects.ReplaySubject;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * @author Lukasz
 * @since 07.09.2016.
 */
public class ChooseAlgorithmDialog extends JFrame {

    private JButton implicationButton;
    private JButton eventSearchButton;
    private JPanel rootPanel;
    private ChooseAlgorithmDialog self;
    private rx.functions.Action1<java.lang.Throwable> defaultErrorHandlingImpl = new Action1<Throwable>() {
        @Override
        public void call(Throwable throwable) {
            System.err.println(throwable.getLocalizedMessage());
            throwable.printStackTrace();
            System.exit(1);
        }
    };
    private rx.Subscription implicationSubscription, eventBasedSubscription;


    public ChooseAlgorithmDialog() {
        super();
        self = this;
        setContentPane(rootPanel);
        connectButtons();
        initDefaultConfig();
    }

    /**
     * Explained what have done here:
     * loading dataset (List<DatasetItem>) is heavy operation, and It must be performed first
     * <p>
     * Creation of DatasetDialog is triggered by buttons (implication or event_based btn)
     * <p>
     * Scenario is following:
     * dataset is creating first(this takes some time),
     * no matter user clicked something or not.
     * <p>
     * if user clicked button, result is chained with already-living Observable<DatasetItem> so we do not waste time
     * in creating List<DatasetItem> , we just pass it further to create Dialog with this data.
     */
    private void connectButtons() {

        System.out.println("connectButtons");

        final rx.subjects.Subject<List<DatasetItem>, List<DatasetItem>> pendingDatasetSubject = ReplaySubject.create();
        DatasetsObservableProvider.provide().subscribe(pendingDatasetSubject);

        implicationSubscription = OnButton.clicked(implicationButton)
                .flatMap(new Func1<Boolean, Observable<List<DatasetItem>>>() {
                    @Override
                    public Observable<List<DatasetItem>> call(Boolean aBoolean) {
                        if (eventBasedSubscription != null) eventBasedSubscription.unsubscribe();
                        return pendingDatasetSubject;
                    }
                }).flatMap(new Func1<List<DatasetItem>, Observable<DatasetDialog>>() {
                    @Override
                    public Observable<DatasetDialog> call(List<DatasetItem> datasetItems) {
                        return MainDatasetOperationFrame.buildDialogWithDataset(datasetItems, Behaviour.IMPLICATION);
                    }
                }).subscribe(new Action1<DatasetDialog>() {
                    @Override
                    public void call(DatasetDialog dialog) {
                        self.setVisible(false);
                        self.dispose();
                    }
                }, defaultErrorHandlingImpl);

        eventBasedSubscription = OnButton.clicked(eventSearchButton)
                .flatMap(new Func1<Boolean, Observable<List<DatasetItem>>>() {
                    @Override
                    public Observable<List<DatasetItem>> call(Boolean aBoolean) {
                        if (implicationSubscription != null) implicationSubscription.unsubscribe();
                        return pendingDatasetSubject;
                    }
                }).flatMap(new Func1<List<DatasetItem>, Observable<DatasetDialog>>() {
                    @Override
                    public Observable<DatasetDialog> call(List<DatasetItem> datasetItems) {
                        return MainDatasetOperationFrame.buildDialogWithDataset(datasetItems, Behaviour.EVENT_BASED);
                    }
                }).subscribe(new Action1<DatasetDialog>() {
                    @Override
                    public void call(DatasetDialog dialog) {
                        self.setVisible(false);
                        self.dispose();
                    }
                }, defaultErrorHandlingImpl);
    }

    public void initDefaultConfig() {
        setMinimumSize(new Dimension(300, 200));
        setPreferredSize(new Dimension(300, 200));
        pack();
        setLocation(300, 300);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setVisible(true);
    }
}
