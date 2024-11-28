package io.github.jxch.zhiban.clock.view;

import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.service.ClockService;
import io.github.jxch.zhiban.clock.service.UserConfigService;

@Route("clock")
public class ClockView extends VerticalLayout {
    private final UserConfigService userConfigService;
    private final ClockService clockService;

    private final ComboBox<User> comboBox = new ComboBox<>("User");

    public ClockView(UserConfigService userConfigService, ClockService clockService) {
        this.userConfigService = userConfigService;
        this.clockService = clockService;
        buildView();
    }

    private void buildView() {
        comboBox.setItems(userConfigService.allUsers());
        comboBox.setItemLabelGenerator(User::getUserName);

        Button clockIn = new Button("上班打卡");
        clockIn.addClickListener(e -> clockService.clockIn(comboBox.getValue().getUserName()));

        Button clockOut = new Button("下班打卡");
        clockIn.addClickListener(e -> clockService.clockOut(comboBox.getValue().getUserName()));

        add(comboBox, clockIn, clockOut);
    }

}
