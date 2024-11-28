package io.github.jxch.zhiban.clock.view;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.service.ClockService;
import io.github.jxch.zhiban.clock.service.UserConfigService;

import java.util.List;

@Route("clock")
public class ClockView extends VerticalLayout {
    private final UserConfigService userConfigService;
    private final ClockService clockService;

    private final ComboBox<User> comboBox = new ComboBox<>("User");
    private final TextArea textArea = new TextArea("Detail");

    public ClockView(UserConfigService userConfigService, ClockService clockService) {
        this.userConfigService = userConfigService;
        this.clockService = clockService;
        buildView();
    }

    private void buildView() {
        List<User> users = userConfigService.allUsers();
        comboBox.setItems(users);
        comboBox.setValue(CollectionUtil.getFirst(users));
        comboBox.setItemLabelGenerator(User::getUserName);
        comboBox.addValueChangeListener(event -> textArea.setValue(JSONUtil.toJsonPrettyStr(comboBox.getValue())));
        comboBox.addAttachListener(event -> textArea.setValue(JSONUtil.toJsonPrettyStr(comboBox.getValue())));

        Button clockIn = new Button("上班打卡");
        clockIn.addClickListener(e -> clockService.clockIn(comboBox.getValue().getUserName()));

        Button clockOut = new Button("下班打卡");
        clockIn.addClickListener(e -> clockService.clockOut(comboBox.getValue().getUserName()));

        // todo 根据 textArea 更新 userConfig
        add(comboBox, clockIn, clockOut, textArea);
    }

}
