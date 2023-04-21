package com.example.application.views.listitemdetail;

import com.example.application.views.MainLayout;
import com.example.application.views.itemview.ItemView;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

@PageTitle("List - Item - Detail")
@Route(value = "list-item-detail", layout = MainLayout.class)
public class ListItemDetailView extends SplitLayout {

    public ListItemDetailView() {
        setSizeFull();
        setSplitterPosition(50);
        addThemeVariants(SplitLayoutVariant.LUMO_SMALL);

        Div listColumn = new Div(createList());
        addToPrimary(listColumn);
        listColumn.setMaxWidth("24rem");

        SplitLayout itemDetailLayout = new SplitLayout();
        itemDetailLayout.setSplitterPosition(65);
        itemDetailLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        itemDetailLayout.addToPrimary(new ItemView());

        Div detailsColumn = new Div(createDetailsContent());
        detailsColumn.setMinWidth("20rem");
        itemDetailLayout.addToSecondary(detailsColumn);

        addToSecondary(itemDetailLayout);
    }

    private Component createDetailsContent() {
        MessageList list = new MessageList();
        list.addClassNames(LumoUtility.Flex.GROW, LumoUtility.Padding.Top.SMALL);
        Instant yesterday = LocalDateTime.now().minusDays(1).toInstant(ZoneOffset.UTC);
        Instant fiftyMinsAgo = LocalDateTime.now().minusMinutes(50).toInstant(ZoneOffset.UTC);
        MessageListItem message1 = new MessageListItem("Linsey, could you check if the details with the order are okay?",
            yesterday, "Matt Mambo");
        message1.setUserColorIndex(1);
        MessageListItem message2 = new MessageListItem("All good. Ship it.",
            fiftyMinsAgo, "Linsey Listy");
        message2.setUserColorIndex(2);
        list.setItems(Arrays.asList(message1, message2));

        MessageInput input = new MessageInput();
        input.setWidthFull();
        input.addSubmitListener(submitEvent -> {
            Notification.show("Message received: " + submitEvent.getValue(),
                3000, Notification.Position.MIDDLE);
        });

        VerticalLayout detailsLayout = new VerticalLayout(list, input);
        detailsLayout.setSizeFull();
        detailsLayout.setPadding(false);
        detailsLayout.setSpacing(false);

        return detailsLayout;
    }

    private Component createList() {
        TextField searchField = new TextField();
        searchField.setPlaceholder("Search");
        searchField.getElement().setAttribute("aria-label", "Search");
        searchField.setSuffixComponent(VaadinIcon.SEARCH.create());
        searchField.setWidthFull();
        searchField.addClassNames(LumoUtility.Padding.Horizontal.LARGE, LumoUtility.Padding.Vertical.SMALL, LumoUtility.BoxSizing.BORDER);

        VirtualList<String> list = new VirtualList<>();
        list.setSizeFull();
        list.getElement().setAttribute("style", "overflow-x: hidden;");
        list.setItems("Logitech K380", "Logitech MX Keys", "Apple Magic Keyboard", "Dell Multimedia KB216", "Lenovo TrackPoint Keyboard II", "Microsoft Bluetoot Keyboard");
        list.setRenderer(itemCardRenderer);

        VerticalLayout listColumnContent = new VerticalLayout();
        listColumnContent.setSizeFull();
        listColumnContent.setPadding(false);
        listColumnContent.setSpacing(false);
        listColumnContent.add(searchField, list);

        return listColumnContent;
    }

    private ComponentRenderer<Component, String> itemCardRenderer = new ComponentRenderer<>(
        item -> {
            VerticalLayout itemCard = new VerticalLayout();
            itemCard.setSpacing(false);
            itemCard.addClassNames(LumoUtility.Border.BOTTOM, LumoUtility.BorderColor.CONTRAST_10, LumoUtility.Padding.Horizontal.LARGE,
                LumoUtility.Whitespace.NOWRAP);
            Span itemSubtitle = new Span("Keyboard");
            itemSubtitle.addClassNames(LumoUtility.FontWeight.SEMIBOLD, LumoUtility.TextColor.SECONDARY);
            Span itemTitle = new Span(item);
            itemTitle.addClassNames(LumoUtility.FontSize.LARGE, LumoUtility.FontWeight.SEMIBOLD);
            Span itemId = new Span("920-009411");
            Span itemRating = new Span("4.6");
            Span itemStock = new Span("16 items in stock");
            itemStock.getElement().getThemeList().add("badge success");
            HorizontalLayout itemCardDetails = new HorizontalLayout(itemId, itemRating, itemStock);
            itemCardDetails.addClassName("line-separators");

            itemCard.add(itemSubtitle, itemTitle, itemCardDetails);

            Anchor itemAnchor = new Anchor("#", itemCard);
            itemAnchor.addClassNames("item-anchor");

            return itemAnchor;
        });

}
