package io.github.jxch.zhiban.clock.view;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.json.JSONUtil;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.service.ClockService;
import io.github.jxch.zhiban.clock.service.UserConfigService;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Calendar;
import java.util.List;

@Route("clock")
public class ClockView extends VerticalLayout {
    private final UserConfigService userConfigService;
    private final ClockService clockService;

    private final ComboBox<User> comboBox = new ComboBox<>("User");
    private final TextArea textArea = new TextArea("Detail");
    private final Button clockIn = new Button("上班打卡");
    private final Button clockOut = new Button("下班打卡");
    private final Button clockOutEnable = new Button("下班打卡开关");

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

        clockIn.addClickListener(e -> clockIn());
        clockOut.addClickListener(e -> clockOut());
        clockOutEnable.addClickListener(e -> clockOut.setEnabled(!clockOut.isEnabled()));
        clockOutEnable();

        add(comboBox, clockIn, clockOut, textArea, clockOutEnable);
    }

    private void clockIn() {
        String userName = comboBox.getValue().getUserName();
        if (clockService.isClockIn(userName)) {
            showNotification("已经打过卡了");
        } else {
            clockService.clockIn(userName);
            showNotification("打卡成功");
        }
    }

    private void clockOut() {
        String userName = comboBox.getValue().getUserName();
        if (clockService.isClockIn(userName)) {
            if (clockService.isClockOut(userName)) {
                clockService.clockOut(userName);
                showNotification("已经打过卡了, 再次执行打卡");
            } else {
                clockService.clockOut(userName);
                showNotification("初次打卡成功");
            }
        } else {
            showNotification("还未执行上班打卡");
        }
    }

    private void showNotification(String message) {
        Notification.show(message, 5000, Notification.Position.TOP_CENTER).open();
    }

    @Scheduled(cron = "0 0 * * * ?")
    public void clockOutEnable() {
        int dayOfWeek = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek < Calendar.SATURDAY && dayOfWeek > Calendar.SUNDAY) {
            clockOut.setEnabled(DateUtil.date().isAfterOrEquals(DateUtil.parse(DateUtil.today() + " 18:00:00")));
        } else {
            clockOut.setEnabled(true);
        }
    }

}
