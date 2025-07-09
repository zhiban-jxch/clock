package io.github.jxch.zhiban.clock.view;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSONConfig;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.vaadin.flow.component.ComponentEvent;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.timepicker.TimePicker;
import com.vaadin.flow.router.Route;
import io.github.jxch.zhiban.clock.context.ClockContext;
import io.github.jxch.zhiban.clock.entity.User;
import io.github.jxch.zhiban.clock.service.ClockService;
import io.github.jxch.zhiban.clock.service.UserConfigService;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Route("clock")
public class ClockView extends VerticalLayout {
    private final UserConfigService userConfigService;
    private final ClockService clockService;

    private final ComboBox<User> comboBox = new ComboBox<>("User");
    private final TextArea textArea = new TextArea("Detail");
    private final Button clockIn = new Button("上班打卡");
    private final Button clockOut = new Button("下班打卡");
    private final Button clockOutEnable = new Button("下班打卡开关");

    private final Span clockInStatus = new Span();
    private final Span clockOutStatus = new Span();
    private final Span nowDateTime = new Span();
    private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private final ComboBox<String> timezoneCombo = new ComboBox<>("选择时区");
    private final static String DEFAULT_ZONE = "Asia/Shanghai";

    private final TimePicker timePicker = new TimePicker("打卡时间");
    private final Checkbox timeCheckbox = new Checkbox("修改打卡时间");
    private final DatePicker datePicker = new DatePicker("打卡日期");
    private final Checkbox dateCheckbox = new Checkbox("修改打卡日期");
    private final Checkbox secondRandom = new Checkbox("随机秒钟");

    private final JSONConfig jsonConfig = new JSONConfig().setDateFormat("yyyy-MM-dd");

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
        comboBox.addValueChangeListener(event -> textArea.setValue(JSONUtil.toJsonPrettyStr(new JSONObject(comboBox.getValue(), jsonConfig))));
        comboBox.addAttachListener(event -> textArea.setValue(JSONUtil.toJsonPrettyStr(new JSONObject(comboBox.getValue(), jsonConfig))));
        comboBox.addValueChangeListener(event -> init());

        clockIn.addClickListener(e -> clockIn());
        clockOut.addClickListener(e -> clockOut());
        clockOutEnable.addClickListener(e -> clockOut.setEnabled(!clockOut.isEnabled()));

        nowDateTime.getStyle().set("color", "orange");

        this.timePicker.setValue(LocalTime.now());
        this.timePicker.setStep(Duration.ofMinutes(10));
        this.timePicker.addValueChangeListener(this::refresh);
        this.timeCheckbox.setValue(false);
        this.timeCheckbox.addValueChangeListener(this::refresh);

        this.datePicker.setValue(LocalDate.now());
        this.datePicker.addValueChangeListener(event -> init());
        this.dateCheckbox.setValue(false);
        this.dateCheckbox.addValueChangeListener(this::refresh);

        timezoneCombo.setItems(ZoneId.getAvailableZoneIds().stream().sorted().toList());
        timezoneCombo.setValue(DEFAULT_ZONE);
        timezoneCombo.addValueChangeListener(this::refresh);

        UI ui = UI.getCurrent();
        ui.setPollInterval(500);
        ui.addPollListener(this::refresh);
        init();

        HorizontalLayout dateTimeCheckboxLayout = new HorizontalLayout();
        dateTimeCheckboxLayout.add(dateCheckbox, timeCheckbox, secondRandom);
        HorizontalLayout dateTimePickerLayout = new HorizontalLayout();
        dateTimePickerLayout.add(datePicker, timePicker);

        add(nowDateTime, clockInStatus, clockOutStatus, comboBox, clockIn, clockOut, dateTimeCheckboxLayout, dateTimePickerLayout, timezoneCombo, clockOutEnable, textArea);
    }

    private void init() {
        refresh(null);
        refreshContext();
        updateClockStatus();
        clockOutEnable();
    }

    private void clockIn() {
        try {
            refreshContext();
            String userName = comboBox.getValue().getUserName();
            if (clockService.isClockIn(userName)) {
                showNotification("已经打过卡了");
            } else {
                clockService.clockIn(userName);
                showNotification("打卡成功");
                clockOut.setEnabled(false);
            }
        } finally {
            updateClockStatus();
            ClockContext.removeClockDate();
        }
    }

    private void clockOut() {
        try {
            refreshContext();
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
        } finally {
            updateClockStatus();
            ClockContext.removeClockDate();
        }
    }

    private void showNotification(String message) {
        Notification.show(message, 5000, Notification.Position.TOP_CENTER).open();
    }

    private void refreshContext() {
        updateTime();

        ZonedDateTime now = getZonedDateTime();
        LocalDate date = now.toLocalDate();
        LocalTime time = now.toLocalTime();

        if (dateCheckbox.getValue()) {
            date = datePicker.getValue();
        }
        if (timeCheckbox.getValue()) {
            time = timePicker.getValue();
            if (secondRandom.getValue()) {
                time = time.withSecond(new Random().nextInt(60));
            }
        }

        ZonedDateTime zonedDateTime = date.atTime(time).atZone(now.getZone());
        ClockContext.setClockDate(Date.from(zonedDateTime.toInstant()));
    }

    private void refresh(ComponentEvent event) {
        if (Objects.isNull(event) || !event.isFromClient()) {
            return;
        }

        updateTime();
    }

    public void clockOutEnable() {
        ZonedDateTime now = getZonedDateTime();

        int dayOfWeek = now.getDayOfWeek().getValue();
        boolean isWorkDay = dayOfWeek >= 1 && dayOfWeek <= 5;

        ZonedDateTime sixPmToday = now.withHour(18).withMinute(0).withSecond(0).withNano(0);
        boolean afterSix = now.isAfter(sixPmToday);

        if (isWorkDay) {
            clockOut.setEnabled(afterSix);
        } else {
            clockOut.setEnabled(true);
        }
    }

    private ZonedDateTime getZonedDateTime() {
        String selectedZoneId = Optional.ofNullable(timezoneCombo.getValue()).orElse(DEFAULT_ZONE);
        return ZonedDateTime.now(ZoneId.of(selectedZoneId));
    }

    private void updateTime() {
        ZonedDateTime now = getZonedDateTime();

        LocalDate date = now.toLocalDate();
        LocalTime time = now.toLocalTime();

        if (!dateCheckbox.getValue()) {
            datePicker.setValue(date);
            datePicker.setReadOnly(true);
        } else {
            datePicker.setReadOnly(false);
        }
        if (!timeCheckbox.getValue()) {
            timePicker.setValue(time);
            timePicker.setReadOnly(true);
        } else {
            timePicker.setReadOnly(false);
        }

        nowDateTime.setText(now.getZone().getId() + " " + date.format(dateFormatter) + " " + time.format(timeFormatter));
    }

    private void updateClockStatus() {
        ZonedDateTime now = getZonedDateTime();
        String userName = comboBox.getValue().getUserName();
        if (clockService.isClockIn(userName)) {
            String clockInTimes = clockService.getClockInDate(userName).stream()
                    .map(date -> date.toInstant().atZone(now.getZone()).toLocalTime())
                    .map(timeFormatter::format).collect(Collectors.joining(" | "));
            clockInStatus.setText("上班打卡 - 已打卡 : " + clockInTimes);
            clockInStatus.getStyle().set("color", "green");
        } else {
            clockInStatus.setText("上班打卡 - 未打卡");
            clockInStatus.getStyle().set("color", "red");
        }

        if (clockService.isClockOut(userName)) {
            String clockOutTimes = clockService.getClockOutDate(userName).stream()
                    .map(date -> date.toInstant().atZone(now.getZone()).toLocalTime())
                    .map(timeFormatter::format).collect(Collectors.joining(" | "));
            clockOutStatus.setText("下班打卡 - 已打卡 : " + clockOutTimes);
            clockOutStatus.getStyle().set("color", "green");
        } else {
            clockOutStatus.setText("下班打卡 - 未打卡");
            clockOutStatus.getStyle().set("color", "red");
        }
    }

}
