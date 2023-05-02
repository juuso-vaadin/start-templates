package com.example.application.views.filtermasterdetail;

import com.example.application.views.MainLayout;
import com.example.application.views.itemview.ItemView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.checkbox.Checkbox;
import com.vaadin.flow.component.checkbox.CheckboxGroup;
import com.vaadin.flow.component.checkbox.CheckboxGroupVariant;
import com.vaadin.flow.component.combobox.MultiSelectComboBox;
import com.vaadin.flow.component.customfield.CustomField;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;

import java.util.ArrayList;

@PageTitle("Filter - Master - Detail")
@Route(value = "filter-master-detail", layout = MainLayout.class)
public class FilterMasterDetailView extends SplitLayout {

    private int browserWidth;
    private Div filterColumn;
    private Button toggleFiltersBtn;
    private Button clearFiltersBtn;

    private Div masterColumn;
    private SplitLayout masterDetailLayout;

    private ItemView itemView;

    @Override
    protected void onAttach(AttachEvent attachEvent) {

    }

    public FilterMasterDetailView() {
        setSizeFull();
        addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        addClassName("outer-split");

        filterColumn = new Div(createFilters());
        filterColumn.setMinWidth("4rem");
        addToPrimary(filterColumn);

        masterDetailLayout = new SplitLayout();
        masterDetailLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);

        masterColumn = new Div(createMasterContent());

        masterDetailLayout.addToPrimary(masterColumn);

        itemView = new ItemView();
        itemView.closeBtn.addClickListener(e -> {
            if (browserWidth < 800) {
                filterColumn.setVisible(true);
                masterDetailLayout.setSplitterPosition(100);
            } else if (browserWidth < 1400) {
                masterDetailLayout.setSplitterPosition(100);
            }
        });
        itemView.messagesBtn.setVisible(false);
        masterDetailLayout.addToSecondary(itemView);

        addToSecondary(masterDetailLayout);

        fetchWindowWidth();
        initWindowResizeListener();
    }

    private void fetchWindowWidth() {
        // Set the browser width initially
        UI.getCurrent().getPage().retrieveExtendedClientDetails(details -> {
            browserWidth = details.getWindowInnerWidth();
            initResponsiveRules();
        });
    }

    private void initWindowResizeListener() {
        // Add listener for browser width
        UI.getCurrent().getPage().addBrowserWindowResizeListener(e -> {
            browserWidth = e.getWidth();
            initResponsiveRules();
        });
    }

    private void initResponsiveRules() {
        if (browserWidth < 800) {
            setOrientation(Orientation.VERTICAL);
            setSplitterPosition(0);
            getUI().get().addClassName("filters-collapsed");
            toggleFiltersBtn.setIcon(LumoIcon.ANGLE_DOWN.create());
            masterDetailLayout.setSplitterPosition(100);
            getUI().get().addClassName("detail-closed");
            itemView.setMinWidth("");
            itemView.setMaxWidth("");
            filterColumn.setMinHeight("3rem");
            filterColumn.setMaxWidth("");
            itemView.closeBtn.setVisible(true);
        } else if (browserWidth < 1400) {
            setOrientation(Orientation.HORIZONTAL);
            setSplitterPosition(25);
            getUI().get().removeClassName("filters-collapsed");
            toggleFiltersBtn.setIcon(LumoIcon.ANGLE_LEFT.create());
            masterDetailLayout.setSplitterPosition(100);
            getUI().get().addClassName("detail-closed");
            itemView.setMinWidth("");
            itemView.setMaxWidth("");
            filterColumn.setMaxWidth("24rem");
            itemView.closeBtn.setVisible(true);
        } else {
            setOrientation(Orientation.HORIZONTAL);
            setSplitterPosition(25);
            getUI().get().removeClassName("filters-collapsed");
            toggleFiltersBtn.setIcon(LumoIcon.ANGLE_LEFT.create());
            masterDetailLayout.setSplitterPosition(50);
            getUI().get().removeClassName("detail-closed");
            itemView.setMinWidth("4rem");
            itemView.setMaxWidth("50vw");
            filterColumn.setMaxWidth("24rem");
            itemView.closeBtn.setVisible(false);
        }


    }

    private Component createFilters() {
        H3 filtersHeading = new H3("Filters");
        filtersHeading.setWidthFull();
        clearFiltersBtn = new Button("Clear");
        clearFiltersBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        clearFiltersBtn.addClassName("clear-button");
        clearFiltersBtn.setEnabled(false);

        toggleFiltersBtn = new Button(LumoIcon.ANGLE_LEFT.create());
        toggleFiltersBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY_INLINE);
        toggleFiltersBtn.addClickListener(e -> {
            if (getUI().get().hasClassName("filters-collapsed")) {
                getUI().get().removeClassName("filters-collapsed");
                if (browserWidth < 800) {
                    setSplitterPosition(80);
                    toggleFiltersBtn.setIcon(LumoIcon.ANGLE_UP.create());
                } else {
                    setSplitterPosition(25);
                    toggleFiltersBtn.setIcon(LumoIcon.ANGLE_LEFT.create());
                }
            } else {
                getUI().get().addClassName("filters-collapsed");
                setSplitterPosition(0);
                if (browserWidth < 800) {
                    toggleFiltersBtn.setIcon(LumoIcon.ANGLE_DOWN.create());
                } else {
                    toggleFiltersBtn.setIcon(LumoIcon.ANGLE_RIGHT.create());
                }
            }
        });
        FlexLayout filtersHeader = new FlexLayout(filtersHeading, clearFiltersBtn, toggleFiltersBtn);
        filtersHeader.setAlignItems(FlexComponent.Alignment.CENTER);
        filtersHeader.setJustifyContentMode(FlexComponent.JustifyContentMode.CENTER);
        filtersHeader.addClassNames(LumoUtility.Padding.Bottom.LARGE, LumoUtility.Gap.SMALL);
        filtersHeader.setWidthFull();
        filtersHeader.addClassName("filters-header");

        H4 productHeading = new H4("Product");
        MultiSelectComboBox<String> categoryFilter = new MultiSelectComboBox<>("Category");
        categoryFilter.setItems("Keyboards", "Mice", "Monitors");
        categoryFilter.setWidthFull();
        MultiSelectComboBox<String> brandFilter = new MultiSelectComboBox<>("Brand");
        brandFilter.setItems("Apple", "Dell", "Lenovo", "Logitech", "Microsoft");
        brandFilter.setWidthFull();
        TextField modelFilter = new TextField("Model");
        modelFilter.setWidthFull();
        CheckboxGroup stockFilter = new CheckboxGroup<>("Stock balance");
        stockFilter.setItems("Only items in stock");
        stockFilter.setWidthFull();

        H4 specificationsHeading = new H4("Specifications");
        specificationsHeading.addClassName(LumoUtility.Padding.Top.LARGE);
        CheckboxGroup featuresFilter = new CheckboxGroup<>("Keyboard features");
        featuresFilter.setItems("Wireless bluetooth", "USB wired", "Mechanical switches", "Backlit keyboard");
        featuresFilter.addThemeVariants(CheckboxGroupVariant.LUMO_VERTICAL);
        featuresFilter.setWidthFull();

        VerticalLayout filtersContent = new VerticalLayout();
        filtersContent.addClassName("filters-content");
        filtersContent.setSizeFull();
        filtersContent.setPadding(true);
        filtersContent.setSpacing(false);
        filtersContent.add(filtersHeader, productHeading, categoryFilter, brandFilter, modelFilter, stockFilter, specificationsHeading, featuresFilter);

        return filtersContent;
    }

    private Component createMasterContent() {
        H3 gridHeading = new H3("Products");
        Span gridItemCount = new Span("6");
        HorizontalLayout gridHeader = new HorizontalLayout(gridHeading, gridItemCount);
        gridHeader.addClassNames(LumoUtility.AlignItems.BASELINE, LumoUtility.Padding.Horizontal.MEDIUM,
            LumoUtility.Padding.Top.LARGE, LumoUtility.Padding.Bottom.SMALL);

        Grid<Item> grid = new Grid();
        grid.addColumn(Item::getItem).setHeader("Items");
        grid.addThemeVariants(GridVariant.LUMO_NO_BORDER);
        grid.addItemClickListener(e -> {
            if (browserWidth < 800) {
                masterDetailLayout.setSplitterPosition(0);
                filterColumn.setVisible(false);
            } else if (browserWidth < 1400) {
                masterDetailLayout.setSplitterPosition(0);
            }
        });

        ArrayList<Item> items = new ArrayList();
        for (int i = 0; i < 6; i++) {
            items.add(new Item("Item " + i));
        }
        grid.setItems(items);

        VerticalLayout gridLayout = new VerticalLayout(gridHeader, grid);
        gridLayout.addClassName(LumoUtility.Overflow.HIDDEN);
        gridLayout.setSizeFull();
        gridLayout.setPadding(false);
        gridLayout.setSpacing(false);

        return gridLayout;
    }

    private class Item {

        String item;

        Item(String item) {
            this.item = item;
        }

        public String getItem() {
            return item;
        }
    }

}
