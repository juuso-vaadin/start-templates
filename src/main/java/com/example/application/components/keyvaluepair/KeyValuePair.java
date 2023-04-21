package com.example.application.components.keyvaluepair;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.Unit;
import com.vaadin.flow.component.html.DescriptionList;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.theme.lumo.LumoUtility;

public class KeyValuePair extends FlexLayout {

	private KeyPosition keyPosition;

	private DescriptionList.Term key;
	private DescriptionList.Description value;

	public KeyValuePair(String key, String value) {
		this(new Text(key), new Text(value));
	}

	public KeyValuePair(String key, Component value) {
		this(new Text(key), value);
	}

	public KeyValuePair(Component key, Component value) {
		this.key = new DescriptionList.Term(key);
		this.key.addClassNames(
				LumoUtility.FontSize.SMALL, LumoUtility.FontWeight.MEDIUM, LumoUtility.TextColor.SECONDARY
		);

		this.value = new DescriptionList.Description(value);
		this.value.addClassNames(LumoUtility.Margin.NONE);

		add(this.key, this.value);

		setAlignItems(FlexComponent.Alignment.BASELINE);
		this.addClassNames(LumoUtility.Gap.Column.MEDIUM);
		setKeyPosition(KeyPosition.SIDE);
		setKeyWidth(25, Unit.PERCENTAGE);
	}

	public void setKeyPosition(KeyPosition keyPosition) {
		this.keyPosition = keyPosition;
		updateClassNames();
	}

	public void setKeyWidth(float width, Unit unit) {
		this.key.setMinWidth(width, unit);
	}

	private void updateClassNames() {
		if (this.keyPosition != null) {
			if (this.keyPosition.equals(KeyPosition.SIDE)) {
				setFlexDirection(FlexDirection.ROW);
			} else {
				setFlexDirection(FlexDirection.COLUMN);
			}
		}
	}

	public enum KeyPosition {
		SIDE, TOP
	}

}
