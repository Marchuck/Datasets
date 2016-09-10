package pl.datasets.rx_utils;

import rx.AsyncEmitter;
import rx.functions.Action1;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @author Lukasz
 * @since 09.09.2016.
 * <p>
 * Naive implementation
 */
public class OnButton {

    public static rx.Observable<Boolean> clicked(final AbstractButton button) {
        return rx.Observable.fromEmitter(new Action1<AsyncEmitter<Boolean>>() {
            @Override
            public void call(final AsyncEmitter<Boolean> subscriber) {

                final ActionListener actionListener = (new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        subscriber.onNext(true);
                    }
                });

                button.addActionListener(actionListener);

                subscriber.setCancellation(new AsyncEmitter.Cancellable() {
                    @Override
                    public void cancel() throws Exception {
                        if (button != null) {
                            button.removeActionListener(actionListener);
                        }
                    }
                });
            }
        }, AsyncEmitter.BackpressureMode.LATEST);
    }
}
