package io.github.jxch.zhiban.clock.view;

import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("clock")
public class ClockView extends VerticalLayout {

    public ClockView() {
        add(new H1("test"));
        add(new H1("test2"));
    }

}
