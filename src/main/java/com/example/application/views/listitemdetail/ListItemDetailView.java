package com.example.application.views.listitemdetail;

import com.example.application.views.MainLayout;
import com.example.application.views.itemview.ItemView;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.avatar.Avatar;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.html.*;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.messages.MessageInput;
import com.vaadin.flow.component.messages.MessageList;
import com.vaadin.flow.component.messages.MessageListItem;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.FlexComponent;
import com.vaadin.flow.component.orderedlayout.FlexLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.page.Page;
import com.vaadin.flow.component.splitlayout.SplitLayout;
import com.vaadin.flow.component.splitlayout.SplitLayoutVariant;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.virtuallist.VirtualList;
import com.vaadin.flow.data.renderer.ComponentRenderer;
import com.vaadin.flow.dom.ElementFactory;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.router.RouteConfiguration;
import com.vaadin.flow.theme.lumo.LumoIcon;
import com.vaadin.flow.theme.lumo.LumoUtility;
import com.vaadin.flow.theme.lumo.LumoUtility.Margin;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Arrays;

@PageTitle("List - Item - Detail")
@Route(value = "list-item-detail", layout = MainLayout.class)
public class ListItemDetailView extends SplitLayout {

    private int browserWidth;
    private Div listColumn;
    private Div detailsColumn;

    private HorizontalLayout messagesHeader;
    private SplitLayout itemDetailLayout;

    private ItemView itemView;

    @Override
    protected void onAttach(AttachEvent attachEvent) {

    }

    public ListItemDetailView() {
        setSizeFull();
        addThemeVariants(SplitLayoutVariant.LUMO_SMALL);

        listColumn = new Div(createList());
        addToPrimary(listColumn);

        itemDetailLayout = new SplitLayout();
        itemDetailLayout.addThemeVariants(SplitLayoutVariant.LUMO_SMALL);
        itemView = new ItemView();
        itemView.closeBtn.addClickListener(e -> {
            if (browserWidth < 800) {
                setSplitterPosition(100);
            }
        });
        itemView.messagesBtn.addClickListener(e -> {
            if (browserWidth < 800) {
                itemDetailLayout.setSplitterPosition(0);
            } else if (browserWidth < 1400 && getUI().get().hasClassName("detail-closed")) {
                itemDetailLayout.setSplitterPosition(65);
                getUI().get().removeClassName("detail-closed");
            } else if (browserWidth < 1400) {
                itemDetailLayout.setSplitterPosition(100);
                getUI().get().addClassName("detail-closed");
            }
        });
        itemDetailLayout.addToPrimary(itemView);

        detailsColumn = new Div(createDetailsContent());

        itemDetailLayout.addToSecondary(detailsColumn);

        addToSecondary(itemDetailLayout);

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
            setSplitterPosition(100);
            itemDetailLayout.setSplitterPosition(100);
            getUI().get().addClassName("detail-closed");
            detailsColumn.setMinWidth("");
            detailsColumn.setMaxWidth("");
            listColumn.setMaxWidth("");
            itemView.closeBtn.setVisible(true);
            messagesHeader.setVisible(true);
            itemView.messagesBtn.setVisible(true);
        } else if (browserWidth < 1400) {
            setSplitterPosition(25);
            itemDetailLayout.setSplitterPosition(100);
            getUI().get().addClassName("detail-closed");
            detailsColumn.setMinWidth("");
            detailsColumn.setMaxWidth("");
            listColumn.setMaxWidth("24rem");
            itemView.closeBtn.setVisible(false);
            messagesHeader.setVisible(false);
            itemView.messagesBtn.setVisible(true);
        } else {
            setSplitterPosition(50);
            itemDetailLayout.setSplitterPosition(65);
            getUI().get().removeClassName("detail-closed");
            detailsColumn.setMinWidth("4rem");
            detailsColumn.setMaxWidth("30rem");
            listColumn.setMaxWidth("24rem");
            itemView.closeBtn.setVisible(false);
            messagesHeader.setVisible(false);
            itemView.messagesBtn.setVisible(false);
        }


    }

    private Component createDetailsContent() {
        messagesHeader = new HorizontalLayout();
        messagesHeader.setVisible(false);
        messagesHeader.setWidthFull();
        messagesHeader.setPadding(true);
        messagesHeader.setAlignItems(FlexComponent.Alignment.BASELINE);
        messagesHeader.addClassName(LumoUtility.Gap.SMALL);
        H2 itemTitle = new H2("Logitech MX Keys");
        Span itemSubtitle = new Span("Keyboard");
        itemSubtitle.addClassName(LumoUtility.TextColor.SECONDARY);
        FlexLayout itemTitles = new FlexLayout(itemTitle, itemSubtitle);
        itemTitles.setFlexWrap(FlexLayout.FlexWrap.WRAP);
        itemTitles.setAlignItems(FlexComponent.Alignment.BASELINE);
        itemTitles.addClassName(LumoUtility.Gap.MEDIUM);

        Button closeMessagesBtn = new Button(LumoIcon.CROSS.create());
        closeMessagesBtn.addThemeVariants(ButtonVariant.LUMO_TERTIARY);
        closeMessagesBtn.getElement().setAttribute("aria-label", "Close");
        closeMessagesBtn.setTooltipText("Close");
        closeMessagesBtn.addClassName(LumoUtility.Margin.Left.AUTO);
        closeMessagesBtn.addClickListener(e -> {
            itemDetailLayout.setSplitterPosition(100);
        });

        messagesHeader.add(itemTitles, closeMessagesBtn);

        MessageList list = new MessageList();
        list.addClassNames(LumoUtility.Flex.GROW, LumoUtility.Padding.Top.SMALL, LumoUtility.Overflow.HIDDEN);
        list.setMinWidth("14rem");
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

        VerticalLayout detailsLayout = new VerticalLayout(messagesHeader, list, input);
        detailsLayout.addClassName(LumoUtility.Overflow.HIDDEN);
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

            Div itemAnchor = new Div(itemCard);
            // TODO Should be anchor element instead
            // TODO Active classname is never removed
            itemAnchor.addClassNames("item-anchor");
            itemAnchor.addClickListener(e -> {
                itemAnchor.addClassName("active");
                if (browserWidth < 800) {
                    setSplitterPosition(0);
                }
            });

            return itemAnchor;
        });

}
