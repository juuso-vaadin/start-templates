package com.example.application.views.itemview;

import com.example.application.components.keyvaluepair.KeyValuePair;
import com.example.application.components.keyvaluepair.KeyValuePairs;
import com.example.application.views.MainLayout;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tab;
import com.vaadin.flow.component.tabs.TabSheet;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteAlias;
import com.vaadin.flow.theme.lumo.LumoUtility;

@PageTitle("Item View")
@Route(value = "item-view", layout = MainLayout.class)
@RouteAlias(value = "", layout = MainLayout.class)
public class ItemView extends VerticalLayout {

    private H2 itemTitle;
    private Span itemSubtitle;
    private VerticalLayout tabContent;

    public Button closeBtn = new Button("Close");
    public Button messagesBtn = new Button("Messages");

    public ItemView() {
        setSizeFull();
        setPadding(false);
        setSpacing(false);

        // View header
        HorizontalLayout headlineWithActions = createHeadlineWithActions("Logitech MX Keys", "Keyboard");
        DescriptionList itemSummary = createItemSummary();

        VerticalLayout itemHeader = new VerticalLayout();
        itemHeader.addClassNames(LumoUtility.Padding.Horizontal.XLARGE, LumoUtility.Padding.Top.XLARGE);
        itemHeader.add(headlineWithActions, itemSummary);
        add(itemHeader);

        // View content
        TabSheet itemContent = new TabSheet();
        itemContent.setWidthFull();
        itemContent.addClassName(LumoUtility.Flex.GROW);
        itemContent.addClassName("horizontal-space-large");
        // Style variant for displaying content as card
        //itemContent.addClassName("card-style");

        itemContent.add(new Tab("Specifications"), createSpecificationsTabContent());
        itemContent.add(new Tab("Stock balance"), createEmptyTabContent());
        itemContent.add(new Tab("Order history"), createEmptyTabContent());
        add(itemContent);
    }

    private HorizontalLayout createHeadlineWithActions(String title, String subtitle) {
        HorizontalLayout itemHeaderIdentifier = new HorizontalLayout();
        itemHeaderIdentifier.setWidthFull();
        itemHeaderIdentifier.setAlignItems(Alignment.BASELINE);
        itemHeaderIdentifier.addClassName(LumoUtility.Gap.SMALL);
        itemTitle = new H2(title);
        itemSubtitle = new Span(subtitle);
        itemSubtitle.addClassName(LumoUtility.TextColor.SECONDARY);
        FlexLayout itemTitles = new FlexLayout(itemTitle, itemSubtitle);
        itemTitles.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        itemTitles.setAlignItems(Alignment.BASELINE);
        itemTitles.addClassName(LumoUtility.Gap.MEDIUM);

        FlexLayout itemActions = new FlexLayout();
        itemActions.addClassNames(LumoUtility.Margin.Left.AUTO, LumoUtility.Gap.SMALL);
        Button editBtn = new Button("Edit");
        editBtn.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        itemActions.add(messagesBtn, closeBtn, editBtn);

        itemHeaderIdentifier.add(itemTitles, itemActions);
        return itemHeaderIdentifier;
    }

    private DescriptionList createItemSummary() {
        DescriptionList itemSummary = new DescriptionList();

        itemSummary.addClassNames(LumoUtility.Display.FLEX, LumoUtility.Gap.XLARGE, LumoUtility.Margin.NONE);
        KeyValuePair productNumber = new KeyValuePair("Product number", "920-009411");

        productNumber.setKeyPosition(KeyValuePair.KeyPosition.TOP);
        KeyValuePair rating = new KeyValuePair("Rating", "4.6 / 5");

        rating.setKeyPosition(KeyValuePair.KeyPosition.TOP);
        Span stockBalanceBadge = new Span("16 items");
        stockBalanceBadge.getElement().getThemeList().add("badge success");
        KeyValuePair stockBalance = new KeyValuePair("Stock balance", stockBalanceBadge);
        stockBalance.setKeyPosition(KeyValuePair.KeyPosition.TOP);
        itemSummary.add(productNumber, rating, stockBalance);
        return itemSummary;
    }

    private Component createEmptyTabContent() {
        tabContent = new VerticalLayout(new Span("Nothing here"));
        tabContent.addClassNames("tab-content");
        tabContent.addClassNames(LumoUtility.Padding.LARGE, LumoUtility.Margin.Horizontal.AUTO);
        tabContent.setMaxWidth("1200px");

        return tabContent;
    }

    private Component createSpecificationsTabContent() {
        Paragraph itemDescription = new Paragraph("Designed for creatives & engineered for coders. Features easy switch pairing with 3 devices. Compatible with Win, Mac, Linux, iOS & Android OS");
        itemDescription.addClassNames(LumoUtility.FontSize.XLARGE, LumoUtility.Flex.GROW);
        itemDescription.getElement().setAttribute("style", "flex-basis: 60%;");
        Image itemImage = new Image("https://resource.logitech.com/content/dam/logitech/en/products/keyboards/mx-keys-for-business/gallery/mx-keys-business-keyboard-gallery-ch-graphite-1.png", "Logitech MX Keys keyboard");
        itemImage.addClassNames(
            LumoUtility.Flex.GROW,
            LumoUtility.Flex.SHRINK,
            LumoUtility.Width.FULL,
            LumoUtility.Height.AUTO,
            LumoUtility.Overflow.HIDDEN
        );
        itemImage.getElement().setAttribute("style", "flex-basis: 40%;");
        itemImage.setMinWidth("12rem");
        itemImage.setMaxWidth("20rem");
        FlexLayout itemContentHead = new FlexLayout(itemDescription, itemImage);
        itemContentHead.setAlignItems(Alignment.CENTER);
        itemContentHead.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        itemContentHead.addClassName(LumoUtility.Padding.Bottom.LARGE);

        H3 dimensionsHeading = new H3("Dimensions");

        Span keyboardHeading = new Span("Keyboard");
        keyboardHeading.addClassName(LumoUtility.FontWeight.SEMIBOLD);
        KeyValuePairs keyboardKeyValuePairs = new KeyValuePairs();
        keyboardKeyValuePairs.setWidthFull();
        KeyValuePair keyboardHeight = new KeyValuePair("Height", "5.18 in (131.63 mm)");
        KeyValuePair keyboardWidth = new KeyValuePair("Width", "16.94 in (430.2 mm)");
        KeyValuePair keyboardDepth = new KeyValuePair("Depth", "0.81 in (20.5 mm)");
        KeyValuePair keyboardWeight = new KeyValuePair("Weight", "28.57 oz (810 g)");
        keyboardKeyValuePairs.add(keyboardHeight, keyboardWidth, keyboardDepth, keyboardWeight);

        Span receiverHeading = new Span("Unifying USB Receiver");
        receiverHeading.addClassName(LumoUtility.FontWeight.SEMIBOLD);
        KeyValuePairs receiverKeyValuePairs = new KeyValuePairs();
        receiverKeyValuePairs.setWidthFull();
        KeyValuePair receiverHeight = new KeyValuePair("Height", "5.18 in (131.63 mm)");
        KeyValuePair receiverWidth = new KeyValuePair("Width", "16.94 in (430.2 mm)");
        KeyValuePair receiverDepth = new KeyValuePair("Depth", "0.81 in (20.5 mm)");
        KeyValuePair receiverWeight = new KeyValuePair("Weight", "28.57 oz (810 g)");
        receiverKeyValuePairs.add(receiverHeight, receiverWidth, receiverDepth, receiverWeight);

        H3 specificationsHeading = new H3("Technical Specifications");

        UnorderedList specificationsList = new UnorderedList();
        specificationsList.addClassNames(LumoUtility.Margin.NONE, LumoUtility.Display.FLEX, LumoUtility.FlexDirection.COLUMN,
            LumoUtility.Gap.SMALL, LumoUtility.Padding.Left.LARGE);
        specificationsList.add(
            new ListItem("Dual connectivity: Connect via the included Unifying USB Receiver or Bluetooth low energy"),
            new ListItem("Easy-switch keys to connect up to three devices and easily switch between them"),
            new ListItem("10 meters wireless range"),
            new ListItem("Hand proximity sensors that turn the backlighting on"),
            new ListItem("Ambient light sensors that adjust backlighting brightness"),
            new ListItem("USB-C rechargeable. Full charge lasts 10 days – or 5 months with backlighting off"),
            new ListItem("On/Off power switch"),
            new ListItem("Caps Lock and Battery indicator lights"),
            new ListItem("Compatible with Logitech Flow enabled mouse")
        );

        VerticalLayout dimensionsColumn = new VerticalLayout(dimensionsHeading, keyboardHeading, keyboardKeyValuePairs, receiverHeading, receiverKeyValuePairs);
        dimensionsColumn.setPadding(false);
        dimensionsColumn.addClassName(LumoUtility.Flex.GROW);
        dimensionsColumn.getElement().setAttribute("style", "flex-basis: 33%;");
        dimensionsColumn.setMinWidth("16rem");
        VerticalLayout specificationsColumn = new VerticalLayout(specificationsHeading, specificationsList);
        specificationsColumn.setPadding(false);
        specificationsColumn.addClassName(LumoUtility.Flex.GROW);
        specificationsColumn.getElement().setAttribute("style", "flex-basis: 66%;");
        FlexLayout itemContentBody = new FlexLayout(dimensionsColumn, specificationsColumn);
        itemContentBody.setWidthFull();
        itemContentBody.addClassName(LumoUtility.Gap.Row.XLARGE);
        itemContentBody.setFlexWrap(FlexLayout.FlexWrap.WRAP);

        tabContent = new VerticalLayout(itemContentHead, itemContentBody);
        tabContent.addClassNames("tab-content");
        tabContent.addClassNames(LumoUtility.Padding.LARGE, LumoUtility.Margin.Horizontal.AUTO);
        tabContent.setMaxWidth("1200px");

        return tabContent;
    }

}
